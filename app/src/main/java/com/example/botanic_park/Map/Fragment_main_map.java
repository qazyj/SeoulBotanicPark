package com.example.botanic_park.Map;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.botanic_park.MainActivity;
import com.example.botanic_park.R;

public class Fragment_main_map extends Fragment {


    boolean isNaverMap = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_main_map, container, false);

        Button button =(Button) view.findViewById(R.id.change_map);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(isNaverMap) {
                    if(!button.isShown()) ((MainActivity) getActivity()).setCurveBottomBarVisibility();
                    replaceFrgment(new Fragment_BotanicCenter());
                    button.setText("외부 지도 보기");
                    isNaverMap = false;
                }

                else{
                    replaceFrgment(new Fragment_Map(button));
                    button.setText("문화 센터 내부 지도 보기");
                    isNaverMap = true;
                }

            }
        });

       return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        replaceFrgment(new Fragment_Map((Button) view.findViewById(R.id.change_map)));
    }

    private void replaceFrgment(Fragment map)
    {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.main_map, map).commit();
    }

}
