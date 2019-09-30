package com.example.botanic_park.PlantSearch;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.botanic_park.AppManager;
import com.example.botanic_park.OnSingleClickListener;
import com.example.botanic_park.R;

import java.util.ArrayList;

public class AchivementViewActivity extends AppCompatActivity {
    final String statusFront = "모은 식물: ";
    final String statusBack = "개  /  전체 식물: 121개";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_achivement);

        Button closeBtn = findViewById(R.id.close_button);
        ImageView achiveImage = (ImageView) findViewById(R.id.achive_tree_image);
        TextView leveltext = (TextView) findViewById(R.id.leveltext);
        TextView statusText = findViewById(R.id.status_text);

        if (AppManager.getInstance().collectionCount == 121) {
            achiveImage.setImageResource(R.drawable.tree6);
            leveltext.setText("[Master] 열매 단계");
            statusText.setText("축하합니다! 식물을 전부 획득하였습니다.\n" +
                    "\"서울식물원 화이팅!\" 이라는 이름으로\n서울식물원에 나무 한그루가 기증되었습니다.");

            closeBtn.setText("[ 초 기 화 ]");
            closeBtn.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    finish();
                    AppManager.getInstance().collectionCount = 0;
                    ArrayList<PlantBookItem> list = AppManager.getInstance().getList();
                    for (PlantBookItem item : list) {
                        item.setCollected(false);
                    }
                    AppManager.getInstance().setList(list); // 해야하는 지 잘 모르겠음
                }
            });
        } else {
            statusText.setText(statusFront + AppManager.getInstance().collectionCount + statusBack);
            closeBtn.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) { finish();
                }
            });

            if (AppManager.getInstance().collectionCount >= 0 && AppManager.getInstance().collectionCount <= 121 / 5) {
                achiveImage.setImageResource(R.drawable.tree1);
                leveltext.setText("[Lv1] 씨앗 단계 (0~24개)");
            } else if (AppManager.getInstance().collectionCount <= 121 / 5 * 2) {
                achiveImage.setImageResource(R.drawable.tree2);
                leveltext.setText("[Lv2] 새싹 단계 (25~48개)");
            } else if (AppManager.getInstance().collectionCount <= 121 / 5 * 3) {
                achiveImage.setImageResource(R.drawable.tree3);
                leveltext.setText("[Lv3] 잎새 단계 (49~72개)");
            } else if (AppManager.getInstance().collectionCount <= 121 / 5 * 4) {
                achiveImage.setImageResource(R.drawable.tree4);
                leveltext.setText("[Lv4] 가지 단계 (73~96개)");
            } else if (AppManager.getInstance().collectionCount < 121) {
                achiveImage.setImageResource(R.drawable.tree5);
                leveltext.setText("[Lv5] 나무 단계 (97~120개)");
            }
        }


    }
}
