package cn.lcl.controller;

import cn.lcl.entity.Teacher;
import cn.lcl.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Teacher)表控制层
 *
 * @author makejava
 * @since 2020-03-16 18:45:51
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    /**
     * 服务对象
     */
    @Resource
    private TeacherService teacherService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public Teacher selectOne(Integer id) {
        return this.teacherService.queryById(id);
    }

}