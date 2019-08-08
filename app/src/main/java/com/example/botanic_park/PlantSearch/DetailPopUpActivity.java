package com.example.botanic_park.PlantSearch;

import android.app.Activity;
import android.content.Intent;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.botanic_park.R;


import java.lang.reflect.Field;


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

        setData();  // 화면에 데이터 세팅

        ImageButton closeBtn = findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();   // 액티비티 닫음
            }
        });
    }

    private void setData() {

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

        ImageView imageView = findViewById(R.id.image_detail);
        Log.d("테스트", "setData");
        try {
            Field field = R.drawable.class.getField("species_" + selectedItem.getId());
            int drawableID = field.getInt(null);
            Glide.with(imageView).load(drawableID).centerCrop().thumbnail(0.1f).into(imageView);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //Glide.with(getApplication()).load(selectedItem.getImg_url()).into(imageView);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // 바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
    */

}
