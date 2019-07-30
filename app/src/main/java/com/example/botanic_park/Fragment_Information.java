package com.example.botanic_park;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Information extends Fragment implements View.OnClickListener{

    private CardView information_information_use, information_way_to_come, information_news, information_community;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);

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
        Intent intent;
        switch(view.getId()){
            case R.id.information_information_use :
                intent = new Intent(getActivity(), ActivityInformationUse.class);
                startActivity(intent);
                break;
            case R.id.information_way_to_come :
                intent = new Intent(getActivity(), WayToComeActivity.class);
                startActivity(intent);
                break;
            case R.id.information_news :
                intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
                break;
            case R.id.information_community :
                intent = new Intent(getActivity(), ActivityInformationUse.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }

}
