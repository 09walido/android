package com.example.tekupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tekupproject.model.Student;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Arrays;

public class AddStudent extends AppCompatActivity {

    Spinner spinLevel,spinSection,spinClass;
    String[] sec,niv,classes;
    Student student;
    Button btnAddStudent;
    MaterialEditText editName,editLastName;
    Toolbar TBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        spinLevel = findViewById(R.id.spinLevelStudent);
        spinSection = findViewById(R.id.spinSectionStudent);
        spinClass = findViewById(R.id.spinClasseStudent);
        btnAddStudent = findViewById(R.id.btnAddStudent);
        editName = findViewById(R.id.studentName);
        editLastName = findViewById(R.id.studentLastName);

        TBar=findViewById(R.id.TBar);
        setSupportActionBar(TBar);

        student = new Student();



        niv = new String[]{"Premiere","Deuxieme","Troisieme"};
        ArrayList<String> niveaux =new ArrayList<>(Arrays.asList(niv));
        ArrayAdapter<String> niveauAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, niveaux);
        niveauAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinLevel.setAdapter(niveauAdapter);

        spinLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinLevel.getSelectedItem().toString().equals("Premiere")){
                    sec = new String[]{"TIC"};
                }else{
                    sec = new String[]{"GLSI","DSEN","SSIR","DMWM"};
                }
                student.setNiveau(spinLevel.getSelectedItem().toString())  ;


                final ArrayList<String> section =new ArrayList<>(Arrays.asList(sec));
                ArrayAdapter<String> sectionAdapter = new ArrayAdapter<>(AddStudent.this, android.R.layout.simple_spinner_item, section);
                sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinSection.setAdapter(sectionAdapter);

                spinSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (spinSection.getSelectedItem().toString() ){
                            case "TIC" : classes = new String[]{"TIC A","TIC B","TIC C","TIC D", "TIC E"};
                                break;
                            case "GLSI": classes = new String[]{"GLSI A","GLSI B"};
                                break;
                            case "DSEN": classes = new String[]{"DSEN A","DSEN B"};
                                break;
                            case "SSIR": classes = new String[]{"SSIR A","SSIR B"};
                                break;
                            case "DMWM": classes = new String[]{"DMWM A","DMWM B"};
                                break;
                        }

                        student.setSection(spinSection.getSelectedItem().toString())  ;


                        ArrayList<String> cl =new ArrayList<>(Arrays.asList(classes));
                        ArrayAdapter<String> clAdapter = new ArrayAdapter<>(AddStudent.this, android.R.layout.simple_spinner_item, cl);
                        clAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinClass.setAdapter(clAdapter);

                        spinClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                student.setClasse(spinClass.getSelectedItem().toString());


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final DatabaseReference refStudent = FirebaseDatabase.getInstance().getReference().child("Student");
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                student.setPrenom(editName.getText().toString());
                student.setNom(editLastName.getText().toString());
                refStudent.push().setValue(student);
                Toast.makeText(AddStudent.this,"Ajout Success",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddStudent.this,Add.class);
                startActivity(intent);

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.logOut:
                intent = new Intent(AddStudent.this,SignIn.class);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        startActivity(intent);
        return true;
    }
}
