package top.xcphoenix.groupblog.utils;

import com.alibaba.fastjson.JSONObject;
import top.xcphoenix.groupblog.expection.BlogArgException;
import top.xcphoenix.groupblog.model.dao.BlogType;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author      xuanc
 * @date        2020/1/15 下午1:48
 * @version     1.0
 */ 
public class UrlUtil {

    private BlogType blogType;
    private Map<String, Object> userArgs;
    private String userZoneUrl;
    private String rssUrl;
    private static Pattern argPattern = Pattern.compile("\\{\\{(\\w+)}}");

    public UrlUtil(String userBlogArgs, BlogType blogType) {
        this.blogType = blogType;
        userArgs = JSONObject.parseObject(userBlogArgs).getInnerMap();
    }

    private String parseUrlByRule(String url) {
        Matcher argMatcher = argPattern.matcher(url);
        while (argMatcher.find()) {
            String value = (String) userArgs.get(argMatcher.group(1));
            if (value == null) {
                throw new BlogArgException("缺少规则(" + url + ")解析参数: " + argMatcher.group(1));
            }
            // 对替换字符进行转义
            url = url.replaceAll(Pattern.quote(argMatcher.group(0)), value);
        }
        return url;
    }

    public String getUserZoneUrl() {
        if (userZoneUrl != null) {
            return userZoneUrl;
        }
        userZoneUrl = blogType.getUserZoneRule();
        return parseUrlByRule(userZoneUrl);
    }

    public String getRssUrl() {
        if (rssUrl != null) {
            return rssUrl;
        }
        rssUrl = blogType.getRssRule();
        return parseUrlByRule(rssUrl);
    }

}
