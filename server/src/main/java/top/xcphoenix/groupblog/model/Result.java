package top.xcphoenix.groupblog.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;

/**
 * @author      xuanc
 * @date        2020/1/27 下午6:19
 * @version     1.0
 */
@Data
@NoArgsConstructor
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public Result(T data) {
        this.code = 0;
        this.data = data;
    }
    public Result(String msg, T data) {
        this.code = 0;
        this.msg = msg;
        this.data = data;
    }
    public Result(int code) {
        this.code = code;
    }
    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(msg, data);
    }

    public static <T> Result<T> error(int errorCode) {
        return new Result<>(errorCode);
    }

    public static <T> Result<T> error(int errorCode, String msg) {
        return new Result<>(errorCode, msg);
    }

}
