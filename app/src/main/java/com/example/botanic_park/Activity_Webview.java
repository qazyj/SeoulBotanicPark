package com.example.botanic_park;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Activity_Webview extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //상태 바 색 바꿔줌
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#FAFAFA"));
        setContentView(R.layout.activity_webview);

        Intent intent = getIntent();

        String indexForWebView = intent.getExtras().getString("index");

        mWebView=(WebView)findViewById(R.id.webView);
        mWebView.setWebViewClient(new WebViewClient()); //새창이 안뜨게하는 코드
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);

        switch(indexForWebView) {
            case "seoul_botanic_park_information_use":
                mWebView.loadUrl("http://botanicpark.seoul.go.kr/front/introduce/useInfo.do");
            break;
            case "seoul_botanic_park_way_to_come":
                mWebView.loadUrl("http://botanicpark.seoul.go.kr/front/introduce/location.do");
                break;
            case "seoul_botanic_park_news":
                mWebView.loadUrl("http://botanicpark.seoul.go.kr/front/board/newsList.do");
                break;
            case "seoul_botanic_park_community":
                mWebView.loadUrl("http://botanicpark.seoul.go.kr/front/introduce/useInfo.do");
                break;
            default:
                break;
        }
    }
}
