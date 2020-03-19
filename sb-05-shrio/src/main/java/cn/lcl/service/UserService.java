package cn.lcl.service;

import cn.lcl.pojo.User;

public interface UserService {

    public abstract User queryUserByName(String name);
}
