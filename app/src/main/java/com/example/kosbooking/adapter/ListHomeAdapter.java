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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kosbooking.MainActivity;
import com.example.kosbooking.oderActivity.OrderRoomActivity;
import com.example.kosbooking.R;
import com.example.kosbooking.modul.Room;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListHomeAdapter extends ArrayAdapter<Room> {
    final String TAG = getClass().getSimpleName();
    Context context;
    int resource;
    ArrayList<Room> arrayListContext;
    ListHomeAdapter.SaveView saveView;
    public ListHomeAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Room> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrayListContext = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_room,parent,false);
            saveView = new SaveView();
            saveView.imageViewRoom = (ImageView) convertView.findViewById(R.id.imgroom);
            saveView.button_choose_room = (Button) convertView.findViewById(R.id.btn_room);
            saveView.textViewadd = (TextView) convertView.findViewById(R.id.addroom);
            saveView.textViewprice = (TextView) convertView.findViewById(R.id.price_room);

            convertView.setTag(saveView);
        }else {
            saveView = (ListHomeAdapter.SaveView) convertView.getTag();
        }

        final Room room = arrayListContext.get(position);
        Picasso.with(context).load(room.img1).into(saveView.imageViewRoom);
        saveView.textViewadd.setText(room.address);
        saveView.textViewprice.setText(room.price);
        saveView.textViewprice.setText(room.price+" VNĐ / ngày");

        saveView.button_choose_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, OrderRoomActivity.class);
                intent.putExtra("temp_img1",room.img1);
                intent.putExtra("price_room",room.price);
                intent.putExtra("sl_room",room.slroom);
                intent.putExtra("key_user",room.detail);
                context.startActivities(new Intent[]{intent});
            }
        });
        Log.d(TAG,"stt"+position);
        return convertView;
    }
    public class SaveView{
        Button button_choose_room;
        TextView textViewadd,textViewprice;
        ImageView imageViewRoom;
    }
}
