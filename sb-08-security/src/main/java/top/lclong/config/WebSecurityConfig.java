package top.lclong.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import top.lclong.service.UserDetailsServiceImpl;

import java.io.PrintWriter;

/**
 * @Author: zlatanlong
 * @Date: 2020/11/23 9:25
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Autowired
    MyAuthenticationProvider myAuthenticationProvider;
    @Autowired
    PersistentTokenRepository tokenRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // 去除 ROLE_ 前缀
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 密码加密方式
        return new BCryptPasswordEncoder();
    }


    @Bean
    public MyAuthenticationFilter myAuthenticationFilter() throws Exception {
        MyAuthenticationFilter myAuthenticationFilter = new MyAuthenticationFilter(tokenRepository);
        myAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        myAuthenticationFilter.setUserDetailsService(userDetailsServiceImpl);
        myAuthenticationFilter.setAuthenticationSuccessHandler((req, res, authentication) -> {
            res.setHeader("Content-Type", "application/json;charset:UTF-8");
            PrintWriter writer = res.getWriter();
            writer.write(new ObjectMapper().writeValueAsString(authentication));
            writer.flush();
            writer.close();
        });
        myAuthenticationFilter.setAuthenticationFailureHandler((req, res, exception) -> {
            PrintWriter writer = res.getWriter();
            res.setHeader("Content-Type", "application/json;charset:UTF-8");
            writer.write(exception.getMessage());
            writer.flush();
            writer.close();
        });
        return myAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http
                .csrf().disable()
                // 自定义的拦截器，处理json形式登录
                .addFilterBefore(myAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                // 未登录就要访问时的异常处理
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                .and()
                .authenticationProvider(myAuthenticationProvider)
                // form的拦截器是自带的，处理的form-data形式登录
                .formLogin()
                .loginProcessingUrl("/doLogin")
                .successHandler((req, res, authentication) -> {
                    PrintWriter writer = res.getWriter();
                    writer.print("Login success!");
                    writer.flush();
                    writer.close();
                })
                .failureHandler((req, res, exception) -> {
                    PrintWriter writer = res.getWriter();
                    writer.print("Login error!");
                    writer.flush();
                    writer.close();
                })
                .and()
                // 注销
                .logout().logoutUrl("/logout")
                .logoutSuccessHandler((req, res, authentication) -> {
                    res.setContentType("application/json;charset:UTF-8");
                    PrintWriter out = res.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(authentication));
                    out.flush();
                    out.close();
                })
                .and()
                .authorizeRequests((authorize) -> authorize
                        .antMatchers("/css/**", "/index").permitAll()
                        .antMatchers("/user/**").permitAll()
                )
                .rememberMe().tokenRepository(tokenRepository)
                .tokenValiditySeconds(TokenBasedRememberMeServices.TWO_WEEKS_S)
                .userDetailsService(userDetailsServiceImpl)

        ;
    }

}
