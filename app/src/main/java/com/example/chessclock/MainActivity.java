package com.example.chessclock;

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

public class MainActivity extends AppCompatActivity{

    private Chronometer player1,player2;
    private ImageView settings,pause,replay;
    private long baseTime,varTime,addTime1,addTime2,pauseOffSet1=0,pauseOffSet2=0,additionalTime;
    private boolean flag=true,isPlay=true;
    private  int counter1=0,counter2=0,currentPlayer=0;
    private TextView txtcounter1,txtcounter2;

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

        varTime=3;
        addTime1=0;
        addTime2=0;
        additionalTime=1000*2;
        baseTime=60*1005*varTime;

        player1.setBase(SystemClock.elapsedRealtime()+baseTime);
        player2.setBase(SystemClock.elapsedRealtime()+baseTime);

        pauseOffSet1=-baseTime;
        pauseOffSet2=-baseTime;

        player1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //counting number of taps and setting it to the txtcounter TextView...
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

                //counting number of taps and setting it to the txtcounter TextView...
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
    }
}