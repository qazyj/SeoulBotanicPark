package com.example.botanic_park.Help;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.botanic_park.R;
import me.relex.circleindicator.CircleIndicator;

public class HelpActivity extends AppCompatActivity {
    public static String HELP_CODE = "help code";
    public static final int HELP_TODAY_PLANT = 0;
    public static final int HELP_PLANT_BOOK = 1;

    FragmentPagerAdapter adapter;
    String title;
    int helpCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_tabs);

        Intent intent = getIntent();
        helpCode = intent.getIntExtra(HELP_CODE, HELP_TODAY_PLANT);
        switch (helpCode){
            case HELP_TODAY_PLANT:
                title = "\"오늘의 식물\"";
                adapter = new PagerAdapter(getSupportFragmentManager(), helpCode, 3);
                break;
            case HELP_PLANT_BOOK:
                title = "\"식물 도감\"";
                adapter = new PagerAdapter(getSupportFragmentManager(), helpCode, 5);
                break;
        }

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        TextView textView = findViewById(R.id.title);
        textView.setText(title);

        Button skipBtn = findViewById(R.id.skip_btn);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public static class PagerAdapter extends FragmentPagerAdapter {
        int helpCode = 0;
        int count = 0;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public PagerAdapter(FragmentManager fm, int helpCode, int count) {
            super(fm);
            this.helpCode = helpCode;
            Log.d("helpActivity", helpCode + "");
            this.count = count;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return HelpFragment.newInstance(helpCode, 1);
                case 1:
                    return HelpFragment.newInstance(helpCode, 2);
                case 2:
                    return HelpFragment.newInstance(helpCode, 3);
                case 3:
                    return HelpFragment.newInstance(helpCode, 4);
                case 4:
                    return HelpFragment.newInstance(helpCode, 5);
            }
            return null;
        }


        @Override
        public int getCount() {
            return count;
        }
    }
}


