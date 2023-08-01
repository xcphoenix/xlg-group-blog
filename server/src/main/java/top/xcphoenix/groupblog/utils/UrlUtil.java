package top.xcphoenix.groupblog.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import top.xcphoenix.groupblog.expection.blog.BlogArgException;
import top.xcphoenix.groupblog.model.dao.BlogType;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author      xuanc
 * @date        2020/1/15 下午1:48
 * @version     1.0
 */ 
@Slf4j
public class UrlUtil {

    private BlogType blogType;
    private Map<String, Object> userArgs;
    private String overviewUrl;
    private String feedUrl;
    private static Pattern argPattern = Pattern.compile("\\{\\{(\\w+)}}");

    public UrlUtil(String userBlogArgs, BlogType blogType) {
        this.blogType = blogType;
        userArgs = JSONObject.parseObject(userBlogArgs).getInnerMap();
    }

    private String parseUrlByRule(String url) throws BlogArgException {
        if (url == null) {
            throw new BlogArgException("invalid url");
        }
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

    public String getOverviewUrl() throws BlogArgException {
        if (overviewUrl != null) {
            return overviewUrl;
        }
        overviewUrl = blogType.getOverviewRule();
        return parseUrlByRule(overviewUrl);
    }

    public String getFeedUrl() throws BlogArgException {
        if (feedUrl != null) {
            return feedUrl;
        }
        feedUrl = blogType.getFeedRule();
        return parseUrlByRule(feedUrl);
    }

    public static String getAuthor(String overviewUrl) throws MalformedURLException {
        URL url = new URL(overviewUrl);
        String author = url.getHost();
        if(author.startsWith("blog.")){
            author = author.substring(5);
        }
        if(author.endsWith(".github.io")){
            author = author.substring(0,author.length()-".github.io".length());
        }else {
            author = author.substring(0, author.lastIndexOf('.'));
        }

        return author;
    }

    public static boolean imgUrlValidate(String imageUrl) {
        try {
            // 开始查找
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            log.warn(imageUrl+"图片检测结果："+responseCode);
            return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_MOVED_PERM;
        } catch (Exception e) {
            return false;
        }
    }
}
