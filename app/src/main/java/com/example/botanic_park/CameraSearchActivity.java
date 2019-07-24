package com.example.botanic_park;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

/* 이미지 식물 검색 액티비티 */
public class CameraSearchActivity extends AppCompatActivity {
    private CameraSurfaceView surfaceView;
    private ImageView galleryBtn;

    private int cameraFacing;  // 카메라 전면,후면 구분
    private static CameraSearchActivity activity;

    private static final int PICK_FROM_ALBUM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;
        cameraFacing = Camera.CameraInfo.CAMERA_FACING_BACK;

        init();
    }

    private void init() {
        surfaceView = new CameraSurfaceView(activity, cameraFacing);
        setContentView(surfaceView);
        addContentView(LayoutInflater.from(activity).inflate( R.layout.activity_camera_search, null),
                new FrameLayout.LayoutParams( FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT ));

        FrameLayout frameLayout = findViewById(R.id.camera_preview);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                surfaceView.autoFocus();    // 자동 초점 맞춤
            }
        });

        ImageButton captureBtn = findViewById(R.id.capture_btn);
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capture();
            }
        });

        ImageButton closeBtn = findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();   // 액티비티 닫음
            }
        });

        ImageButton switchCameraBtn = findViewById(R.id.switch_camera_btn);
        switchCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 카메라 화면 전환
                cameraFacing = (cameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK) ?
                        Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK;
                surfaceView = new CameraSurfaceView(activity, cameraFacing);
                init();
            }
        });

        galleryBtn = findViewById(R.id.gallery_btn);
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAlbum();
            }
        });
    }

    private void capture(){
        surfaceView.capture(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                galleryBtn.setImageBitmap(bitmap);

                camera.startPreview();
            }
        });
    }

    private void goToAlbum(){
        // 갤러리로 이동
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

}
