package cn.lcl.entity;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class User {

    private Long id;

    @TableField(condition = SqlCondition.LIKE)
    private String name;

    @TableField(condition = "%s&lt;#{%s}")
    private Integer age;

    private String email;

    private Long managerId;

    private LocalDateTime createTime;

    @TableField(select = false)
    private Integer deleted;

}
