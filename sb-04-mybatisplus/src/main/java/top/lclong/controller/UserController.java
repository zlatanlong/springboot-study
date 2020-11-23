package top.lclong.controller;

import top.lclong.ov.UserTeacher;
import top.lclong.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Api(tags = "API接口")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "根据OV模型返回", response = UserTeacher.class, notes = "一端注解")
    @GetMapping(value = "/ov/{teacherId}/{page}/{size}")
    public Page<?> getOA(@PathVariable Integer teacherId,
                      @PathVariable Integer page,
                      @PathVariable Integer size){
        return userService.seletOA(new Page<>(page,size), teacherId);
    }

    @GetMapping(value = "/map/{teacherId}/{page}/{size}")
    public Page<?> getMap(@PathVariable Integer teacherId,
                      @PathVariable Integer page,
                      @PathVariable Integer size){
        return userService.seletMap(new Page<UserTeacher>(page,size), teacherId);
    }

    @GetMapping(value = "/map2/{teacherId}/{page}/{size}")
    public Page<?> getMap2(@PathVariable Integer teacherId,
                      @PathVariable Integer page,
                      @PathVariable Integer size){
        return userService.seletMap2(new Page<>(page,size), teacherId);
    }
}
