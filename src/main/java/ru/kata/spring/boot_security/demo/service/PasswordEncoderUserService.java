package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collections;

@Service
public class PasswordEncoderUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordEncoderUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.isUserCheckBox()) {
            user.addRole(new Role(1, "ROLE_USER"));
        }
        if (user.isAdminCheckBox()) {
            user.addRole(new Role(2, "ROLE_ADMIN"));
        }
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(int id, User updatedUser) {
        User userToBeUpdated = userRepository.getById(id);
        userToBeUpdated.setFirstName(updatedUser.getFirstName());
        userToBeUpdated.setLastName(updatedUser.getLastName());
        userToBeUpdated.setEmail(updatedUser.getEmail());
        userToBeUpdated.setUsername(updatedUser.getUsername());
        userToBeUpdated.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userRepository.save(userToBeUpdated);
    }
}
