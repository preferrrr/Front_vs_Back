package com.android.front_vs_back;

import android.app.Application;

public class Count extends Application {

    private int front=0;
    private int back=0;
    private boolean theme_mode = false;

    public int getFront() {
        return front;
    }

    public int getBack() {
        return back;
    }

    public boolean getMode() { return theme_mode; }

    public void setFront(int front) {
        this.front = front;
    }

    public void setBack(int back) {
        this.back = back;
    }

    public void setMode(boolean mode) { this.theme_mode = mode;}
}
