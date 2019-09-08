package com.example.botanic_park.PlantSearch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.botanic_park.AppManager;
import com.example.botanic_park.R;

import java.io.*;
import java.util.ArrayList;


/* 이미지 식물 검색 액티비티 */
public class CameraSearchActivity extends AppCompatActivity {
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;

    private CameraSurfaceView surfaceView;
    private ImageView galleryBtn;
    private ProgressBar progressBar;

    public static Bitmap bitmap;
    private int cameraFacing;  // 카메라 전면,후면 구분

    private ArrayList<PlantBookItem> list;          // 전체 식물 리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 전체 식물 리스트 받아옴
        list = AppManager.getInstance().getList();

        bitmap = null;
        cameraFacing = Camera.CameraInfo.CAMERA_FACING_BACK;

        init();
    }

    private void init() {
        surfaceView = new CameraSurfaceView(CameraSearchActivity.this, cameraFacing);
        setContentView(surfaceView);
        addContentView(LayoutInflater.from(CameraSearchActivity.this).inflate(R.layout.activity_camera_search, null),
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        LinearLayout linearLayout = findViewById(R.id.camera_preview);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                surfaceView.autoFocus();    // 자동 초점 맞춤
            }
        });

        ImageButton captureBtn = findViewById(R.id.capture_btn);
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                surfaceView.capture(new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        options.inSampleSize = 4;
                        Bitmap originalBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);  // 회전

                        bitmap = Bitmap.createBitmap(originalBitmap, 0, 0,
                                originalBitmap.getWidth(), originalBitmap.getHeight(), matrix, true);

                        Intent intent = new Intent(CameraSearchActivity.this, ImagePreviewActivity.class);
                        startActivityForResult(intent, PICK_FROM_CAMERA);  //  카메라 미리보기 화면 보여주기
                    }
                });
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
                surfaceView = new CameraSurfaceView(CameraSearchActivity.this, cameraFacing);
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

    @Override
    protected void onResume() {
        super.onResume();
        if(bitmap != null){     // request API
            PlantAPITask task =
                    new PlantAPITask(CameraSearchActivity.this, getBase64EncodedImage(bitmap));
            task.execute();
            bitmap = null;
        }
    }

    private void goToAlbum() {
        // 갤러리로 이동
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /*
         * 예외사항 처리
         * 앨범으로 이동했지만 선택하지 않고 뒤로 간 경우
         */
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {
            Uri photoUri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(photoUri);
                bitmap = BitmapFactory.decodeStream(inputStream);   // 가져온 비트맵 저장

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {    // PICK_FORM_CAMERA
            bitmap = null;
        }
    }

    private String getBase64EncodedImage(Bitmap bitmap) {
        // 비트맵을 64bit로 인코딩한 string을 반환
        // 서버로 이미지를 보내기 위해서는 64bit 인코딩 필요
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);    // 저장된 이미지를 jpg로 포맷 품질 100으로 하여 출력
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        String result = Base64.encodeToString(byteArray, Base64.DEFAULT);  // 이미지 인코딩
        return result;
    }

}


