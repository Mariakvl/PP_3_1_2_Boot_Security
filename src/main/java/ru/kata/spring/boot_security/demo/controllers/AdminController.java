package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "new";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam(value = "selectedRoles", required = false) Set<Long> selectedRoleIds) {
        if (selectedRoleIds != null) {
            Set<Role> selectedRoles = roleService.getRolesByIds(selectedRoleIds);
            user.setRoles(selectedRoles);
        } else {
            user.setRoles(new HashSet<>());
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam("id") long id,
                             @ModelAttribute("user") User user,
                             @RequestParam(value = "selectedRoles", required = false) Set<Long> selectedRoleIds) {

        if (selectedRoleIds != null) {
            Set<Role> selectedRoles = roleService.getRolesByIds(selectedRoleIds);
            user.setRoles(selectedRoles);
        } else {
            user.setRoles(new HashSet<>());
        }

        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}
