package cn.lcl.service.impl;

import cn.lcl.dao.UserMapper;
import cn.lcl.ov.UserTeacher;
import cn.lcl.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public Page<?> seletOA(Page<?> page, Integer id) {
        return userMapper.selectOAById(page, id);
    }

    @Override
    public Page<?> seletMap(Page<?> page, Integer id) {
        return userMapper.selectMapById(page, id);
    }

    @Override
    public Page<?> seletMap2(Page<?> page, Integer id) {
        return userMapper.selectMapById2(page, id);
    }

}
