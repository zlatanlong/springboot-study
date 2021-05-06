package top.lclong.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import top.lclong.config.bean.JWTProperties;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: zlatanlong
 * @Date: 2021/4/19 20:07
 */
@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends GenericFilter {

    private final JWTProperties jwtProperties;
    private final TokenProvider tokenProvider;

    /**
     * 从请求中获取到token
     * @param request httpRequest
     * @param response httpResponse
     * @param chain filterChain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获得我们token对应开头的
        String token = resolveToken((HttpServletRequest) request);
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
//            如果成功获取了就保存在SecurityContextHolder中
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("set Authentication to security context for '{}'", authentication.getName());
        } else {
            log.info("no valid JWT token found");
        }
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProperties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtProperties.getTokenStartWith())) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
