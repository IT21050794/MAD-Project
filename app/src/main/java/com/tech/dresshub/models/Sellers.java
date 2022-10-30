package com.tech.dresshub.models;

public class Sellers {

    String id;
    String name;
    String email;
    String age;
    String phone;
    String password;

    public Sellers() {

    }

    public Sellers(String id, String name, String email, String age, String phone, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.phone = phone;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
