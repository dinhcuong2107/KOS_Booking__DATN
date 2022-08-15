package com.example.kosbooking.oderActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kosbooking.ManagerOrderActivity;
import com.example.kosbooking.R;
import com.example.kosbooking.User;
import com.example.kosbooking.modul.AirTicket;
import com.example.kosbooking.modul.ROrd;
import com.example.kosbooking.modul.Room;
import com.example.kosbooking.modul.TicketOrd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class OrderAir1Activity extends AppCompatActivity {
String key_ticket,key_user, name, air_code, time_start, day_start,time_now;
TextView tvSum,an_t1,textViewnameair,textViewaircode, textViewtype,textViewF,textViewT,textViewTs,textViewTb,textViewprice_pt,textViewprice_tg,textViewprice_n1,textViewsl_pt,textViewsl_tg,textViewsl_n1;
Button t1,t2,t3,g1,g2,g3,btnorderair;
int ppt,ptg,pn1,slpt,sltg,sln1,sum,i1,i2,i3;
LinearLayout layoutpt,layouttg,layoutn1;
DatabaseReference databaseReference;
    Calendar calendar = Calendar.getInstance();
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH);
    int year = calendar.get(Calendar.YEAR);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.kosbooking.R.layout.activity_order_air1);

        layoutpt = (LinearLayout) findViewById(R.id.layoutpt);
        layouttg = (LinearLayout) findViewById(R.id.layouttg);
        layoutn1 = (LinearLayout) findViewById(R.id.layoutn1);

        btnorderair = (Button) findViewById(R.id.buttonOrderTicket);
        t1 = (Button) findViewById(R.id.btnslt1);
        t2 = (Button) findViewById(R.id.btnslt2);
        t3 = (Button) findViewById(R.id.btnslt3);
        g1 = (Button) findViewById(R.id.btnslg1);
        g2 = (Button) findViewById(R.id.btnslg2);
        g3 = (Button) findViewById(R.id.btnslg3);
        tvSum = (TextView) findViewById(R.id.textViewSum);
        an_t1 = (TextView) findViewById(R.id.an_t1);
        textViewnameair = (TextView) findViewById(R.id.textViewNameAir);
        textViewaircode = (TextView) findViewById(R.id.textViewIDAir);
        textViewtype = (TextView) findViewById(R.id.textViewTypeAir);
        textViewF = (TextView) findViewById(R.id.textViewFormAir);
        textViewT = (TextView) findViewById(R.id.textViewToAir);
        textViewTs = (TextView) findViewById(R.id.textViewTimeSAir);
        textViewTb = (TextView) findViewById(R.id.textViewTimeBAir);
        textViewprice_pt = (TextView) findViewById(R.id.t_price_air_pt);
        textViewprice_tg = (TextView) findViewById(R.id.t_price_air_tg);
        textViewprice_n1 = (TextView) findViewById(R.id.t_price_air_hn);
        textViewsl_pt = (TextView) findViewById(R.id.textViewsl1);
        textViewsl_tg = (TextView) findViewById(R.id.textViewsl2);
        textViewsl_n1 = (TextView) findViewById(R.id.textViewsl3);

        // nhận data từ intent
        key_user =  getIntent().getStringExtra("key_air_ticket");
        name= getIntent().getStringExtra("name");
        air_code = getIntent().getStringExtra("air_code");
        time_start = getIntent().getStringExtra("time_start");
        day_start = getIntent().getStringExtra("day_start");

        btnorderair.setVisibility(View.INVISIBLE);
        // load data
        databaseReference= FirebaseDatabase.getInstance().getReference("AirTicket");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    AirTicket airTicket = dataSnapshot.getValue(AirTicket.class);
                    if (airTicket.name.equals(name) && airTicket.air_code.equals(air_code) && airTicket.time_start.equals(time_start) && airTicket.day_start.equals(day_start))
                    {
                        key_ticket = dataSnapshot.getKey();
                        textViewnameair.setText(""+name);
                        textViewaircode.setText(""+air_code);
                        if (airTicket.type_of_ticket.equals("1"))
                        {
                            textViewtype.setText("1 chiều");
                            an_t1.setVisibility(View.INVISIBLE);
                        }else {
                            textViewtype.setText("Khứ hồi");
                        }



                        textViewF.setText(""+airTicket.form);
                        textViewT.setText(""+airTicket.to);
                        textViewTs.setText(""+airTicket.time_start+"   "+airTicket.day_start );
                        textViewTb.setText(""+airTicket.time_comeback+"   "+airTicket.day_comeback);

                        textViewsl_pt.setText(""+i1);
                        textViewsl_tg.setText(""+i2);
                        textViewsl_n1.setText(""+i3);
                        slpt = Integer.parseInt(airTicket.ticket_pt);
                        if (slpt>0){
                            ppt = Integer.parseInt(airTicket.price_pt);
                            textViewprice_pt.setText(airTicket.price_pt+ "   VNĐ");
                        }

                        sltg =  Integer.parseInt(airTicket.ticket_tg);
                        if (sltg>0){
                            ptg = Integer.parseInt(airTicket.price_tg);
                            textViewprice_tg.setText(airTicket.price_tg+"   VNĐ");
                        }

                        sln1 = Integer.parseInt(airTicket.ticket_n1);
                        if (sln1>0){
                            pn1 = Integer.parseInt(airTicket.price_n1);
                            textViewprice_n1.setText(airTicket.price_n1+"   VNĐ");
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(key_user);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user.position.equals("admin"))
                {
                    btnorderair.setVisibility(View.INVISIBLE);
                    t1.setVisibility(View.INVISIBLE);
                    t2.setVisibility(View.INVISIBLE);
                    t3.setVisibility(View.INVISIBLE);
                    g1.setVisibility(View.INVISIBLE);
                    g2.setVisibility(View.INVISIBLE);
                    g3.setVisibility(View.INVISIBLE);
                    tvSum.setVisibility(View.INVISIBLE);
                    textViewsl_pt.setText(""+slpt);
                    textViewsl_tg.setText(""+sltg);
                    textViewsl_n1.setText(""+sln1);
                }
                else {
                    btnorderair.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i1 = Integer.parseInt(textViewsl_pt.getText().toString());
                i1++;
                if (i1>slpt){
                    i1 = slpt;
                }
                textViewsl_pt.setText(""+i1);
                load_sum();
            }
        });
        g1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i1 = Integer.parseInt(textViewsl_pt.getText().toString());
                i1--;
                if (i1<0){
                    i1=0;
                }
                textViewsl_pt.setText(""+i1);
                load_sum();
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2 = Integer.parseInt(textViewsl_tg.getText().toString());
                i2++;
                if (i2>sltg){
                    i2 = sltg;
                }
                textViewsl_tg.setText(""+i2);
                load_sum();
            }
        });
        g2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2 = Integer.parseInt(textViewsl_tg.getText().toString());
                i2--;
                if (i2<0){
                    i2=0;
                }
                textViewsl_tg.setText(""+i2);
                load_sum();
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i3 = Integer.parseInt(textViewsl_n1.getText().toString());
                i3++;
                if (i3>sln1){
                    i3 = sln1;
                }
                textViewsl_n1.setText(""+i3);
                load_sum();
            }
        });
        g3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i3 = Integer.parseInt(textViewsl_n1.getText().toString());
                i3--;
                if (i3<0){
                    i3=0;
                }
                textViewsl_n1.setText(""+i3);
                load_sum();
            }
        });

        btnorderair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(textViewsl_pt.getText().toString())*ppt+Integer.parseInt(textViewsl_tg.getText().toString())*ptg+Integer.parseInt(textViewsl_n1.getText().toString())*pn1==0)
                {
                    Toast.makeText(OrderAir1Activity.this,"bạn chưa chọn loại vé",Toast.LENGTH_LONG).show();
                }
                else {
                    final TicketOrd ticketOrd = new TicketOrd();
                    ticketOrd.key_user_ord= key_user;
                    ticketOrd.key_ticket_ord=key_ticket;
                    req_time_now();
                    ticketOrd.time_ord=time_now;
                    ticketOrd.sl_pt_ord=textViewsl_pt.getText().toString();
                    ticketOrd.sl_tg_ord=textViewsl_tg.getText().toString();
                    ticketOrd.sl_n1_ord=textViewsl_n1.getText().toString();
                    ticketOrd.status = "1";
                    databaseReference= FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("Order").child("Order_AirTicket").push().setValue(ticketOrd, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error==null){

                                int sl_tpt = slpt - Integer.parseInt(ticketOrd.sl_pt_ord);
                                String string_tpt = ""+sl_tpt;
                                databaseReference= FirebaseDatabase.getInstance().getReference();
                                databaseReference.child("AirTicket").child(key_ticket).child("ticket_pt").setValue(string_tpt);
                                int sl_ttg = sltg - Integer.parseInt(textViewsl_tg.getText().toString());
                                String string_ttg = ""+sl_ttg;
                                databaseReference= FirebaseDatabase.getInstance().getReference();
                                databaseReference.child("AirTicket").child(key_ticket).child("ticket_tg").setValue(string_ttg);
                                int sl_tn1 = sln1 - Integer.parseInt(textViewsl_n1.getText().toString());
                                String string_tn1 = ""+sl_tn1;
                                databaseReference= FirebaseDatabase.getInstance().getReference();
                                databaseReference.child("AirTicket").child(key_ticket).child("ticket_n1").setValue(string_tn1);

                                Toast.makeText(OrderAir1Activity.this, "hoàn thành", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(OrderAir1Activity.this, ManagerOrderActivity.class);
                                intent.putExtra("key_user",key_user);
                                startActivity(intent);
                            }else {
                                Toast.makeText(OrderAir1Activity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void req_time_now() {
        android.icu.util.Calendar mcurrentTime = android.icu.util.Calendar.getInstance();
        int hour_now = mcurrentTime.get(android.icu.util.Calendar.HOUR_OF_DAY);
        int minute_now = mcurrentTime.get(android.icu.util.Calendar.MINUTE);
        time_now = ""+hour_now+":"+minute_now+"-"+day+"/"+month+"/"+year;
    }

    private void load_sum() {
        sum = Integer.parseInt(textViewsl_pt.getText().toString())*ppt+Integer.parseInt(textViewsl_tg.getText().toString())*ptg+Integer.parseInt(textViewsl_n1.getText().toString())*pn1;
        tvSum.setText(""+sum+ "   VNĐ");
    }
}