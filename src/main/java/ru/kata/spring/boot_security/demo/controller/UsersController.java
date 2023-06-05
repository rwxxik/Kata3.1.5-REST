package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.security.MyUserDetails;
import ru.kata.spring.boot_security.demo.service.MyUserService;
import ru.kata.spring.boot_security.demo.service.PasswordEncoderUserService;

import java.util.Collections;

@Controller
public class UsersController {

    final PasswordEncoderUserService userService;
    final PasswordEncoder passwordEncoder;


    @Autowired
    public UsersController(PasswordEncoderUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/user")
    public String showUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        model.addAttribute(userDetails.getUser());
        return "user";
    }


    //создание первого админа в БД, если его нет.
//    @GetMapping("/default-admin")
//    public  String createDefaultAdmin() {
//        User defaultAdmin = new User();
//        defaultAdmin.setUsername("admin");
//        defaultAdmin.setPassword(passwordEncoder.encode("admin"));
//        defaultAdmin.setRoles(Collections.singleton(new Role(2, "ROLE_ADMIN")));
//        userService.addUser(defaultAdmin);
//        return "redirect:/";
//    }

//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }

}
