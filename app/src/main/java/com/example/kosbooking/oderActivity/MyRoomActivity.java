package com.example.kosbooking.oderActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kosbooking.KHActivity;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

public class MyRoomActivity extends AppCompatActivity {
    String temp_img,key_room,img1,img2,img3,time_now;
    String key_user,key_ord_room;
    Button loadL,loadR;
    Button button_huy_Ord_Room;
    DatabaseReference databaseReference;
    ImageView imageView;
    TextView stextViewUser,stextViewAdd,stextViewMota,stextViewSLP,stextViewNgayThue,stextViewPrice,sbutton_ord_day;
    int i=0,sl=1,pri,sl_max,sl_ngay=1;
    Calendar calendar = Calendar.getInstance();
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH);
    int year = calendar.get(Calendar.YEAR);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_room);
        imageView = (ImageView) findViewById(R.id.my_view_img);
        loadL = (Button) findViewById(R.id.my_btn_left);
        loadR = (Button) findViewById(R.id.my_btn_right);
        button_huy_Ord_Room = (Button) findViewById(R.id.my_button_huy_room);
        stextViewUser = (TextView) findViewById(R.id.text_user);
        sbutton_ord_day = (TextView) findViewById(R.id.my_ord_day);
        stextViewAdd = (TextView) findViewById(R.id.textadd);
        stextViewMota  = (TextView) findViewById(R.id.text_mt);
        stextViewNgayThue  = (TextView) findViewById(R.id.textsn);
        stextViewSLP  = (TextView) findViewById(R.id.textsl);
        stextViewPrice = (TextView) findViewById(R.id.text_sum_ord_room_price);

        // nhận data từ intent
        key_ord_room =  getIntent().getStringExtra("key_ord_room");


        databaseReference= FirebaseDatabase.getInstance().getReference("Order").child("Order_Room");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    ROrd rOrd = dataSnapshot.getValue(ROrd.class);
                    if (dataSnapshot.getKey().equals(key_ord_room))
                    {
                        load_room(rOrd.key_room_ord);
                        key_user = rOrd.key_user_ord;
                        key_room = rOrd.key_room_ord;
                        load_user_name(key_user);
                        Toast.makeText(MyRoomActivity.this,""+rOrd.day_start,Toast.LENGTH_LONG).show();
                        stextViewSLP.setText(""+rOrd.sl_room_ord);
                        stextViewNgayThue.setText(""+rOrd.sn_room_ord);
                        sbutton_ord_day.setText(""+rOrd.day_start);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        stextViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyRoomActivity.this, KHActivity.class);
                intent.putExtra("key_user",key_user);
                startActivity(intent);
            }
        });
        loadL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                if (i<0){i=0;}
                load_temp(i);
            }
        });
        loadR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                load_temp(i);
            }
        });
        button_huy_Ord_Room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                req_time_now();
                databaseReference= FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Order").child("Order_Room").child(key_ord_room).child("status").setValue("hủy "+time_now);
                Intent intent = new Intent(MyRoomActivity.this, ManagerOrderActivity.class);
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

    private void load_user_name(final String key_user) {
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    User user = dataSnapshot.getValue(User.class);
                    if (dataSnapshot.getKey().equals(key_user)){
                        stextViewUser.setText(user.fullname);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void load_room(final String key_room_ord) {
        databaseReference= FirebaseDatabase.getInstance().getReference("Rooms");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Room room =dataSnapshot.getValue(Room.class);
                    if (dataSnapshot.getKey().equals(key_room_ord)){
                        img1=room.img1;
                        temp_img=img1;
                        img2=room.img2;
                        img3=room.img3;
                        stextViewAdd.setText(room.address);
                        stextViewMota.setText(room.detail);

                        int sum =Integer.parseInt(stextViewNgayThue.getText().toString())*Integer.parseInt(stextViewSLP.getText().toString())*Integer.parseInt(room.price);
                        stextViewPrice.setText(""+sum);
                    }
                    new MyRoomActivity.Load_img().execute(temp_img);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void load_temp(int i) {
        if (i%3==0){
            new MyRoomActivity.Load_img().execute(img1);
        }
        if (i%3==1){
            new MyRoomActivity.Load_img().execute(img2);
        }
        if (i%3==2){
            new MyRoomActivity.Load_img().execute(img3);
        }
    }

    private class Load_img extends AsyncTask<String,Void, Bitmap> {
        Bitmap bit_map= null;
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url= new URL(strings[0]);
                InputStream inputStream = url.openConnection().getInputStream();
                bit_map= BitmapFactory.decodeStream(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bit_map;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}