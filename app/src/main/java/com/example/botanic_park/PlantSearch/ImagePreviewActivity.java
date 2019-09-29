package com.example.botanic_park.PlantSearch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.botanic_park.OnSingleClickListener;
import com.example.botanic_park.R;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImagePreviewActivity extends AppCompatActivity {
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        bitmap = CameraSearchActivity.bitmap;
        ImageView imageView = findViewById(R.id.image_preview);
        Glide.with(this).load(bitmap).into(imageView);

        Button backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                CameraSearchActivity.bitmap = null; // 이미지 초기화
                finish();
            }
        });

        Button sendBtn = findViewById(R.id.send_btn);
        sendBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                PlantAPITask task =
                        new PlantAPITask(ImagePreviewActivity.this, getBase64EncodedImage(bitmap));
                task.execute();
            }
        });

        Button saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) { saveImage();
            }
        });
    }

    @Override
    public void onBackPressed() {
        CameraSearchActivity.bitmap = null; // 이미지 초기화
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        CameraSearchActivity.bitmap = null; // 이미지 초기화
        super.onDestroy();
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

}
