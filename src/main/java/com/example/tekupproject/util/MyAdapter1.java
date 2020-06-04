package com.example.tekupproject.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tekupproject.R;

import java.util.ArrayList;

public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder>{

    ArrayList<String> list;
    public MyAdapter1(ArrayList<String> list){
        this.list=list;
    }


    @NonNull
    @Override
    public MyAdapter1.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_contents1,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter1.MyViewHolder holder, int position) {
        holder.textView.setText( list.get(position));

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        MyViewHolder(View view){
            super(view);
            this.textView= (TextView) view.findViewById(R.id.card_text1);


        }
    }
}