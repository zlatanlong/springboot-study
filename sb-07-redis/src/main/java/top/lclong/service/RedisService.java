package top.lclong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.lclong.pojo.User;

import java.io.Serializable;
import java.util.Random;

/**
 * @Author: zlatanlong
 * @Date: 2020/9/30 16:03
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public void saveUser() {
        final String KEY = "testobj";
        User user = new User();
        Random random = new Random();
        user.setId(random.nextInt());
        user.setUserName("hhh");
        user.setUserSex("n");
        redisTemplate.opsForValue().set(KEY,user);

        Boolean bool = redisTemplate.hasKey(KEY);

        System.out.println("has already exist this key?"+bool);

        System.out.println(redisTemplate.opsForValue().get(KEY));
    }

}
