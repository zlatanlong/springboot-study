package cn.lcl;

import cn.lcl.dao.UserMapper;
import cn.lcl.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class FillTest {


    @Autowired
    UserMapper userMapper;


    @Test
    void insert() {
        User user = new User();
        user.setName("三didi");
        user.setAge(37);
        user.setEmail("22@qq.com");

        System.out.println(userMapper.insert(user));
    }

    @Test
    public void updateByIds() {
        User user = new User();
        user.setAge(27);
        user.setEmail("wangtianff@qq.com");
        user.setId(1088248166370832385L);

        int rows = userMapper.updateById(user);

        System.out.println("影响记录数" + rows);

    }


}
