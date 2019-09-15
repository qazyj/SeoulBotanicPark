package com.example.botanic_park.PaymentAndQR;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import androidx.annotation.Nullable;
import com.example.botanic_park.AppManager;
import com.example.botanic_park.R;

import java.util.Calendar;
import java.util.TimeZone;

public class PaymentPopUpActivity extends Activity {
    Animation translateDown;
    ImageView zeroPay;
    Activity thisActivity = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 타이틀바 없애기
        setContentView(R.layout.activity_payment_pop_up);
        zeroPay = (ImageView) findViewById(R.id.zero_pay);

        ImageView closeBtn = findViewById(R.id.close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getInstance().setPaymentPopUpActivity(null);  // 액티비티 닫음
                finish();
            }
        });


        if (canBuyTicketNow()) doToBuyTicket();
        else
            ((TextView) findViewById(R.id.limit)).setText("현재 결제는 불가능합니다. \n 오늘 마감 시간은 \n" + String.valueOf(AppManager.getInstance().getMainActivity().limitTime) + ":00 입니다.");

    }

    @Override
    public void onBackPressed() {
        AppManager.getInstance().setPaymentPopUpActivity(null);  // 액티비티 닫음
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    private boolean canBuyTicketNow() {

        TimeZone jst = TimeZone.getTimeZone("Asia/Seoul");
        Calendar calendar = Calendar.getInstance(jst);

        int limitTime = AppManager.getInstance().getMainActivity().limitTime;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour < limitTime) {
            if ((hour == limitTime - 1) && calendar.get(Calendar.MINUTE) >= 30) return false;
            return true;
        }

        return false;
    }

    private void doToBuyTicket() {
        Toast.makeText(getApplicationContext(), "오늘 마감 시간은 " + String.valueOf(AppManager.getInstance().getMainActivity().limitTime) + ":00 입니다.", Toast.LENGTH_SHORT).show();
        AppManager.getInstance().setPaymentPopUpActivity(this);
        translateDown = AnimationUtils.loadAnimation(this, R.anim.slide_down_layout);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        mainLayout.startAnimation(translateDown);
        mainLayout.setVisibility(View.VISIBLE);

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

    }
}
