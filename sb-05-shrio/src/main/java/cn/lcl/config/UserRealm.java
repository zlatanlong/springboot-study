package cn.lcl.config;

import cn.lcl.pojo.User;
import cn.lcl.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义的realm
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了==>授权doGetAuthorizationInfo");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        Subject subject = SecurityUtils.getSubject();

        User user = (User) subject.getPrincipal();

        info.addStringPermission(user.getPerms());

        return info;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("进行了==>认证doGetAuthenticationInfo");

        // 获取当前用户userToken
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        // user : 根据输入的用户名去查到的用户对象
        User user = userService.queryUserByName(userToken.getUsername());

        if (user == null) { // 没有这个人，防止下面空指针异常
            return null; // throw Exception
        }

        // 封装用户登录数据
        // 密码认证由shiro做
        return
                /**
                 * 第一个参数是数据库中查到的用户
                 * 如果认证成功
                 * 可以传递到授权，授权时候就可以看到用户的身份了。
                 */
                new SimpleAuthenticationInfo(user, user.getPwd(), "");
    }
}
