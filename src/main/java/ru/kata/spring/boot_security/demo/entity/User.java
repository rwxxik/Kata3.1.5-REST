package ru.kata.spring.boot_security.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username", unique = true)
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, max = 30, message = "Имя должно быть от 2 до 30 символов")
    private String username;
    @Column(name = "password")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
    @Column(name = "firstName")
    @NotBlank(message = "Имя не может быть пустым")
    private String firstName;
    @Column(name = "lastName")
    @NotBlank(message = "Фамилия не может быть пустым")
    private String lastName;
    @Column(name = "email")
    @NotBlank(message = "Емейл не может быть пустым")
    @Email(message = "Неверный формат email")
    private String email;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles;
    @Transient
    private boolean adminCheckBox;
    @Transient
    private boolean userCheckBox;

    {
        roles = new HashSet<>();
    }

    public User() {
    }

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }


    public boolean isAdminCheckBox() {
        return adminCheckBox;
    }

    public void setAdminCheckBox(boolean adminCheckBox) {
        this.adminCheckBox = adminCheckBox;
    }

    public boolean isUserCheckBox() {
        return userCheckBox;
    }

    public void setUserCheckBox(boolean userCheckBox) {
        this.userCheckBox = userCheckBox;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
