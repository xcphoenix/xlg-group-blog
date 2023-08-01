package top.xcphoenix.groupblog.service.api.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import top.xcphoenix.groupblog.mybatis.mapper.BlogTypeMapper;
import top.xcphoenix.groupblog.model.dao.BlogType;
import top.xcphoenix.groupblog.model.dto.BlogTypeParam;
import top.xcphoenix.groupblog.service.api.BlogTypeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author      xuanc
 * @date        2020/1/27 下午5:54
 * @version     1.0
 */
@Service
public class BlogTypeServiceImpl implements BlogTypeService {

    private BlogTypeMapper blogTypeMapper;

    public BlogTypeServiceImpl(BlogTypeMapper blogTypeMapper) {
        this.blogTypeMapper = blogTypeMapper;
    }

    @Override
    public String getParams(int typeId) {
        BlogType blogType = blogTypeMapper.getBlogTypeByTid(typeId);
        if (blogType == null) {
            return null;
        }
        return blogType.getNeedArg();
    }

    @Override
    public String validateParams(long uid, BlogTypeParam blogTypeParam, String needParams) {
        JSONArray needParamArray = JSONObject.parseArray(needParams);
        Map<String, String> userBlogParams = new HashMap<>();
        Map<String, String> blogParamMap = blogTypeParam.getParams();

        for (int i = 0; i < needParamArray.size(); i++) {
            String param = needParamArray.getJSONObject(i).getString("param");
            String value = blogParamMap.get(param);
            if (value == null) {
                return null;
            }
            userBlogParams.put(param, value);
        }

        return JSON.toJSONString(userBlogParams);
    }

    @Override
    public List<BlogType> getType() {
        return blogTypeMapper.getBlogType();
    }

}
