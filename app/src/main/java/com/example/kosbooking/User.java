package com.example.kosbooking;

public class User {
    public String avatar;
    public String fullname;
    public String dateofbirth;
    public String sex;
    public String CMND;
    public String phonenumber;
    public String email;
    public String password;
    public String address;
    public String position;

    public User() {
    }

    public User(String avatar, String fullname, String dateofbirth, String sex, String CMND, String phonenumber, String email, String password, String address, String position) {
        this.avatar = avatar;
        this.fullname = fullname;
        this.dateofbirth = dateofbirth;
        this.sex = sex;
        this.CMND = CMND;
        this.phonenumber = phonenumber;
        this.email = email;
        this.password = password;
        this.address = address;
        this.position = position;
    }
}