package com.example.samobeli.Model;

public class User {
    private String Name;
    private String PhoneNumber;
    private String Password;


    public User() {
    }

    public User(String name, String Phone, String password) {
        Name = name;
        PhoneNumber = Phone;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String Phone) {
        PhoneNumber = Phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
