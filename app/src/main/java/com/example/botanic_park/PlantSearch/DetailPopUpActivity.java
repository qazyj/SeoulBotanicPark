package com.example.botanic_park.PlantSearch;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.botanic_park.R;

public class DetailPopUpActivity extends Activity {
    private PlantBookItem selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 타이틀바 없애기
        setContentView(R.layout.activity_detail_pop_up);

        // 데이터 가져오기
        Intent intent = getIntent();
        selectedItem = (PlantBookItem) intent.getSerializableExtra(Fragment_Plant_Book.SELECTED_ITEM_KEY);

        // 화면에 데이터 세팅
        TextView name_ko = findViewById(R.id.content_name);
        name_ko.setText(selectedItem.getName_ko());

        TextView name_sc = findViewById(R.id.content_scientific_name);
        name_sc.setText(selectedItem.getName_sc());

        TextView name_en = findViewById(R.id.content_name_en);
        name_en.setText(selectedItem.getName_en());

        TextView type = findViewById(R.id.content_type);
        type.setText(selectedItem.getType());

        TextView blossom = findViewById(R.id.content_blossom);
        blossom.setText(selectedItem.getBlossom());

        TextView details = findViewById(R.id.details);
        details.setText(selectedItem.getDetails());

        ImageButton closeBtn = findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();   // 액티비티 닫음
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

}
