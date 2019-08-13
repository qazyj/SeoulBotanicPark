package com.example.botanic_park.PaymentAndQR;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import com.example.botanic_park.AppManager;
import com.example.botanic_park.R;
import kr.co.bootpay.*;
import kr.co.bootpay.enums.PG;


public class KakaoPay extends Activity {

    private String application_id = "5d43308c02f57e0037f7fdaa";
    private KakaoPay kakaoPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_pay);
        BootpayAnalytics.init(this, application_id);
        kakaoPay = this;
        // 초기설정 - 해당 프로젝트(안드로이드)의 application id 값을 설정합니다. 결제와 통계를 위해 꼭 필요합니다.
//        BootpayAnalytics.init(this, "5b14c0ffb6d49c40cda92c4e");

//        BootpayAnalytics.init(this, "59a7e647396fa64fcad4a8c2");

        onClick_request();

    }

    public void onClick_request() {
        // 결제호출
        Bootpay.init(getFragmentManager())
                .setApplicationId(application_id) // 해당 프로젝트(안드로이드)의 application id 값
                .setPG(PG.KCP)
                .setName("서울식물원 대인 1명") // 결제할 상품명
                .setOrderId("1") // 결제 고유번호
                .setPrice(5000) // 결제할 금액
               .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                    @Override
                    public void onConfirm(@Nullable String message) {
                        Bootpay.confirm(message); // 재고가 있을 경우.
                    }
                })
                .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                    @Override
                    public void onDone(@Nullable String message) {
                        AppManager.getInstance().getMainActivity().setDateOfPayment();
                        AppManager.getInstance().getMenuFloatingActionButton().callOnClick();

                    }
                })
                .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                    @Override
                    public void onReady(@Nullable String message) {
                        Log.d("ready", message);
                    }
                })
                .onCancel(new CancelListener() { // 결제 취소시 호출
                    @Override
                    public void onCancel(@Nullable String message) {
                        finish();
                    }
                })
                .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                    @Override
                    public void onError(@Nullable String message) {
                        Log.d("error", message);
                    }
                })
                .onClose(new CloseListener() { //결제창이 닫힐때 실행되는 부분
                    @Override
                    public void onClose(String message) {
                        finish();
                    }
                })
                .show();
    }


}
