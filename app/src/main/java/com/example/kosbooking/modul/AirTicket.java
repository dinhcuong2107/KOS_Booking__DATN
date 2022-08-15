package com.example.kosbooking.modul;

public class AirTicket {
    public String name;
    public String air_code;
    public String type_of_ticket;
    public String form;
    public String to;
    public String time_start;
    public String day_start;
    public String time_comeback;
    public String day_comeback;
    public String price_pt;
    public String ticket_pt;
    public String price_tg;
    public String ticket_tg;
    public String price_n1;
    public String ticket_n1;

    public AirTicket() {
    }

    public AirTicket(String name, String air_code, String type_of_ticket, String form, String to, String time_start, String day_start, String time_comeback, String day_comeback, String price_pt, String ticket_pt, String price_tg, String ticket_tg, String price_n1, String ticket_n1) {
        this.name = name;
        this.air_code = air_code;
        this.type_of_ticket = type_of_ticket;
        this.form = form;
        this.to = to;
        this.time_start = time_start;
        this.day_start = day_start;
        this.time_comeback = time_comeback;
        this.day_comeback = day_comeback;
        this.price_pt = price_pt;
        this.ticket_pt = ticket_pt;
        this.price_tg = price_tg;
        this.ticket_tg = ticket_tg;
        this.price_n1 = price_n1;
        this.ticket_n1 = ticket_n1;
    }
}
