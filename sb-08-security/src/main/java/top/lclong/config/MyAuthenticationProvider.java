package top.lclong.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import top.lclong.domain.User;
import top.lclong.repository.UserRepository;

/**
 * @Author: zlatanlong
 * @Date: 2020/11/24 15:46
 */
@Component
@RequiredArgsConstructor
public class MyAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        User user = userRepository.findByUsername(username);
        if (user == null || !encoder.matches(password,user.getPassword())) {
            throw new BadCredentialsException("用户名 或 密码错误！");
        }
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(),user.getAuthorities());
    }



    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
