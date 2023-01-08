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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class QuestionsActivity extends AppCompatActivity {

    RadioGroup[] radioGroup = new RadioGroup[13];
    Integer[] radioGroupId = {R.id.radioG1, R.id.radioG2, R.id.radioG3, R.id.radioG4,
            R.id.radioG5, R.id.radioG6, R.id.radioG7, R.id.radioG8, R.id.radioG9, R.id.radioG10, R.id.radioG11, R.id.radioG12, R.id.radioG13};

    Button[] btnNext = new Button[13];
    Integer[] btnNextId = {R.id.btnNext1, R.id.btnNext2, R.id.btnNext3, R.id.btnNext4, R.id.btnNext5, R.id.btnNext6,
            R.id.btnNext7, R.id.btnNext8, R.id.btnNext9, R.id.btnNext10, R.id.btnNext11, R.id.btnNext12, R.id.btnNext13};

    RadioButton[] radioBtnFirst = new RadioButton[13];
    Integer[] radioBtnFirstId = {R.id.radioBtn1_1, R.id.radioBtn2_1, R.id.radioBtn3_1, R.id.radioBtn4_1, R.id.radioBtn5_1,
            R.id.radioBtn6_1, R.id.radioBtn7_1, R.id.radioBtn8_1, R.id.radioBtn9_1, R.id.radioBtn10_1, R.id.radioBtn11_1, R.id.radioBtn12_1, R.id.radioBtn13_1};

    RadioButton[] radioBtnSecond = new RadioButton[13];
    Integer[] radioBtnSecondId = {R.id.radioBtn1_2, R.id.radioBtn2_2, R.id.radioBtn3_2, R.id.radioBtn4_2, R.id.radioBtn5_2,
            R.id.radioBtn6_2, R.id.radioBtn7_2, R.id.radioBtn8_2, R.id.radioBtn9_2, R.id.radioBtn10_2, R.id.radioBtn11_2, R.id.radioBtn12_2, R.id.radioBtn13_2};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        getSupportActionBar().setElevation(0);

        final ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper1);

        for (int i = 0; i < radioGroupId.length; i++) {
            radioGroup[i] = (RadioGroup) findViewById(radioGroupId[i]);
        }

        for (int i = 0; i < btnNextId.length; i++) {
            btnNext[i] = (Button) findViewById(btnNextId[i]);
        }

        for (int i = 0; i < radioBtnFirstId.length; i++) {
            radioBtnFirst[i] = (RadioButton) findViewById(radioBtnFirstId[i]);
        }
        for (int i = 0; i < radioBtnSecondId.length; i++) {
            radioBtnSecond[i] = (RadioButton) findViewById(radioBtnSecondId[i]);
        }


        for (int i = 0; i < btnNextId.length; i++) {
            final int index;
            index = i;

            btnNext[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Count count = (Count) getApplication();
                    Intent intent = new Intent(getApplicationContext(), ResultLoadingActivity.class);


                    if(index != 12) {
                        if (radioBtnFirst[index].isChecked() == false && radioBtnSecond[index].isChecked() == false) {
                            Toast.makeText(getApplicationContext(), "항목을 선택해주세요", Toast.LENGTH_SHORT).show();
                        } else if (radioBtnFirst[index].isChecked()) {
                            count.setFront(count.getFront()+1);
                            viewFlipper.showNext();
                        } else if (radioBtnSecond[index].isChecked()) {
                            count.setBack(count.getBack()+1);
                            viewFlipper.showNext();
                        }
                    }

                    else{


                        if (radioBtnFirst[index].isChecked() == false && radioBtnSecond[index].isChecked() == false) {
                            Toast.makeText(getApplicationContext(), "항목을 선택해주세요", Toast.LENGTH_SHORT).show();
                        } else if (radioBtnFirst[index].isChecked()) {
                            count.setFront(count.getFront()+1);
                            startActivity(intent);
                        } else if (radioBtnSecond[index].isChecked()) {
                            count.setBack(count.getBack()+1);
                            startActivity(intent);
                        }
                    }


                }
            });
        }

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

}