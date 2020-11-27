package com.example.chessclock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.SystemClock;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private Chronometer player1,player2;
    private ImageView settings,pause,replay;
    private long baseTime,varTime,addTime1,addTime2,pauseOffSet1=0,pauseOffSet2=0,additionalTime;
    private boolean flag=true,isPlay=true;
    private  int counter1=0,counter2=0,currentPlayer=0;
    private TextView txtcounter1,txtcounter2;

    private SharedPreferences sharedPreferences;
    private static String MINUTE="minute";
    private static String SECOND="second";
    private static String SHARED_PREF_NAME="name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1=findViewById(R.id.player1);
        player2=findViewById(R.id.player2);
        settings=findViewById(R.id.settings);
        pause=findViewById(R.id.pause);
        replay=findViewById(R.id.replay);
        txtcounter1=findViewById(R.id.counter1);
        txtcounter2=findViewById(R.id.counter2);

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        varTime=sharedPreferences.getInt(MINUTE,5);
        additionalTime=sharedPreferences.getInt(SECOND,5);

        addTime1=0;
        addTime2=0;
        baseTime=60*1000*varTime;

        player1.setBase(SystemClock.elapsedRealtime()+baseTime);
        player2.setBase(SystemClock.elapsedRealtime()+baseTime);

        pauseOffSet1=-baseTime;
        pauseOffSet2=-baseTime;

        player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //counting number of taps and setting it to the txtCounter TextView...
                counter1++;
                txtcounter1.setText(counter1+"");

                if(flag) {
                    player1.setBase(SystemClock.elapsedRealtime() + baseTime);
                    flag=false;
                }
                player1.stop();
                pauseOffSet1=SystemClock.elapsedRealtime()-player1.getBase();
                player1.setBase(SystemClock.elapsedRealtime()-pauseOffSet1+addTime1);


                player2.setBase(SystemClock.elapsedRealtime()-pauseOffSet2+addTime2);
                player2.start();
                addTime2=additionalTime;

                //Disabling current view and enabling other view...
                player1.setEnabled(false);
                player2.setEnabled(true);

                //Recording who is playing now...
                currentPlayer=1;
            }
        });

        player2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //counting number of taps and setting it to the txtCounter TextView...
                counter2++;
                txtcounter2.setText(counter2+"");

                if(flag) {
                    player2.setBase(SystemClock.elapsedRealtime() + baseTime);
                    flag=false;
                }
                player2.stop();
                pauseOffSet2=SystemClock.elapsedRealtime()-player2.getBase();
                player2.setBase(SystemClock.elapsedRealtime()-pauseOffSet2+addTime2);


                player1.start();
                player1.setBase(SystemClock.elapsedRealtime()-pauseOffSet1+addTime1);
                addTime1=additionalTime;

                //Disabling current view and enabling other view
                player2.setEnabled(false);
                player1.setEnabled(true);

                //Recording who is playing now...
                currentPlayer=2;
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isPlay){
                    isPlay=true;
                    pause.setImageResource(R.drawable.pause_image);
                    if(currentPlayer==1) {
                        player2.setBase(SystemClock.elapsedRealtime()-pauseOffSet2);
                        player2.start();
                    }
                    else if(currentPlayer==2) {
                        player1.setBase(SystemClock.elapsedRealtime()-pauseOffSet1);
                        player1.start();
                    }
                }
                else {
                    isPlay=false;
                    pause.setImageResource(R.drawable.play_image);
                    if(currentPlayer==1)
                    {
                        pauseOffSet2=SystemClock.elapsedRealtime()-player2.getBase();
                        player2.stop();
                    }
                    else {
                        pauseOffSet1=SystemClock.elapsedRealtime()-player1.getBase();
                        player1.stop();
                    }

                }

            }
        });

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtcounter1.setText(0+"");
                txtcounter2.setText(0+"");
                player1.setEnabled(true);
                player2.setEnabled(true);
                counter2=0;
                counter1=0;
                player2.stop();
                player1.stop();
                player1.setBase(SystemClock.elapsedRealtime()+baseTime);
                player2.setBase(SystemClock.elapsedRealtime()+baseTime);
                pauseOffSet1=-baseTime;
                pauseOffSet2=-baseTime;
                addTime1=0;
                addTime2=0;
                flag=true;
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),SettingsActivity.class));
            }
        });
    }
}

//    Hello connections...
//        This is my first post.
//        I have made this app which can be use while playing Chess... By using this app players can set timer for their chess game play...
//        For now this app has one default timer which is Blitz(2-3) timer... In future I have decided to add some more timer and a feature of setting own customizable timer so that player can set their own timer according to their own preferences... I hope like it... #android #gaming #selfmade #Chess #ChessClock