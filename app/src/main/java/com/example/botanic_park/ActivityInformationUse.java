package com.example.botanic_park;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.example.botanic_park.PlantSearch.PlantBookItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ActivityInformationUse extends Activity {

    private ImageView imageView;
    private String imageURL;
    private String BaseURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_use);

        BaseURL="http://botanicpark.seoul.go.kr";

        // AsyncTask 작동시킴(파싱)
        new ParseInformationTask().execute();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TextView textView1 = findViewById(R.id.spannable1);
        TextView textView2 = findViewById(R.id.spannable2);

        // 일부 글자색을 바꿔줄 스트링을 가져온다
        String content1 = textView1.getText().toString();
        String content2 = textView2.getText().toString();
        SpannableString spannableString1 = new SpannableString(content1);
        SpannableString spannableString2 = new SpannableString(content2);

        // 글자색을 바꿔준 뒤
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#E46C21")), 12, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#E46C21")), 12, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 텍스트 뷰에 다시 입힌다
        textView1.setText(spannableString1);
        textView2.setText(spannableString2);

    }


    /* 웹에서 정보 긁어오는 클래스 */
    public class ParseInformationTask extends AsyncTask<Void, Void, Void> {
        String INFORMATION_USE_URL = "https://botanicpark.seoul.go.kr/front/introduce/useInfo.do";

        @Override
        protected Void doInBackground(Void... params) {
            try{
                // 인증서 있는 홈페이지를 인증서 없이도 연결 가능하게 설정
                SSLConnect sslConnect = new SSLConnect();
                sslConnect.postHttps(INFORMATION_USE_URL,1000,1000);

                // 웹에서 정보 읽어옴
                Document document = Jsoup.connect(INFORMATION_USE_URL).get();

                //해당 이미지 url 저장
                imageURL = document.select("div[class=info]").select("img").attr("src");

            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            //가져온 이미지 화면에 띄움
            imageView = findViewById(R.id.seoul_botanical_garden_section_place);
            Glide.with(getApplication()).load(BaseURL+imageURL).into(imageView);
        }
    }
}
