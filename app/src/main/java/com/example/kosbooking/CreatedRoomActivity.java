package com.example.kosbooking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kosbooking.modul.Room;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class CreatedRoomActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    DatePickerDialog.OnDateSetListener dateSetListener;

    ImageView imageViewIMG,imageViewIMG1,imageViewIMG2,imageViewIMG3;
    Button button,btn_t,btn_g;
    EditText editTextAdd,editTextPrice,editTextDetail;
    TextView textViewSL;
    // private int[] imgView = new int[]{R.id.imageViewimg1,R.id.imageViewimg2,R.id.imageViewimg3};
    String urla1="",urla2="",urla3="";

    int number = 1;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_room);

        imageViewIMG1 = (ImageView)findViewById(R.id.imageViewimg1);
        imageViewIMG2 = (ImageView)findViewById(R.id.imageViewimg2);
        imageViewIMG3 = (ImageView)findViewById(R.id.imageViewimg3);

        button = (Button)findViewById(R.id.button3);
        btn_g = (Button)findViewById(R.id.button4);
        btn_t = (Button)findViewById(R.id.button5);

        editTextAdd = (EditText) findViewById(R.id.editadd);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        editTextDetail = (EditText) findViewById(R.id.edtdetail);

        textViewSL = (TextView) findViewById(R.id.textView3);

        FirebaseStorage storage;
        StorageReference storageReference;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());

        imageViewIMG1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                imageViewIMG=imageViewIMG1;
            }
        });
        imageViewIMG2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                imageViewIMG=imageViewIMG2;

            }
        });
        imageViewIMG3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                imageViewIMG=imageViewIMG3;
            }
        });
        btn_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number++;
                textViewSL.setText(""+number);
            }
        });
        btn_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number--;
                if (number<=0)
                {
                    number=1;
                }
                textViewSL.setText(""+number);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urla1.equals("") || urla2.equals("") || urla3.equals(""))
                {
                    Toast.makeText(CreatedRoomActivity.this, "Chọn đủ 3 ảnh",Toast.LENGTH_LONG).show();
                }
                else {
                    if (editTextAdd.getText().toString().length()==0 || editTextPrice.getText().toString().length()==0||editTextDetail.getText().toString().length()==0)
                    {
                    Toast.makeText(CreatedRoomActivity.this, "Chọn đủ thông tin",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Room room = new Room();
                        room.img1=urla1;
                        room.img2=urla2;
                        room.img3=urla3;
                        room.address=editTextAdd.getText().toString();
                        room.price= editTextPrice.getText().toString();
                        room.slroom=""+number;
                        room.detail=editTextDetail.getText().toString();
                        room.act=false;
                        databaseReference= FirebaseDatabase.getInstance().getReference();

    /*                          databaseReference.child("Users").push().setValue(user);
                                Toast.makeText(CreatedActivity.this, "hoàn thành", Toast.LENGTH_SHORT).show();
                                new_intent();
    */
                        databaseReference.child("Rooms").push().setValue(room, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if (error==null){
                                    Toast.makeText(CreatedRoomActivity.this, "hoàn thành", Toast.LENGTH_SHORT).show();
                                    delete_edittext();
                                    new_intent();
                                }else {
                                    Toast.makeText(CreatedRoomActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }

                            void new_intent(){
                                Intent intent = new Intent(CreatedRoomActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                            void delete_edittext(){
                                editTextAdd.getText().clear();
                                editTextDetail.getText().clear();
                                editTextPrice.getText().clear();
                            }
                        });
                }
            }}
        });
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
                imageViewIMG.setImageBitmap(bitmap);

                FirebaseStorage storage;
                StorageReference storageReference;
                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();
                final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
                if(filePath != null)
                {
                    final ProgressDialog progressDialog = new ProgressDialog(CreatedRoomActivity.this);
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
                                            if (imageViewIMG==imageViewIMG1){urla1=downloadUrl.toString();}
                                            if (imageViewIMG==imageViewIMG2){urla2=downloadUrl.toString();}
                                            if (imageViewIMG==imageViewIMG3){urla3=downloadUrl.toString();}
                                        }
                                    });
                                    Toast.makeText(CreatedRoomActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(CreatedRoomActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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