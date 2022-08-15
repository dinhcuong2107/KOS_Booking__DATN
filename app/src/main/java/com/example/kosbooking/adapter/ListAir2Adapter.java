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

public class ListAir2Adapter extends ArrayAdapter<AirTicket> {
    final String TAG = getClass().getSimpleName();
    Context context;
    int resource;
    ArrayList<AirTicket> arrayListContext;
    ListAir2Adapter.SaveView saveView;
    public ListAir2Adapter(@NonNull Context context, int resource, @NonNull ArrayList<AirTicket> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrayListContext = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_air2,parent,false);
            saveView = new SaveView();
            saveView.button_choose = (Button) convertView.findViewById(R.id.btn_air2);
            saveView.textViewtimes = (TextView) convertView.findViewById(R.id.timestart_air2);
            saveView.textViewtimeb = (TextView) convertView.findViewById(R.id.timeback_air2);
            saveView.textViewname = (TextView) convertView.findViewById(R.id.name_air2);
            saveView.textViewform= (TextView) convertView.findViewById(R.id.form_air2);
            saveView.textViewto = (TextView) convertView.findViewById(R.id.to_air2);
            saveView.textViewprice = (TextView) convertView.findViewById(R.id.vnd_air2);

            convertView.setTag(saveView);
        }else {
            saveView = (ListAir2Adapter.SaveView) convertView.getTag();
        }

        final AirTicket airTicket = arrayListContext.get(position);
        saveView.textViewtimes.setText("Turn1   "+airTicket.time_start + "   "+airTicket.day_start);
        saveView.textViewtimeb.setText("Turn2   "+airTicket.time_comeback + "   "+airTicket.day_comeback);
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
        TextView textViewtimes,textViewtimeb,textViewname,textViewform,textViewto,textViewprice;
    }
}
