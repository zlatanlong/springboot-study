package cn.lcl;

import cn.lcl.dao.UserMapper;
import cn.lcl.entity.User;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class SimpleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void select() {
        List<User> list = userMapper.selectList(null);

//        Assert.assertEquals(5,list.size());

        list.forEach(System.out::println);
    }

    @Test
    void insert() {
        User user = new User();
        user.setName("三didi");
        user.setAge(37);
        user.setEmail("22@qq.com");
        user.setCreateTime(LocalDateTime.now());

        System.out.println(LocalDateTime.now());
        System.out.println(userMapper.insert(user));
    }

    @Test
    void other() {
        System.out.println(LocalDateTime.now());
    }

}
