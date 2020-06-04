package com.example.tekupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.tekupproject.model.Admin;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Configuration extends AppCompatActivity {

    SignInButton btnSign;
    MaterialEditText cin, password;
    Toolbar TBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        TBar=findViewById(R.id.TBar);
        setSupportActionBar(TBar);


        btnSign = findViewById(R.id.btnAdminSignIn);
        cin = findViewById(R.id.adminCinNumber);
        password = findViewById(R.id.adminPassword);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_admin = database.getReference("Admin");

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(Configuration.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                table_admin.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(cin.getText().toString()).exists()) {

                            mDialog.dismiss();
                            Admin admin = dataSnapshot.child(cin.getText().toString()).getValue(Admin.class);

                            if (admin.getPass().equals(password.getText().toString())) {
                                Toast.makeText(Configuration.this, "Sign in successfully !", Toast.LENGTH_SHORT).show();


                                Intent intent = new Intent(Configuration.this, Add.class);

                                startActivity(intent);

                            } else {


                                Toast.makeText(Configuration.this, "Sign in failed !!!", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mDialog.dismiss();

                            Toast.makeText(Configuration.this, "Admin not exist", Toast.LENGTH_SHORT).show();
                            ;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar_menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.logOut:
                intent = new Intent(Configuration.this,MainActivity.class);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        startActivity(intent);
        return true;
    }
}
