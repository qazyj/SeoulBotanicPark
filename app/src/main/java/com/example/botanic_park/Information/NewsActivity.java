package com.example.botanic_park.Information;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private String[] obtainedString = new String[44];
    private TextView inputTextView;
    private String[] connectURLString = new String[11];
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);



        intent = new Intent(getApplicationContext(), WebViewActivity.class);

        // AsyncTask 작동시킴(파싱)
        new ParseInformationTask().execute();

        findViewById(R.id.viewMore).setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        intent.putExtra("URLString", "http://botanicpark.seoul.go.kr/front/board/newsList.do");
                        startActivity(intent);
                    }
                }
        );

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
                obtainedString[0] = textElements.get(0).text();


                //title1
                obtainedString[1] = textElements.get(2).text();
                connectURLString[0] = textElements.get(2).select("a").attr("href");


                //registration_date1
                obtainedString[2] = textElements.get(3).text();

                //views1
                obtainedString[3] = textElements.get(4).text();



                //no2
                obtainedString[4] = textElements.get(5).text();

                //title2
                obtainedString[5] = textElements.get(7).text();
                connectURLString[1] = textElements.get(7).select("a").attr("href");

                //registration_date2
                obtainedString[6] = textElements.get(8).text();

                //views2
                obtainedString[7] = textElements.get(9).text();



                //no3
                obtainedString[8] = textElements.get(10).text();

                //title3
                obtainedString[9] = textElements.get(12).text();
                connectURLString[2] = textElements.get(12).select("a").attr("href");

                //registration_date3
                obtainedString[10] = textElements.get(13).text();

                //views3
                obtainedString[11] = textElements.get(14).text();


                //no4
                obtainedString[12] = textElements.get(15).text();

                //title4
                obtainedString[13] = textElements.get(17).text();
                connectURLString[3] = textElements.get(17).select("a").attr("href");

                //registration_date4
                obtainedString[14] = textElements.get(18).text();

                //views4
                obtainedString[15] = textElements.get(19).text();



                //no5
                obtainedString[16] = textElements.get(20).text();

                //title5
                obtainedString[17] = textElements.get(22).text();
                connectURLString[4] = textElements.get(22).select("a").attr("href");

                //registration_date5
                obtainedString[18] = textElements.get(23).text();

                //views5
                obtainedString[19] = textElements.get(24).text();



                //no6
                obtainedString[20] = textElements.get(25).text();

                //title6
                obtainedString[21] = textElements.get(27).text();
                connectURLString[5] = textElements.get(27).select("a").attr("href");

                //registration_date6
                obtainedString[22] = textElements.get(28).text();

                //views6
                obtainedString[23] = textElements.get(29).text();



                //no7
                obtainedString[24] = textElements.get(30).text();

                //title7
                obtainedString[25] = textElements.get(32).text();
                connectURLString[6] = textElements.get(32).select("a").attr("href");

                //registration_date7
                obtainedString[26] = textElements.get(33).text();

                //views7
                obtainedString[27] = textElements.get(34).text();



                //no8
                obtainedString[28] = textElements.get(35).text();

                //title8
                obtainedString[29] = textElements.get(37).text();
                connectURLString[7] = textElements.get(37).select("a").attr("href");

                //registration_date8
                obtainedString[30] = textElements.get(38).text();

                //views8
                obtainedString[31] = textElements.get(39).text();


                //no9
                obtainedString[32] = textElements.get(40).text();

                //title9
                obtainedString[33] = textElements.get(42).text();
                connectURLString[8] = textElements.get(42).select("a").attr("href");

                //registration_date9
                obtainedString[34] = textElements.get(43).text();

                //views9
                obtainedString[35] = textElements.get(44).text();


                //no10
                obtainedString[36] = textElements.get(45).text();

                //title10
                obtainedString[37] = textElements.get(47).text();
                connectURLString[9] = textElements.get(47).select("a").attr("href");

                //registration_date10
                obtainedString[38] = textElements.get(48).text();

                //views10
                obtainedString[39] = textElements.get(49).text();


                //no11
                obtainedString[40] = textElements.get(50).text();

                //title11
                obtainedString[41] = textElements.get(52).text();
                connectURLString[10] = textElements.get(52).select("a").attr("href");

                //registration_date11
                obtainedString[42] = textElements.get(53).text();

                //views11
                obtainedString[43] = textElements.get(54).text();



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

            //no1
            inputTextView = findViewById(R.id.no1);
            inputTextView.setText(obtainedString[0]);

            //title1
            inputTextView = (TextView) findViewById(R.id.title1);
            inputTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(connectURLString[0] != null) {
                        intent.putExtra("URLString", "http://botanicpark.seoul.go.kr" + connectURLString[0]);
                        startActivity(intent);
                    }
                }
            });
            inputTextView.setText(obtainedString[1]);

            //registration_date1
            inputTextView = findViewById(R.id.registration_date1);
            inputTextView.setText(obtainedString[2]);

            //views1
            inputTextView = findViewById(R.id.views1);
            inputTextView.setText(obtainedString[3]);

            //no2
            inputTextView = findViewById(R.id.no2);
            inputTextView.setText(obtainedString[4]);

            //title2
            inputTextView = findViewById(R.id.title2);
            inputTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(connectURLString[1] != null) {
                        intent.putExtra("URLString", "http://botanicpark.seoul.go.kr" + connectURLString[1]);
                        startActivity(intent);
                    }
                }
            });
            inputTextView.setText(obtainedString[5]);

            //registration_date2
            inputTextView = findViewById(R.id.registration_date2);
            inputTextView.setText(obtainedString[6]);

            //views2
            inputTextView = findViewById(R.id.views2);
            inputTextView.setText(obtainedString[7]);


            //no3
            inputTextView = findViewById(R.id.no3);
            inputTextView.setText(obtainedString[8]);

            //title3
            inputTextView = findViewById(R.id.title3);
            inputTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(connectURLString[2] != null) {
                        intent.putExtra("URLString", "http://botanicpark.seoul.go.kr" + connectURLString[2]);
                        startActivity(intent);
                    }
                }
            });
            inputTextView.setText(obtainedString[9]);

            //registration_date3
            inputTextView = findViewById(R.id.registration_date3);
            inputTextView.setText(obtainedString[10]);

            //views3
            inputTextView = findViewById(R.id.views3);
            inputTextView.setText(obtainedString[11]);



            //no4
            inputTextView = findViewById(R.id.no4);
            inputTextView.setText(obtainedString[12]);

            //title4
            inputTextView = findViewById(R.id.title4);
            inputTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(connectURLString[3] != null) {
                        intent.putExtra("URLString", "http://botanicpark.seoul.go.kr" + connectURLString[3]);
                        startActivity(intent);
                    }
                }
            });
            inputTextView.setText(obtainedString[13]);

            //registration_date4
            inputTextView = findViewById(R.id.registration_date4);
            inputTextView.setText(obtainedString[14]);

            //views4
            inputTextView = findViewById(R.id.views4);
            inputTextView.setText(obtainedString[15]);



            //no5
            inputTextView = findViewById(R.id.no5);
            inputTextView.setText(obtainedString[16]);

            //title5
            inputTextView = findViewById(R.id.title5);
            inputTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(connectURLString[4] != null) {
                        intent.putExtra("URLString", "http://botanicpark.seoul.go.kr" + connectURLString[4]);
                        startActivity(intent);
                    }
                }
            });
            inputTextView.setText(obtainedString[17]);

            //registration_date5
            inputTextView = findViewById(R.id.registration_date5);
            inputTextView.setText(obtainedString[18]);

            //views5
            inputTextView = findViewById(R.id.views5);
            inputTextView.setText(obtainedString[19]);


            //no6
            inputTextView = findViewById(R.id.no6);
            inputTextView.setText(obtainedString[20]);

            //title6
            inputTextView = findViewById(R.id.title6);
            inputTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(connectURLString[5] != null) {
                        intent.putExtra("URLString", "http://botanicpark.seoul.go.kr" + connectURLString[5]);
                        startActivity(intent);
                    }
                }
            });
            inputTextView.setText(obtainedString[21]);

            //registration_date6
            inputTextView = findViewById(R.id.registration_date6);
            inputTextView.setText(obtainedString[22]);

            //views6
            inputTextView = findViewById(R.id.views6);
            inputTextView.setText(obtainedString[23]);


            //no7
            inputTextView = findViewById(R.id.no7);
            inputTextView.setText(obtainedString[24]);

            //title7
            inputTextView = findViewById(R.id.title7);
            inputTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(connectURLString[6] != null) {
                        intent.putExtra("URLString", "http://botanicpark.seoul.go.kr" + connectURLString[6]);
                        startActivity(intent);
                    }
                }
            });
            inputTextView.setText(obtainedString[25]);

            //registration_date7
            inputTextView = findViewById(R.id.registration_date7);
            inputTextView.setText(obtainedString[26]);

            //views7
            inputTextView = findViewById(R.id.views7);
            inputTextView.setText(obtainedString[27]);



            //no8
            inputTextView = findViewById(R.id.no8);
            inputTextView.setText(obtainedString[28]);

            //title8
            inputTextView = findViewById(R.id.title8);
            inputTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(connectURLString[7] != null) {
                        intent.putExtra("URLString", "http://botanicpark.seoul.go.kr" + connectURLString[7]);
                        startActivity(intent);
                    }
                }
            });
            inputTextView.setText(obtainedString[29]);

            //registration_date8
            inputTextView = findViewById(R.id.registration_date8);
            inputTextView.setText(obtainedString[30]);

            //views8
            inputTextView = findViewById(R.id.views8);
            inputTextView.setText(obtainedString[31]);


            //no9
            inputTextView = findViewById(R.id.no9);
            inputTextView.setText(obtainedString[32]);

            //title9
            inputTextView = findViewById(R.id.title9);
            inputTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(connectURLString[8] != null) {
                        intent.putExtra("URLString", "http://botanicpark.seoul.go.kr" + connectURLString[8]);
                        startActivity(intent);
                    }
                }
            });
            inputTextView.setText(obtainedString[33]);

            //registration_date9
            inputTextView = findViewById(R.id.registration_date9);
            inputTextView.setText(obtainedString[34]);

            //views9
            inputTextView = findViewById(R.id.views9);
            inputTextView.setText(obtainedString[35]);


            //no10
            inputTextView = findViewById(R.id.no10);
            inputTextView.setText(obtainedString[36]);

            //title10
            inputTextView = findViewById(R.id.title10);
            inputTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(connectURLString[9] != null) {
                        intent.putExtra("URLString", "http://botanicpark.seoul.go.kr" + connectURLString[9]);
                        startActivity(intent);
                    }
                }
            });
            inputTextView.setText(obtainedString[37]);

            //registration_date10
            inputTextView = findViewById(R.id.registration_date10);
            inputTextView.setText(obtainedString[38]);

            //views10
            inputTextView = findViewById(R.id.views10);
            inputTextView.setText(obtainedString[39]);



            //no11
            inputTextView = findViewById(R.id.no11);
            inputTextView.setText(obtainedString[40]);

            //title11
            inputTextView = findViewById(R.id.title11);
            inputTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(connectURLString[10] != null) {
                        intent.putExtra("URLString", "http://botanicpark.seoul.go.kr" + connectURLString[10]);
                        startActivity(intent);
                    }
                }
            });
            inputTextView.setText(obtainedString[41]);

            //registration_date11
            inputTextView = findViewById(R.id.registration_date11);
            inputTextView.setText(obtainedString[42]);

            //views11
            inputTextView = findViewById(R.id.views11);
            inputTextView.setText(obtainedString[43]);


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
