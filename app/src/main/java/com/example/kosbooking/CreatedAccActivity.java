package com.example.kosbooking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatedAccActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    DatePickerDialog.OnDateSetListener dateSetListener;
    EditText editText_fullname, editText_dateofbirth,editText_cmnd,editText_phonenumber,editText_email,editText_password,editText_passwordagain,editText_address;
    RadioButton ck_boy,ck_girl;
    Button button_created;
    String ck_sex="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_acc);
        button_created = (Button)findViewById(R.id.buttonCreated);
        editText_fullname= (EditText) findViewById(R.id.editTextFullName);
        editText_dateofbirth = (EditText) findViewById(R.id.editTextDateOfBirth);
        editText_cmnd = (EditText) findViewById(R.id.editTextCMND);
        editText_phonenumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        editText_email = (EditText) findViewById(R.id.editTextEmail);
        editText_password = (EditText) findViewById(R.id.editTextPassword);
        editText_passwordagain = (EditText) findViewById(R.id.editTextPasswordAgain);
        editText_address = (EditText) findViewById(R.id.editTextAddress);
        ck_boy = (RadioButton) findViewById(R.id.checkBoy);
        ck_girl = (RadioButton) findViewById(R.id.checkGirl);
        ck_boy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        ck_girl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        editText_dateofbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreatedAccActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month+1;
                String temp_date = day+"/"+month+"/"+year;
                editText_dateofbirth.setText(temp_date);
            }
        };
        button_created.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                if (ck_boy.isChecked())
                {
                    ck_sex="boy";
                }
                if (ck_girl.isChecked())
                {
                    ck_sex="girl";
                }
                if (
                        editText_fullname.getText().toString().equals("") ||
                                editText_dateofbirth.getText().toString().equals("") ||
                                editText_cmnd.getText().toString().equals("") ||
                                editText_phonenumber.getText().toString().equals("") ||
                                editText_email.getText().toString().equals("") ||
                                editText_password.getText().toString().equals("") ||
                                editText_passwordagain.getText().toString().equals("") ||
                                editText_address.getText().toString().equals("") ||
                                ck_sex.equals("")
                )
                {
                    Toast.makeText(CreatedAccActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (editText_password.getText().toString().length() < 8)
                    {
                        Toast.makeText(CreatedAccActivity.this, "Mật khẩu chưa đủ an toàn", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (editText_password.getText().toString().equals(editText_passwordagain.getText().toString()))
                        {
                            user.avatar="";
                            user.fullname=editText_fullname.getText().toString();
                            user.dateofbirth=editText_dateofbirth.getText().toString();
                            user.sex=ck_sex;
                            user.CMND= editText_cmnd.getText().toString();
                            user.phonenumber=editText_phonenumber.getText().toString();
                            user.email=editText_email.getText().toString();
                            user.password=editText_password.getText().toString();
                            user.address=editText_address.getText().toString();
                            user.position="user";


                            databaseReference= FirebaseDatabase.getInstance().getReference();

/*                          databaseReference.child("Users").push().setValue(user);
                            Toast.makeText(CreatedActivity.this, "hoàn thành", Toast.LENGTH_SHORT).show();
                            new_intent();
*/
                            databaseReference.child("Users").push().setValue(user, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    if (error==null){
                                        Toast.makeText(CreatedAccActivity.this, "hoàn thành", Toast.LENGTH_SHORT).show();
                                        delete_edittext();
                                        new_intent();
                                    }else {
                                        Toast.makeText(CreatedAccActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(CreatedAccActivity.this, ""+editText_password.getText().toString()+ "!="+ editText_passwordagain.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
            void new_intent(){
                Intent intent = new Intent(CreatedAccActivity.this,LoginActivity.class);
                startActivity(intent);
            }
            void delete_edittext(){
                editText_fullname.getText().clear();
                editText_dateofbirth.getText().clear();
                editText_cmnd.getText().clear();
                editText_phonenumber.getText().clear();
                editText_email.getText().clear();
                editText_password.getText().clear();
                editText_passwordagain.getText().clear();
                editText_address.getText().clear();
            }
        });
    }
}