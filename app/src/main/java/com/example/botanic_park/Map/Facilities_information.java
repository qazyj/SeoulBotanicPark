package com.example.botanic_park.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.botanic_park.R;

public class Facilities_information extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilities_information);

        ImageView backButton = findViewById(R.id.back_btn);
        backButton.setOnClickListener(this);

        Intent intent = getIntent();
        String[] information = intent.getStringArrayExtra("information");
        try {
            setName(information[0]);
        } catch (Exception e) {
            finish();
        }

    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.back_btn) finish();

    }

    public void setName(String name)
    {
        ((TextView) findViewById(R.id.space_name)).setText(name);
    }
}
