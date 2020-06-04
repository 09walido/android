package com.example.tekupproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Add extends AppCompatActivity {
    Button btnAddTeacher,btnAddStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnAddStudent= findViewById(R.id.addStudent);
        btnAddTeacher= findViewById(R.id.addTeacher);
        btnAddTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add.this,AddTeacher.class);
                startActivity(intent);
            }
        });

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add.this,AddStudent.class);
                startActivity(intent);
            }
        });
    }
}
