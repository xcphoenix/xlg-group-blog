package top.xcphoenix.groupblog.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import top.xcphoenix.groupblog.model.dao.User;

import java.util.Date;

@Slf4j
public class TokenUtil {

    private static final String SECRET_KEY = "XiYouLinuxGroup";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 过期时间，单位为毫秒，这里设置为 24 小时

    /**
     * 加密生成token
     * @param object 载体信息
     * @param maxAge  有效时长
     * @param secret  服务器私钥
     * @param <T>
     * @return
     */
    public static String getToken(User user) {
        try {
            final Algorithm signer = Algorithm.HMAC256(SECRET_KEY+user.getPassword());//生成签名
            String token = JWT.create()
                    .withIssuer(user.getPassword())
                    .withSubject("user")
                    .withClaim("uid", user.getUid())
                    .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                    .sign(signer);
            return token;
        } catch(Exception e) {
            log.error("生成token异常：",e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析验证token
     * @param token 加密后的token字符串
     * @return
     */
    public static Boolean verifyToken(String token,String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY+password);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            log.warn("校验失败");
            e.printStackTrace();
        }
        return false;
    }

}


