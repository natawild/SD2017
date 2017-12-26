/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mnjo.users;

import java.util.Objects;

/**
 *
 * @author Celia
 */

public class User {
    private String username;
    private String password;
    private Integer rate;

    public User() {
        this.username = "";
        this.password = "";
        this.rate=0;
    }

    public User(User c) {
        this.username = c.getUsername();
        this.password = c.getPassword();
        this.rate=c.getRate();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.rate=0;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
    
    

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Username: ");
        string.append(username).append("\n");
        string.append("password: ");
        string.append(password).append("\n");
        return string.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        User u = (User) obj;
        return u.getUsername().equals(this.username) && u.getPassword().equals(this.password);

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.username);
        hash = 41 * hash + Objects.hashCode(this.password);
        hash = 41 * hash + Objects.hashCode(this.rate);
        return hash;
    }


    public User clone(){
        return new User(this);
    }
        
}
