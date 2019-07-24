package com.example.botanic_park.PlantSearch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.botanic_park.R;
import com.example.botanic_park.SSLConnect;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Fragment_Plant_Book extends Fragment {
    PlantBookExpandableGridView plantBookGridView;
    ArrayList<PlantBookItem> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_book, container, false);

        ImageButton cameraSearchBtn = view.findViewById(R.id.camera_search_btn);
        cameraSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CameraSearchActivity.class);
                startActivity(intent);
            }
        });

        EditText editText = view.findViewById(R.id.search_edit_text);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                switch (i) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 검색 동작
                        Intent intent = new Intent();
                        break;
                    default:
                        // 기본 엔터키 동작
                        return false;
                }
                return true;
            }
        });

        plantBookGridView = view.findViewById(R.id.gridview_plant_book);

        new ParsePlantTask().execute(); // AsyncTask 작동시킴(파싱)

        return view;
    }


    /* 웹에서 식물 정보 긁어오는 클래스 */
    public class ParsePlantTask extends AsyncTask<Void, Void, Void> {
        String PLANT_LIST_URL = "https://botanicpark.seoul.go.kr/front/plants/plantsIntro.do";
        String IMAGE_START_URL= "https://botanicpark.seoul.go.kr";
        String PLANT_DETAIL_URL = "https://botanicpark.seoul.go.kr/front/plants/plantsIntroView.do";
        String ID = "?plt_sn=";     // 식물 식별 id
        String PAGE = "&page=";     // 페이지

        @Override
        protected Void doInBackground(Void... params) {
            try{

                int id = 1;
                for(int page=1; page <= 5; page++){
                    for(int itemCount = 0; itemCount < 8; itemCount++){
                        String DETAIL_PAGE_URL = PLANT_DETAIL_URL + ID + id + PAGE + page;

                        // 인증서 있는 홈페이지를 인증서 없이도 연결 가능하게 설정
                        SSLConnect sslConnect = new SSLConnect();
                        sslConnect.postHttps(DETAIL_PAGE_URL,1000,1000);

                        Document document = Jsoup.connect(DETAIL_PAGE_URL).get();
                        String imgUrl = document.select("div[class=img_area]").select("img").attr("src");
                        Elements textElements = document.select("div[class=text_area]").select("span");
                        //Log.d("debug textElements", textElements + "");

                        String name_ko = textElements.get(0).text();
                        name_ko = name_ko.replace("이 름:", "");

                        String name_en = textElements.get(1).text();
                        name_en = name_en.replace("학 명:", "");

                        list.add(new PlantBookItem(IMAGE_START_URL + imgUrl, name_ko, name_en));
                        Log.d("debug input", imgUrl + "\n" + name_ko +"\n" + name_en);

                        id++;
                    }
                }

            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            PlantBookAdapter plantBookAdapter = new PlantBookAdapter(getContext(), R.layout.item_plant_preview, list);
            plantBookGridView.setAdapter(plantBookAdapter); // 어댑터를 그리드 뷰네 적용
            plantBookGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // 아이템 클릭시 상세 페이지로 넘어감 (구현 할지 말지 고민중)
                }
            });

        }
    }

}

class PlantBookAdapter extends BaseAdapter{
    Context context;
    int layout;
    ArrayList<PlantBookItem> list;  // item 목록
    LayoutInflater layoutInflater;

    View itemView;          // item이 뿌려질 뷰
    PlantBookItem item;     // item 정보 객체
    Bitmap bitmap;          // item 안에 들어가는 이미지
    Drawable drawable;

    public PlantBookAdapter(Context context, int layout, ArrayList<PlantBookItem> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;

        layoutInflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = layoutInflater.inflate(layout, null);

        itemView = view;
        item = list.get(i);
        Log.d("debug output", item.toString());

        ImageView imageView = view.findViewById(R.id.image_thumb);
        //Glide.with(view).load(item.getImg_url()).thumbnail(0.1f).into(imageView);

        TextView koNameView = view.findViewById(R.id.name_ko);
        koNameView.setText(item.getName_ko());


        return view;

    }

}

