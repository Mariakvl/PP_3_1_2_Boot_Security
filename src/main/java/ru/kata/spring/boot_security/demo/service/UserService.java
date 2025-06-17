package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    User getUserById(long id);

    void saveUser(User user);

    void updateUser(long id, User user);

    List<User> getAllUsers();

    void removeUserById(long id);

    List<Role> getAllRoles();
}
