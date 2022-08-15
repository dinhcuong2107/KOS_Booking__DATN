package com.example.kosbooking.oderActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kosbooking.CreatedRoomActivity;
import com.example.kosbooking.CreatedTicketActivity;
import com.example.kosbooking.InfActivity;
import com.example.kosbooking.LoginActivity;
import com.example.kosbooking.MainActivity;
import com.example.kosbooking.ManagerOrderActivity;
import com.example.kosbooking.R;
import com.example.kosbooking.User;
import com.example.kosbooking.modul.ROrd;
import com.example.kosbooking.modul.Room;
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

public class OrderRoomActivity extends AppCompatActivity {
String temp_img,key_room,img1,img2,img3,time_now;
String key_user;
Button loadL,loadR,tsl,gsl,td,gd,button_ord_day;
Button button_Ord_Room;
DatabaseReference databaseReference;
ImageView imageView;
TextView textViewAdd,textViewMota,textViewSLP,textViewNgayThue,textViewPrice,t1,t2;
int i=0,sl=1,pri,sl_max,sl_ngay=1,sl_new;

    Calendar calendar = Calendar.getInstance();
    int hour =calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH);
    int year = calendar.get(Calendar.YEAR);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_room);

        imageView = (ImageView) findViewById(R.id.viewimg);
        loadL = (Button) findViewById(R.id.btnleft);
        loadR = (Button) findViewById(R.id.btnright);
        tsl = (Button) findViewById(R.id.buttont);
        gsl = (Button) findViewById(R.id.buttong);
        td = (Button) findViewById(R.id.buttontd);
        gd = (Button) findViewById(R.id.buttongd);
        button_ord_day = (Button) findViewById(R.id.ord_day);
        button_Ord_Room = (Button) findViewById(R.id.button_order_room);

        t1=(TextView) findViewById(R.id.an_r1);
        t2= (TextView) findViewById(R.id.an_r2);
        textViewAdd = (TextView) findViewById(R.id.ord_add);
        textViewMota  = (TextView) findViewById(R.id.ord_mota);
        textViewNgayThue  = (TextView) findViewById(R.id.ord_r2);
        textViewSLP  = (TextView) findViewById(R.id.ord_r1);
        textViewPrice = (TextView) findViewById(R.id.ord_r_price);

        // nhận data từ intent
        key_user =  getIntent().getStringExtra("key_user");
        temp_img =  getIntent().getStringExtra("temp_img1");
        sl_max = Integer.parseInt(getIntent().getStringExtra("sl_room"));
        pri = Integer.parseInt(getIntent().getStringExtra("price_room"));

        button_Ord_Room.setVisibility(View.INVISIBLE);

        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(key_user);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user.position.equals("admin"))
                {
                    button_Ord_Room.setVisibility(View.INVISIBLE);
                    gsl.setVisibility(View.INVISIBLE);
                    tsl.setVisibility(View.INVISIBLE);
                    gd.setVisibility(View.INVISIBLE);
                    td.setVisibility(View.INVISIBLE);
                    textViewPrice.setVisibility(View.INVISIBLE);
                    textViewNgayThue.setVisibility(View.INVISIBLE);
                    t1.setVisibility(View.INVISIBLE);
                    t2.setVisibility(View.INVISIBLE);
                    button_ord_day.setVisibility(View.INVISIBLE);
                    textViewSLP.setText(""+sl_max);

                }
                else {
                    button_Ord_Room.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        new OrderRoomActivity.Load_img().execute(temp_img);
        // load data
        databaseReference= FirebaseDatabase.getInstance().getReference("Rooms");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Room room = dataSnapshot.getValue(Room.class);
                    if (room.img1.equals(temp_img)){
                        key_room= dataSnapshot.getKey();
                        img1 = room.img1;
                        img2 = room.img2;
                        img3 = room.img3;
                        textViewAdd.setText(""+room.address);
                        textViewMota.setText(""+room.detail);
                        textViewPrice.setText(""+room.price);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        tsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl++;
                if (sl>sl_max)
                {
                    sl=sl_max;
                }
                textViewSLP.setText(""+sl);
                sum_pri_room();
            }
        });
        gsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl--;
                if (sl<1){sl=1;}
                textViewSLP.setText(""+sl);
                sum_pri_room();
            }
        });
        td.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl_ngay++;
                textViewNgayThue.setText(""+sl_ngay);
                sum_pri_room();
            }
        });
        gd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl_ngay--;
                if (sl_ngay<1){sl_ngay=1;}
                textViewNgayThue.setText(""+sl_ngay);
                sum_pri_room();
            }
        });
        loadL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                if (i<0){i=0;}
                load_img(i);
            }
        });
        loadR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                load_img(i);
            }
        });
        button_Ord_Room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button_ord_day.getText().toString().length()>0){
                    ROrd rOrd = new ROrd();
                    rOrd.key_user_ord=key_user;
                    rOrd.key_room_ord = key_room;
                    req_time_now();
                    rOrd.time_ord=time_now;
                    rOrd.day_start = button_ord_day.getText().toString();
                    rOrd.sl_room_ord = textViewSLP.getText().toString();
                    rOrd.sn_room_ord = textViewNgayThue.getText().toString();
                    rOrd.status = "1";
                    databaseReference= FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("Order").child("Order_Room").push().setValue(rOrd, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error==null){
                                sl_new = sl_max - Integer.parseInt(textViewSLP.getText().toString());
                                String string_new = ""+sl_new;
                                databaseReference= FirebaseDatabase.getInstance().getReference();
                                databaseReference.child("Rooms").child(key_room).child("slroom").setValue(string_new);
                                Toast.makeText(OrderRoomActivity.this, "hoàn thành", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(OrderRoomActivity.this, ManagerOrderActivity.class);
                                intent.putExtra("key_user",key_user);
                                startActivity(intent);
                            }else {
                                Toast.makeText(OrderRoomActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(OrderRoomActivity.this,"Chọn ngày bắt đầu",Toast.LENGTH_LONG).show();
                }
            }
        });
        button_ord_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create DatePickerDialog (Spinner Mode):
                DatePickerDialog datePickerDialog = new DatePickerDialog(OrderRoomActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        dateSetListener, year, month, day);

                // Show
                datePickerDialog.show();
            }
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    String day_back = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    button_ord_day.setText(day_back);
                }
            };
        });
    }

    private void sum_pri_room() {
        int sum = pri*sl*sl_ngay;
        textViewPrice.setText(""+sum);
    }

    private void req_time_now(){
        android.icu.util.Calendar mcurrentTime = android.icu.util.Calendar.getInstance();
        int hour_now = mcurrentTime.get(android.icu.util.Calendar.HOUR_OF_DAY);
        int minute_now = mcurrentTime.get(android.icu.util.Calendar.MINUTE);
        time_now = ""+hour_now+":"+minute_now+"-"+day+"/"+month+"/"+year;
    }

    private void load_img(int i) {
        if (i%3==0){
            new OrderRoomActivity.Load_img().execute(img1);
        }
        if (i%3==1){
            new OrderRoomActivity.Load_img().execute(img2);
        }
        if (i%3==2){
            new OrderRoomActivity.Load_img().execute(img3);
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
