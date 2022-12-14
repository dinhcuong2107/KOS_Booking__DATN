package com.example.kosbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.kosbooking.adapter.ListAir1Adapter;
import com.example.kosbooking.adapter.ListAir2Adapter;
import com.example.kosbooking.adapter.ListHomeAdapter;
import com.example.kosbooking.modul.AirTicket;
import com.example.kosbooking.modul.Room;
import com.example.kosbooking.oderActivity.OrderRoomActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
Button btnair1,btnair2;
Button btnfunction,btn_destination;
LinearLayout btn_add_main, btn_search;
String temp = "Rooms";
String temp_type_air = "1";
DatabaseReference databaseReference;
ListView listView;
String key_user,avatar,fullname,dateofbirth,sex,CMND,phonenumber,email,password,address,position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_destination = (Button) findViewById(R.id.buttonDestination);
        btn_search = (LinearLayout) findViewById(R.id.buttonSearch);
        btn_add_main = (LinearLayout) findViewById(R.id.buttonAddMain);

        btnair1 = (Button) findViewById(R.id.choose_air1);
        btnair2 = (Button) findViewById(R.id.choose_air2);
        btnfunction = (Button) findViewById(R.id.buttonfun);

        listView = (ListView) findViewById(R.id.list_main);



        btn_add_main.setVisibility(View.INVISIBLE);
        btn_search.setVisibility(View.INVISIBLE);
        btn_destination.setVisibility(View.INVISIBLE);
        btnair1.setVisibility(View.INVISIBLE);
        btnair2.setVisibility(View.INVISIBLE);

        // nh???n data t??? intent
        key_user =  getIntent().getStringExtra("key_user");
        avatar = getIntent().getStringExtra("avatar");
        fullname = getIntent().getStringExtra("fullname");
        dateofbirth = getIntent().getStringExtra("dateofbirth");
        sex = getIntent().getStringExtra("sex");
        CMND = getIntent().getStringExtra("CMND");
        phonenumber = getIntent().getStringExtra("phonenumber");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        address = getIntent().getStringExtra("address");
        position = getIntent().getStringExtra("position");

        load_list(temp,temp_type_air);

        if (position.equals("admin"))
        {
            btn_add_main.setVisibility(View.VISIBLE);
        }

        btn_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        btn_add_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temp.equals("AirTicket")){
                    Intent intent = new Intent(MainActivity.this,CreatedTicketActivity.class);
                    startActivity(intent);
                }
                if (temp.equals("Rooms")){
                    Intent intent = new Intent(MainActivity.this,CreatedRoomActivity.class);
                    startActivity(intent);
                }
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_destination.getText().toString().length()>0)
                {
                    databaseReference= FirebaseDatabase.getInstance().getReference("AirTicket");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<AirTicket> arrayList=new ArrayList<>();
                            for (DataSnapshot dataSnapshot: snapshot.getChildren())
                            {
                                AirTicket airTicket = dataSnapshot.getValue(AirTicket.class);
                                if (airTicket.to.equals(btn_destination.getText().toString()))
                                {
                                    if (Integer.parseInt(airTicket.ticket_pt)>=1|Integer.parseInt(airTicket.ticket_tg)>=1|Integer.parseInt(airTicket.ticket_n1)>=1)
                                    {
                                        arrayList.add(airTicket);
                                        ListAir1Adapter listAir1Adapter = new ListAir1Adapter(MainActivity.this,R.layout.item_air1,arrayList);
                                        listView.setAdapter(listAir1Adapter);
                                    }
                                }
                                else {
                                    ListAir1Adapter listAir1Adapter = new ListAir1Adapter(MainActivity.this,R.layout.item_air1,arrayList);
                                    listView.setAdapter(listAir1Adapter);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else {
                    pop();
                    Toast.makeText(MainActivity.this, "Ch???n n??i mu???n ?????n", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnfunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_fun(fullname);
            }
        });

        btnair1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp_type_air = "1";
                load_list(temp,temp_type_air);
            }
        });
        btnair2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp_type_air = "2";

                load_list(temp,temp_type_air);
            }
        });
    }

    private void pop() {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, btn_destination);
        Menu menu = popupMenu.getMenu();
        MenuItem menuItem1 = menu.add(1, 1, 1, "S??n bay Qu???c t??? C???n Th??");
        MenuItem menuItem2 = menu.add(1, 1, 1, "S??n bay Qu???c t??? ???? N???ng");
        MenuItem menuItem3 = menu.add(1, 1, 1, "S??n bay Qu???c t??? C??t Bi ??? Ha??i Pho??ng");
        MenuItem menuItem4 = menu.add(1, 1, 1, "S??n bay Qu???c t??? N???i B??i ??? Ha?? N????i");
        MenuItem menuItem5 = menu.add(1, 1, 1, "S??n bay Qu???c t??? T??n S??n Nh???t - HCM");
        MenuItem menuItem6 = menu.add(1, 1, 1, "S??n bay Qu???c t??? Cam Ranh - Kh??nh H??a");
        MenuItem menuItem7 = menu.add(1, 1, 1, "S??n bay Qu???c t??? Ph?? Qu???c - Ki??n Giang");
        MenuItem menuItem8 = menu.add(1, 1, 1, "S??n bay Qu???c t??? Vinh ??? Ngh???? An");
        MenuItem menuItem9 = menu.add(1, 1, 1, "S??n bay Qu???c t??? Ph?? B??i ??? Hu????");
        MenuItem menuItem10 = menu.add(1, 1, 1, "S??n bay C??n ?????o	- B?? R???a-V??ng T??u");
        MenuItem menuItem11 = menu.add(1, 1, 1, "S??n bay Na?? Sa??n - S??n La");
        MenuItem menuItem12 = menu.add(1, 1, 1, "S??n bay Ph?? C??t ??? Bi??nh ??i??nh");
        MenuItem menuItem13 = menu.add(1, 1, 1, "S??n bay C?? Mau");
        MenuItem menuItem14 = menu.add(1, 1, 1, "S??n bay Bu??n Ma Thu???t - ?????k L???k");
        MenuItem menuItem15 = menu.add(1, 1, 1, "S??n bay ??i???n Bi??n Ph??? - ??i???n Bi??n");
        MenuItem menuItem16 = menu.add(1, 1, 1, "S??n bay Pleiku ??? Gia Lai");
        MenuItem menuItem17 = menu.add(1, 1, 1, "S??n bay R???ch Gi?? ??? Ki??n Giang");
        MenuItem menuItem18 = menu.add(1, 1, 1, "S??n bay Li??n Kh????ng ??? ??a?? La??t (L??m ?????ng)");
        MenuItem menuItem19 = menu.add(1, 1, 1, "S??n bay Tuy H??a ??? Phu?? Y??");
        MenuItem menuItem20 = menu.add(1, 1, 1, "S??n bay ?????ng H???i ??? Qua??ng Bi??nh");
        MenuItem menuItem21 = menu.add(1, 1, 1, "S??n bay Chu Lai ??? Qua??ng Nam");
        MenuItem menuItem22 = menu.add(1, 1, 1, "S??n bay Th??? Xu??n ??? Thanh Ho??a");
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(
                        MainActivity.this,
                        "" + item.getTitle(),
                        Toast.LENGTH_SHORT
                ).show();
                btn_destination.setText(""+item.getTitle());
                return true;
            }
        });
        popupMenu.show();
    }

    private void show_fun(String full_name) {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.dialog_fun_main, null);
        final LinearLayout layout_air = (LinearLayout) alertLayout.findViewById(R.id.dia11);
        final LinearLayout layout_zoom = (LinearLayout) alertLayout.findViewById(R.id.dia12);
        final LinearLayout layout_car = (LinearLayout) alertLayout.findViewById(R.id.dia13);
        final LinearLayout layout_inf = (LinearLayout) alertLayout.findViewById(R.id.dia21);
        final LinearLayout layout_shopping = (LinearLayout) alertLayout.findViewById(R.id.dia22);
        final LinearLayout layout_out = (LinearLayout) alertLayout.findViewById(R.id.dia23);

        layout_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        layout_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManagerOrderActivity.class);
                intent.putExtra("key_user",key_user);
                startActivity(intent);
            }
        });
        layout_air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = "AirTicket";
                load_list(temp,temp_type_air);
                btn_destination.setVisibility(View.VISIBLE);
                btn_search.setVisibility(View.VISIBLE);
                btnair1.setVisibility(View.VISIBLE);
                btnair2.setVisibility(View.VISIBLE);
            }
        });
        layout_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_search.setVisibility(View.INVISIBLE);
                btn_destination.setVisibility(View.INVISIBLE);
                btnair1.setVisibility(View.INVISIBLE);
                btnair2.setVisibility(View.INVISIBLE);
                temp="Rooms";
                load_list(temp,temp_type_air);
            }
        });
        layout_inf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InfActivity.class);
                intent.putExtra("key_user",key_user);
                intent.putExtra("avatar",avatar);
                intent.putExtra("fullname",fullname);
                intent.putExtra("dateofbirth",dateofbirth);
                intent.putExtra("sex",sex);
                intent.putExtra("CMND",CMND);
                intent.putExtra("phonenumber",phonenumber);
                intent.putExtra("position",position);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                intent.putExtra("address",address);
                startActivity(intent);
            }
        });
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(""+full_name);
        alert.setView(alertLayout);
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void load_list(final String temp, final String temp_type_air) {
        if (temp.equals("AirTicket") && temp_type_air.equals("1")){
            databaseReference= FirebaseDatabase.getInstance().getReference("AirTicket");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<AirTicket> arrayList=new ArrayList<>();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        AirTicket airTicket = dataSnapshot.getValue(AirTicket.class);
                        if (airTicket.type_of_ticket.equals(temp_type_air))
                        {
                            if (Integer.parseInt(airTicket.ticket_pt)>=1|Integer.parseInt(airTicket.ticket_tg)>=1|Integer.parseInt(airTicket.ticket_n1)>=1)
                            {
                                airTicket.type_of_ticket=key_user;
                                arrayList.add(airTicket);
                                ListAir1Adapter listAir1Adapter = new ListAir1Adapter(MainActivity.this,R.layout.item_air1,arrayList);
                                listView.setAdapter(listAir1Adapter);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if (temp.equals("AirTicket") && temp_type_air.equals("2")){
            databaseReference= FirebaseDatabase.getInstance().getReference("AirTicket");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<AirTicket> arrayList=new ArrayList<>();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        AirTicket airTicket = dataSnapshot.getValue(AirTicket.class);
                        if (airTicket.type_of_ticket.equals(temp_type_air))
                        {
                            if (Integer.parseInt(airTicket.ticket_pt)>=1|Integer.parseInt(airTicket.ticket_tg)>=1|Integer.parseInt(airTicket.ticket_n1)>=1)
                            {
                                airTicket.type_of_ticket=key_user;
                                arrayList.add(airTicket);
                                ListAir2Adapter listAir2Adapter = new ListAir2Adapter(MainActivity.this,R.layout.item_air2,arrayList);
                                listView.setAdapter(listAir2Adapter);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        if (temp.equals("Rooms")){
            databaseReference= FirebaseDatabase.getInstance().getReference("Rooms");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Room> arrayList=new ArrayList<>();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        Room room = dataSnapshot.getValue(Room.class);
                        if (Integer.parseInt(room.slroom)>=1)
                        {
                            room.detail=key_user;
                            arrayList.add(room);
                            ListHomeAdapter listHomeAdapter = new ListHomeAdapter(MainActivity.this,R.layout.item_room,arrayList);
                            listView.setAdapter(listHomeAdapter);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }
}