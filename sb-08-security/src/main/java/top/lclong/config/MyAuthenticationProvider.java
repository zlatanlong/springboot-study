package top.lclong.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import top.lclong.security.entity.User;
import top.lclong.security.repository.UserRepository;

/**
 * @Author: zlatanlong
 * @Date: 2020/11/24 15:46
 */
@Component
@Slf4j
public class MyAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public MyAuthenticationProvider(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    /**
     * 核心认证模块，验证用户密码等各种复杂的逻辑
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("--authenticate--");
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        User myTypeUser = userRepository.findByUsername(username);
        if (myTypeUser == null || !encoder.matches(password, myTypeUser.getPassword())) {
            throw new BadCredentialsException("用户名 或 密码错误！");
        }
        org.springframework.security.core.userdetails.User authSuccessUser
                = new org.springframework.security.core.userdetails.User(myTypeUser.getUsername(),
                myTypeUser.getPassword(), myTypeUser.getAuthorities());
        // 认证成功之后 传递一个Security的User类作为principal
        // details是通过filter 传过去的
        return new UsernamePasswordAuthenticationToken(authSuccessUser, authSuccessUser.getPassword(), authSuccessUser.getAuthorities());
    }



    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
