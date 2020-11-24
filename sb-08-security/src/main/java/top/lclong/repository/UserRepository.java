package top.lclong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.lclong.domain.User;

/**
 * @Author: zlatanlong
 * @Date: 2020/11/23 21:01
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
