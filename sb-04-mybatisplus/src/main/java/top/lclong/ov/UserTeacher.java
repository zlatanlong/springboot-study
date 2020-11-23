package top.lclong.ov;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("用户带老师OV")
public class UserTeacher {

    private Long id;

    private String name;

    private Integer age;

    private String email;

    private LocalDateTime createTime;

    private Integer teacherId;

    private String teacherName;

    private Integer teacherAge;

}
