package com.example.kosbooking.modul;

public class ROrd {
    public String key_user_ord;
    public String key_room_ord;
    public String sl_room_ord;
    public String day_start;
    public String sn_room_ord;
    public String time_ord;
    public String status;

    public ROrd() {
    }

    public ROrd(String key_user_ord, String key_room_ord, String sl_room_ord, String day_start, String sn_room_ord, String time_ord,String status) {
        this.key_user_ord = key_user_ord;
        this.key_room_ord = key_room_ord;
        this.sl_room_ord = sl_room_ord;
        this.day_start = day_start;
        this.sn_room_ord = sn_room_ord;
        this.time_ord = time_ord;
        this.status = status;
    }

}
