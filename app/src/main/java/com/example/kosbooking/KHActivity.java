package com.example.kosbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class KHActivity extends AppCompatActivity {
DatabaseReference databaseReference;
ImageView imageView_kh;
TextView kh_name,kh_func,kh_gt,kh_cccd,kh_date,kh_phone,kh_email,kh_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_h);
        imageView_kh = (ImageView) findViewById(R.id.kh_image_inf_avatar);

        kh_name= (TextView) findViewById(R.id.kh_name);
        kh_func = (TextView) findViewById(R.id.kh_position);
        kh_gt = (TextView) findViewById(R.id.kh_sex);
        kh_cccd = (TextView) findViewById(R.id.kh_CMND);
        kh_date= (TextView) findViewById(R.id.kh_date);
        kh_phone= (TextView) findViewById(R.id.kh_phone);
        kh_email= (TextView) findViewById(R.id.kh_email);
        kh_add= (TextView) findViewById(R.id.kh_add);

        // get_data_to_intent
        final String key_user =  getIntent().getStringExtra("key_user");
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    User user = dataSnapshot.getValue(User.class);
                    if (dataSnapshot.getKey().equals(key_user)){
                        new KHActivity.Load_avatar().execute(user.avatar);
                        kh_name.setText(user.fullname);
                        kh_func.setText(user.position);
                        kh_gt.setText(user.sex);
                        kh_cccd.setText("Số CCCD: "+user.CMND);
                        kh_date.setText("Ngày sinh: "+user.dateofbirth);
                        kh_phone.setText("Số ĐT: "+user.phonenumber);
                        kh_email.setText("email: "+user.email);
                        kh_add.setText("Địa chỉ: "+user.address);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private class Load_avatar extends AsyncTask<String,Void, Bitmap> {
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
            imageView_kh.setImageBitmap(bitmap);
        }
    }
}