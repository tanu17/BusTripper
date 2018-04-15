package com.bustripper.bustripper.Entity;

public class Account {
    private String username, email, password;
    public Account (String u, String e, String p) {
        username = u;
        email = e;
        password = p;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
}
