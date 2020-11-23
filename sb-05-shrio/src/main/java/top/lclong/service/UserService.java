package top.lclong.service;

import top.lclong.pojo.User;

public interface UserService {

    public abstract User queryUserByName(String name);
}
