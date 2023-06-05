package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.MyUserService;
import ru.kata.spring.boot_security.demo.service.PasswordEncoderUserService;

import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    final MyUserService userService;
    final PasswordEncoderUserService passwordEncoderUserService;

    @Autowired
    public AdminController(MyUserService userService, PasswordEncoderUserService registrationUserService) {
        this.userService = userService;
        this.passwordEncoderUserService = registrationUserService;
    }

    @GetMapping()
    public String getAllUsersCRUD(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "indexCRUD";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "userCRUD";
    }

    @GetMapping("/new")
    public String openNewUserForm(@ModelAttribute("user") User user) {
        return "newUserForm";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "newUserForm";
        }
        passwordEncoderUserService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String openEditUserForm(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUser(id));
        return "editUserForm";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult,
                             @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "editUserForm";
        }

        passwordEncoderUserService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }
}
