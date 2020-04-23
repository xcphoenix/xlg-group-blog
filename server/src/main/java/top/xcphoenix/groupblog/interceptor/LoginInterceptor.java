package top.xcphoenix.groupblog.interceptor;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.xcphoenix.groupblog.annotation.AdminAuth;
import top.xcphoenix.groupblog.annotation.UserAuth;
import top.xcphoenix.groupblog.model.Result;
import top.xcphoenix.groupblog.model.dao.Auth;
import top.xcphoenix.groupblog.model.dao.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * @author      xuanc
 * @date        2020/1/28 下午7:04
 * @version     1.0
 */ 
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User sUser = (User) session.getAttribute("user");
        boolean isAuth = false;

        if (HttpMethod.OPTIONS.matches(request.getMethod()) || !(handler instanceof HandlerMethod)) {
            return true;
        }

        if (sUser != null) {
            isAuth = true;
            Auth auth = sUser.getAuthority();

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
                    (methodFlag == 2 && auth.equals(Auth.ADMIN))) {
                return true;
            }
        }

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String resp = JSON.toJSONString(
                isAuth ? Result.error(Result.UN_AUTH, "权限拒绝")
                        : Result.error(Result.UN_LOGIN, "用户未登录"));
        response.getWriter().write(resp);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
