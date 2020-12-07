package top.lclong.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import redis.clients.jedis.JedisCluster;
import top.lclong.pojo.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @Author: zlatanlong
 * @Date: 2020/9/30 16:26
 */
@SpringBootTest
class RedisServiceTest {

    @Autowired
    private JedisCluster jedisCluster;
    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void test() {
//        jedisCluster.set("userName", "zhangsan");
        String userName = jedisCluster.get("m1");
        System.out.println("userName======" + userName);
    }

    @Test
    public void test2() {
        Object m1 =  redisTemplate.opsForValue().get("m1");
        if ((m1 instanceof String)) {
            System.out.println("123");
        }

        System.out.println(m1);
    }

    @Test
    public void test3() {
        User user = new User();
        user.setId(1);
        user.setUserName("zhangsn");
        user.setUserSex("nan");
        ValueOperations<String,User> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("user", user);
        System.out.println(valueOperations.get("user"));
    }
    @Test
    public void test4() {
        User user = new User();
        user.setId(1);
        user.setUserName("zhangsn");
        user.setUserSex("nan");
    }
}