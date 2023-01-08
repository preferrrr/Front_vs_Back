package com.android.front_vs_back;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().setElevation(0);

        ImageView imageResult = (ImageView) findViewById(R.id.imageResult);
        TextView textResult1 = (TextView) findViewById(R.id.textResult1);
        TextView textResult2 = (TextView) findViewById(R.id.textResult2);

        Button btnBack = (Button) findViewById(R.id.btnBack);
        Button shareBtn = (Button) findViewById(R.id.shareBtn);
        Button vedioBtn = (Button) findViewById(R.id.vedioBtn);

        // front - back >=  5 = front 9 4
        // front - back <= -5 = back  4 9
        // -5< front - back < 5 = full -4 ~ 4 / 5, 8 / 6, 7 / 7, 6 / 8, 5 /
        Count count = (Count) getApplication();

        String front1 = "디자이너 갬성 물씬~\n완벽 주의자 프론트엔드";
        String front2 = "1px에 집착하고 자간에 집착하는 당신. 완벽한 레이아웃이 아니면 개발을 시작할 수 없어 ! " +
                "대칭 균형 딱딱 맞는 사이트를 완벽하게 구현해냈을 때의 희열이란 ! " +
                "프론트는 당신의 데스티니~ 누구보다 미적 감각이 뛰어난 당신 혹시 전생에 예술가는 아니셨는지 ? " +
                "혼자서도 잘하는 당신이지만, 가끔은 당신의 도움을 간절히 바라는 친구들은 없는지 주변을 둘러봐주세요.";

        String back1 = "나는야 API 공장장\n알파고 백엔드";
        String back2 = "갬성이 뭐죠 ? 애니메이션은 먹는 건가요 ? 디자인은 알 바 아니고 나는 내가 생각한게 맞는지가 제일 중요해 ! " +
                "인풋. 아웃풋. 효율. 삐빅. 터미널 접속. 판다 로직. " +
                "보낸다. 빠르게. 데이터. 삐빅. 이런 정신상태로는 안돼. 삐빅. 임무 완료. 그" +
                "렇지만 따뜻한 마음을 가진 당신은 휴먼~ 무심한 듯 툭 던지는 진심 어린 말 한마디로 모두를 감동시키고 있는 것은 아닌지 ?";

        String full1 = "놓치지 않을 거에요 ~\n문어발 풀스택";
        String full2 = "디자인이라도 놀고 싶고 데이터랑도 놀고 싶은 당신은 욕심쟁이 우후훗 ~ " +
                "어차피 다 하게 될거 좀 더 끌리는 걸 먼저 배워볼까나 ~ 뭘 해도 잘할 당신 ! " +
                "지금은 내면의 목소리에 조금 더 귀를 기울여보면 어떨까요 ?";

        if(count.getFront() - count.getBack() >= 5) {
            imageResult.setImageResource(R.drawable.frontend);

            textResult1.setText(front1);
            textResult2.setText(front2);
        }
        else if(count.getFront() - count.getBack() <= -5) {
            imageResult.setImageResource(R.drawable.backend);

            textResult1.setText(back1);
            textResult2.setText(back2);
        }
        else {
            imageResult.setImageResource(R.drawable.fullstack);

            textResult1.setText(full1);
            textResult2.setText(full2);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View container;
                container = getWindow().getDecorView();
                container.buildDrawingCache();
                Bitmap captureView = container.getDrawingCache();

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DISPLAY_NAME, "capture.jpg");
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
                values.put(MediaStore.Images.Media.IS_PENDING, 1);

                ContentResolver contentResolver = getContentResolver();
                Uri collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                Uri item = contentResolver.insert(collection, values);

                try {
                    ParcelFileDescriptor pdf = contentResolver.openFileDescriptor(item, "w", null);
                    if (pdf == null){

                    } else {
                        InputStream inputStream = getImageInputStram(captureView);
                        byte[] strToByte = getBytes(inputStream);
                        FileOutputStream fos = new FileOutputStream(pdf.getFileDescriptor());
                        fos.write(strToByte);
                        fos.close();
                        inputStream.close();
                        pdf.close();
                        contentResolver.update(item, values, null, null);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                values.clear();
                values.put(MediaStore.Images.Media.IS_PENDING, 0);
                contentResolver.update(item, values, null, null);

                Intent shareintent = new Intent(Intent.ACTION_SEND);
                String path = MediaStore.Images.Media.insertImage(contentResolver, captureView, "title", null);
                Uri uri = Uri.parse(path);
                shareintent.putExtra(Intent.EXTRA_STREAM, uri);
                shareintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareintent.setType("image/*");
                startActivity(shareintent);
           }

            private InputStream getImageInputStram(Bitmap bmp) {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                byte[] bitmapData = bytes.toByteArray();
                ByteArrayInputStream bs = new ByteArrayInputStream(bitmapData);

                return bs;
            }

            public byte[] getBytes(InputStream inputStream) throws IOException {
                ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];

                int len = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    byteBuffer.write(buffer, 0, len);
                }
                return byteBuffer.toByteArray();
            }
        });


        vedioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);

                if(count.getFront() - count.getBack() >= 5){
                    intent.setData(Uri.parse("https://www.youtube.com/watch?v=bFaKx7_epvY"));
                    startActivity(intent);
                }
                else if(count.getFront() - count.getBack() <= -5){
                    intent.setData(Uri.parse("https://www.youtube.com/watch?v=8oIJ26CNPXQ"));
                    startActivity(intent);
                }

                else{
                    intent.setData(Uri.parse("https://www.youtube.com/watch?v=2HKO20NWE04"));
                    startActivity(intent);
                }
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
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
