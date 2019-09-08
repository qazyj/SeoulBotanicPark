package com.example.botanic_park.PlantSearch;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.botanic_park.AppManager;
import com.example.botanic_park.R;

public class AchivementViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_achivement);

        ImageView imageView = (ImageView) findViewById(R.id.close_button);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView achiveImage = (ImageView) findViewById(R.id.achive_tree_image);

        if(AppManager.getInstance().collectionCount==121)
        {

        }

    }
}
