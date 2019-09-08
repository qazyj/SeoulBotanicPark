package com.example.botanic_park.Help;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.botanic_park.R;

public class HelpFragment extends Fragment {
    private int helpCode;
    private int page;

    public HelpFragment() {
    }

    public static HelpFragment newInstance(int helpCode, int page) {
        HelpFragment fragment = new HelpFragment();
        Bundle args = new Bundle();
        args.putInt("help code", helpCode);
        args.putInt("page", page);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helpCode = getArguments().getInt("help code", HelpActivity.HELP_TODAY_PLANT);
        page = getArguments().getInt("page", 0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_help, container, false);

        ImageView imageView = view.findViewById(R.id.image_help);
        TextView textView = view.findViewById(R.id.description);

        if(helpCode == HelpActivity.HELP_TODAY_PLANT) {
            switch (page) {
                case 1:
                    Glide.with(view).load(R.drawable.help_today_1).centerInside().into(imageView);
                    textView.setText("매일 새롭게 생기는\n오늘의 식물을 찾아보세요!");
                    break;
                case 2:
                    Glide.with(view).load(R.drawable.help_today_2).centerInside().into(imageView);
                    textView.setText("전부 모으면 식물원 안에서 사용할 수 있는\n할인 쿠폰이 지급됩니다.");
                    break;
                case 3:
                    Glide.with(view).load(R.drawable.help_today_3).centerInside().into(imageView);
                    textView.setText("식물 획득은 '식물'탭의\n식물 이미지 검색을 통해 할 수 있어요.");
                    break;

            }
        } else if(helpCode == HelpActivity.HELP_PLANT_BOOK){
            switch (page) {
                case 1:
                    Glide.with(view).load(R.drawable.help_plant_1).centerInside().into(imageView);
                    textView.setText(Html.fromHtml("<b>*프로그래스바*</b>"
                            + "<br>상단에 있는 프로그래스바로<br>얼마나 모았는지 확인할 수 있어요!"));
                    break;
                case 2:
                    Glide.with(view).load(R.drawable.help_plant_2).centerInside().into(imageView);
                    textView.setText(Html.fromHtml("<b>*도감 정렬*</b>"
                            + "<br>상단에 있는 콤보박스를 누르면<br>식물도감을 획득순, 최신순, 가나다순으로<br>" +
                            "정렬할 수 있어요."));
                    break;
                case 3:
                    Glide.with(view).load(R.drawable.help_plant_3).centerInside().into(imageView);
                    textView.setText(Html.fromHtml("<b>*식물 검색*</b>"
                            + "<br>1) 검색창에 원하는 식물을<br>영문, 한글로 입력해 검색할 수 있어요.<br>" +
                            "2) 카메라 버튼을 누르면<br>이미지를 통해 식물을 검색할 수 있어요.<br>"));
                    break;

            }
        }

        return view;
    }
}
