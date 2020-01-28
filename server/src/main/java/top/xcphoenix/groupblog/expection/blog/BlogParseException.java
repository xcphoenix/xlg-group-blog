package top.xcphoenix.groupblog.expection.blog;

/**
 * @author      xuanc
 * @date        2020/1/27 上午11:16
 * @version     1.0
 */ 
public class BlogParseException extends Exception {

    public BlogParseException(String message) {
        super(message);
    }

    public BlogParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
