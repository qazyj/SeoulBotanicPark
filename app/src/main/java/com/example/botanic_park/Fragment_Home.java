package com.example.botanic_park;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.go.seoul.airquality.AirQualityTypeMini;

public class Fragment_Home extends Fragment {

    private AirQualityTypeMini typeMini;
    private String OpenApiKey = "6b6753787371617a39326e6d7a5375";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        typeMini = (AirQualityTypeMini) view.findViewById(R.id.button_mini);
        typeMini.setOpenAPIKey(OpenApiKey);
        return view;
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
