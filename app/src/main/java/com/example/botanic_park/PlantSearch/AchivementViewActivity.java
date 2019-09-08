package com.example.botanic_park.PlantSearch;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
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
        TextView leveltext = (TextView) findViewById(R.id.leveltext);

        if(AppManager.getInstance().collectionCount>=0 && AppManager.getInstance().collectionCount<=121/5)
        {
            achiveImage.setImageResource(R.drawable.tree1);
            leveltext.setText("Lv1 (0~24개)");
        }
        else if(AppManager.getInstance().collectionCount<=121/5*2)
        {
            achiveImage.setImageResource(R.drawable.tree2);
            leveltext.setText("Lv2 (25~48개)");
        }
        else if(AppManager.getInstance().collectionCount<=121/5*3)
        {
            achiveImage.setImageResource(R.drawable.tree3);
            leveltext.setText("Lv3 (49~72개)");
        }
        else if(AppManager.getInstance().collectionCount<=121/5*4)
        {
            achiveImage.setImageResource(R.drawable.tree4);
            leveltext.setText("Lv4 (73~96개)");
        }
        else if(AppManager.getInstance().collectionCount<121)
        {
            achiveImage.setImageResource(R.drawable.tree5);
            leveltext.setText("Lv5 (97~120개)");
        }
        else if(AppManager.getInstance().collectionCount==121)
        {
            achiveImage.setImageResource(R.drawable.tree6);
            leveltext.setText("Master");
        }
    }
}
