package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.exception_handling.UserNotFoundException;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public RestController(UserService userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    void init() {
        User initUser = new User();
        initUser.setFirstName("init");
        initUser.setLastName("init");
        initUser.setEmail("init@init.com");
        initUser.setUsername("init");
        initUser.setPassword("init");
        initUser.addRole(roleService.findByName("ROLE_USER"));
        initUser.addRole(roleService.findByName("ROLE_ADMIN"));
        if (userService.getUserByUsername(initUser.getUsername()) == null) {
            userService.addUser(initUser);
        }
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("users/{id}")
    public User getUser(@PathVariable int id) {
        if (userService.getUser(id) == null) {
            throw new UserNotFoundException("User with id = " + id + " not found");
        }
        return userService.getUser(id);
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        userService.addUser(user);
        return user;
    }

    @PutMapping("/users/{id}")
    public User editUser(@RequestBody User user) {
        userService.updateUser(user.getId(), user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id) {
        if (userService.getUser(id) == null) {
            throw new UserNotFoundException("Can not delete. User with id = " + id + " not found");
        }
        userService.removeUser(id);
        return "user deleted";
    }

    @GetMapping("/user")
    public User getCurrentAuthenticatedUser() {
        return userService.getUserByAuthentication(SecurityContextHolder.getContext().getAuthentication());
    }
}
