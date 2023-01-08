package com.android.front_vs_back;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(0);

        Button startBtn = (Button) findViewById(R.id.startBtn);
        Button resultBtn = (Button) findViewById(R.id.resultBtn);
        Button makerBtn = (Button)  findViewById(R.id.makerBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Count count = (Count) getApplication();
                count.setBack(0);
                count.setFront(0);
                Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
                startActivity(intent);
            }
        });

        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                startActivity(intent);
            }
        });

        makerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog dlg = new MyDialog(MainActivity.this);
                dlg.callDialog();
            }
        });

    }

    private long time = 0;
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() > time + 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(System.currentTimeMillis() <= time + 2000) {
            finishAffinity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Count count = (Count) getApplication();

        switch (item.getItemId()) {
            case R.id.theme_mode:
                if (count.getMode()){
                    ThemeManager.applyTheme(ThemeManager.ThemeMode.LIGHT);
                    count.setMode(!count.getMode());
                } else {
                    ThemeManager.applyTheme(ThemeManager.ThemeMode.DARK);
                    count.setMode(!count.getMode());
                }
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}