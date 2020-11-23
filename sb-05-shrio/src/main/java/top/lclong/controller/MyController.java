package top.lclong.controller;

import top.lclong.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class MyController {

    @Autowired
    UserService userService;

    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @GetMapping("/update")
    public String update() {
        return "update";
    }

    @GetMapping("/index")
    public String toIndex() {
        return "home";
    }

    @GetMapping("/noauth")
    public String noauth() {
        return "noauth";
    }

    @PostMapping("/login")
    public String login(@RequestBody HashMap<String, String> loginMap) {
        String username = loginMap.get("username");
        String password = loginMap.get("password");
        System.out.println(loginMap);

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token); // 执行登录的方法，如果没有异常说明没问题了
        } catch (UnknownAccountException e) {
            return "用户名不存在";
        }catch (IncorrectCredentialsException e) {
            return "密码不对";
        }

        return "login";
    }


}
