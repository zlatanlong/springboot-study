package top.lclong;

import top.lclong.dao.UserMapper;
import top.lclong.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
