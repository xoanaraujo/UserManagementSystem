package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable{
    private String name;
    private String password;
    private String email;
    private LocalDateTime regisLocalDateTime, lastLoginDateTime;

    public User(String name, String email, String password) {
        this.name = name;
        this.password = password;
        this.email = email;
        regisLocalDateTime = LocalDateTime.now();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastLoginDateTime(LocalDateTime lastLoginDateTime) {
        this.lastLoginDateTime = lastLoginDateTime;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getRegisLocalDateTime() {
        return regisLocalDateTime;
    }

    public LocalDateTime getLastLoginDateTime() {
        return lastLoginDateTime;
    }
}
