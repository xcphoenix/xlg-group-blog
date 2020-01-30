package top.xcphoenix.groupblog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xuanc
 * @version 1.0
 * @date 2020/1/13 下午12:23
 */
public class HtmlUtil {

    private static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?</script>";
    private static final String REGEX_STYLE = "<style[^>]*?>[\\s\\S]*?</style>";
    private static final String REGEX_TAG = "<[^>]+>";

    private static final Pattern PATTERN_SCRIPT = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_STYLE = Pattern.compile(REGEX_STYLE, Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_TAG = Pattern.compile(REGEX_TAG, Pattern.CASE_INSENSITIVE);

    private HtmlUtil() {}

    public static String htmlToText(String htmlStr) {
        Matcher mScript = PATTERN_SCRIPT.matcher(htmlStr);
        htmlStr = mScript.replaceAll("");

        Matcher mStyle = PATTERN_STYLE.matcher(htmlStr);
        htmlStr = mStyle.replaceAll("");

        Matcher mHtml = PATTERN_TAG.matcher(htmlStr);
        htmlStr = mHtml.replaceAll("");

        return htmlStr.trim();
    }

    public static String htmlToCompressedText(String htmlStr) {
        return delSpace(htmlToText(htmlStr));
    }

    public static String delSpace(String htmlStr) {
        htmlStr = htmlStr.replaceAll("\n", "");
        htmlStr = htmlStr.replaceAll("\t", "");
        htmlStr = htmlStr.replaceAll("\\s+", " ");
        return htmlStr.trim();
    }

}
