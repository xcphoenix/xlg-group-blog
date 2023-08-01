package top.xcphoenix.groupblog.interceptor;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.xcphoenix.groupblog.annotation.AdminAuth;
import top.xcphoenix.groupblog.annotation.UserAuth;
import top.xcphoenix.groupblog.manager.dao.UserManager;
import top.xcphoenix.groupblog.model.Result;
import top.xcphoenix.groupblog.model.dao.Auth;
import top.xcphoenix.groupblog.model.dao.User;
import top.xcphoenix.groupblog.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author      xuanc
 * @date        2020/1/28 下午7:04
 * @version     1.0
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserManager userManager;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.matches(request.getMethod()) || !(handler instanceof HandlerMethod)) {
            return true;
        }

        // 从 http 请求头中取出 token
        String authorizationHeader = request.getHeader("Authorization");
        // 执行认证
        if (authorizationHeader == null  || !authorizationHeader.startsWith("Bearer ")) {
            response.getWriter().write(JSON.toJSONString(Result.error(Result.UN_LOGIN, "登录异常")));
            return false;
        }

        String token = authorizationHeader.substring(7);
        // 获取 token 中的 user id
        Long userId;
        try {
            Claim uid = JWT.decode(token).getClaim("uid");
            userId = uid.asLong();
        } catch (JWTDecodeException j) {
            response.getWriter().write(JSON.toJSONString(Result.error(Result.UN_LOGIN, "登录异常")));
            return false;
        }
        User user = userManager.checkUser(userId);
        if (user == null) {
            response.getWriter().write(JSON.toJSONString(Result.error(Result.UN_LOGIN, "用户未登录")));
            return false;
        }

        // 验证 token
        try {
            TokenUtil.verifyToken(token,user.getPassword());
        } catch (JWTVerificationException e) {
            response.getWriter().write(JSON.toJSONString(Result.error(Result.UN_LOGIN, "登录异常")));
            return false;
        }

        Auth auth = user.getAuthority();

        // 获取注解信息
        // 方法 > 类; admin > user
        Method method = ((HandlerMethod)handler).getMethod();

        int methodFlag = method.isAnnotationPresent(AdminAuth.class) ? 2 :
                (method.isAnnotationPresent(UserAuth.class) ? 1 : 0);
        if (methodFlag == 0) {
            methodFlag = method.getDeclaringClass().isAnnotationPresent(AdminAuth.class) ? 2 :
                    (method.getDeclaringClass().isAnnotationPresent(UserAuth.class) ? 1 : 0);
        }

        if ((methodFlag == 1 && auth.equals(Auth.USER)) ||
                (methodFlag == 2 && auth.equals(Auth.ADMIN)) || (methodFlag == 1 && auth.equals(Auth.ADMIN))) {
            return true;
        }

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(JSON.toJSONString(Result.error(Result.UN_AUTH, "权限拒绝")));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
