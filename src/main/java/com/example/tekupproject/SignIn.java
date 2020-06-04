package com.example.tekupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.tekupproject.model.Users;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity {

    SignInButton btnSign;
    MaterialEditText cin, password;
    Map<String,String>teacherCoord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSign= findViewById(R.id.signIn);
        cin=findViewById(R.id.cinNumber);
        password = findViewById(R.id.editPassword);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_users = database.getReference("Users");

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                table_users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(cin.getText().toString()).exists())
                        {

                        mDialog.dismiss();
                        Users user = dataSnapshot.child(cin.getText().toString()).getValue(Users.class);

                        if (user.getPass().equals(password.getText().toString()))
                        {
                            Toast.makeText(SignIn.this,"Sign in successfully !",Toast.LENGTH_SHORT).show();

                            teacherCoord = new HashMap<>();
                            teacherCoord.put("cin",cin.getText().toString());
                            teacherCoord.put("name",user.getName());
                            teacherCoord.put("password",user.getPass());
                            Intent intent = new Intent(SignIn.this,UserHome.class);
                            intent.putExtra("teacherCoord", (Serializable) teacherCoord);

                            startActivity(intent);

                        }
                        else
                            {



                            Toast.makeText(SignIn.this,"Sign in failed !!!",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            {

                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                mDialog.dismiss();

                                Toast.makeText(SignIn.this,"User not exist",Toast.LENGTH_SHORT).show();;
                            }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent signIn = getIntent();
        if (signIn.getStringExtra("main").equals("main")){
            super.onBackPressed();
        }else{

        }

    }
}
