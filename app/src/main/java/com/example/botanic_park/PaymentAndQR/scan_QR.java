package com.example.botanic_park.PaymentAndQR;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.example.botanic_park.AppManager;
import com.example.botanic_park.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

public class scan_QR extends CaptureActivity {

    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        AppManager.getInstance().setScan_qr(this);
        qrScan = new IntentIntegrator(this);
        qrScan.setCaptureActivity(CaptureForm.class);
        qrScan.setOrientationLocked(true); // default가 세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경됩니다.
        qrScan.setPrompt("티켓에 있는 QR코드를 스캔하세요.");
        qrScan.initiateScan();
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
            } else {
                AppManager.getInstance().getMainActivity().setDateOfPayment();
                AppManager.getInstance().getPaymentPopUpActivity().finish();
                AppManager.getInstance().getMenuFloatingActionButton().callOnClick();
                AppManager.getInstance().setPaymentPopUpActivity(null);
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
