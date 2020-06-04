package com.example.tekupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.tekupproject.util.MyAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Presence extends AppCompatActivity {

    Toolbar TBar;
    RecyclerView recyclerView;
    ArrayList<Student> list;
    RecyclerView.Adapter adapter;
    Spinner spinMatiere;
    HashMap<String,String> map;
    Button btnMatiere;
    String choixMatiere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presence);


        TBar=findViewById(R.id.TBar);
        setSupportActionBar(TBar);

        final Intent intent1=getIntent();

        String[] matiere = new String[]{"Angular","DevMobile"};
        spinMatiere = findViewById(R.id.spinMatiere);
        ArrayList<String> matieres =new ArrayList<>(Arrays.asList(matiere));
        ArrayAdapter<String> matiereAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, matieres);
        matiereAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMatiere.setAdapter(matiereAdapter);

        spinMatiere.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choixMatiere = spinMatiere.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });








        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<Student>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Student");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Student s = dataSnapshot1.getValue(Student.class);
                    if ((s.getSection().equals(intent1.getStringExtra("section")))
                            &&(s.getNiveau().equals(intent1.getStringExtra("niveau")))
                            &&(s.getClasse().equals(intent1.getStringExtra("classe")))) {
                        list.add(s);
                    }
                }
                adapter=new MyAdapter(list);
                recyclerView.setAdapter(adapter);
                map=MyAdapter.getMapPresence();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference refHistorique = FirebaseDatabase.getInstance().getReference().child("Historique");

        btnMatiere=findViewById(R.id.btnMatiere);
        btnMatiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> listHistorique = new ArrayList<String>();
                for(Map.Entry<String, String> entry: map.entrySet()) {
                    listHistorique.add( entry.getValue());
                }

                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());
                String historiqueDateTime = currentDate+"-"+currentTime+"h";

                refHistorique.child(intent1.getStringExtra("niveau"))
                                .child(intent1.getStringExtra("section"))
                                    .child(intent1.getStringExtra("classe"))
                                        .child(choixMatiere)
                                            .child(historiqueDateTime)
                                                .setValue(listHistorique);

                Toast.makeText(Presence.this,"Success ",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Presence.this,UserHome.class);
                startActivity(intent);


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
                intent = new Intent(Presence.this,SignIn.class);
                break;
            case R.id.hist:
                intent = new Intent(Presence.this,Historique.class);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        startActivity(intent);
        return true;
    }
}
