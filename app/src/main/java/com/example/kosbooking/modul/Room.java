package com.example.kosbooking.modul;

public class Room {
    public String name;
    public String img1;
    public String img2;
    public String img3;
    public String address;
    public String slroom;
    public String price;
    public String detail;
    public Boolean act;

    public Room() {
    }

    public Room(String name, String img1, String img2, String img3, String address, String slroom, String price, String detail, Boolean act) {
        this.name = name;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.address = address;
        this.slroom = slroom;
        this.price = price;
        this.detail = detail;
        this.act = act;
    }
}
