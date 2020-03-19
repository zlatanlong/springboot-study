package cn.lcl.service.impl;

import cn.lcl.dao.UserMapper;
import cn.lcl.pojo.User;
import cn.lcl.service.UserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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
