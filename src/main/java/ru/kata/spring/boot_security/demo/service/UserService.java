package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    public void addUser(User user);
    public void updateUser(int id, User updatedUser);
    public List<User> getAllUsers();
    public User getUser(int id);
    public void removeUser(int id);
    public User getUserByUsername(String username);
    public User getUserByAuthentication(Authentication authentication);
}
