package com.example.botanic_park.PaymentAndQR;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.botanic_park.AppManager;
import com.example.botanic_park.OnSingleClickListener;
import com.example.botanic_park.R;
import com.journeyapps.barcodescanner.CaptureActivity;

public class CaptureForm extends CaptureActivity {

    Boolean isCliked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v  = getLayoutInflater().inflate(R.layout.layout_capture_form, null);
        addContentView(v,new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        ImageView close_btn = v.findViewById(R.id.close);

        close_btn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if(isCliked) return;
                isCliked = true;
                AppManager.getInstance().getScan_qr().finish();
                AppManager.getInstance().setScan_qr(null);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AppManager.getInstance().getScan_qr().finish();
        AppManager.getInstance().setScan_qr(null);
        finish();
    }
}
