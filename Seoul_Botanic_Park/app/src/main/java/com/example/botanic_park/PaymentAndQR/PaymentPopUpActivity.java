package com.example.botanic_park.PaymentAndQR;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.example.botanic_park.AppManager;
import com.example.botanic_park.R;

public class PaymentPopUpActivity extends Activity {
    Animation translateDown;
    ImageView zeroPay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 타이틀바 없애기
        setContentView(R.layout.activity_payment_pop_up);
        AppManager.getInstance().setPaymentPopUpActivity(this);

        Activity thisActivity = this;

        translateDown = AnimationUtils.loadAnimation(this,R.anim.slide_down_layout);

        LinearLayout mainLayout =  (LinearLayout)findViewById(R.id.main_layout);
        mainLayout.startAnimation(translateDown);
        mainLayout.setVisibility(View.VISIBLE);

        ImageView closeBtn = findViewById(R.id.close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getInstance().setPaymentPopUpActivity(null);  // 액티비티 닫음
                finish();
            }
        });

        FrameLayout ticketPayment = findViewById(R.id.ticket_payment);
        FrameLayout ticketScanning = findViewById(R.id.ticket_scanning);

        ticketPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, KakaoPay.class);
                startActivity(intent);

            }
        });

        ticketScanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisActivity, scan_QR.class);
                startActivity(intent);

            }
        });

        zeroPay = (ImageView) findViewById(R.id.zero_pay);
    }

    @Override
    public void onBackPressed() {
        AppManager.getInstance().setPaymentPopUpActivity(null);  // 액티비티 닫음
        finish();
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
