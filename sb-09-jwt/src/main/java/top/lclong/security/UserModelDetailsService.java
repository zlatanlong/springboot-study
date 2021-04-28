package top.lclong.security;

import cn.hutool.core.lang.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.lclong.security.domain.User;
import top.lclong.security.repository.UserRepository;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserModelDetailsService implements UserDetailsService {

   private final Logger log = LoggerFactory.getLogger(UserModelDetailsService.class);

   private final UserRepository userRepository;

   public UserModelDetailsService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String username) {
      log.debug("Authenticating user '{}'", username);

      // 邮箱查询
      if (Validator.isEmail(username)) {
         return userRepository.findOneWithAuthoritiesByEmailIgnoreCase(username)
            .map(user -> createSpringSecurityUser(username, user))
            .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " was not found in the database"));
      }

      // 用户名查询
      String lowercaseLogin = username.toLowerCase(Locale.ENGLISH);
      return userRepository.findOneWithAuthoritiesByUsername(lowercaseLogin)
         .map(user -> createSpringSecurityUser(lowercaseLogin, user))
         .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));

   }

   /**
    * 将数据库中的user转化为SpringSecurity中的user
    * @param lowercaseUsername
    * @param user
    * @return
    */
   private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseUsername, User user) {
      if (!user.isActivated()) {
         throw new UserNotActivatedException("User " + lowercaseUsername + " was not activated");
      }
      List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
         .map(authority -> new SimpleGrantedAuthority(authority.getName()))
         .collect(Collectors.toList());
      return new org.springframework.security.core.userdetails.User(user.getUsername(),
         user.getPassword(),
         grantedAuthorities);
   }
}
