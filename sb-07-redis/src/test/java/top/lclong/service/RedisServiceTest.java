package top.lclong.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
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
    RedisService redisService;
    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    // inject the template as ListOperations
    // can also inject as Value, Set, ZSet, and HashOperations


    @Test
    public void test() {
        redisService.saveUser();
    }

    @Test
    public void testList() {
        List<Integer> list = new ArrayList<>();
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        redisTemplate.opsForList().leftPush("testlist", "h1");
        redisTemplate.opsForList().leftPush("testlist", "h2");
        redisTemplate.opsForList().leftPush("testlist", "h3");
        redisTemplate.opsForList().leftPush("testlist", "h4");
    }

    @Test
    public void testHash() {
        User user = new User();
        Random random = new Random();
        user.setId(random.nextInt());
        user.setUserName("hhh");
        user.setUserSex("n");
        redisTemplate.opsForHash().put("testhash", "id", user.getId());
        redisTemplate.opsForHash().put("testhash", "userName", user.getUserName());
        redisTemplate.opsForHash().put("testhash", "userSex", user.getUserSex());
    }
}