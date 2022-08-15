package com.example.kosbooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kosbooking.R;
import com.example.kosbooking.modul.AirTicket;
import com.example.kosbooking.oderActivity.MyAirActivity;

import java.util.ArrayList;

public class ListOrdTicketAdapter extends ArrayAdapter<AirTicket> {
    final String TAG = getClass().getSimpleName();
    Context context;
    int resource;
    ArrayList<AirTicket> arrayListContext;
    ListOrdTicketAdapter.SaveView saveView;
    public ListOrdTicketAdapter(@NonNull Context context, int resource, @NonNull ArrayList<AirTicket> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrayListContext = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_air_ord,parent,false);
            saveView = new SaveView();
            saveView.button_choose = (Button) convertView.findViewById(R.id.btn_air);
            saveView.textViewtime = (TextView) convertView.findViewById(R.id.time_air_ord);
            saveView.textViewform= (TextView) convertView.findViewById(R.id.form_air);
            saveView.textViewto = (TextView) convertView.findViewById(R.id.to_air);
            saveView.textViewprice = (TextView) convertView.findViewById(R.id.price_air);

            convertView.setTag(saveView);
        }else {
            saveView = (SaveView) convertView.getTag();
        }

        final AirTicket airTicket = arrayListContext.get(position);
        saveView.textViewtime.setText(airTicket.time_start + "   "+airTicket.day_start);
        saveView.textViewform.setText(airTicket.form);
        saveView.textViewto.setText(airTicket.to);
        saveView.textViewprice.setText(airTicket.name+"   vé");

        saveView.button_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, MyAirActivity.class);
                intent.putExtra("key_ord_ticket",airTicket.type_of_ticket);
                context.startActivities(new Intent[]{intent});
            }
        });
        Log.d(TAG,"stt"+position);
        return convertView;
    }
    public class SaveView{
        Button button_choose;
        TextView textViewtime,textViewform,textViewto,textViewprice;
    }
}
