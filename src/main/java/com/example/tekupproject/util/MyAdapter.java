package com.example.tekupproject.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tekupproject.Presence;
import com.example.tekupproject.R;
import com.example.tekupproject.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    ArrayList<Student> list;
    static HashMap<String,String> mapPresence;
    public MyAdapter(ArrayList<Student> list ){
        this.list=list;
        mapPresence = new HashMap<String, String>();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_contents,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.textView.setText( list.get(position).getNom()+" "+list.get(position).getPrenom());
        final String varTemp= holder.textView.getText().toString();



        holder.btnPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textView.setText(varTemp+" Present");
                mapPresence.put(varTemp,varTemp+" Present");

            }
        });

        holder.btnAbsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textView.setText(varTemp+" Absent");
                mapPresence.put(varTemp,varTemp+" Absent");
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button btnPresent,btnAbsent;

        MyViewHolder(View view){
            super(view);
            this.textView= (TextView) view.findViewById(R.id.card_text);
            this.btnPresent= (Button) view.findViewById(R.id.present);
            this.btnAbsent= (Button) view.findViewById(R.id.absent);

        }
    }

    public static HashMap<String,String> getMapPresence(){
        return mapPresence;
    }
}