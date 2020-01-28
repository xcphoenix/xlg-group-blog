package top.xcphoenix.groupblog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/13 下午12:23
 */
public class HtmlUtil {

    private HtmlUtil() {}

    public static String delHtmlTag(String htmlStr) {
        String regExScript = "<script[^>]*?>[\\s\\S]*?</script>";
        String regExStyle = "<style[^>]*?>[\\s\\S]*?</style>";
        String regExHtml = "<[^>]+>";

        Pattern pScript = Pattern.compile(regExScript, Pattern.CASE_INSENSITIVE);
        Matcher mScript = pScript.matcher(htmlStr);
        htmlStr = mScript.replaceAll("");

        Pattern pStyle = Pattern.compile(regExStyle, Pattern.CASE_INSENSITIVE);
        Matcher mStyle = pStyle.matcher(htmlStr);
        htmlStr = mStyle.replaceAll("");

        Pattern pHtml = Pattern.compile(regExHtml, Pattern.CASE_INSENSITIVE);
        Matcher mHtml = pHtml.matcher(htmlStr);
        htmlStr = mHtml.replaceAll("");

        return htmlStr.trim();
    }

    public static String delSpace(String htmlStr) {
        htmlStr = htmlStr.replaceAll("\n", "");
        htmlStr = htmlStr.replaceAll("\t", "");
        return htmlStr.trim();
    }

}
