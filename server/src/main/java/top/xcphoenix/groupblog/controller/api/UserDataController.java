package top.xcphoenix.groupblog.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.xcphoenix.groupblog.annotation.AdminAuth;
import top.xcphoenix.groupblog.annotation.UserAuth;
import top.xcphoenix.groupblog.expection.blog.BlogArgException;
import top.xcphoenix.groupblog.manager.dao.UserManager;
import top.xcphoenix.groupblog.model.Result;
import top.xcphoenix.groupblog.model.dao.BlogType;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.model.dto.BlogTypeParam;
import top.xcphoenix.groupblog.mybatis.mapper.BlogTypeMapper;
import top.xcphoenix.groupblog.mybatis.mapper.CategoryMapper;
import top.xcphoenix.groupblog.service.api.BlogTypeService;
import top.xcphoenix.groupblog.service.api.UserService;
import top.xcphoenix.groupblog.utils.UrlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author      xuanc
 * @date        2020/1/28 下午2:42
 * @version     1.0
 */
@Slf4j
@UserAuth
@RestController
@RequestMapping("/api/user")
@PropertySource(value = "${config-dir}/processor.properties",encoding = "utf-8")
public class UserDataController {
    private UserService userService;
    private UserManager userManager;
    private BlogTypeService blogTypeService;
    private CategoryMapper categoryMapper;
    private BlogTypeMapper blogTypeMapper;
    @Value("${picture.service-name}")
    private String serviceName;
    @Value("${picture.address}")
    private String pictureAddress;


    public UserDataController(UserService userService,
                              UserManager userManager,
                              BlogTypeService blogTypeService,
                              CategoryMapper categoryMapper,
                              BlogTypeMapper blogTypeMapper) {
        this.userService = userService;
        this.userManager = userManager;
        this.blogTypeService = blogTypeService;
        this.categoryMapper = categoryMapper;
        this.blogTypeMapper = blogTypeMapper;
    }

    @GetMapping("/data")
    public Result<User> getUserData(@RequestHeader("Authorization") String token, HttpServletResponse response) {
        token = token.substring(7);
        Long uid = JWT.decode(token).getClaim("uid").asLong();
        User user = userService.getUserData(uid);
        if (user == null) {
            return Result.error(-1, "用户不存在");
        }
        if(user.getAvatarUrl() == null || "null".equals(user.getAvatarUrl())){
            user.setAvatarUrl(null);
            return Result.success(null, user);
        }
        user.setAvatarUrl("https://"+serviceName+user.getAvatarUrl());

        // 设置响应头,使其不缓存
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Expires", "0");

        return Result.success(null,user);
    }

    @PutMapping("/data")
    public Result<Void> updateUserData(@RequestHeader("Authorization") String token,
                                       @RequestBody User user) {
        token = token.substring(7);
        Long uid = JWT.decode(token).getClaim("uid").asLong();
        user.setUid(uid);
        if (user.getSignature() != null && user.getSignature().length() >= 25) {
            return Result.error(-1, "个性签名超出长度限制");
        }
        userService.updateUserData(user);
        return Result.success("修改成功", null);
    }

    @GetMapping("/blog/params")
    public Result<User> getUserBlogTypeData(@RequestHeader("Authorization") String token) {
        token = token.substring(7);
        Long uid = JWT.decode(token).getClaim("uid").asLong();
        User user = userManager.getUserBlogArgs(uid);
        return Result.success(user);
    }

    @PutMapping("/blog/params")
    public Result<Void> updateUserBlogParam(@RequestHeader("Authorization") String token,
                                            @RequestBody BlogTypeParam blogTypeParam) {
        token = token.substring(7);
        Long uid = JWT.decode(token).getClaim("uid").asLong();
        String needParams = blogTypeService.getParams(blogTypeParam.getBlogType());
        if (needParams == null) {
            return Result.error(-1, "无效的博客类型");
        }

        String userBlogParams = blogTypeService.validateParams(uid, blogTypeParam, needParams);
        if (userBlogParams == null) {
            return Result.error(-1, "缺少必要的参数");
        }
        userService.updateUserBlogParams(uid, blogTypeParam.getBlogType(), userBlogParams);
        return Result.success("修改成功", null);
    }

    @PutMapping("/passwd")
    public Result<Void> updateUserPasswd(@RequestHeader("Authorization") String token,
                                         @RequestBody JSONObject jsonObject) {
        token = token.substring(7);
        Long uid = JWT.decode(token).getClaim("uid").asLong();
        String currPasswd = jsonObject.getString("currentPasswd");
        String newPasswd = jsonObject.getString("newPasswd");

        if (currPasswd == null || newPasswd == null) {
            return Result.error(-1, "缺少必要的数据");
        }
        if (!userService.validatePasswd(newPasswd)) {
            return Result.error(-1, "密码不符合要求");
        }

        if (userService.checkPasswd(uid, currPasswd)) {
            userService.updatePasswd(uid, newPasswd);
            return Result.success("修改成功", null);
        } else {
            return Result.error(-1, "密码错误");
        }

    }

    @GetMapping("/grade")
    public Result<List<String>> getGrade() {
        List<String> allCategory = categoryMapper.getGrade();
        if (allCategory == null) {
            return Result.error(-1, "查询出错");
        }
        return Result.success(allCategory);
    }

    @GetMapping("/infos/{grade}")
    public Result<List<User>> getUsersInfo(@PathVariable("grade") String grade) throws BlogArgException {
        Long cid = categoryMapper.getIdByGrade(grade);
        List<User> users = userManager.getUsersInfo(cid);

        for (User user : users) {
            BlogType blogType = blogTypeMapper.getBlogTypeByTid(user.getBlogType());
            switch (user.getBlogType()) {
                case 1:
                    UrlUtil urlUtil = new UrlUtil(user.getBlogArg(), blogType);
                    String overviewUrl = urlUtil.getOverviewUrl();
                    user.setBlogArg(overviewUrl);
                    break;
                case 2:
                case 3:
                    urlUtil = new UrlUtil(user.getBlogArg(), blogType);
                    String feedUrl = urlUtil.getFeedUrl();
                    feedUrl = feedUrl.substring(0, feedUrl.lastIndexOf("/"));
                    user.setBlogArg(feedUrl);
                    break;
                case 4:
                case 5:
                    urlUtil = new UrlUtil(user.getBlogArg(), blogType);
                    overviewUrl = urlUtil.getOverviewUrl();
                    user.setBlogArg(overviewUrl);
                    break;
                default:
                    break;
            }
        }

        return Result.success(users);
    }

    @PostMapping("/image")
    public Result<Map> uploadImage(@RequestHeader("Authorization") String token,
                                   @RequestParam(value = "image") MultipartFile image) {
        token = token.substring(7);
        Long uid = JWT.decode(token).getClaim("uid").asLong();
        if (image != null && !image.isEmpty()) {
            try {
                // 获取文件的原始名称
                String fileName = image.getOriginalFilename();
                //判断文件类型
                if(fileName == null || !fileName.endsWith(".jpg")) {
                    return Result.error(-1,"文件类型不对");
                }
                // 获取文件的扩展名
                String fileExt;
                fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                // 生成新的文件名
                String newFileName = uid+"-avatar" + "." + fileExt;
                //若目录不存在则创建目录
                File folder = new File(pictureAddress+"/avatar/");
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                File file = new File(folder.getAbsolutePath(), newFileName);
                log.info(file.getAbsolutePath());
                image.transferTo(file);
                String url = "/avatar/" + newFileName;
                userService.updateUserAvatar(uid,url);
                // 返回上传成功的结果
                Map<String, Object> data = new HashMap<>();
                data.put("src","https://" + serviceName + url);
                return Result.success("上传成功",data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Result.error(-1,"上传失败");
    }

    @AdminAuth
    @PostMapping("/addMember")
    public Result<Void> addMember(@RequestBody User user) throws MalformedURLException {
        if(user.getGrade() == null || user.getGrade().length() != 4){
            return Result.error(-1,"错误的信息");
        }
        String arg;
        if(user.getBlogArg() == null || "".equals(user.getBlogArg())){
            user.setBlogType(5);
        }
        switch (user.getBlogType()){
            case 1:
                arg = "\"username\": ";
                if (user.getBlogArg().startsWith("https://blog.csdn.net")){
                    URL url = new URL(user.getBlogArg());
                    String path = url.getPath().substring(1);
                    int end = path.indexOf('/');
                    if(end == -1){
                        arg += "\"" + path + "\"";
                    }else{
                        arg += "\"" + path.substring(0,end) + "\"";
                    }
                    user.setBlogArg("{" + arg +"}");
                }else{
                    return Result.error(-1,"错误的url地址");
                }
                break;
            case 2:
                arg = "\"atomUrl\": ";
                arg += "\"" + user.getBlogArg() + "\"";
                user.setBlogArg("{" + arg +"}");
                break;
            case 3:
                arg = "\"rss\": ";
                arg += "\"" + user.getBlogArg() + "\"";
                user.setBlogArg("{" + arg +"}");
                break;
            case 4:
                arg = "\"github\": ";
                arg += "\"" + user.getBlogArg() + "\"";
                user.setBlogArg("{" + arg +"}");
                break;
            case 5:
                user.setBlogArg("{}");
                break;
            default:
                log.info("不进行任何操作(暂时至少...)");
                return Result.error(-1,"错误的类型");
        }
        user.setLastPubTime(new Timestamp(0));
        String grade = user.getGrade() + '级';
        // 没有该年级就创建该年级
        Long cid = categoryMapper.getIdByGrade(grade);
        if(cid == null){
            categoryMapper.createNewGrade(grade);
            cid = categoryMapper.getIdByGrade(grade);
        }

        user.setCategoryId(Math.toIntExact(cid));
        userManager.createNewUser(user);

        return Result.success(null);
    }
}