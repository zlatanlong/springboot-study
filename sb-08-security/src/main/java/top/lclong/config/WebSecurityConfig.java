package top.lclong.config;

import lombok.RequiredArgsConstructor;
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
    private final MyAuthenticationProvider myAuthenticationProvider;
    private final MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl);
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
        MyAuthenticationFilter myAuthenticationFilter = new MyAuthenticationFilter();
        myAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        myAuthenticationFilter.setAuthenticationSuccessHandler((req, res, authentication) -> {
            res.setHeader("Content-Type","application/json");
            PrintWriter writer = res.getWriter();
            writer.print(authentication);
            writer.flush();
            writer.close();
        });
        myAuthenticationFilter.setAuthenticationFailureHandler((req, res, exception) -> {
            PrintWriter writer = res.getWriter();
            res.setHeader("Content-Type","application/json");
            writer.print(exception.getMessage());
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
                .addFilterBefore(myAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                .and()
                .authenticationProvider(myAuthenticationProvider)
                .formLogin()
                .successHandler((req, res, authentication) -> {
                    PrintWriter writer = res.getWriter();
                    writer.print("登录成功！");
                    writer.flush();
                    writer.close();
                })
                .failureHandler((req, res, exception) -> {
                    PrintWriter writer = res.getWriter();
                    writer.print("登录失败！");
                    writer.flush();
                    writer.close();
                })
                .and()
                .authorizeRequests((authorize) -> authorize
                        .antMatchers("/css/**", "/index").permitAll()
                        .antMatchers("/user/**").permitAll()
                )
        ;
    }

}
