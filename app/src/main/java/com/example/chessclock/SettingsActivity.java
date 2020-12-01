package com.example.chessclock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity implements Custom_Time_Dialog.CustomTimerDialogListener {

    private ArrayList<CustomTimerData> arrayList;
    private RecyclerView recyclerView;
    private PreferrencesAdapter adapter;
    private Button start;
    private int varTime=5,additionalTime=5;
    private TextView name,minute,second;

    private static String MINUTE="minute";
    private static String SECOND="second";
    private static String TIMER_NAME="timer";
    private static String SHARED_PREF_NAME="name";

    MyDBHelper dbHelper;

    private SharedPreferences sharedPreferences1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");
        name=findViewById(R.id.name_display);
        minute=findViewById(R.id.minute_display);
        second=findViewById(R.id.second_display);
        start=findViewById(R.id.btn_start);
        recyclerView=findViewById(R.id.recyclerView);

        dbHelper=new MyDBHelper(SettingsActivity.this);

        arrayList= new ArrayList<>();
        storeDataInArray();

        LinearLayoutManager linearLayout=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayout);
        adapter=new PreferrencesAdapter(arrayList,SettingsActivity.this);
        recyclerView.setAdapter(adapter);

        sharedPreferences1 = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

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

                if(position>3){
                    name.setText(arrayList.get(position).getTitle()+"");
                    varTime=arrayList.get(position).getMinute();
                    additionalTime=arrayList.get(position).getSecond();
                }

                setValueInDisplay(arrayList.get(position).getTitle()+"",varTime,additionalTime);

            }
        });

//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                confirmDialog(adapterPosition);
//            }
//        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context=getBaseContext();
                Intent intent = new Intent(context, MainActivity.class);

                //Creating editor to store values to shared preferences...
                SharedPreferences.Editor editor = sharedPreferences1.edit();

                //Adding values to editor...
                editor.putString(TIMER_NAME,name.getText()+"");
                editor.putInt(MINUTE, varTime);
                editor.putInt(SECOND, additionalTime);
                editor.commit();

                context.startActivity(intent);
            }
        });

        name.setText(sharedPreferences1.getString(TIMER_NAME,"default"));
        minute.setText("Minute:"+sharedPreferences1.getInt(MINUTE,5));
        second.setText("Second:"+sharedPreferences1.getInt(SECOND,5)+"");

    }


    public void setValueInDisplay(String title, int min, int sec){

        name.setText(title+"");
        minute.setText("Minute:"+min);
        second.setText("Second:"+sec);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_preferences,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.add_timer:
                openDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void openDialog(){
        Custom_Time_Dialog exampleDialog = new Custom_Time_Dialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String custom_title, int custom_minute, int custom_second) {
        String string=custom_title+"("+custom_minute+"|"+custom_second+")";
        dbHelper.addTimer(string,custom_minute,custom_second);
        finish();
        startActivity(getIntent());
    }

    public void storeDataInArray(){
        Cursor cursor=dbHelper.readAllData();
        if(cursor.getCount()!=0){
            while(cursor.moveToNext()){
                arrayList.add(new CustomTimerData(cursor.getInt(0),cursor.getString(1),
                        cursor.getInt(2),cursor.getInt(3)));
            }
        }
    }

}