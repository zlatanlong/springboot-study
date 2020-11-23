package top.lclong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lclong.domain.User;
import top.lclong.service.UserService;

import java.util.List;

/**
 * @Author: zlatanlong
 * @Date: 2020/11/23 21:26
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping("/init/{name}")
    public User init(@PathVariable String name) {
        User user = new User();
        user.setName(name);
        user.setUsername(name);
        user.setPassword("123123");
        return userService.save(user);
    }

    @RequestMapping("/all")
    public List<User> all() {
        return userService.findAll();
    }
}
