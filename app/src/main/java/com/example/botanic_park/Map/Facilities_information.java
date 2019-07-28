package com.example.botanic_park.Map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.botanic_park.R;

public class Facilities_information extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilities_information);

        ImageView backButton = findViewById(R.id.back_btn);
        backButton.setOnClickListener(this);

        Intent intent = getIntent();
        int tag = intent.getIntExtra("tag", 1);
        setName(tag);

    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.back_btn) finish();

    }

    public void setName(int tag)
    {
        TextView spaceName = findViewById(R.id.space_name);

        switch (tag)
        {
            case 1:
                spaceName.setText("식물문화센터");
                break;

            case 2:
                spaceName.setText("치유의 정원");
                break;

            case 3:
                spaceName.setText("숲정원");
                break;

            case 4:
                spaceName.setText("바람의 정원");
                break;

            case 5:
                spaceName.setText("오늘의 정원");
                break;

            case 6:
                spaceName.setText("추억의 정원");
                break;

            case 7:
                spaceName.setText("정원사 정원");
                break;

            case 8:
                spaceName.setText("사색 정원");
                break;

            case 9:
                spaceName.setText("초대의 정원");
                break;

        }
    }
}
