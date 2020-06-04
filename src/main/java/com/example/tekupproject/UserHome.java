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

import java.util.ArrayList;
import java.util.Arrays;

public class UserHome extends AppCompatActivity {

    Toolbar TBar;

    Button btnRechercher;
    Intent intent1;


    Spinner spinLevel,spinSection,spinClass;
    String[] sec,niv,classes;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        intent1 = new Intent(UserHome.this,Presence.class);


        TBar=findViewById(R.id.TBar);
        setSupportActionBar(TBar);





        spinLevel = findViewById(R.id.spinner1);
        spinSection = findViewById(R.id.spinner2);
        spinClass = findViewById(R.id.spinner3);

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
               intent1.putExtra("niveau",spinLevel.getSelectedItem().toString())  ;


                final ArrayList<String> section =new ArrayList<>(Arrays.asList(sec));
                ArrayAdapter<String> sectionAdapter = new ArrayAdapter<>(UserHome.this, android.R.layout.simple_spinner_item, section);
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

                        intent1.putExtra("section",spinSection.getSelectedItem().toString())  ;


                        ArrayList<String> cl =new ArrayList<>(Arrays.asList(classes));
                        ArrayAdapter<String> clAdapter = new ArrayAdapter<>(UserHome.this, android.R.layout.simple_spinner_item, cl);
                        clAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinClass.setAdapter(clAdapter);

                        spinClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                intent1.putExtra("classe",spinClass.getSelectedItem().toString());
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

        btnRechercher= findViewById(R.id.btnRechercher);


        btnRechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
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
                 intent = new Intent(UserHome.this,SignIn.class);
                break;
            case R.id.hist:
                intent = new Intent(UserHome.this,Historique.class);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        startActivity(intent);
        return true;
    }

}
