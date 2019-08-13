package com.example.botanic_park.PaymentAndQR;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.botanic_park.AppManager;
import com.example.botanic_park.R;
import com.journeyapps.barcodescanner.CaptureActivity;

public class CaptureForm extends CaptureActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View v  = getLayoutInflater().inflate(R.layout.layout_capture_form, null);
        addContentView(v,new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        ImageView close_btn = v.findViewById(R.id.close);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getInstance().getScan_qr().finish();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AppManager.getInstance().getScan_qr().finish();
        finish();
    }
}
