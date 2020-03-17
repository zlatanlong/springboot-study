package cn.lcl;

import cn.lcl.dao.UserMapper;
import cn.lcl.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
public class SimpleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void select() {
        List<User> list = userMapper.selectList(null);

        Assert.assertEquals(5,list.size());

        list.forEach(System.out::println);

    }
}
