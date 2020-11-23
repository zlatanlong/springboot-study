package top.lclong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.lclong.domain.Role;

/**
 * @Author: zlatanlong
 * @Date: 2020/11/23 21:02
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
