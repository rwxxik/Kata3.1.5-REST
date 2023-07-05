package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.util.List;


@RestController
@RequestMapping("/api/roles")
public class RestRoleController {

    private final RoleService roleService;

    @Autowired
    public RestRoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping()
    public List<Role> showAllUsers() {
        return roleService.getAllRoles();
    }

    @GetMapping("{name}")
    public Role getSingleUsers(@PathVariable String name) {
        return roleService.findByName(name);
    }

}
