package top.lclong.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.lclong.security.entity.User;

/**
 * @Author: zlatanlong
 * @Date: 2020/11/23 21:01
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
