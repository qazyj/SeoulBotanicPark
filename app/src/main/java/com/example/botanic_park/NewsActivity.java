package com.example.botanic_park;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NewsActivity extends AppCompatActivity {

    private ImageView imageView;
    private String imageURL;
    private String BaseURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        BaseURL="http://botanicpark.seoul.go.kr";

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

        @Override
        protected Void doInBackground(Void... params) {
            try{
                // 인증서 있는 홈페이지를 인증서 없이도 연결 가능하게 설정
                SSLConnect sslConnect = new SSLConnect();
                sslConnect.postHttps(NEWS_URL,1000,1000);

                // 웹에서 정보 읽어옴
                Document document = Jsoup.connect(NEWS_URL).get();

                //해당 이미지 url 저장
                Elements textElements = document.select("div[class=table_list]").select("tbody");

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
