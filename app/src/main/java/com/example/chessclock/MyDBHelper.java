package com.example.chessclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME="TimerLibrary.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="timer";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_TITLE="title";
    private static final String COLUMN_MINUTE="minute";
    private static final String COLUMN_SECOND="second";



    public MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE_NAME+" ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_TITLE+" TEXT, "+
                COLUMN_MINUTE+" INTEGER, "+
                COLUMN_SECOND+" INTEGER);";

        db.execSQL(query);
        defaultTimer(db);
    }

    private void defaultTimer(SQLiteDatabase db) {

        ContentValues cv=new ContentValues();
        cv.put(COLUMN_TITLE,"Blitz(3|2)");
        cv.put(COLUMN_MINUTE,3);
        cv.put(COLUMN_SECOND,2);
        db.insert(TABLE_NAME,null,cv);

        cv.clear();
        cv.put(COLUMN_TITLE,"Fischer(5|5)");
        cv.put(COLUMN_MINUTE,5);
        cv.put(COLUMN_SECOND,5);
        db.insert(TABLE_NAME,null,cv);

        cv.clear();
        cv.put(COLUMN_TITLE,"Fischer Rapid(10|5)");
        cv.put(COLUMN_MINUTE,10);
        cv.put(COLUMN_SECOND,5);
        db.insert(TABLE_NAME,null,cv);

        cv.clear();
        cv.put(COLUMN_TITLE,"Delay Bullet(1|2)");
        cv.put(COLUMN_MINUTE,1);
        cv.put(COLUMN_SECOND,2);
        db.insert(TABLE_NAME,null,cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }


    public void addTimer(String title,int mintue,int second){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_MINUTE,mintue);
        cv.put(COLUMN_SECOND,second);
        long result=db.insert(TABLE_NAME,null,cv);

        if(result==-1)
            Toast.makeText(context, "Timer Not Created... Please Try Again", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Timer Created...", Toast.LENGTH_SHORT).show();
    }

    public Cursor readAllData(){
        String query="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=null;
        if(db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
}
