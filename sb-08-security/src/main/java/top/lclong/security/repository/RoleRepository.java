package top.lclong.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.lclong.security.entity.Role;

/**
 * @Author: zlatanlong
 * @Date: 2020/11/23 21:02
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
