package com.example.kosbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kosbooking.adapter.ListOrdHomeAdapter;
import com.example.kosbooking.adapter.ListOrdTicketAdapter;
import com.example.kosbooking.modul.AirTicket;
import com.example.kosbooking.modul.ROrd;
import com.example.kosbooking.modul.Room;
import com.example.kosbooking.modul.TicketOrd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManagerOrderActivity extends AppCompatActivity {
    TextView textViewm0;
    Button btn_ticket,btn_room,btn_car,textFind,btn_pop;
    String temp = "AirTicket";
    String temp_type_air = "1";
    DatabaseReference databaseReference,databaseReference_user,main_databaseReference;
    ListView listView;
    String key_user,user_phone;
    Boolean admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_order);
        listView = (ListView) findViewById(R.id.id_list_main);
        btn_ticket = (Button) findViewById(R.id.id_ticket);
        btn_room = (Button) findViewById(R.id.id_room);
        btn_car = (Button) findViewById(R.id.id_car);
        textViewm0 = (TextView) findViewById(R.id.textM0);
        textFind = (Button) findViewById(R.id.textM1);
        btn_pop = (Button) findViewById(R.id.textpop);

        // nhận data từ intent
        key_user =  getIntent().getStringExtra("key_user");

        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(key_user);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user.position.equals("admin"))
                {
                    admin= true;
                    textViewm0.setText("Đơn hàng");
                    load_list(temp,admin);
                    btn_pop.setVisibility(View.VISIBLE);
                    textFind.setVisibility(View.VISIBLE);
                }
                else {
                    admin = false;
                    textViewm0.setText("Đơn hàng của tôi");
                    load_list(temp,admin);
                    textFind.setVisibility(View.INVISIBLE);
                    btn_pop.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = "AirTicket";
                temp_type_air = "1";
                btn_ticket.setBackgroundColor(Color.parseColor("#FF9292"));
                btn_room.setBackgroundColor(Color.parseColor("#FFEAEA"));
                btn_car.setBackgroundColor(Color.parseColor("#FFEAEA"));
//                btnair1.setVisibility(View.VISIBLE);
//                btnair2.setVisibility(View.VISIBLE);
                load_list(temp,admin);
            }
        });
        btn_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_ticket.setBackgroundColor(Color.parseColor("#FFEAEA"));
                btn_room.setBackgroundColor(Color.parseColor("#FF9292"));
                btn_car.setBackgroundColor(Color.parseColor("#FFEAEA"));
                temp="Rooms";
                load_list(temp,admin);
            }
        });
//        btn_pop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu popupMenu = new PopupMenu(ManagerOrderActivity.this, btn_pop);
//                final Menu menu = popupMenu.getMenu();
//                MenuItem menuItem1 = menu.add(1, 1, 1, "Tất cả");
//                if (temp.equals("AirTicket")){
//                    main_databaseReference= FirebaseDatabase.getInstance().getReference("Order").child("Order_AirTicket");
//                    main_databaseReference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull final DataSnapshot snapshot) {
//                            for (final DataSnapshot dataSnapshot: snapshot.getChildren())
//                            {
//                                final TicketOrd ticketOrd = dataSnapshot.getValue(TicketOrd.class);
//                                if (ticketOrd.status.equals("1")){
//                                    databaseReference_user= FirebaseDatabase.getInstance().getReference("Users").child(ticketOrd.key_user_ord);
//                                    databaseReference_user.addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
//                                            User user = snapshot2.getValue(User.class);
//                                            MenuItem menuItem1 = menu.add(1, 1, 1, ""+user.fullname);
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError error) {
//
//                                        }
//                                    });
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        Toast.makeText(
//                                ManagerOrderActivity.this,
//                                "" + item.getTitle(),
//                                Toast.LENGTH_SHORT
//                        ).show();
//                        btn_pop.setText(""+item.getTitle());
//                        return true;
//                    }
//                });
//                popupMenu.show();
//            }
//        });

        textFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog= new Dialog(ManagerOrderActivity.this);
                dialog.setContentView(R.layout.dialog_find);
                final EditText editTextsdt = (EditText) dialog.findViewById(R.id.editTextDiaSDT);
                Button buttonfind = (Button) dialog.findViewById(R.id.buttonDiaFind);
                buttonfind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user_phone = editTextsdt.getText().toString();
                        find_list(temp,admin,user_phone);
                    }
                });
                dialog.show();
            }
        });
    }

    private void find_list(String temp, final Boolean admin, final String user_phone) {
        if (temp.equals("AirTicket")){
            main_databaseReference= FirebaseDatabase.getInstance().getReference("Order").child("Order_AirTicket");
            main_databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot snapshot) {
                    final ArrayList<AirTicket> arrayList=new ArrayList<>();
                    for (final DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        final TicketOrd ticketOrd = dataSnapshot.getValue(TicketOrd.class);
                        if (admin==true){
                            databaseReference= FirebaseDatabase.getInstance().getReference("AirTicket");
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                    for (DataSnapshot dataSnapshot1: snapshot1.getChildren())
                                    {
                                        final AirTicket airTicket = dataSnapshot1.getValue(AirTicket.class);

                                        if (dataSnapshot1.getKey().equals(ticketOrd.key_ticket_ord) && ticketOrd.status.equals("1"))
                                        {
                                            databaseReference_user= FirebaseDatabase.getInstance().getReference("Users").child(ticketOrd.key_user_ord);
                                            databaseReference_user.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                                    User user = snapshot2.getValue(User.class);
                                                    if (user.phonenumber.equals(user_phone))
                                                    {
                                                        btn_pop.setText(""+user.fullname);
                                                        airTicket.type_of_ticket=dataSnapshot.getKey();
                                                        int temp = Integer.parseInt(ticketOrd.sl_pt_ord)+Integer.parseInt(ticketOrd.sl_tg_ord)+Integer.parseInt(ticketOrd.sl_n1_ord);
                                                        airTicket.name = "" + temp;
                                                        arrayList.add(airTicket);
                                                        ListOrdTicketAdapter listOrdTicketAdapter = new ListOrdTicketAdapter(ManagerOrderActivity.this,R.layout.item_air_ord,arrayList);
                                                        listView.setAdapter(listOrdTicketAdapter);
                                                    }
//                                                    else {
//                                                        Toast.makeText(ManagerOrderActivity.this, "Không tìm thấy đơn hàng",Toast.LENGTH_LONG).show();
//                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else {
                            if (ticketOrd.key_user_ord.equals(key_user)){
                                databaseReference= FirebaseDatabase.getInstance().getReference("AirTicket");
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                        for (DataSnapshot dataSnapshot1: snapshot1.getChildren())
                                        {
                                            AirTicket airTicket = dataSnapshot1.getValue(AirTicket.class);
                                            if (dataSnapshot1.getKey().equals(ticketOrd.key_ticket_ord) && ticketOrd.status.equals("1"))
                                            {
                                                airTicket.type_of_ticket=dataSnapshot.getKey();
                                                int temp = Integer.parseInt(ticketOrd.sl_pt_ord)+Integer.parseInt(ticketOrd.sl_tg_ord)+Integer.parseInt(ticketOrd.sl_n1_ord);
                                                airTicket.name = "" + temp;
                                                arrayList.add(airTicket);
                                                ListOrdTicketAdapter listOrdTicketAdapter = new ListOrdTicketAdapter(ManagerOrderActivity.this,R.layout.item_air_ord,arrayList);
                                                listView.setAdapter(listOrdTicketAdapter);
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
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if (temp.equals("Rooms")){
            main_databaseReference= FirebaseDatabase.getInstance().getReference("Order").child("Order_Room");
            main_databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot snapshot) {
                    final ArrayList<Room> arrayList=new ArrayList<>();
                    for (final DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        final ROrd rOrd = dataSnapshot.getValue(ROrd.class);
                        if (admin==true){
                            databaseReference= FirebaseDatabase.getInstance().getReference("Rooms");
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                    for (DataSnapshot dataSnapshot1: snapshot1.getChildren())
                                    {
                                        final Room room = dataSnapshot1.getValue(Room.class);
                                        if (dataSnapshot1.getKey().equals(rOrd.key_room_ord)  && rOrd.status.equals("1"))
                                        {
                                            databaseReference_user= FirebaseDatabase.getInstance().getReference("Users").child(rOrd.key_user_ord);
                                            databaseReference_user.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                                    User user = snapshot2.getValue(User.class);
                                                    String err= "";
                                                    if (user.phonenumber.equals(user_phone))
                                                    {
                                                        err = err + user.fullname;
                                                        btn_pop.setText(""+user.fullname);
                                                        room.detail=dataSnapshot.getKey();
                                                        room.name = rOrd.sl_room_ord+" phòng - "+rOrd.sn_room_ord+" ngày";
                                                        arrayList.add(room);
                                                        ListOrdHomeAdapter listOrdHomeAdapter = new ListOrdHomeAdapter(ManagerOrderActivity.this,R.layout.item_room_ord,arrayList);
                                                        listView.setAdapter(listOrdHomeAdapter);
                                                    }
                                                    if(err.length()==0)
                                                    {
                                                        Toast.makeText(ManagerOrderActivity.this, "Không tìm thấy đơn hàng",Toast.LENGTH_LONG).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else {
                            if (rOrd.key_user_ord.equals(key_user)){
                                databaseReference= FirebaseDatabase.getInstance().getReference("Rooms");
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                        for (DataSnapshot dataSnapshot1: snapshot1.getChildren())
                                        {
                                            final Room room = dataSnapshot1.getValue(Room.class);
                                            if (dataSnapshot1.getKey().equals(rOrd.key_room_ord)  && rOrd.status.equals("1"))
                                            {
                                                room.detail=dataSnapshot.getKey();
                                                room.name = rOrd.sl_room_ord+" phòng - "+rOrd.sn_room_ord+" ngày";
                                                arrayList.add(room);
                                                ListOrdHomeAdapter listOrdHomeAdapter = new ListOrdHomeAdapter(ManagerOrderActivity.this,R.layout.item_room_ord,arrayList);
                                                listView.setAdapter(listOrdHomeAdapter);
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
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void load_list(String temp, final Boolean admin) {
        if (temp.equals("AirTicket")){
            main_databaseReference= FirebaseDatabase.getInstance().getReference("Order").child("Order_AirTicket");
            main_databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot snapshot) {
                    final ArrayList<AirTicket> arrayList=new ArrayList<>();
                    for (final DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        final TicketOrd ticketOrd = dataSnapshot.getValue(TicketOrd.class);
                        if (admin==true){
                            databaseReference= FirebaseDatabase.getInstance().getReference("AirTicket");
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                    for (DataSnapshot dataSnapshot1: snapshot1.getChildren())
                                    {
                                        AirTicket airTicket = dataSnapshot1.getValue(AirTicket.class);
                                        if (dataSnapshot1.getKey().equals(ticketOrd.key_ticket_ord) && ticketOrd.status.equals("1"))
                                        {
                                            airTicket.type_of_ticket=dataSnapshot.getKey();
                                            int temp = Integer.parseInt(ticketOrd.sl_pt_ord)+Integer.parseInt(ticketOrd.sl_tg_ord)+Integer.parseInt(ticketOrd.sl_n1_ord);
                                            airTicket.name = "" + temp;
                                            arrayList.add(airTicket);
                                            ListOrdTicketAdapter listOrdTicketAdapter = new ListOrdTicketAdapter(ManagerOrderActivity.this,R.layout.item_air_ord,arrayList);
                                            listView.setAdapter(listOrdTicketAdapter);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else {
                            if (ticketOrd.key_user_ord.equals(key_user)){
                                databaseReference= FirebaseDatabase.getInstance().getReference("AirTicket");
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                        for (DataSnapshot dataSnapshot1: snapshot1.getChildren())
                                        {
                                            AirTicket airTicket = dataSnapshot1.getValue(AirTicket.class);
                                            if (dataSnapshot1.getKey().equals(ticketOrd.key_ticket_ord) && ticketOrd.status.equals("1"))
                                            {
                                                airTicket.type_of_ticket=dataSnapshot.getKey();
                                                int temp = Integer.parseInt(ticketOrd.sl_pt_ord)+Integer.parseInt(ticketOrd.sl_tg_ord)+Integer.parseInt(ticketOrd.sl_n1_ord);
                                                airTicket.name = "" + temp;
                                                arrayList.add(airTicket);
                                                ListOrdTicketAdapter listOrdTicketAdapter = new ListOrdTicketAdapter(ManagerOrderActivity.this,R.layout.item_air_ord,arrayList);
                                                listView.setAdapter(listOrdTicketAdapter);
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
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if (temp.equals("Rooms")){
            main_databaseReference= FirebaseDatabase.getInstance().getReference("Order").child("Order_Room");
            main_databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot snapshot) {
                    final ArrayList<Room> arrayList=new ArrayList<>();
                    for (final DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        final ROrd rOrd = dataSnapshot.getValue(ROrd.class);
                        if (admin==true){
                            databaseReference= FirebaseDatabase.getInstance().getReference("Rooms");
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                    for (DataSnapshot dataSnapshot1: snapshot1.getChildren())
                                    {
                                        final Room room = dataSnapshot1.getValue(Room.class);
                                        if (dataSnapshot1.getKey().equals(rOrd.key_room_ord)  && rOrd.status.equals("1"))
                                        {
                                            room.detail=dataSnapshot.getKey();
                                            room.name = rOrd.sl_room_ord+" phòng - "+rOrd.sn_room_ord+" ngày";
                                            arrayList.add(room);
                                            ListOrdHomeAdapter listOrdHomeAdapter = new ListOrdHomeAdapter(ManagerOrderActivity.this,R.layout.item_room_ord,arrayList);
                                            listView.setAdapter(listOrdHomeAdapter);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else {
                            if (rOrd.key_user_ord.equals(key_user)){
                                databaseReference= FirebaseDatabase.getInstance().getReference("Rooms");
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot1) {

                                        for (DataSnapshot dataSnapshot1: snapshot1.getChildren())
                                        {
                                            final Room room = dataSnapshot1.getValue(Room.class);
                                            if (dataSnapshot1.getKey().equals(rOrd.key_room_ord)  && rOrd.status.equals("1"))
                                            {
                                                room.detail=dataSnapshot.getKey();
                                                room.name = rOrd.sl_room_ord+" phòng - "+rOrd.sn_room_ord+" ngày";
                                                arrayList.add(room);
                                                ListOrdHomeAdapter listOrdHomeAdapter = new ListOrdHomeAdapter(ManagerOrderActivity.this,R.layout.item_room_ord,arrayList);
                                                listView.setAdapter(listOrdHomeAdapter);
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
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}