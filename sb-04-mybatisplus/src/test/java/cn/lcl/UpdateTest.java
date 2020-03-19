package cn.lcl;

import cn.lcl.dao.UserMapper;
import cn.lcl.entity.User;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UpdateTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void updateByIds() {
        User user = new User();
        user.setAge(27);
        user.setEmail("wangtianff@qq.com");
        user.setId(1240174663691149313L);

        int rows = userMapper.updateById(user);

        System.out.println("影响记录数" + rows);

    }


    /**
     * DEBUG==>  Preparing: UPDATE user SET age=?, email=? WHERE (name = ? AND age = ?)
     * DEBUG==> Parameters: 29(Integer), liyiwei@qq.com(String), 李艺伟(String), 28(Integer)
     * DEBUG<==    Updates: 1
     * 影响记录数1
     */
    @Test
    public void updateByWrapper() {
        UpdateWrapper<User> updateWrapper = Wrappers.update();

        updateWrapper.eq("name", "李艺伟").eq("age", 28);
        User user = new User();
        user.setAge(29);
        user.setEmail("liyiwei@qq.com");

        int rows = userMapper.update(user, updateWrapper);

        System.out.println("影响记录数" + rows);

    }

    @Test
    public void updateByWrapper2() {
        User user = new User();
        user.setAge(30);
        user.setEmail("liyiwei@qq.com");

        UpdateWrapper<User> updateWrapper = Wrappers.update(user);

        User newUser = new User();
        newUser.setAge(29);

        int rows = userMapper.update(newUser, updateWrapper);

        System.out.println("影响记录数" + rows);

    }

    @Test
    public void updateByWrapper3() {
        UpdateWrapper<User> updateWrapper = Wrappers.update();

        updateWrapper.eq("name", "李艺伟").eq("age", 28)
                .set("age", 30);

        int rows = userMapper.update(null, updateWrapper);

        System.out.println("影响记录数" + rows);

    }

    @Test
    public void updateByWrapperLambda() {
        LambdaUpdateWrapper<User> lambdaUpdate = Wrappers.lambdaUpdate();

        lambdaUpdate.eq(User::getName, "李艺伟")
                .eq(User::getAge, 29)
                .set(User::getAge, 31);

        int rows = userMapper.update(null, lambdaUpdate);

        System.out.println("影响记录数" + rows);

    }

    @Test
    public void updateByWrapperLambdaChain() {

        boolean b = new LambdaUpdateChainWrapper<>(userMapper)
                .eq(User::getName, "李艺伟")
                .eq(User::getAge, 31)
                .set(User::getAge, 32)
                .update();

        System.out.println("是否成功" + b);

    }


}
