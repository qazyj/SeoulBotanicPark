package com.example.botanic_park.Map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.botanic_park.R;

public class Facilities_information extends AppCompatActivity implements View.OnClickListener{

    View informationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilities_information);

        ImageView backButton = findViewById(R.id.back_btn);
        backButton.setOnClickListener(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Intent intent = getIntent();
        String[] information = intent.getStringArrayExtra("information");

        try {
            setName(information[0]);

            RelativeLayout contain = (RelativeLayout) findViewById(R.id.contain);

            if(information[0].equals(Fragment_BotanicCenter.LIBRARY))
            {
                informationView = getLayoutInflater().inflate(R.layout.library, null);
                setTextViewToClick(informationView.findViewById(R.id.url),information[1]);

            }

            else  if(information[0].equals(Fragment_BotanicCenter.SEED_LIBRARY))
            {
                informationView = getLayoutInflater().inflate(R.layout.seed_library, null);
               setTextViewToClick(informationView.findViewById(R.id.url),information[1]);

            }
            informationView.setLayoutParams(layoutParams);
            contain.addView(informationView);

        } catch (Exception e) {
            finish();
        }

    }

    public void setTextViewToClick(TextView textView, String url)
    {
        textView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                newUriIntent(url);
            }
        });
    }

    public void newUriIntent(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        startActivity(intent);

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
