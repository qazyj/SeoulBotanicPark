package com.example.botanic_park.Information;

import android.app.Activity;

import android.graphics.Color;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import android.view.View;

import android.widget.TextView;
import com.example.botanic_park.AppManager;
import com.example.botanic_park.R;


public class InformationUseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //상태 바 색 바꿔줌
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#FAFAFA"));
        setContentView(R.layout.activity_information_use);
        AppManager.getInstance().setInformationUseActivity(this);


        TextView textView1 = findViewById(R.id.spannable1);
        TextView textView2 = findViewById(R.id.spannable2);
        TextView textView3 = findViewById(R.id.themeinformation);
        TextView textView4 = findViewById(R.id.themeinformation2);
        TextView textView5 = findViewById(R.id.informationuse1);
        TextView textView6 = findViewById(R.id.age_classification1);
        TextView textView7 = findViewById(R.id.age_classification2);
        TextView textView8 = findViewById(R.id.age_classification3);


        // 일부 글자색을 바꿔줄 스트링을 가져온다
        String content1 = textView1.getText().toString();
        String content2 = textView2.getText().toString();
        String content3 = textView3.getText().toString();
        String content4 = textView4.getText().toString();
        String content5 = textView5.getText().toString();
        String content6 = textView6.getText().toString();
        String content7 = textView7.getText().toString();
        String content8 = textView8.getText().toString();

        SpannableString spannableString1 = new SpannableString(content1);
        SpannableString spannableString2 = new SpannableString(content2);
        SpannableString spannableString3 = new SpannableString(content3);
        SpannableString spannableString4 = new SpannableString(content4);
        SpannableString spannableString5 = new SpannableString(content5);
        SpannableString spannableString6 = new SpannableString(content6);
        SpannableString spannableString7 = new SpannableString(content7);
        SpannableString spannableString8 = new SpannableString(content8);


        // 글자색을 바꿔준 뒤
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#E46C21")), 12, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#E46C21")), 12, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString3.setSpan(new ForegroundColorSpan(Color.parseColor("#90BC43")), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString4.setSpan(new ForegroundColorSpan(Color.parseColor("#90BC43")), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString5.setSpan(new ForegroundColorSpan(Color.parseColor("#90BC43")), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString6.setSpan(new ForegroundColorSpan(Color.parseColor("#90BC43")), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString7.setSpan(new ForegroundColorSpan(Color.parseColor("#90BC43")), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString8.setSpan(new ForegroundColorSpan(Color.parseColor("#90BC43")), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 텍스트 뷰에 다시 입힌다
        textView1.setText(spannableString1);
        textView2.setText(spannableString2);
        textView3.setText(spannableString3);
        textView4.setText(spannableString4);
        textView5.setText(spannableString5);
        textView6.setText(spannableString6);
        textView7.setText(spannableString7);
        textView8.setText(spannableString8);

    }
}
