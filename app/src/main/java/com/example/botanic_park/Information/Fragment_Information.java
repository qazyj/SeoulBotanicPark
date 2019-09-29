package com.example.botanic_park.Information;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.botanic_park.NetworkStatus;
import com.example.botanic_park.OnSingleClickListener;
import com.example.botanic_park.R;

public class Fragment_Information extends Fragment implements View.OnClickListener {
    private ImageButton guideOutside, guideInside;
    private LinearLayout information_information_use, information_way_to_come, information_news, information_community;
    private Intent intent;

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

        // 안내도 버튼
        guideOutside = view.findViewById(R.id.guide_outside);
        guideInside = view.findViewById(R.id.guide_inside);
        Glide.with(view)
                .load(R.drawable.guide_outside)
                .fitCenter()
                .thumbnail(0.1f)
                .into(guideOutside);
        Glide.with(view)
                .load(R.drawable.guide_inside)
                .fitCenter()
                .thumbnail(0.1f)
                .into(guideInside);
        guideOutside.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                excuteWebBrowser("http://botanicpark.seoul.go.kr/front/img/%EC%95%88%EB%82%B4%EB%8F%84.pdf" );
            }
        });
        guideInside.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                excuteWebBrowser("http://botanicpark.seoul.go.kr/front/img/greenhouse_ripplet_02.pdf");
            }
        });

        //카드 정의
        information_information_use = view.findViewById(R.id.information_information_use);
        information_way_to_come = view.findViewById(R.id.information_way_to_come);
        information_news = view.findViewById(R.id.information_news);
        information_community = view.findViewById(R.id.information_community);

        //카드 click listener 추가
        information_information_use.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                intent = new Intent(getActivity(), InformationUseActivity.class);
                startActivity(intent);
            }
        });
        information_way_to_come.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                intent = new Intent(getActivity(), WayToComeActivity.class);
                startActivity(intent);
            }
        });
        information_news.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });
        information_community.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                intent = new Intent(getActivity(), InconvenienceActivity.class);
                checkNewworkBeforeNextActivity(intent);
            }
        });
        return view;
    }

    public void excuteWebBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        startActivity(intent);
    }

    private void checkNewworkBeforeNextActivity(Intent intent) {
        if(NetworkStatus.getConnectivityStatus(getActivity())!=3) {
            startActivity(intent);
        }
        else {
            Toast.makeText(getActivity(), "네트워크가 연결되어야 이용할 수 있습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {

    }
}
