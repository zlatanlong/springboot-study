package top.lclong;

import top.lclong.dao.UserMapper;
import top.lclong.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class SimpleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void select() {
        List<Map<String,Object>> list = userMapper.selectMaps(null);

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
