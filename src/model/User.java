package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User implements Serializable{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime regisLocalDateTime, lastLoginDateTime;
    private boolean isBanned;
    
    public User(){
        
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.password = password;
        this.email = email;
        regisLocalDateTime = LocalDateTime.now();
        this.isBanned = false;
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

    public void setBanned(boolean isBanned) {
        this.isBanned = isBanned;
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

    public boolean isBanned() {
        return isBanned;
    }
    
    public String toCsv(){
        return (isBanned ? "[BANNED], " : "[OK], ") + name + "," + email + "," + regisLocalDateTime + ", " + lastLoginDateTime;
    }
    @Override
    public String toString(){
        return (isBanned ? "[BANNED], " : "[OK], ") + name + "," + email + "," + regisLocalDateTime + ", " + lastLoginDateTime;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
        return true;
        if (obj == null)
        return false;
        if (getClass() != obj.getClass())
        return false;
        User other = (User) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }
}
