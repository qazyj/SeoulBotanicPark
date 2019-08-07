package com.example.botanic_park.PlantSearch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.botanic_park.R;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class ImagePreviewActivity extends AppCompatActivity {
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        bitmap = CameraSearchActivity.bitmap;
        ImageView imageView = findViewById(R.id.image_preview);
        Glide.with(this).load(bitmap).override(500,500).into(imageView);

        Button backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button sendBtn = findViewById(R.id.send_btn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchResult(bitmap);
            }
        });

        Button saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImage();
            }
        });
    }

    private void saveImage(){
        OutputStream outputStream = null;
        try {
            File image = createImageFile();
            outputStream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            // 갤러리에 반영
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(Uri.fromFile(image));
            getApplicationContext().sendBroadcast(mediaScanIntent);

            Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "저장하지 못했습니다.", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private File createImageFile() throws IOException {
        // 카메라에서 직은 사진을 저장할 폴더 및 파일 만듦

        // 이미지 파일 이름
        String timeStamp = new SimpleDateFormat("YYMMDD").format(new Date());
        String imageFileName = "BotanicPark" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/DCIM/BotanicPark/");
        if(!storageDir.exists())
            storageDir.mkdirs();    // 해당 폴더가 없는 경우 생성

        // 빈 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        //Log.d("테스트", "createImageFile : " + image.getAbsolutePath());

        return image;
    }

    private String getBase64EncodedImage(Bitmap bitmap){
        // 비트맵을 64bit로 인코딩한 string을 반환
        // 서버로 이미지를 보내기 위해서는 64bit 인코딩 필요
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);    // 저장된 이미지를 jpg로 포맷 품질 100으로 하여 출력
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        String result = Base64.encodeToString(byteArray, Base64.DEFAULT);  // 이미지 인코딩
        return result;
    }

    private void showSearchResult(Bitmap bitmap){
        ArrayList<String> result = new ArrayList<>();
        try {
            // request API
            PlantAPITask task = new PlantAPITask(this, getBase64EncodedImage(bitmap));
            ArrayList<ProbablePlant> plants = task.execute().get();
            Log.d("테스트", "API처리 끝남");
            for(ProbablePlant plant : plants){
                result.add(plant.name);
            }
            Log.d("테스트", "이름만 뽑기 끝남");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 검색 결과 창 띄우기
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra(SearchResultActivity.RESULT_TYPE, SearchResultActivity.IMAGE_SEARCH);
        intent.putExtra(Fragment_Plant_Book.SEARCH_WORD_KEY, result);
        startActivity(intent);
    }
}
