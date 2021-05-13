package top.lclong;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: zlatanlong
 * @Date: 2021/5/13 10:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Kitten {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int gender;
    private Date birthday;
}
