package com.example.botanic_park;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.go.seoul.airquality.AirQualityTypeMini;

public class Fragment_Information extends Fragment implements View.OnClickListener{

    private CardView information_information_use, information_way_to_come, information_news, information_community;
    private AirQualityTypeMini typeMini;
    private String OpenApiKey = "6b6753787371617a39326e6d7a5375";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        //서울시 api
        typeMini = (AirQualityTypeMini) view.findViewById(R.id.button_mini);
        typeMini.setOpenAPIKey(OpenApiKey);

        //카드 정의
        information_information_use=(CardView)view.findViewById(R.id.information_information_use);
        information_way_to_come=(CardView)view.findViewById(R.id.information_way_to_come);
        information_news=(CardView)view.findViewById(R.id.information_news);
        information_community=(CardView)view.findViewById(R.id.information_community);
        //카드 click listener 추가
        information_information_use.setOnClickListener(this);
        information_way_to_come.setOnClickListener(this);
        information_news.setOnClickListener(this);
        information_community.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), Activity_Webview.class);;
        switch(view.getId()){
            case R.id.information_information_use :
                intent.putExtra("index","seoul_botanic_park_information_use");
                startActivity(intent);
                break;
            case R.id.information_way_to_come :
                intent.putExtra("index","seoul_botanic_park_way_to_come");
                startActivity(intent);
                break;
            case R.id.information_news :
                intent.putExtra("index","seoul_botanic_park_news");
                startActivity(intent);
                break;
            case R.id.information_community :
                intent.putExtra("index","seoul_botanic_park_community");
                startActivity(intent);
                break;
            default:
                break;

        }
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
