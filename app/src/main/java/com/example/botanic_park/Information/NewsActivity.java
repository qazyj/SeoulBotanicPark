package com.example.botanic_park.Information;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.botanic_park.R;
import com.example.botanic_park.SSLConnect;
import com.example.botanic_park.WebViewActivity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NewsActivity extends Activity {

    private ImageView imageView;
    private String obtainedString;
    private TextView inputTextView;
    private String connectURLString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // AsyncTask 작동시킴(파싱)
        new ParseInformationTask().execute();

        //이용안내 창 들어간다음 사진이 늦게나오는걸 방지하기 위해 쓰레드 슬립줌
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* 웹에서 정보 긁어오는 클래스 */

    public class ParseInformationTask extends AsyncTask<Void, Void, Void> {
        String NEWS_URL = "https://botanicpark.seoul.go.kr/front/board/newsList.do";
        String imageURL1;
        String imageURL2;
        String imageURL3;
        String imageURL4;
        String imageURL5;
        String imageURL6;
        String imageURL7;
        String imageURL8;
        String imageURL9;
        String imageURL10;
        String imageURL11;

        @Override
        protected Void doInBackground(Void... params) {
            try{
                // 인증서 있는 홈페이지를 인증서 없이도 연결 가능하게 설정
                SSLConnect sslConnect = new SSLConnect();
                sslConnect.postHttps(NEWS_URL,1000,1000);

                // 웹에서 정보 읽어옴
                Document document = Jsoup.connect(NEWS_URL).get();

                //해당 이미지 url 저장
                Elements textElements = document.select("div[class=table_list]").select("td");
                Elements URLElements = document.select("div[class=left]").select("a");

                //no1
                obtainedString = textElements.get(0).text();
                inputTextView = findViewById(R.id.no1);
                inputTextView.setText(obtainedString);

                //title1
                obtainedString = textElements.get(2).text();
                inputTextView = (TextView) findViewById(R.id.title1);
                inputTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connectURLString = textElements.get(2).select("a").attr("href");
                        if(connectURLString != null) {
                            Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                            intent.putExtra("detailsInformationURL", "http://botanicpark.seoul.go.kr" + connectURLString);
                            startActivity(intent);
                        }
                    }
                });
                inputTextView.setText(obtainedString);


                //registration_date1
                obtainedString = textElements.get(3).text();
                inputTextView = findViewById(R.id.registration_date1);
                inputTextView.setText(obtainedString);

                //views1
                obtainedString = textElements.get(4).text();
                inputTextView = findViewById(R.id.views1);
                inputTextView.setText(obtainedString);



                //no2
                obtainedString = textElements.get(5).text();
                inputTextView = findViewById(R.id.no2);
                inputTextView.setText(obtainedString);

                //title2
                obtainedString = textElements.get(7).text();
                inputTextView = findViewById(R.id.title2);
                inputTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connectURLString = textElements.get(7).select("a").attr("href");
                        if(connectURLString != null) {
                            Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                            intent.putExtra("detailsInformationURL", "http://botanicpark.seoul.go.kr" + connectURLString);
                            startActivity(intent);
                        }
                    }
                });
                inputTextView.setText(obtainedString);

                //registration_date2
                obtainedString = textElements.get(8).text();
                inputTextView = findViewById(R.id.registration_date2);
                inputTextView.setText(obtainedString);

                //views2
                obtainedString = textElements.get(9).text();
                inputTextView = findViewById(R.id.views2);
                inputTextView.setText(obtainedString);



                //no3
                obtainedString = textElements.get(10).text();
                inputTextView = findViewById(R.id.no3);
                inputTextView.setText(obtainedString);

                //title3
                obtainedString = textElements.get(12).text();
                inputTextView = findViewById(R.id.title3);
                inputTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connectURLString = textElements.get(12).select("a").attr("href");
                        if(connectURLString != null) {
                            Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                            intent.putExtra("detailsInformationURL", "http://botanicpark.seoul.go.kr" + connectURLString);
                            startActivity(intent);
                        }
                    }
                });
                inputTextView.setText(obtainedString);

                //registration_date3
                obtainedString = textElements.get(13).text();
                inputTextView = findViewById(R.id.registration_date3);
                inputTextView.setText(obtainedString);

                //views3
                obtainedString = textElements.get(14).text();
                inputTextView = findViewById(R.id.views3);
                inputTextView.setText(obtainedString);



                //no4
                obtainedString = textElements.get(15).text();
                inputTextView = findViewById(R.id.no4);
                inputTextView.setText(obtainedString);

                //title4
                obtainedString = textElements.get(17).text();
                inputTextView = findViewById(R.id.title4);
                inputTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connectURLString = textElements.get(17).select("a").attr("href");
                        if(connectURLString != null) {
                            Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                            intent.putExtra("detailsInformationURL", "http://botanicpark.seoul.go.kr" + connectURLString);
                            startActivity(intent);
                        }
                    }
                });
                inputTextView.setText(obtainedString);

                //registration_date4
                obtainedString = textElements.get(18).text();
                inputTextView = findViewById(R.id.registration_date4);
                inputTextView.setText(obtainedString);

                //views4
                obtainedString = textElements.get(19).text();
                inputTextView = findViewById(R.id.views4);
                inputTextView.setText(obtainedString);



                //no5
                obtainedString = textElements.get(20).text();
                inputTextView = findViewById(R.id.no5);
                inputTextView.setText(obtainedString);

                //title5
                obtainedString = textElements.get(22).text();
                inputTextView = findViewById(R.id.title5);
                inputTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connectURLString = textElements.get(22).select("a").attr("href");
                        if(connectURLString != null) {
                            Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                            intent.putExtra("detailsInformationURL", "http://botanicpark.seoul.go.kr" + connectURLString);
                            startActivity(intent);
                        }
                    }
                });
                inputTextView.setText(obtainedString);

                //registration_date5
                obtainedString = textElements.get(23).text();
                inputTextView = findViewById(R.id.registration_date5);
                inputTextView.setText(obtainedString);

                //views5
                obtainedString = textElements.get(24).text();
                inputTextView = findViewById(R.id.views5);
                inputTextView.setText(obtainedString);



                //no6
                obtainedString = textElements.get(25).text();
                inputTextView = findViewById(R.id.no6);
                inputTextView.setText(obtainedString);

                //title6
                obtainedString = textElements.get(27).text();
                inputTextView = findViewById(R.id.title6);
                inputTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connectURLString = textElements.get(27).select("a").attr("href");
                        if(connectURLString != null) {
                            Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                            intent.putExtra("detailsInformationURL", "http://botanicpark.seoul.go.kr" + connectURLString);
                            startActivity(intent);
                        }
                    }
                });
                inputTextView.setText(obtainedString);

                //registration_date6
                obtainedString = textElements.get(28).text();
                inputTextView = findViewById(R.id.registration_date6);
                inputTextView.setText(obtainedString);

                //views6
                obtainedString = textElements.get(29).text();
                inputTextView = findViewById(R.id.views6);
                inputTextView.setText(obtainedString);



                //no7
                obtainedString = textElements.get(30).text();
                inputTextView = findViewById(R.id.no7);
                inputTextView.setText(obtainedString);

                //title7
                obtainedString = textElements.get(32).text();
                inputTextView = findViewById(R.id.title7);
                inputTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connectURLString = textElements.get(32).select("a").attr("href");
                        if(connectURLString != null) {
                            Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                            intent.putExtra("detailsInformationURL", "http://botanicpark.seoul.go.kr" + connectURLString);
                            startActivity(intent);
                        }
                    }
                });
                inputTextView.setText(obtainedString);

                //registration_date7
                obtainedString = textElements.get(33).text();
                inputTextView = findViewById(R.id.registration_date7);
                inputTextView.setText(obtainedString);

                //views7
                obtainedString = textElements.get(34).text();
                inputTextView = findViewById(R.id.views7);
                inputTextView.setText(obtainedString);



                //no8
                obtainedString = textElements.get(35).text();
                inputTextView = findViewById(R.id.no8);
                inputTextView.setText(obtainedString);

                //title8
                obtainedString = textElements.get(37).text();
                inputTextView = findViewById(R.id.title8);
                inputTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connectURLString = textElements.get(37).select("a").attr("href");
                        if(connectURLString != null) {
                            Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                            intent.putExtra("detailsInformationURL", "http://botanicpark.seoul.go.kr" + connectURLString);
                            startActivity(intent);
                        }
                    }
                });
                inputTextView.setText(obtainedString);

                //registration_date8
                obtainedString = textElements.get(38).text();
                inputTextView = findViewById(R.id.registration_date8);
                inputTextView.setText(obtainedString);

                //views8
                obtainedString = textElements.get(39).text();
                inputTextView = findViewById(R.id.views8);
                inputTextView.setText(obtainedString);

                //no9
                obtainedString = textElements.get(40).text();
                inputTextView = findViewById(R.id.no9);
                inputTextView.setText(obtainedString);

                //title9
                obtainedString = textElements.get(42).text();
                inputTextView = findViewById(R.id.title9);
                inputTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connectURLString = textElements.get(42).select("a").attr("href");
                        if(connectURLString != null) {
                            Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                            intent.putExtra("detailsInformationURL", "http://botanicpark.seoul.go.kr" + connectURLString);
                            startActivity(intent);
                        }
                    }
                });
                inputTextView.setText(obtainedString);

                //registration_date9
                obtainedString = textElements.get(43).text();
                inputTextView = findViewById(R.id.registration_date9);
                inputTextView.setText(obtainedString);

                //views9
                obtainedString = textElements.get(44).text();
                inputTextView = findViewById(R.id.views9);
                inputTextView.setText(obtainedString);



                //no10
                obtainedString = textElements.get(45).text();
                inputTextView = findViewById(R.id.no10);
                inputTextView.setText(obtainedString);

                //title10
                obtainedString = textElements.get(47).text();
                inputTextView = findViewById(R.id.title10);
                inputTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connectURLString = textElements.get(47).select("a").attr("href");
                        if(connectURLString != null) {
                            Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                            intent.putExtra("detailsInformationURL", "http://botanicpark.seoul.go.kr" + connectURLString);
                            startActivity(intent);
                        }
                    }
                });
                inputTextView.setText(obtainedString);

                //registration_date10
                obtainedString = textElements.get(48).text();
                inputTextView = findViewById(R.id.registration_date10);
                inputTextView.setText(obtainedString);

                //views10
                obtainedString = textElements.get(49).text();
                inputTextView = findViewById(R.id.views10);
                inputTextView.setText(obtainedString);



                //no11
                obtainedString = textElements.get(50).text();
                inputTextView = findViewById(R.id.no11);
                inputTextView.setText(obtainedString);

                //title11
                obtainedString = textElements.get(52).text();
                inputTextView = findViewById(R.id.title11);
                inputTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connectURLString = textElements.get(52).select("a").attr("href");
                        if(connectURLString != null) {
                            Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                            intent.putExtra("detailsInformationURL", "http://botanicpark.seoul.go.kr" + connectURLString);
                            startActivity(intent);
                        }
                    }
                });
                inputTextView.setText(obtainedString);

                //registration_date11
                obtainedString = textElements.get(53).text();
                inputTextView = findViewById(R.id.registration_date11);
                inputTextView.setText(obtainedString);

                //views11
                obtainedString = textElements.get(54).text();
                inputTextView = findViewById(R.id.views11);
                inputTextView.setText(obtainedString);




                //이미지 갖고오는 것 아이콘
                imageURL1 = document.select("div[class=table_list]").select("img").get(0).attr("src");
                imageURL2 = document.select("div[class=table_list]").select("img").get(1).attr("src");
                imageURL3 = document.select("div[class=table_list]").select("img").get(2).attr("src");
                imageURL4 = document.select("div[class=table_list]").select("img").get(3).attr("src");
                imageURL5 = document.select("div[class=table_list]").select("img").get(4).attr("src");
                imageURL6 = document.select("div[class=table_list]").select("img").get(5).attr("src");
                imageURL7 = document.select("div[class=table_list]").select("img").get(6).attr("src");
                imageURL8 = document.select("div[class=table_list]").select("img").get(7).attr("src");
                imageURL9 = document.select("div[class=table_list]").select("img").get(8).attr("src");
                imageURL10 = document.select("div[class=table_list]").select("img").get(9).attr("src");
                imageURL11 = document.select("div[class=table_list]").select("img").get(10).attr("src");

            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            //가져온 이미지 화면에 띄움
            imageView = findViewById(R.id.icon1);
            Glide.with(getApplication()).load("https://botanicpark.seoul.go.kr"+imageURL1).into(imageView);

            imageView = findViewById(R.id.icon2);
            Glide.with(getApplication()).load("https://botanicpark.seoul.go.kr"+imageURL2).into(imageView);

            imageView = findViewById(R.id.icon3);
            Glide.with(getApplication()).load("https://botanicpark.seoul.go.kr"+imageURL3).into(imageView);

            imageView = findViewById(R.id.icon4);
            Glide.with(getApplication()).load("https://botanicpark.seoul.go.kr"+imageURL4).into(imageView);

            imageView = findViewById(R.id.icon5);
            Glide.with(getApplication()).load("https://botanicpark.seoul.go.kr"+imageURL5).into(imageView);

            imageView = findViewById(R.id.icon6);
            Glide.with(getApplication()).load("https://botanicpark.seoul.go.kr"+imageURL6).into(imageView);

            imageView = findViewById(R.id.icon7);
            Glide.with(getApplication()).load("https://botanicpark.seoul.go.kr"+imageURL7).into(imageView);

            imageView = findViewById(R.id.icon8);
            Glide.with(getApplication()).load("https://botanicpark.seoul.go.kr"+imageURL8).into(imageView);

            imageView = findViewById(R.id.icon9);
            Glide.with(getApplication()).load("https://botanicpark.seoul.go.kr"+imageURL9).into(imageView);

            imageView = findViewById(R.id.icon10);
            Glide.with(getApplication()).load("https://botanicpark.seoul.go.kr"+imageURL10).into(imageView);

            imageView = findViewById(R.id.icon11);
            Glide.with(getApplication()).load("https://botanicpark.seoul.go.kr"+imageURL11).into(imageView);
        }
    }

}
