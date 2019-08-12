package com.example.botanic_park.PaymentAndQR;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.example.botanic_park.R;

public class PaymentPopUpActivity extends Activity {
    Animation translateDown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 타이틀바 없애기
        setContentView(R.layout.activity_payment_pop_up);

        Activity thisActivity = this;

        translateDown = AnimationUtils.loadAnimation(this,R.anim.slide_down_layout);


        LinearLayout mainLayout =  (LinearLayout)findViewById(R.id.main_layout);
        mainLayout.startAnimation(translateDown);
        mainLayout.setVisibility(View.VISIBLE);

        ImageButton closeBtn = findViewById(R.id.close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();   // 액티비티 닫음
            }
        });

        ImageView ticketPayment = findViewById(R.id.ticket_payment);
        ImageView ticketScanning = findViewById(R.id.ticket_scanning);

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
    }

}
