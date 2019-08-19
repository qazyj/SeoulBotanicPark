package com.example.botanic_park.Information;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.botanic_park.R;

public class InconvenienceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inconvenience);

        findViewById(R.id.registration_post_button2).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(InconvenienceActivity.this, RegistrationPostInInconvenienceActivity.class);
                startActivity(intent);
            }
        }
        );

    }
}
