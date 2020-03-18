package cn.lcl;

import cn.lcl.dao.UserMapper;
import cn.lcl.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;

@SpringBootTest
public class DeleteTest {


    @Autowired
    UserMapper userMapper;

    @Test
    public void deleteById() {
        int i = userMapper.deleteById(1240103137285607426L);
        System.out.println("删除条数" + i);
    }


    @Test
    public void deleteByMap() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("name", "大哥");
        hashMap.put("age", 19);

        int i = userMapper.deleteByMap(hashMap);

        System.out.println("删除条数" + i);

    }

    @Test
    public void deleteBatchIds() {
        int i = userMapper.deleteBatchIds(Arrays.asList(1239819061550923777L,
                1239915123695427586L));

        System.out.println("删除条数" + i);
    }

    @Test
    public void deleteWrapper() {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();

        lambdaQuery.eq(User::getAge,37).or().gt(User::getAge,41);

        int delete = userMapper.delete(lambdaQuery);

        System.out.println("删除条数" + delete);
    }





}
