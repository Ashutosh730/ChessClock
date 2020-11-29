package com.example.chessclock;

public class CustomTimerData {
    private String title;
    private int minute,second,id;

    public CustomTimerData(int id,String title, int minute, int second) {
        this.id=id;
        this.title = title;
        this.minute = minute;
        this.second = second;
    }

    public String getTitle() {
        return title;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public int getId() {
        return id;
    }
}
