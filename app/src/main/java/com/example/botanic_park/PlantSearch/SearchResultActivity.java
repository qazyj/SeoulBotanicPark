package com.example.botanic_park.PlantSearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.botanic_park.R;

import java.io.InputStream;

/* 식물 검색 결과 액티비티 */
public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        Intent intent = getIntent();
        String searchword = intent.getStringExtra("search word");

        TextView textView = findViewById(R.id.test);
        textView.setText(searchword);
    }
}
