package com.example.botanic_park.PlantSearch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.botanic_park.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* 식물 검색 결과 액티비티 */
public class SearchResultActivity extends AppCompatActivity {
    ArrayList<PlantBookItem> list;          // 전체 식물 리스트
    ArrayList<PlantBookItem> searchList;    // 검색결과 저장 리스트
    String searchword;

    PlantExpandableListView listView;   // 결과 리스트뷰
    LinearLayout linearLayout;  // 결과 없음 메세지
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        Intent intent = getIntent();
        list = (ArrayList<PlantBookItem>) intent.getSerializableExtra(Fragment_Plant_Book.PLANT_LIST_KEY);
        searchword = intent.getStringExtra(Fragment_Plant_Book.SEARCH_WORD_KEY);

        listView = findViewById(R.id.listview);
        linearLayout = findViewById(R.id.no_result_message);

        getSearchResult();
        showSearchResult();
    }

    private void getSearchResult() {
        // 텍스트 검색 결과 리스트
        searchList = new ArrayList<PlantBookItem>();

        if(searchword.isEmpty())
            return;

        for (PlantBookItem item : list) {
            if (item.getName_ko().contains(searchword)
                    || item.getName_en().contains(searchword)
                    || item.getName_sc().contains(searchword)) {
                searchList.add(item);
            }
        }
    }

    private void showSearchResult(){
        if(searchList.size() > 0){
            linearLayout.setVisibility(View.GONE);
            PlantBookAdapter adapter = new PlantBookAdapter(getApplicationContext(),
                    R.layout.item_plant, searchList, PlantBookAdapter.SHOW_ALL_NAME);
            listView.setAdapter(adapter);   // 어댑터 연결

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // 아이템 클릭시 상세 페이지로 넘어감
                    Intent intent = new Intent(getApplicationContext(), DetailPopUpActivity.class);
                    intent.putExtra(Fragment_Plant_Book.SELECTED_ITEM_KEY, list.get(i));
                    startActivity(intent);
                }
            });
        } else{
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

}


