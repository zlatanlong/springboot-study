package top.lclong.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import top.lclong.pojo.User;

/**
 * @Author: zlatanlong
 * @Date: 2020/9/30 16:26
 */
@SpringBootTest
class RedisServiceTest {

//    @Autowired
//    private JedisCluster jedisCluster;
@Qualifier("redisTemplate")
@Autowired
private RedisTemplate<Object, Object> redisTemplate;


    @Test
    public void test() {
//        jedisCluster.set("userName", "zhangsan");
//        String userName = jedisCluster.get("m1");
//        System.out.println("userName======" + userName);
    }

    @Test
    public void getValue() {
        Object m1 = redisTemplate.opsForValue().get("m1");
        if ((m1 instanceof String)) {
            System.out.println("123");
        }

        System.out.println(m1);
    }

    @Test
    public void saveOneObject() {
        User user = new User();
        user.setId(1);
        user.setUserName("zhangsn");
        user.setUserSex("nan");
        User user1 = new User();
        user1.setId(2);
        user1.setUserName("lsi");
        user1.setUserSex("asdasdsadasd");
        user.setUsers(user1);
        redisTemplate.opsForValue().set("user:3", user);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }
    @Test
    public void test4() {
        User user = new User();
        user.setId(1);
        user.setUserName("zhangsn");
        user.setUserSex("nan");
    }
}
