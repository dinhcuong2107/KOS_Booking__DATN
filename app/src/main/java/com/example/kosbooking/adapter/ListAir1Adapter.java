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
import com.example.kosbooking.oderActivity.OrderAir1Activity;

import java.util.ArrayList;

public class ListAir1Adapter extends ArrayAdapter<AirTicket> {
    final String TAG = getClass().getSimpleName();
    Context context;
    int resource;
    ArrayList<AirTicket> arrayListContext;
    SaveView saveView;
    public ListAir1Adapter(@NonNull Context context, int resource, @NonNull ArrayList<AirTicket> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrayListContext = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_air1,parent,false);
            saveView = new SaveView();
            saveView.button_choose = (Button) convertView.findViewById(R.id.btn_air1);
            saveView.textViewtime = (TextView) convertView.findViewById(R.id.time_air1);
            saveView.textViewname = (TextView) convertView.findViewById(R.id.name_air1);
            saveView.textViewform= (TextView) convertView.findViewById(R.id.form_air1);
            saveView.textViewto = (TextView) convertView.findViewById(R.id.to_air1);
            saveView.textViewprice = (TextView) convertView.findViewById(R.id.price_air1);

            convertView.setTag(saveView);
        }else {
            saveView = (SaveView) convertView.getTag();
        }

        final AirTicket airTicket = arrayListContext.get(position);
        saveView.textViewtime.setText(airTicket.time_start + "   "+airTicket.day_start);
        saveView.textViewname.setText(airTicket.name);
        saveView.textViewform.setText(airTicket.form);
        saveView.textViewto.setText(airTicket.to);
        if (Integer.parseInt(airTicket.ticket_pt)>=1){
            saveView.textViewprice.setText(airTicket.price_pt+"   VNĐ");
        }else {
            if (Integer.parseInt(airTicket.ticket_tg)>=1){
                saveView.textViewprice.setText(airTicket.price_tg+"   VNĐ");
            }
            else {
                saveView.textViewprice.setText(airTicket.price_n1+"   VNĐ");
            }
        }

        saveView.button_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context,""+getItem(position),Toast.LENGTH_LONG).show();
                Intent intent= new Intent(context, OrderAir1Activity.class);
                intent.putExtra("name",airTicket.name);
                intent.putExtra("air_code",airTicket.air_code);
                intent.putExtra("key_air_ticket",airTicket.type_of_ticket);
                intent.putExtra("time_start",airTicket.time_start);
                intent.putExtra("day_start",airTicket.day_start);
                context.startActivities(new Intent[]{intent});
            }
        });
        Log.d(TAG,"stt"+position);
        return convertView;
    }
    public class SaveView{
        Button button_choose;
        TextView textViewtime,textViewname,textViewform,textViewto,textViewprice;
    }
}
