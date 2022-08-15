package com.example.kosbooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kosbooking.R;
import com.example.kosbooking.modul.Room;
import com.example.kosbooking.oderActivity.MyRoomActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListOrdHomeAdapter extends ArrayAdapter<Room> {
    final String TAG = getClass().getSimpleName();
    String item_key;
    Context context;
    int resource;
    ArrayList<Room> arrayListContext;
    ListOrdHomeAdapter.SaveView saveView;
    public ListOrdHomeAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Room> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrayListContext = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_room_ord,parent,false);
            saveView = new SaveView();
            saveView.imageViewRoom = (ImageView) convertView.findViewById(R.id.id_img_room);
            saveView.button_choose_room = (Button) convertView.findViewById(R.id.id_btn_room);
            saveView.textViewTime = (TextView) convertView.findViewById(R.id.id_day_room);
            saveView.textViewprice = (TextView) convertView.findViewById(R.id.id_price_room);

            convertView.setTag(saveView);
        }else {
            saveView = (SaveView) convertView.getTag();
        }

        final Room room = arrayListContext.get(position);
        item_key=room.detail;
        Picasso.with(context).load(room.img1).into(saveView.imageViewRoom);
        saveView.textViewprice.setText(item_key);
        saveView.textViewTime.setText(room.name);

        saveView.button_choose_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, MyRoomActivity.class);
                intent.putExtra("key_ord_room",room.detail);
                context.startActivities(new Intent[]{intent});
            }
        });
        Log.d(TAG,"stt"+position);
        return convertView;
    }


    public class SaveView{
        Button button_choose_room;
        TextView textViewTime,textViewprice;
        ImageView imageViewRoom;
    }
}
