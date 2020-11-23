package top.lclong.service.impl;

import top.lclong.dao.UserMapper;
import top.lclong.pojo.User;
import top.lclong.service.UserService;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User queryUserByName(String name) {
        LambdaQueryChainWrapper<User> eq = new LambdaQueryChainWrapper<User>(userMapper).eq(User::getName, name);

        return new LambdaQueryChainWrapper<User>(userMapper).eq(User::getName, name).one();
    }
}
