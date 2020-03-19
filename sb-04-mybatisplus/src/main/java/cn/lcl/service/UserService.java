package cn.lcl.service;

import cn.lcl.ov.UserTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface UserService {

    public abstract Page<?> seletOA(Page<?> page, Integer id);


    public abstract Page<?> seletMap(Page<?> page, Integer id);


    public abstract Page<?> seletMap2(Page<?> page, Integer id);


}
