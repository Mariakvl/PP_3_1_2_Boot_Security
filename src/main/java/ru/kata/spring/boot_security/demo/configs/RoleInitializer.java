package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;


@Component
public class RoleInitializer {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RoleInitializer(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        if (userService.getAllUsers().isEmpty()) {
            Role roleAdmin = new Role("ROLE_ADMIN");
            Role roleUser = new Role("ROLE_USER");

            roleService.saveRole(roleAdmin);
            roleService.saveRole(roleUser);

            User admin = new User("admin", "password", "admin_name", "admin_surname", 20);
            admin.addRole(roleAdmin);
            admin.addRole(roleUser);

            User user = new User("user", "password", "user_name", "user_surname", 20);
            user.addRole(roleUser);

            userService.saveUser(admin);
            userService.saveUser(user);
        }
    }
}