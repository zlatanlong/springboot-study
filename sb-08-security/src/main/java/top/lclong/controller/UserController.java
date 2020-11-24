package top.lclong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lclong.domain.User;
import top.lclong.service.UserDetailsServiceImpl;

import java.util.List;

/**
 * @Author: zlatanlong
 * @Date: 2020/11/23 21:26
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @RequestMapping("/init/{name}")
    public User init(@PathVariable String name) {
        User user = new User();
        user.setName(name);
        user.setUsername(name);
        user.setPassword("123123");
        return userDetailsServiceImpl.save(user);
    }

    @RequestMapping("/all")
    public List<User> all() {
        return userDetailsServiceImpl.findAll();
    }

    @GetMapping
    public Authentication get() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
