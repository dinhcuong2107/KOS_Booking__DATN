package com.example.kosbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class InfActivity extends AppCompatActivity {
    private Uri filePath;
    String urla="";
    private static final int PICK_IMAGE_REQUEST=71;
    DatabaseReference databaseReference;
    ImageView imageAvatar_inf,imageView;
    TextView t_fullname,t_date,t_sex,t_cmnd,t_phone,t_email,t_pass,t_add,t_position,t_updata,t_change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf);

        imageAvatar_inf = (ImageView) findViewById(R.id.image_inf_avatar);

        t_fullname= (TextView) findViewById(R.id.tname);
        t_position= (TextView) findViewById(R.id.tposition);
        t_sex= (TextView) findViewById(R.id.tsex);
        t_cmnd= (TextView) findViewById(R.id.tCMND);
        t_date= (TextView) findViewById(R.id.tdate);
        t_phone= (TextView) findViewById(R.id.phone);
        t_add= (TextView) findViewById(R.id.tadd);
        t_email= (TextView) findViewById(R.id.temail);

        t_updata= (TextView) findViewById(R.id.tupdate);
        t_change= (TextView) findViewById(R.id.tchange);

        // get_data_to_intent
        final String key_user =  getIntent().getStringExtra("key_user");
        String avatar = getIntent().getStringExtra("avatar");
        String fullname = getIntent().getStringExtra("fullname");
        String dateofbirth = getIntent().getStringExtra("dateofbirth");
        String sex = getIntent().getStringExtra("sex");
        String CMND = getIntent().getStringExtra("CMND");
        final String phonenumber = getIntent().getStringExtra("phonenumber");
        String email = getIntent().getStringExtra("email");
        final String password = getIntent().getStringExtra("password");
        String address = getIntent().getStringExtra("address");
        String position = getIntent().getStringExtra("position");

        // set_data
        new Load_avatar().execute(avatar);
        t_fullname.setText(fullname);
        t_position.setText(position);
        t_sex.setText(sex);
        t_cmnd.setText("CMND/CCCD :    "+CMND);
        t_date.setText("Ngày sinh :    "+dateofbirth);
        t_phone.setText("Điện thoại :    "+phonenumber);
        t_add.setText("Địa chỉ :    "+address);
        t_email.setText("Email :    "+email);

        t_updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog= new Dialog(InfActivity.this);
                dialog.setContentView(R.layout.dialog_update_avata);
                final ImageView imgupdateavata = (ImageView) dialog.findViewById(R.id.imageViewupdateavatar);
                final Button buttonsaveimg = (Button) dialog.findViewById(R.id.buttonChangeAva);
                buttonsaveimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference= FirebaseDatabase.getInstance().getReference();
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                                {
                                    User user = dataSnapshot.getValue(User.class);
                                    if (urla.length()>=0)
                                    {
                                        databaseReference= FirebaseDatabase.getInstance().getReference();
                                        databaseReference.child("Users").child(key_user).child("avatar").setValue(urla);
                                        Toast.makeText(InfActivity.this,"đổi mk thành công",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(InfActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(InfActivity.this,"K tìm thấy ảnh",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
                imgupdateavata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageView=imgupdateavata;
                        updata_imag();
                    }
                });
                Button buttonchange = (Button) dialog.findViewById(R.id.buttonChangeAva);
                dialog.show();
            }
        });

        t_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog= new Dialog(InfActivity.this);
                dialog.setContentView(R.layout.dialog_change_password);
                final EditText editTextpass = (EditText) dialog.findViewById(R.id.editTextTextPassword);
                final EditText editTextPassNew = (EditText) dialog.findViewById(R.id.editTextTextPassword2);
                final EditText editTextPassNewAgain = (EditText) dialog.findViewById(R.id.editTextTextPassword3);
                Button buttonchange = (Button) dialog.findViewById(R.id.buttonChange);
                buttonchange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference= FirebaseDatabase.getInstance().getReference();
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                                {
                                    User user = dataSnapshot.getValue(User.class);
                                    if (password.equals(editTextpass.getText().toString().trim()))
                                    {
                                        if (editTextPassNew.getText().toString().length()>7)
                                        {
                                            if (editTextPassNew.getText().toString().equals(editTextPassNewAgain.getText().toString()) & editTextPassNew.getText().toString().length()>0)
                                            {
                                                databaseReference= FirebaseDatabase.getInstance().getReference();
                                                databaseReference.child("Users").child(key_user).child("password").setValue(editTextPassNew.getText().toString());
                                                Toast.makeText(InfActivity.this,"đổi mk thành công",Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(InfActivity.this,LoginActivity.class);
                                                startActivity(intent);
                                            }
                                            else {
                                                Toast.makeText(InfActivity.this,"nhập lại new pass",Toast.LENGTH_LONG).show();
                                            }
                                        }else {
                                            Toast.makeText(InfActivity.this,"password không đủ bảo mật",Toast.LENGTH_LONG).show();
                                        }
                                    }else {
                                        Toast.makeText(InfActivity.this,"password không chính xác",Toast.LENGTH_LONG).show();
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
                dialog.show();
            }
        });
    }

    private void updata_imag() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void inputImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
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
            imageAvatar_inf.setImageBitmap(bitmap);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

                FirebaseStorage storage;
                StorageReference storageReference;
                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();
                final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
                if(filePath != null)
                {
                    final ProgressDialog progressDialog = new ProgressDialog(InfActivity.this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                    ref.putFile(filePath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Uri downloadUrl = uri;
                                            urla=downloadUrl.toString();
                                        }
                                    });
                                    Toast.makeText(InfActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(InfActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                            .getTotalByteCount());
                                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                }
                            });
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}