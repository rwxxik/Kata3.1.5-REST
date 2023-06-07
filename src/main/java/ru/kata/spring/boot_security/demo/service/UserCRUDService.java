package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserCRUDService {
    public void addUser(User user);
    public void updateUser(int id, User updatedUser);
    public List<User> getAllUsers();
    public User getUser(int id);
    public void removeUser(int id);
}
