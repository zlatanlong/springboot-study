package top.lclong.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
        response.setHeader("Content-Type", "application/json");
        PrintWriter writer = response.getWriter();
        writer.write("error!!!" + authException.getMessage() + authException.getClass().getName());
        writer.flush();
        writer.close();
    }
}
