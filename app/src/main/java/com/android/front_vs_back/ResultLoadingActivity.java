package com.android.front_vs_back;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ResultLoadingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultloading);

        Count count = (Count) getApplication();

        ImageView loading_gif = (ImageView) findViewById(R.id.loading_gif);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linear1);
        TextView textView = (TextView) findViewById(R.id.text_result);

        if (count.getMode()){
            Glide.with(this).load(R.drawable.progress_bar_v2).into(loading_gif);
            layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            textView.setTextColor(Color.parseColor("#000000"));
        } else {
            Glide.with(this).load(R.drawable.progress_bar).into(loading_gif);
        }

        startLoading();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), ResultActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2700);
    }
}