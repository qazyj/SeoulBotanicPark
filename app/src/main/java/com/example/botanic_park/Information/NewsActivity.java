package com.example.botanic_park.Information;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.botanic_park.AppManager;
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

        //상태 바 색 바꿔줌
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#FAFAFA"));
        setContentView(R.layout.activity_news);
        AppManager.getInstance().setNewsActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

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
/*
        //서울시 로고 핸드폰에 맞춰서 맨위에 뜨게함
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels;// 세로
        LinearLayout frameLayout = (LinearLayout) findViewById(R.id.logoframe);
        LinearLayout.LayoutParams frameLayout2 = (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
        frameLayout2.topMargin= -height;
        frameLayout.setLayoutParams(frameLayout2);*/
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

                GetTextNumber(textElements);        //게시판 숫자를 가져옴
                GetTextTitle(textElements);         //게시판 제목을 가져옴
                GetTextViews(textElements);         //게시판 게시글을 본 횟수를 가져옴
                GetTextRegistrationDate(textElements);      //게시판 올린 날짜를 알려줌
                GetIcon(document);          //공지인지 채용인지 새소식인지 알려주는 아이콘을 가져옴

            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        protected void GetTextNumber(Elements elements)
        {
            obtainedString[0] = elements.get(0).text();     //no1
            obtainedString[4] = elements.get(5).text();     //no2
            obtainedString[8] = elements.get(10).text();    //no3
            obtainedString[12] = elements.get(15).text();   //no4
            obtainedString[16] = elements.get(20).text();   //no5
            obtainedString[20] = elements.get(25).text();   //no6
            obtainedString[24] = elements.get(30).text();   //no7
            obtainedString[28] = elements.get(35).text();   //no8
            obtainedString[32] = elements.get(40).text();   //no9
            obtainedString[36] = elements.get(45).text();   //no10
            obtainedString[40] = elements.get(50).text();   //no11
        }

        protected void GetTextTitle(Elements elements)
        {
            obtainedString[1] = elements.get(2).text();                 //title1
            obtainedString[5] = elements.get(7).text();                 //title2
            obtainedString[9] = elements.get(12).text();                //title3
            obtainedString[13] = elements.get(17).text();                //title4
            obtainedString[17] = elements.get(22).text();               //title5
            obtainedString[21] = elements.get(27).text();               //title6
            obtainedString[25] = elements.get(32).text();               //title7
            obtainedString[29] = elements.get(37).text();               //title8
            obtainedString[33] = elements.get(42).text();               //title9
            obtainedString[37] = elements.get(47).text();               //title10
            obtainedString[41] = elements.get(52).text();               //title11

            //onclick url가져올 위치
            connectURLString[0] = elements.get(2).select("a").attr("href");
            connectURLString[1] = elements.get(7).select("a").attr("href");
            connectURLString[2] = elements.get(12).select("a").attr("href");
            connectURLString[3] = elements.get(17).select("a").attr("href");
            connectURLString[4] = elements.get(22).select("a").attr("href");
            connectURLString[5] = elements.get(27).select("a").attr("href");
            connectURLString[6] = elements.get(32).select("a").attr("href");
            connectURLString[7] = elements.get(37).select("a").attr("href");
            connectURLString[8] = elements.get(42).select("a").attr("href");
            connectURLString[9] = elements.get(47).select("a").attr("href");
            connectURLString[10] = elements.get(52).select("a").attr("href");
        }

        protected void GetTextViews(Elements elements)
        {
            obtainedString[3] = elements.get(4).text();              //views1
            obtainedString[7] = elements.get(9).text();              //views2
            obtainedString[11] = elements.get(14).text();            //views3
            obtainedString[15] = elements.get(19).text();            //views4
            obtainedString[19] = elements.get(24).text();            //views5
            obtainedString[23] = elements.get(29).text();            //views6
            obtainedString[27] = elements.get(34).text();            //views7
            obtainedString[31] = elements.get(39).text();            //views8
            obtainedString[35] = elements.get(44).text();            //views9
            obtainedString[39] = elements.get(49).text();            //views10
            obtainedString[43] = elements.get(54).text();            //views11
        }

        protected void GetTextRegistrationDate(Elements elements)
        {
            obtainedString[2] = elements.get(3).text();            //registration_date1
            obtainedString[6] = elements.get(8).text();            //registration_date2
            obtainedString[10] = elements.get(13).text();            //registration_date3
            obtainedString[14] = elements.get(18).text();            //registration_date4
            obtainedString[18] = elements.get(23).text();            //registration_date5
            obtainedString[22] = elements.get(28).text();            //registration_date6
            obtainedString[26] = elements.get(33).text();            //registration_date7
            obtainedString[30] = elements.get(38).text();            //registration_date8
            obtainedString[34] = elements.get(43).text();            //registration_date9
            obtainedString[38] = elements.get(48).text();            //registration_date10
            obtainedString[42] = elements.get(53).text();            //registration_date11
        }

        protected void GetIcon(Document document)
        {
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
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            SetTextNumber();            //게시판 숫자를 넣음
            SetTextTitle();             //게시판 제목을 넣음
            SetTextRegistrationDate();  //게시판 게시글을 본 횟수를 넣음
            SetTextViews();             //게시판 올린 날짜를 넣음
            SetIcon();                  //공지인지 채용인지 새소식인지 알려주는 아이콘을 넣음\
        }


        protected void SetTextNumber()
        {
            inputTextView = findViewById(R.id.no1);         //no1
            inputTextView.setText(obtainedString[0]);

            inputTextView = findViewById(R.id.no2);         //no2
            inputTextView.setText(obtainedString[4]);

            inputTextView = findViewById(R.id.no3);         //no3
            inputTextView.setText(obtainedString[8]);

            inputTextView = findViewById(R.id.no4);         //no4
            inputTextView.setText(obtainedString[12]);

            inputTextView = findViewById(R.id.no5);         //no5
            inputTextView.setText(obtainedString[16]);

            inputTextView = findViewById(R.id.no6);         //no6
            inputTextView.setText(obtainedString[20]);

            inputTextView = findViewById(R.id.no7);         //no7
            inputTextView.setText(obtainedString[24]);

            inputTextView = findViewById(R.id.no8);         //no8
            inputTextView.setText(obtainedString[28]);

            inputTextView = findViewById(R.id.no9);         //no9
            inputTextView.setText(obtainedString[32]);

            inputTextView = findViewById(R.id.no10);        //no10
            inputTextView.setText(obtainedString[36]);

            inputTextView = findViewById(R.id.no11);        //no11
            inputTextView.setText(obtainedString[40]);
        }

        protected void SetTextTitle()
        {
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
        }

        protected void SetTextViews()
        {
            //views1
            inputTextView = findViewById(R.id.views1);
            inputTextView.setText(obtainedString[3]);
            //views2
            inputTextView = findViewById(R.id.views2);
            inputTextView.setText(obtainedString[7]);
            //views3
            inputTextView = findViewById(R.id.views3);
            inputTextView.setText(obtainedString[11]);
            //views4
            inputTextView = findViewById(R.id.views4);
            inputTextView.setText(obtainedString[15]);
            //views5
            inputTextView = findViewById(R.id.views5);
            inputTextView.setText(obtainedString[19]);
            //views6
            inputTextView = findViewById(R.id.views6);
            inputTextView.setText(obtainedString[23]);
            //views7
            inputTextView = findViewById(R.id.views7);
            inputTextView.setText(obtainedString[27]);
            //views8
            inputTextView = findViewById(R.id.views8);
            inputTextView.setText(obtainedString[31]);
            //views9
            inputTextView = findViewById(R.id.views9);
            inputTextView.setText(obtainedString[35]);
            //views10
            inputTextView = findViewById(R.id.views10);
            inputTextView.setText(obtainedString[39]);
            //views11
            inputTextView = findViewById(R.id.views11);
            inputTextView.setText(obtainedString[43]);
        }

        protected void SetTextRegistrationDate()
        {
            //registration_date1
            inputTextView = findViewById(R.id.registration_date1);
            inputTextView.setText(obtainedString[2]);
            //registration_date2
            inputTextView = findViewById(R.id.registration_date2);
            inputTextView.setText(obtainedString[6]);
            //registration_date3
            inputTextView = findViewById(R.id.registration_date3);
            inputTextView.setText(obtainedString[10]);
            //registration_date4
            inputTextView = findViewById(R.id.registration_date4);
            inputTextView.setText(obtainedString[14]);
            //registration_date5
            inputTextView = findViewById(R.id.registration_date5);
            inputTextView.setText(obtainedString[18]);
            //registration_date6
            inputTextView = findViewById(R.id.registration_date6);
            inputTextView.setText(obtainedString[22]);
            //registration_date7
            inputTextView = findViewById(R.id.registration_date7);
            inputTextView.setText(obtainedString[26]);
            //registration_date8
            inputTextView = findViewById(R.id.registration_date8);
            inputTextView.setText(obtainedString[30]);
            //registration_date9
            inputTextView = findViewById(R.id.registration_date9);
            inputTextView.setText(obtainedString[34]);
            //registration_date10
            inputTextView = findViewById(R.id.registration_date10);
            inputTextView.setText(obtainedString[38]);
            //registration_date11
            inputTextView = findViewById(R.id.registration_date11);
            inputTextView.setText(obtainedString[42]);
        }

        protected void SetIcon()
        {
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
