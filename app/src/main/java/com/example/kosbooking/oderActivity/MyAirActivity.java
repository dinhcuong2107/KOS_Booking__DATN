package com.example.kosbooking.oderActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kosbooking.InfActivity;
import com.example.kosbooking.KHActivity;
import com.example.kosbooking.LoginActivity;
import com.example.kosbooking.MainActivity;
import com.example.kosbooking.ManagerOrderActivity;
import com.example.kosbooking.R;
import com.example.kosbooking.User;
import com.example.kosbooking.adapter.ListAir1Adapter;
import com.example.kosbooking.modul.AirTicket;
import com.example.kosbooking.modul.TicketOrd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MyAirActivity extends AppCompatActivity {
String key_ord_ticket,key_user_ord,key_ticket,time_now;
Button button_huy;
DatabaseReference databaseReference;
String t1,t2,t3;
TextView textViewKH,textnameAir,textcodeAir,texttypeAir,textForm,textTo,texttimeStart,texttimeBack,textslpt,textppt,textsltg,textptg,textsln1,textpn1,textSum;
    Calendar calendar = Calendar.getInstance();
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH);
    int year = calendar.get(Calendar.YEAR);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_air);

        textViewKH=(TextView) findViewById(R.id.stextViewKhachHang);
        textnameAir=(TextView) findViewById(R.id.stextViewNameAir);
        textcodeAir=(TextView) findViewById(R.id.stextViewIDAir);
        texttypeAir=(TextView) findViewById(R.id.stextViewTypeAir);
        textForm=(TextView) findViewById(R.id.stextViewFormAir);
        textTo=(TextView) findViewById(R.id.stextViewToAir);
        texttimeStart=(TextView) findViewById(R.id.stextViewTimeSAir);
        texttimeBack=(TextView) findViewById(R.id.stextViewTimeBAir);
        textslpt=(TextView) findViewById(R.id.sl_air_pt);
        textsltg=(TextView) findViewById(R.id.sl_air_tg);
        textsln1=(TextView) findViewById(R.id.sl_air_hn);
        textppt=(TextView) findViewById(R.id.ppt);
        textptg=(TextView) findViewById(R.id.ptg);
        textpn1=(TextView) findViewById(R.id.pn1);
        textSum=(TextView) findViewById(R.id.stextViewSum);
        button_huy=(Button) findViewById(R.id.button_huy_OrderTicket);

        // nhận data từ intent
        key_ord_ticket =  getIntent().getStringExtra("key_ord_ticket");

        databaseReference= FirebaseDatabase.getInstance().getReference("Order").child("Order_AirTicket");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    TicketOrd ticketOrd= dataSnapshot.getValue(TicketOrd.class);
                    if (dataSnapshot.getKey().equals(key_ord_ticket)){
                        key_user_ord = ticketOrd.key_user_ord;
                        key_ticket = ticketOrd.key_ticket_ord;
                        t1=ticketOrd.sl_pt_ord;
                        t2=ticketOrd.sl_tg_ord;
                        t3=ticketOrd.sl_n1_ord;
                        load_user_name(ticketOrd.key_user_ord);
                        load_ticket(ticketOrd.key_ticket_ord);

                        textsln1.setText(""+ticketOrd.sl_n1_ord);
                        textsltg.setText(""+ticketOrd.sl_tg_ord);
                        textslpt.setText(""+ticketOrd.sl_pt_ord);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        textViewKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAirActivity.this, KHActivity.class);
                intent.putExtra("key_user",key_user_ord);
                startActivity(intent);
            }
        });
        button_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                req_time_now();
                databaseReference= FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Order").child("Order_AirTicket").child(key_ord_ticket).child("status").setValue("hủy "+time_now);

                databaseReference= FirebaseDatabase.getInstance().getReference("AirTicket");
                databaseReference.child(key_ticket).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        AirTicket airTicket = snapshot.getValue(AirTicket.class);
                        databaseReference= FirebaseDatabase.getInstance().getReference("AirTicket");
                        int i1 = Integer.parseInt(t1)+Integer.parseInt(airTicket.ticket_pt);
                        databaseReference.child(key_ticket).child("ticket_pt").setValue(""+i1);

                        databaseReference= FirebaseDatabase.getInstance().getReference("AirTicket");
                        int i2 = Integer.parseInt(t2)+Integer.parseInt(airTicket.ticket_tg);
                        databaseReference.child(key_ticket).child("ticket_tg").setValue(""+i2);

                        databaseReference= FirebaseDatabase.getInstance().getReference("AirTicket");
                        int i3 = Integer.parseInt(t3)+Integer.parseInt(airTicket.ticket_n1);
                        databaseReference.child(key_ticket).child("ticket_n1").setValue(""+i3);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Intent intent = new Intent(MyAirActivity.this, ManagerOrderActivity.class);
                startActivity(intent);

            }
        });
    }

    private void req_time_now() {
        android.icu.util.Calendar mcurrentTime = android.icu.util.Calendar.getInstance();
        int hour_now = mcurrentTime.get(android.icu.util.Calendar.HOUR_OF_DAY);
        int minute_now = mcurrentTime.get(android.icu.util.Calendar.MINUTE);
        time_now = ""+hour_now+":"+minute_now+"-"+day+"/"+month+"/"+year;
    }

    private void load_user_name(final String key_user_ord) {
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    User user = dataSnapshot.getValue(User.class);
                    if (dataSnapshot.getKey().equals(key_user_ord)){
                        textViewKH.setText(user.fullname);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void load_ticket(final String key_ticket_ord) {
        databaseReference= FirebaseDatabase.getInstance().getReference("AirTicket");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    AirTicket airTicket=dataSnapshot.getValue(AirTicket.class);
                    if (dataSnapshot.getKey().equals(key_ticket_ord)){
                        textnameAir.setText(airTicket.name);
                        textcodeAir.setText(airTicket.air_code);
                        texttypeAir.setText(airTicket.type_of_ticket);
                        textForm.setText(airTicket.form);
                        textTo.setText(airTicket.to);
                        texttimeStart.setText(airTicket.time_start+"-"+airTicket.day_start);
                        texttimeBack.setText(airTicket.time_comeback+"-"+airTicket.day_comeback);
                        textppt.setText(airTicket.price_pt);
                        textptg.setText(airTicket.price_tg);
                        textpn1.setText(airTicket.price_n1);
                        int sum =Integer.parseInt(textppt.getText().toString())*Integer.parseInt(textslpt.getText().toString())
                                +Integer.parseInt(textptg.getText().toString())*Integer.parseInt(textsltg.getText().toString())
                                +Integer.parseInt(textpn1.getText().toString())*Integer.parseInt(textsln1.getText().toString());
                        textSum.setText(""+sum);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}