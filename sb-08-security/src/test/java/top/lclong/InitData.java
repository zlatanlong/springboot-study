package top.lclong;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.lclong.domain.Role;
import top.lclong.repository.RoleRepository;

/**
 * @Author: zlatanlong
 * @Date: 2020/11/23 22:07
 */
@SpringBootTest
@RequiredArgsConstructor
public class InitData {

    @Autowired
    RoleRepository roleRepository;

    @Test
    public void init() {

        roleRepository.save(new Role(1L, "vip4"));
        roleRepository.save(new Role(2L, "vip5"));
        roleRepository.save(new Role(3L, "vip6"));

    }
}
