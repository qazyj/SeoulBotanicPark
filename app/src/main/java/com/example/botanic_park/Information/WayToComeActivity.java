package com.example.botanic_park.Information;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.botanic_park.R;

public class WayToComeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_to_come);

        TextView textView = findViewById(R.id.traffic_information2);
        TextView textView1 = findViewById(R.id.way_to_come_car);
        TextView textView2 = findViewById(R.id.line22);
        TextView textView3 = findViewById(R.id.line23);

        // 일부 글자색을 바꿔줄 스트링을 가져온다
        String content = textView.getText().toString();
        String content1 = textView1.getText().toString();
        String content2 = textView2.getText().toString();
        String content3 = textView3.getText().toString();

        SpannableString spannableString = new SpannableString(content);
        SpannableString spannableString1 = new SpannableString(content1);
        SpannableString spannableString2 = new SpannableString(content2);
        SpannableString spannableString3 = new SpannableString(content3);

        // 글자색을 바꿔준 뒤
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new StyleSpan(Typeface.BOLD), 0, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new AbsoluteSizeSpan(33), 0, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), 0, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new StyleSpan(Typeface.BOLD), 79, 107, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new AbsoluteSizeSpan(33), 79, 107, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), 79, 107, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString2.setSpan(new StyleSpan(Typeface.BOLD), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString3.setSpan(new StyleSpan(Typeface.BOLD), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString3.setSpan(new ForegroundColorSpan(Color.parseColor("#000000")), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        // 텍스트 뷰에 다시 입힌다
        textView.setText(spannableString);
        textView1.setText(spannableString1);
        textView2.setText(spannableString2);
        textView3.setText(spannableString3);
    }
}