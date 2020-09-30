package top.lclong.sb07redis.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zlatanlong
 * @Date: 2020/9/30 16:01
 */
@Data
public class User implements Serializable {
    private Integer id;
    private String userName;
    private String userSex;
}
