package cn.lcl;

import cn.lcl.dao.UserMapper;
import cn.lcl.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

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
        user.setName("向后");
        user.setAge(19);
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
