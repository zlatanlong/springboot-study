package top.lclong.controller;

import top.lclong.entity.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "API接口")
public class HelloController {


    @ApiResponse(message = "return a hello", code = 200)
    @GetMapping(value = "/hello")
    public String hello() {
        return "hello";
    }

    @ApiOperation(value = "你好2")
    @GetMapping(value = "/hello2")
    public User hello2(@ApiParam("名字") @RequestParam String te2) {
        return new User(te2, "123");
    }

    @ApiOperation(value = "你好3")
    @GetMapping(value = "/hello3/{id}")
    @ApiImplicitParam(value = "用户id", name = "id")
    public User hello3(@PathVariable("id") String parameter) {
        return new User(parameter, "123");
    }

    @PostMapping("/hellopost")
    public User hellopost(@ApiParam("用户实体") @RequestBody User u1) {
        return u1;
    }

}
