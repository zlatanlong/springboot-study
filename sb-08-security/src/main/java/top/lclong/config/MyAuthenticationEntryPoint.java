package top.lclong.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: zlatanlong
 * @Date: 2020/11/24 18:04
 */
@Configuration
public class MyAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    public MyAuthenticationEntryPoint() {
        super("/login");
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException == null ? "Unauthorized" : authException.getMessage());
    }
}

@Component
class AppErrorAttribute extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, options); // 这里参数可以配置为false
        map.remove("trace");
        return super.getErrorAttributes(webRequest, options);
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace); // 这里参数可以配置为false
        // 禁止trace 覆盖
        map.remove("trace");
        return map;
    }

}