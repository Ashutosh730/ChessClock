package com.example.chessclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    private ArrayList arrayList;
    private RecyclerView recyclerView;
    private PreferrencesAdapter adapter;
    private Button start;
    private int varTime=5,additionalTime=5;
    private TextView name,minute,second;

    private static String MINUTE="minute";
    private static String SECOND="second";
    private static String SHARED_PREF_NAME="name";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        arrayList= new ArrayList<>();
        arrayList.add("Blitz(3|2)");
        arrayList.add( "Fischer(5|5)");
        arrayList.add("Fischer Rapid(10|5)");
        arrayList.add("Delay Bullet(1|2)");

        name=findViewById(R.id.name_display);
        minute=findViewById(R.id.minute_display);
        second=findViewById(R.id.second_display);
        start=findViewById(R.id.btn_start);
        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayout=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayout);
        adapter=new PreferrencesAdapter(arrayList,this);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new PreferrencesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                switch (position){
                    case 0: varTime=3;
                    additionalTime=2;
                    break;

                    case 1: varTime=5;
                        additionalTime=5;
                        break;

                    case 2: varTime=10;
                        additionalTime=5;
                        break;
                    case 3: varTime=1;
                        additionalTime=2;
                        break;

                }

                name.setText(arrayList.get(position)+"");
                minute.setText("Minute:"+varTime+"");
                second.setText("Second:"+additionalTime+"");
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context=getBaseContext();
                Intent intent = new Intent(context, MainActivity.class);

                SharedPreferences sharedPreferences1 = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences...
                SharedPreferences.Editor editor = sharedPreferences1.edit();

                //Adding values to editor...
                editor.putInt(MINUTE, varTime);
                editor.putInt(SECOND, additionalTime);
                editor.commit();
                context.startActivity(intent);
            }
        });

    }

}