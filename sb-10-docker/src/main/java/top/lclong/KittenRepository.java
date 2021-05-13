package top.lclong;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: zlatanlong
 * @Date: 2021/5/13 10:39
 */
public interface KittenRepository extends JpaRepository<Kitten, Long> {
}
