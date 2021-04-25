package top.lclong.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: zlatanlong
 * @Date: 2020/11/23 23:47
 */
@Slf4j
public class MyAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final PersistentTokenRepository tokenRepository;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        log.info("--doFilter--");
        super.doFilter(req, res, chain);
    }

    public MyAuthenticationFilter(PersistentTokenRepository repositoryConfig) {
        super(new AntPathRequestMatcher("/login", "POST"));
        this.tokenRepository = repositoryConfig;
    }

    /**
     * 我们写拦截器的目的就是从request的body中取json
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.info("--attemptAuthentication--");
        UsernamePasswordAuthenticationToken authRequest;
        ObjectMapper mapper = new ObjectMapper();
        Map result = null;
        try {
            result = mapper.readValue(request.getReader(), Map.class);
            String username = (String) result.get("username");
            String password = (String) result.get("password");
            // 记住我
            request.setAttribute(TokenBasedRememberMeServices.DEFAULT_PARAMETER,
                    result.get(TokenBasedRememberMeServices.DEFAULT_PARAMETER));
            authRequest = new UsernamePasswordAuthenticationToken(username, password);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            authRequest = new UsernamePasswordAuthenticationToken("", "");
        }
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);

    }

    void setUserDetailsService(UserDetailsService userDetailsService) {
        PersistentTokenBasedRememberMeServices tokenBasedRememberMeServices =
                new MyBasedRememberMeServices(
                        TokenBasedRememberMeServices.DEFAULT_PARAMETER,
                        userDetailsService,
                        tokenRepository);
        tokenBasedRememberMeServices.setTokenValiditySeconds(TokenBasedRememberMeServices.TWO_WEEKS_S);
        setRememberMeServices(tokenBasedRememberMeServices);
    }

    private void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}

/**
 * 记住我，因为自定义过滤器，json登录的话，
 * 要让security能拿到remember-me的参数是 true 是 false
 */
class MyBasedRememberMeServices extends PersistentTokenBasedRememberMeServices {
    public MyBasedRememberMeServices(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
        super(key, userDetailsService, tokenRepository);
    }

    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        // 记住我第二步
        return (boolean) request.getAttribute(TokenBasedRememberMeServices.DEFAULT_PARAMETER);
    }
}
