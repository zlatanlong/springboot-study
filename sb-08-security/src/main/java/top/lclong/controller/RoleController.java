package top.lclong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lclong.security.entity.Role;
import top.lclong.security.entity.User;
import top.lclong.security.repository.RoleRepository;
import top.lclong.security.repository.UserRepository;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: zlatanlong
 * @Date: 2020/11/23 22:16
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @RequestMapping("/addRole2User/{id}/{roleId}")
    public User add(@PathVariable Long id, @PathVariable Long roleId) {
        User user = userRepository.findById(id).get();
        List<Role> roles = user.getRoles();
        if (roles == null) {
            roles = new LinkedList<>();
        }
        Role role = roleRepository.findById(roleId).get();
        roles.add(role);
        return userRepository.save(user);
    }
}
