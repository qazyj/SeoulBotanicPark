package com.example.botanic_park.PlantSearch;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.botanic_park.OnSingleClickListener;
import com.example.botanic_park.R;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


import java.lang.reflect.Field;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


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
        closeBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) { finish();   // 액티비티 닫음
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
            MultiTransformation multi = new MultiTransformation(
                    new CenterCrop(),
                    new RoundedCornersTransformation(45, 0,
                            RoundedCornersTransformation.CornerType.TOP)
            );
            Glide.with(imageView)
                    .load(drawableID)
                    .apply(bitmapTransform(multi))
                    .thumbnail(0.1f)
                    .into(imageView);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

}
