package com.example.chessclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    ArrayList arrayList;
    RecyclerView recyclerView;
    PreferrencesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        arrayList= new ArrayList<>();
        arrayList.add("Ashutosh");
        arrayList.add( "Kumar");
        arrayList.add("Saharshika");
        arrayList.add("Nikhil");

        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayout=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayout);
        adapter=new PreferrencesAdapter(arrayList,this);
        recyclerView.setAdapter(adapter);
    }
}