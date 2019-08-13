package com.example.botanic_park.Information;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.botanic_park.R;

public class Fragment_Information extends Fragment implements View.OnClickListener{

    private CardView information_information_use, information_way_to_come, information_news, information_community;

    public Fragment_Information() {
    }

    public static Fragment_Information newInstance() {
        Fragment_Information fragment = new Fragment_Information();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        // 카드 정의
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
        Intent intent = null;
        switch(view.getId()){
            case R.id.information_information_use :
                intent = new Intent(getActivity(), InformationUseActivity.class);
                break;
            case R.id.information_way_to_come :
                intent = new Intent(getActivity(), WayToComeActivity.class);
                break;
            case R.id.information_news :
                intent = new Intent(getActivity(), NewsActivity.class);
                break;
            case R.id.information_community :
                intent = new Intent(getActivity(), InformationUseActivity.class);
                break;
            default:
                break;
        }

        if(intent != null)
            startActivity(intent);
    }

}
