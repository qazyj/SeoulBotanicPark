package com.example.botanic_park;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.botanic_park.PlantSearch.PlantBookItem;
import kr.go.seoul.airquality.AirQualityTypeMini;
import kr.go.seoul.airquality.Common.AirInfo;

import java.util.ArrayList;

public class Fragment_Home extends Fragment {
    private ArrayList<PlantBookItem> todaysPlant;

    private AirQualityTypeMini typeMini;
    private String OpenApiKey = "6b6753787371617a39326e6d7a5375";

    private LinearLayout item1, item2, item3;
    private TextView text1, text2, text3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 서울시 api
        typeMini = (AirQualityTypeMini) view.findViewById(R.id.button_mini);
        typeMini.setOpenAPIKey(OpenApiKey);

        // 오늘의 식물
        item1 = view.findViewById(R.id.item1);
        item2 = view.findViewById(R.id.item2);
        item3 = view.findViewById(R.id.item3);

        text1 = view.findViewById(R.id.text1);
        text2 = view.findViewById(R.id.text2);
        text3 = view.findViewById(R.id.text3);

        setTodayPlant();

        return view;
    }

    private void setTodayPlant(){
        ArrayList<PlantBookItem> list = AppManager.getInstance().getList();

        // 일단 오늘의 식물 임의로 넣어둠
        text1.setText(list.get(0).getName_ko());
        text2.setText(list.get(1).getName_ko());
        text3.setText(list.get(2).getName_ko());
    }

    @Override
    public void onResume(){
        super.onResume();
        typeMini.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        typeMini.setVisibility(View.GONE);
    }
}

