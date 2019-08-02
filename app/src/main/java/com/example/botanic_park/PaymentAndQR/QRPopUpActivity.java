package com.example.botanic_park.PaymentAndQR;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import com.example.botanic_park.R;


public class QRPopUpActivity extends Activity {

    SharedPreferences _userData;
    private final boolean isPaid = true;
    private final boolean isNotPaid = false;
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 타이틀바 없애기
        setContentView(R.layout.activity_qr_pop_up);

        //getSharedPreferences("userData",Activity.MODE_PRIVATE);

    }
}