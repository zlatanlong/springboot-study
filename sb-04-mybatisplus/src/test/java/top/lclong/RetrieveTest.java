package top.lclong;

import top.lclong.dao.UserMapper;
import top.lclong.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class RetrieveTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void selectByMap() {
        HashMap<String, Object> columnMap = new HashMap<>();
        //这里的String 必须完全对应于数据库的字段
        columnMap.put("manager_Id", 1088248166370832385L);

        List<User> userList = userMapper.selectByMap(columnMap);
        userList.forEach(System.out::println);
    }

    /**
     * 名字中包含雨并且年龄小于40
     * name like '%雨%' and age<40
     */
    @Test
    void selectByWrapper() {
        // 下面两种方法二选一
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //QueryWrapper<User> query = Wrappers.query();

        queryWrapper.like("name", "雨").lt("age", 40);


        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);

    }

    /**
     * 名字中包含雨年并且龄大于等于20且小于等于40并且email不为空
     * name like '%雨%' and age between 20 and 40 and email is not null
     */
    @Test
    void selectByWrapper2() {
        // 下面两种方法二选一
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //QueryWrapper<User> query = Wrappers.query();

        queryWrapper.like("name", "雨")
                .between("age", 20, 40)
                .isNotNull("email");

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);

    }

    /**
     * 名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
     * name like '王%' or age>=25 order by age desc,id asc
     */
    @Test
    void selectByWrapper3() {
        // 下面两种方法二选一
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //QueryWrapper<User> query = Wrappers.query();

        queryWrapper.likeRight("name", "向").or()
                .ge("age", 25)
                .orderByDesc("age")
                .orderByAsc("id");

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);

    }

    /**
     * 4、创建日期为2019年2月14日之前并且直属上级为名字为王姓
     * date_format(create_time,'%Y-%m-%d') < '2019-02-14'
     * and manager_id in (select id from user where name like '王%')
     */
    @Test
    void selectByWrapper4() {
        // 下面两种方法二选一
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //QueryWrapper<User> query = Wrappers.query();

        queryWrapper.apply("date_format(create_time,'%Y-%m-%d') < {0}", "2019-02-14")
                .inSql("manager_id", "select id from user where name like '王%'");

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);

    }

    /**
     * 名字为王姓并且（年龄小于40或邮箱不为空）
     * name like '王%' and (age<40 or email is not null)
     */
    @Test
    void selectByWrapper5() {
        // 下面两种方法二选一
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //QueryWrapper<User> query = Wrappers.query();

        queryWrapper.likeRight("name", "王")
                .and(wq -> wq.lt("age", 40).or().isNotNull("email"));

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);

    }

    /**
     * 名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
     * name like '王%' or (age<40 and age>20 and email is not null)
     */
    @Test
    void selectByWrapper6() {
        // 下面两种方法二选一
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //QueryWrapper<User> query = Wrappers.query();

        queryWrapper.likeRight("name", "王")
                .or(wq -> wq.between("age", 20, 40)
                        .isNotNull("email"));

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);

    }

    /**
     * 年龄小于40或邮箱不为空并且名字为王姓
     * (age<40 or email is not null) and name like '王%'
     */
    @Test
    void selectByWrapper7() {
        // 下面两种方法二选一
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //QueryWrapper<User> query = Wrappers.query();

        queryWrapper.nested(wq -> wq.lt("age", 40).or().isNotNull("email"))
                .likeRight("name", "王");

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 年龄为30、31、34、35
     * age in (30、31、34、35)
     */
    @Test
    void selectByWrapper8() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age", Arrays.asList(30, 31, 34, 35));

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 9、只返回满足条件的其中一条语句即可
     * limit 1
     */
    @Test
    void selectByWrapper9() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("limit 1");

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 10、名字中包含雨并且年龄小于40(需求1加强版)
     * 第一种情况：select id,name
     * from user
     * where name like '%雨%' and age<40
     * 第二种情况：select id,name,age,email
     * from user
     * where name like '%雨%' and age<40
     */
    @Test
    void selectByWrapper10_1() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name")
                .like("name", "雨")
                .lt("age", 40);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    void selectByWrapper10_2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // select传参构造条件
        queryWrapper.select(User.class, tableFieldInfo -> !tableFieldInfo.getColumn().equals("create_time")
                && !tableFieldInfo.getColumn().equals("manager_id"))
                .like("name", "雨")
                .lt("age", 40);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 传统方法 vs condition
     */
    private void condition(String name, String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        //传统
        // if (StringUtils.isNotEmpty(name)) {
        //     queryWrapper.like("name", name);
        // }
        // if (StringUtils.isNotEmpty(email)) {
        //     queryWrapper.like("email", email);
        // }

        // condition 第一个参数是true ，再进行下一个
        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name)
                .like(StringUtils.isNotEmpty(email), "email", email);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void testCondition() {
        String name = "王";
        String email = "";
        condition(name, email);
    }

    /**
     * 用实体的方法
     * 可以根据实体的@TableField注解的condition值，判断每个字段如何判断
     */
    @Test
    public void selectByWrapperEntity() {
        User whereUser = new User();
        whereUser.setName("刘红雨");
        whereUser.setAge(33);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>(whereUser);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * allEq的参数：
     * params: key为数据库字段名,value为字段值
     * null2IsNull : 为true则在map的value为null时调用 isNull 方法,
     * 为false时则忽略value为null的
     */
    @Test
    public void selectByWrapperAllEq() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        Map<String, Object> params = new HashMap<>();

        params.put("name", "王天风");
        params.put("age", null);
//        queryWrapper.allEq(params,false);

        queryWrapper.allEq((k, v) -> !k.equals("name"), params);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * userMapper.selectMaps() 返回一个map，和queryWrapper.select()配合使用
     */
    @Test
    public void selectMapByWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.select(User.class, tableFieldInfo -> !tableFieldInfo.getColumn().equals("create_time")
                && !tableFieldInfo.getColumn().equals("manager_id"))
                .like("name", "雨")
                .lt("age", 40);

        List<Map<String, Object>> userList = userMapper.selectMaps(queryWrapper);
        userList.forEach(System.out::println);
    }


    /**
     * 11、按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。
     * 并且只取年龄总和小于500的组。
     * select avg(age) avg_age,min(age) min_age,max(age) max_age
     * from user
     * group by manager_id
     * having sum(age) <500
     */
    @Test
    void selectByWrapper11() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("avg(age) avg_age", "min(age) min_age", "max(age) max_age")
                .groupBy("manager_id")
                .having("sum(age)<{0}", 500);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }


    /**
     * selectByWrapperObjs 只返回第一个字段
     */
    @Test
    public void selectObjsByWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.select(User.class, tableFieldInfo -> !tableFieldInfo.getColumn().equals("create_time")
                && !tableFieldInfo.getColumn().equals("manager_id"))
                .like("name", "雨")
                .lt("age", 40);

        List<Object> userList = userMapper.selectObjs(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectCountByWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.like("name", "雨")
                .lt("age", 40);

        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println("总记录是" + count);
    }

    @Test
    public void selectOneByWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.like("name", "张雨琪")
                .lt("age", 40);

        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    /**
     * 通过对entity getter的方法引用，
     * 防止列名误写
     */
    @Test
    public void selectLambda() {
        //以下三种方式选择一种
//        LambdaQueryWrapper<User> lambda = new QueryWrapper<User>().lambda();
        //LambdaQueryWrapper<User> userLambda = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();

        //这里第一个参数传的不是列名而是entity的方法引用
        //where name like %雨% and age < 40
        lambdaQuery.like(User::getName, "雨")
                .lt(User::getAge, 40);

        List<User> userList = userMapper.selectList(lambdaQuery);

        userList.forEach(System.out::println);
    }

    /**
     * 名字为王姓并且（年龄小于40或邮箱不为空）
     * name like '王%' and (age<40 or email is not null)
     */
    @Test
    public void selectLambda5() {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();


        lambdaQuery.likeRight(User::getName, "王")
                .and(lqw -> lqw.lt(User::getAge, 40)
                        .or().isNotNull(User::getEmail));

        List<User> userList = userMapper.selectList(lambdaQuery);

        userList.forEach(System.out::println);
    }

    @Test
    public void selectLambdaChain() {
        List<User> userList = new LambdaQueryChainWrapper<User>(userMapper)
                .like(User::getAge, "雨")
                .ge(User::getAge, 20).list();
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByMy() {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();


        lambdaQuery.likeRight(User::getName, "王")
                .and(lqw -> lqw.lt(User::getAge, 40)
                        .or().isNotNull(User::getEmail));

        List<User> userList = userMapper.selectAll(lambdaQuery);

        userList.forEach(System.out::println);
    }

    @Test
    void selectPage() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.ge("age", 26);

        // new Page<>()
        Page<User> page = new Page<>(1, 2);


        Page<User> resultPage = userMapper.selectPage(page, queryWrapper);
        System.out.println("总页数" + resultPage.getPages());
        System.out.println("总记录数" + resultPage.getTotal());
        List<User> userList = resultPage.getRecords();

        userList.forEach(System.out::println);
    }

    @Test
    void selectMapPage() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.select("id", "name").ge("age", 26);

        Page<Map<String, Object>> page = new Page<>(1, 2);


        Page<Map<String, Object>> resultPage = userMapper.selectMapsPage(page, queryWrapper);
        System.out.println("总页数" + resultPage.getPages());
        System.out.println("总记录数" + resultPage.getTotal());
        List<Map<String, Object>> userList = resultPage.getRecords();

        userList.forEach(System.out::println);
    }

    @Test
    void selectMyPage() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.ge("age", 26);

        Page<User> page = new Page<>(1, 2);

        Page<User> resultPage = userMapper.selectUserPage(page, queryWrapper);
        System.out.println("总页数" + resultPage.getPages());
        System.out.println("总记录数" + resultPage.getTotal());
        List<User> userList = resultPage.getRecords();

        userList.forEach(System.out::println);
    }

}