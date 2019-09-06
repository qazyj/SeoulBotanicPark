package com.example.botanic_park.PaymentAndQR;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.Nullable;
import com.example.botanic_park.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class QRPopUpActivity extends Activity {

    private ImageView iv;
    private WindowManager.LayoutParams params;
    private float brightness;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 타이틀바 없애기
        setContentView(R.layout.activity_qr_pop_up);
        params = getWindow().getAttributes();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);


        ImageView closeBtn = findViewById(R.id.close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();   // 액티비티 닫음
            }
        });

        try{
            iv = (ImageView)findViewById(R.id.qrcode);
            Random rnd = new Random();
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter.encode(String.valueOf(rnd.nextInt()), BarcodeFormat.QR_CODE,300,300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            iv.setImageBitmap(bitmap);

        }catch (Exception e){}

        TextView date = findViewById(R.id.date);
        Date today = Calendar.getInstance().getTime();
        date.setText(new SimpleDateFormat("MM/dd (EE)", Locale.getDefault()).format(today));

        Toast.makeText(getApplicationContext(), "QR화면은 캡처가 불가합니다.", Toast.LENGTH_SHORT).show();
        //getSharedPreferences("userData",Activity.MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        brightness = params.screenBrightness;
        // 최대 밝기로 설정
        params.screenBrightness = 1f;
        // 밝기 설정 적용
        getWindow().setAttributes(params);
    }

    @Override protected void onPause() {
        super.onPause();

        // 기존 밝기로 변경
        params.screenBrightness = brightness;
        getWindow().setAttributes(params);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }


}