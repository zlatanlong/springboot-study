package cn.lcl.dao;

import cn.lcl.entity.User;
import cn.lcl.ov.UserTeacher;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    List<User> selectAll(@Param(Constants.WRAPPER) Wrapper<User> wrapper);

    Page<User> selectUserPage(Page<User> page, @Param(Constants.WRAPPER) Wrapper<User> wrapper);

    Page<UserTeacher> selectOAById(Page<?> page, Integer id);

    Page<Map<String, Object>> selectMapById(Page<?> page, Integer id);

    Page<Map<String, Object>> selectMapById2(Page<?> page, Integer id);

}
