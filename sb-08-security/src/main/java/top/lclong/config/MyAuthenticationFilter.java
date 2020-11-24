package top.lclong.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: zlatanlong
 * @Date: 2020/11/23 23:47
 */
public class MyAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    public MyAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        ObjectMapper mapper = new ObjectMapper();
        Map result = null;
        try {
            result = mapper.readValue(request.getReader(), Map.class);
            String username = (String) result.get("username");
            String password = (String) result.get("password");
            authRequest = new UsernamePasswordAuthenticationToken(username, password);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            authRequest = new UsernamePasswordAuthenticationToken("", "");
        }
        setDetails(request,authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);

    }

    private void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
