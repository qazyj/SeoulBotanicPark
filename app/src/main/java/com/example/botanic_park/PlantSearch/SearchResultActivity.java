package com.example.botanic_park.PlantSearch;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.botanic_park.R;

import java.io.InputStream;
import java.util.ArrayList;

/* 식물 검색 결과 액티비티 */
public class SearchResultActivity extends AppCompatActivity {
    ArrayList<PlantBookItem> list;          // 전체 식물 리스트
    ArrayList<PlantBookItem> searchList;    // 검색결과 저장 리스트
    String searchword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        Intent intent = getIntent();
        list = (ArrayList<PlantBookItem>) intent.getSerializableExtra(Fragment_Plant_Book.PLANT_LIST_KEY);
        searchword = intent.getStringExtra(Fragment_Plant_Book.SEARCH_WORD_KEY);

        // 테스트용 글자 띄우기
        TextView textView = findViewById(R.id.test);
        textView.setText(searchword);
    }

    private void getSearchResult(String searchword) {
        // 텍스트 검색 결과 리스트
        for (PlantBookItem item : list) {
            if (item.getName_ko().contains(searchword)
                    || item.getName_en().contains(searchword)
                    || item.getName_sc().contains(searchword)) {
                searchList.add(item);
            }
        }
    }

}

