package com.example.tekupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;


public class Historique extends AppCompatActivity {
    Toolbar TBar;
    Spinner choixMatiere, choixNiveau, choixClasse, choixSection, choixHeure;
    String[] sec, niv, classes;
    String matiere, niveau, classe, section, datePresence, hh, datePr;
    CalendarView calendarView;
    Button btnChercher;
    Intent intent1;
    Integer dd, yy, mm;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);
        intent1 = new Intent(Historique.this, HistoriqueAction.class);

        TBar = findViewById(R.id.TBar);
        setSupportActionBar(TBar);


        calendarView = findViewById(R.id.calendarView);
        choixMatiere = findViewById(R.id.spinChoixMatiere);
        choixNiveau = findViewById(R.id.spinChoixNiveau);
        choixClasse = findViewById(R.id.spinChoixClasse);
        choixSection = findViewById(R.id.spinChoixSection);
        choixHeure = findViewById(R.id.spinChoixHeure);
        btnChercher = findViewById(R.id.btnChercher);

        niv = new String[]{"Premiere", "Deuxieme", "Troisieme"};
        ArrayList<String> niveaux = new ArrayList<>(Arrays.asList(niv));
        ArrayAdapter<String> niveauAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, niveaux);
        niveauAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choixNiveau.setAdapter(niveauAdapter);

        choixNiveau.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (choixNiveau.getSelectedItem().toString().equals("Premiere")) {
                    sec = new String[]{"TIC"};
                } else {
                    sec = new String[]{"GLSI", "DSEN", "SSIR", "DMWM"};
                }
                niveau = choixNiveau.getSelectedItem().toString();


                final ArrayList<String> sections = new ArrayList<>(Arrays.asList(sec));
                ArrayAdapter<String> sectionAdapter = new ArrayAdapter<>(Historique.this, android.R.layout.simple_spinner_item, sections);
                sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                choixSection.setAdapter(sectionAdapter);

                choixSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (choixSection.getSelectedItem().toString()) {
                            case "TIC":
                                classes = new String[]{"TIC A", "TIC B", "TIC C", "TIC D", "TIC E"};
                                break;
                            case "GLSI":
                                classes = new String[]{"GLSI A", "GLSI B"};
                                break;
                            case "DSEN":
                                classes = new String[]{"DSEN A", "DSEN B"};
                                break;
                            case "SSIR":
                                classes = new String[]{"SSIR A", "SSIR B"};
                                break;
                            case "DMWM":
                                classes = new String[]{"DMWM A", "DMWM B"};
                                break;
                        }

                        section = choixSection.getSelectedItem().toString();


                        ArrayList<String> cl = new ArrayList<>(Arrays.asList(classes));
                        ArrayAdapter<String> clAdapter = new ArrayAdapter<>(Historique.this, android.R.layout.simple_spinner_item, cl);
                        clAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        choixClasse.setAdapter(clAdapter);

                        choixClasse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                classe = choixClasse.getSelectedItem().toString();
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

        final String[] m = new String[]{"Angular", "DevMobile"};
        ArrayList<String> matieres = new ArrayList<>(Arrays.asList(m));
        ArrayAdapter<String> matiereAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, matieres);
        matiereAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choixMatiere.setAdapter(matiereAdapter);

        choixMatiere.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                matiere = choixMatiere.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dd = dayOfMonth;
                yy = year;
                mm = month + 1;
            }
        });

        String[] h = new String[]{"00", "09", "11", "13", "15"};
        ArrayList<String> heures = new ArrayList<>(Arrays.asList(h));
        ArrayAdapter<String> heureAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, heures);
        heureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choixHeure.setAdapter(heureAdapter);

        choixHeure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                hh = choixHeure.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnChercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                datePresence = dd.toString() + "-" + mm.toString() + "-" + yy.toString();

                if (mm < 10) {
                    datePresence = dd.toString() + "-0" + mm.toString() + "-" + yy.toString();
                }
                if (dd < 10) {
                    datePresence = "0" + datePresence;
                }
                datePresence = datePresence + "-" + hh + "h";


                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference table_historique;
                list = new ArrayList<String>();
                try {

                    table_historique = database.getReference("Historique")
                            .child(niveau)
                            .child(section)
                            .child(classe)
                            .child(matiere)
                            .child(datePresence);
                    table_historique.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                list.add(dataSnapshot1.getValue().toString());
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                } catch (NullPointerException e) {
                    Toast.makeText(Historique.this, "Check Data", Toast.LENGTH_SHORT).show();
                }
                for(String s : list){
                    System.out.println(s);
                }
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
                intent = new Intent(Historique.this, SignIn.class);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        startActivity(intent);
        return true;
    }
}
