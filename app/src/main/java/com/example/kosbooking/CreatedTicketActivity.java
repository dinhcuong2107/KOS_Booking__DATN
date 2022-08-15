package com.example.kosbooking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.kosbooking.modul.AirTicket;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ObjectInputStream;
import java.util.Calendar;

public class CreatedTicketActivity extends AppCompatActivity {
DatabaseReference databaseReference;
RadioButton radioButton1d,radioButton2d;
TextView textViewNV,textViewi1,textViewi2,textViewi3;
EditText editTextsi3,editTextsi2,editTextsi1,editTextid,editTextnameari;
Button buttonTimeBack,buttonDayBack,buttonTimeStart,buttonDayStart,buttonCreatticket;
Button buttont1,buttont2,buttont3,buttong1,buttong2,buttong3,btnF,btnT;
String time_start="",day_start="",time_back="",day_back="";
String type_of_ticket="1";
int i1,i2,i3;
    TimePickerDialog.OnTimeSetListener timeSetListener,timeSetListenerback;
//    android.icu.util.Calendar mcurrentTime = android.icu.util.Calendar.getInstance();
//    int hour = mcurrentTime.get(android.icu.util.Calendar.HOUR_OF_DAY);
//    int minute = mcurrentTime.get(android.icu.util.Calendar.MINUTE);

    Calendar calendar = Calendar.getInstance();
    int hour =calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE)    ;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH);
    int year = calendar.get(Calendar.YEAR);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_ticket);
        radioButton1d = (RadioButton) findViewById(R.id.radioButton1D);
        radioButton2d = (RadioButton) findViewById(R.id.radioButton2D);

        textViewNV = (TextView) findViewById(R.id.textViewNgayVe);
        textViewi1 = (TextView) findViewById(R.id.textViewi1);
        textViewi2 = (TextView) findViewById(R.id.textViewi2);
        textViewi3 = (TextView) findViewById(R.id.textViewi3);

        editTextid = (EditText) findViewById(R.id.edtID);
        editTextnameari = (EditText) findViewById(R.id.edtName);
        editTextsi1 = (EditText) findViewById(R.id.priceHpt);
        editTextsi2 = (EditText) findViewById(R.id.priceHtg);
        editTextsi3 = (EditText) findViewById(R.id.priceH1);

        buttonCreatticket = (Button) findViewById(R.id.buttoncreatticket);
        btnF = (Button) findViewById(R.id.btnForm);
        btnT = (Button) findViewById(R.id.btnTo);
        buttont1 = (Button) findViewById(R.id.btnt1);
        buttont2 = (Button) findViewById(R.id.btnt2);
        buttont3 = (Button) findViewById(R.id.btnt3);
        buttong1 = (Button) findViewById(R.id.btng1);
        buttong2 = (Button) findViewById(R.id.btng2);
        buttong3 = (Button) findViewById(R.id.btng3);

        buttonTimeStart = (Button) findViewById(R.id.btnTimeNgayDi);
        buttonDayStart = (Button) findViewById(R.id.btnNgayDi);
        buttonTimeBack = (Button) findViewById(R.id.btnTimeNgayVe);
        buttonDayBack = (Button) findViewById(R.id.btnNgayVe);
        radioButton1d.setChecked(true);
        textViewNV.setVisibility(View.INVISIBLE);
        buttonTimeBack.setVisibility(View.INVISIBLE);
        buttonDayBack.setVisibility(View.INVISIBLE);

        radioButton1d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_of_ticket = "1";
                buttonTimeBack.setText("");
                buttonDayBack.setText("");
                textViewNV.setVisibility(View.INVISIBLE);
                buttonTimeBack.setVisibility(View.INVISIBLE);
                buttonDayBack.setVisibility(View.INVISIBLE);
            }
        });
        radioButton2d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type_of_ticket = "2";
                textViewNV.setVisibility(View.VISIBLE);
                buttonTimeBack.setVisibility(View.VISIBLE);
                buttonDayBack.setVisibility(View.VISIBLE);
            }
        });
        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(CreatedTicketActivity.this, btnF);
                Menu menu = popupMenu.getMenu();
                MenuItem menuItem1 = menu.add(1, 1, 1, "Sân bay Quốc tế Cần Thơ");
                MenuItem menuItem2 = menu.add(1, 1, 1, "Sân bay Quốc tế Đà Nẵng");
                MenuItem menuItem3 = menu.add(1, 1, 1, "Sân bay Quốc tế Cát Bi – Hải Phòng");
                MenuItem menuItem4 = menu.add(1, 1, 1, "Sân bay Quốc tế Nội Bài – Hà Nội");
                MenuItem menuItem5 = menu.add(1, 1, 1, "Sân bay Quốc tế Tân Sơn Nhất - HCM");
                MenuItem menuItem6 = menu.add(1, 1, 1, "Sân bay Quốc tế Cam Ranh - Khánh Hòa");
                MenuItem menuItem7 = menu.add(1, 1, 1, "Sân bay Quốc tế Phú Quốc - Kiên Giang");
                MenuItem menuItem8 = menu.add(1, 1, 1, "Sân bay Quốc tế Vinh – Nghệ An");
                MenuItem menuItem9 = menu.add(1, 1, 1, "Sân bay Quốc tế Phú Bài – Huế");
                MenuItem menuItem10 = menu.add(1, 1, 1, "Sân bay Côn Đảo	- Bà Rịa-Vũng Tàu");
                MenuItem menuItem11 = menu.add(1, 1, 1, "Sân bay Nà Sản - Sơn La");
                MenuItem menuItem12 = menu.add(1, 1, 1, "Sân bay Phù Cát – Bình Định");
                MenuItem menuItem13 = menu.add(1, 1, 1, "Sân bay Cà Mau");
                MenuItem menuItem14 = menu.add(1, 1, 1, "Sân bay Buôn Ma Thuột - Đắk Lắk");
                MenuItem menuItem15 = menu.add(1, 1, 1, "Sân bay Điện Biên Phủ - Điện Biên");
                MenuItem menuItem16 = menu.add(1, 1, 1, "Sân bay Pleiku – Gia Lai");
                MenuItem menuItem17 = menu.add(1, 1, 1, "Sân bay Rạch Giá – Kiên Giang");
                MenuItem menuItem18 = menu.add(1, 1, 1, "Sân bay Liên Khương – Đà Lạt (Lâm Đồng)");
                MenuItem menuItem19 = menu.add(1, 1, 1, "Sân bay Tuy Hòa – Phú Yê");
                MenuItem menuItem20 = menu.add(1, 1, 1, "Sân bay Đồng Hới – Quảng Bình");
                MenuItem menuItem21 = menu.add(1, 1, 1, "Sân bay Chu Lai – Quảng Nam");
                MenuItem menuItem22 = menu.add(1, 1, 1, "Sân bay Thọ Xuân – Thanh Hóa");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                CreatedTicketActivity.this,
                                "" + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        btnF.setText(""+item.getTitle());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(CreatedTicketActivity.this, btnT);
                Menu menu = popupMenu.getMenu();
                MenuItem menuItem1 = menu.add(1, 1, 1, "Sân bay Quốc tế Cần Thơ");
                MenuItem menuItem2 = menu.add(1, 1, 1, "Sân bay Quốc tế Đà Nẵng");
                MenuItem menuItem3 = menu.add(1, 1, 1, "Sân bay Quốc tế Cát Bi – Hải Phòng");
                MenuItem menuItem4 = menu.add(1, 1, 1, "Sân bay Quốc tế Nội Bài – Hà Nội");
                MenuItem menuItem5 = menu.add(1, 1, 1, "Sân bay Quốc tế Tân Sơn Nhất - HCM");
                MenuItem menuItem6 = menu.add(1, 1, 1, "Sân bay Quốc tế Cam Ranh - Khánh Hòa");
                MenuItem menuItem7 = menu.add(1, 1, 1, "Sân bay Quốc tế Phú Quốc - Kiên Giang");
                MenuItem menuItem8 = menu.add(1, 1, 1, "Sân bay Quốc tế Vinh – Nghệ An");
                MenuItem menuItem9 = menu.add(1, 1, 1, "Sân bay Quốc tế Phú Bài – Huế");
                MenuItem menuItem10 = menu.add(1, 1, 1, "Sân bay Côn Đảo	- Bà Rịa-Vũng Tàu");
                MenuItem menuItem11 = menu.add(1, 1, 1, "Sân bay Nà Sản - Sơn La");
                MenuItem menuItem12 = menu.add(1, 1, 1, "Sân bay Phù Cát – Bình Định");
                MenuItem menuItem13 = menu.add(1, 1, 1, "Sân bay Cà Mau");
                MenuItem menuItem14 = menu.add(1, 1, 1, "Sân bay Buôn Ma Thuột - Đắk Lắk");
                MenuItem menuItem15 = menu.add(1, 1, 1, "Sân bay Điện Biên Phủ - Điện Biên");
                MenuItem menuItem16 = menu.add(1, 1, 1, "Sân bay Pleiku – Gia Lai");
                MenuItem menuItem17 = menu.add(1, 1, 1, "Sân bay Rạch Giá – Kiên Giang");
                MenuItem menuItem18 = menu.add(1, 1, 1, "Sân bay Liên Khương – Đà Lạt (Lâm Đồng)");
                MenuItem menuItem19 = menu.add(1, 1, 1, "Sân bay Tuy Hòa – Phú Yê");
                MenuItem menuItem20 = menu.add(1, 1, 1, "Sân bay Đồng Hới – Quảng Bình");
                MenuItem menuItem21 = menu.add(1, 1, 1, "Sân bay Chu Lai – Quảng Nam");
                MenuItem menuItem22 = menu.add(1, 1, 1, "Sân bay Thọ Xuân – Thanh Hóa");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                CreatedTicketActivity.this,
                                "" + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        btnT.setText(""+item.getTitle());
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        buttonCreatticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_to_creattickket();
            }
        });
        buttonTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create TimePickerDialog:
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreatedTicketActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        timeSetListener, hour, minute, true);
                // Show
                timePickerDialog.show();
            }
        });
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                if (minute < 10 && hourOfDay >= 10){
                    time_start=hourOfDay +":0"+minute;
                }
                if (minute < 10 && hourOfDay < 10){
                    time_start="0"+hourOfDay +":0"+minute;
                }
                if (minute >= 10 && hourOfDay >= 10){
                    time_start=""+hourOfDay+":"+minute;
                }
                if (minute >= 10 && hourOfDay < 10){
                    time_start="0"+hourOfDay +":"+minute;
                }

                buttonTimeStart.setText(time_start);
            }
        };
        buttonTimeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create TimePickerDialog:
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreatedTicketActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        timeSetListenerback, hour, minute, true);
                // Show
                timePickerDialog.show();
            }
        });
        timeSetListenerback = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                if (minute < 10 && hourOfDay >= 10){
                    time_back=hourOfDay +":0"+minute;
                }
                if (minute < 10 && hourOfDay < 10){
                    time_back="0"+hourOfDay +":0"+minute;
                }
                if (minute >= 10 && hourOfDay >= 10){
                    time_back=""+hourOfDay+":"+minute;
                }
                if (minute >= 10 && hourOfDay < 10){
                    time_back="0"+hourOfDay +":"+minute;
                }

                buttonTimeBack.setText(time_back);
            }
        };
        buttonDayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create DatePickerDialog (Spinner Mode):
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreatedTicketActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        dateSetListener, year, month, day);

                // Show
                datePickerDialog.show();
            }
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    day_start = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    buttonDayStart.setText(day_start);
                }
            };
        });
        buttonDayBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create DatePickerDialog (Spinner Mode):
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreatedTicketActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        dateSetListener, year, month, day);

                // Show
                datePickerDialog.show();
            }
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    day_back = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    buttonDayBack.setText(day_back);
                }
            };
        });
        buttong1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i1--;
                if (i1<0){
                    i1=0;
                }
                textViewi1.setText(""+i1);
            }
        });
        buttont1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i1++;
                textViewi1.setText(""+i1);
            }
        });
        buttong2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2--;
                if (i2<0){
                    i2=0;
                }
                textViewi2.setText(""+i2);
            }
        });
        buttont2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2++;
                textViewi2.setText(""+i2);
            }
        });
        buttong3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i3--;
                if (i3<0){
                    i3=0;
                }
                textViewi3.setText(""+i3);
            }
        });
        buttont3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i3++;
                textViewi3.setText(""+i3);
            }
        });
    }

    private void check_to_creattickket() {
        String error="";
        if (i3>0 && editTextsi3.getText().toString().length() == 0 ){
            error="Nhập giá vé hạng nhất";
        }else if (i3==0){
            editTextsi3.setText("");
        }
        if (i2>0 && editTextsi2.getText().toString().length() == 0 ){
            error="Nhập giá vé hạng thương gia";
        }else if (i2==0){
            editTextsi2.setText("");
        }
        if (i1>0 && editTextsi1.getText().toString().length() == 0 ){
            error="Nhập giá vé hạng phổ thông";
        }else if (i1==0){
            editTextsi1.setText("");
        }
        if (i2==0 && i1==0 && i3==0){
            error="Nhập số vé";
        }
        if (radioButton2d.isChecked()==true && buttonDayStart.getText().toString().length()==0){
            error="Nhập ngày bay";
        }
        if (radioButton2d.isChecked()==true && buttonTimeStart.getText().toString().length()==0){
            error="Nhập thời gian bay";
        }
        if (radioButton2d.isChecked()==true && buttonDayBack.getText().toString().length()==0){
            error="Nhập ngày bay trở lại";
        }
        if (radioButton2d.isChecked()==true && buttonTimeBack.getText().toString().length()==0){
            error="Nhập thời gian bay trở lại";
        }
        if (radioButton1d.isChecked()==true && buttonDayStart.getText().toString().length()==0){
             error="Nhập ngày bay";
        }
        if (radioButton1d.isChecked()==true && buttonTimeStart.getText().toString().length()==0){
            error="Nhập thời gian bay";
        }
        if (btnF.getText().toString().equals(btnT.getText().toString())){
            error="Nhập đích trùng với điểm xuất phát";
        }
        if (btnF.getText().toString().length()==0){
            error="Nhập đích đến";
        }
        if (btnT.getText().toString().length()==0){
            error="Nhập điểm xuất phát";
        }
        if (editTextid.getText().toString().length()==0){
            error="Nhập Mã chuyến bay";
        }
        if (editTextnameari.getText().toString().length()==0){
            error="Nhập hãng hàng không";
        }
        Toast.makeText(CreatedTicketActivity.this, ""+error,Toast.LENGTH_LONG).show();
        if (error==""){
            creatAirTicket();
            Toast.makeText(CreatedTicketActivity.this, "tạo thành công",Toast.LENGTH_LONG).show();
        };
    }

    private void creatAirTicket() {
        AirTicket airTicket = new AirTicket();
        airTicket.name = editTextnameari.getText().toString();
        airTicket.air_code = editTextid.getText().toString();
        airTicket.type_of_ticket = type_of_ticket;
        airTicket.form = btnF.getText().toString();
        airTicket.to = btnT.getText().toString();
        airTicket.time_start = buttonTimeStart.getText().toString();
        airTicket.day_start = buttonDayStart.getText().toString();
        airTicket.time_comeback = buttonTimeBack.getText().toString();
        airTicket.day_comeback = buttonDayBack.getText().toString();
        if (editTextsi1.getText().toString().length()>0){
            airTicket.price_pt = editTextsi1.getText().toString();
        }else {
            airTicket.price_pt = "0";
        }
        airTicket.ticket_pt = textViewi1.getText().toString();
        if (editTextsi2.getText().toString().length()>0){
            airTicket.price_tg = editTextsi2.getText().toString();
        }else {
            airTicket.price_tg = "0";
        }
        airTicket.ticket_tg = textViewi2.getText().toString();
        if (editTextsi3.getText().toString().length()>0){
            airTicket.price_n1 = editTextsi3.getText().toString();
        }else {
            airTicket.price_n1 = "0";
        }
        airTicket.ticket_n1 = textViewi3.getText().toString();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("AirTicket").push().setValue(airTicket, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error==null){
                    Toast.makeText(CreatedTicketActivity.this, "hoàn thành", Toast.LENGTH_SHORT).show();
                    reset();
                    new_intent();
                }else {
                    Toast.makeText(CreatedTicketActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void new_intent() {
        Intent intent = new Intent(CreatedTicketActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void reset() {
        editTextnameari.getText().clear();
        editTextid.getText().clear();
        btnF.setText("");
        btnT.setText("");
        buttonTimeStart.setText("");
        buttonDayStart.setText("");
        buttonTimeBack.setText("");
        buttonDayBack.setText("");
        editTextsi1.setText("");
        editTextsi2.setText("");
        editTextsi3.setText("");
    }
}