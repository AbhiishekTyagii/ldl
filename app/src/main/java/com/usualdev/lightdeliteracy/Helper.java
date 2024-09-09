package com.usualdev.lightdeliteracy;

public class Helper {
    String name, email, username, password, day , phone;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPhone() {
        return password;
    }

    public void setPhone(String password) {
        this.password = password;
    }



    public Helper(String name, String email, String username, String password, String day, String phone) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.day = day;
        this.phone = phone;
    }

    public Helper() {
    }
}
