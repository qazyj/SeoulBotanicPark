package com.example.botanic_park.PlantSearch;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * 카메라 미리보기 화면을 구현하기 위한 것
 */
public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    public Camera mCamera = null;
    private Context context;
    private int facing;   // 카메라 전면, 후면 구분


    public CameraSurfaceView(Context context, int facing) {
        super(context);
        init(context);

        this.facing = facing;
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    // 초기화를 위한 메서드
    private void init(Context context) {
        this.context = context;
        mHolder = getHolder(); // 서피스뷰 내에 있는 SurfaceHolder 라고 하는 객체를 참조할 수 있다.
        mHolder.addCallback(this); // holder
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mCamera = Camera.open(facing);
        mCamera.setDisplayOrientation(90); //미리보기 화면 회전되어 나옴

        try {
            mCamera.setPreviewDisplay(mHolder); // Camera 객체에 이 서피스뷰를 미리보기로 하도록 설정
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* 서피스뷰가 크기와 같은 것이 변경되는 시점에 호출
     * 화면에 보여지기 전 크기가 결정되는 시점 */
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mCamera.stopPreview();

        // 여러 프로그램에서 동시에 쓸 때 한쪽에서 lock 을 걸어
        // 사용할 수 없는 상태가 될 수 있기 때문에, release 를 꼭 해주어야함
        mCamera.release(); // 리소스 해제
        mCamera = null;
    }


    // 서피스뷰에서 사진을 찍도록 하는 메서드
    public void capture(Camera.PictureCallback jpegCallback) {
        if(mCamera != null) {
            mCamera.takePicture(shutterCallback, rawCallback, jpegCallback);
        }
    }

    private Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            // 셔터음 콜백
        }
    };

    private Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            // 이미지 원본 콜백
        }
    };

    // 카메라 자동 초점 맞춤
    public void autoFocus() {
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    // 초점 맞추기 성공
                } else {
                    // 초점 맞추기 실패
                }

            }
        });
    }
}