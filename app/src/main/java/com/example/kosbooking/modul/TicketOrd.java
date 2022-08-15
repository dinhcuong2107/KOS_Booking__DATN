package com.example.kosbooking.modul;

public class TicketOrd {
    public String key_user_ord;
    public String key_ticket_ord;
    public String sl_pt_ord;
    public String sl_tg_ord;
    public String sl_n1_ord;
    public String time_ord;
    public String status;

    public TicketOrd() {
    }

    public TicketOrd(String key_user_ord, String key_ticket_ord, String sl_pt_ord, String sl_tg_ord, String sl_n1_ord, String time_ord, String status) {
        this.key_user_ord = key_user_ord;
        this.key_ticket_ord = key_ticket_ord;
        this.sl_pt_ord = sl_pt_ord;
        this.sl_tg_ord = sl_tg_ord;
        this.sl_n1_ord = sl_n1_ord;
        this.time_ord = time_ord;
        this.status = status;
    }
}
