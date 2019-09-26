package com.example.botanic_park.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.botanic_park.MainActivity;
import com.example.botanic_park.NetworkStatus;
import com.example.botanic_park.R;

public class Fragment_main_map extends Fragment {


    public Fragment_main_map() {
    }

    public static Fragment_main_map newInstance() {
        Fragment_main_map fragment = new Fragment_main_map();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }


    boolean isNaverMap = true;
    Fragment naverMap;
    Fragment centerMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_main_map, container, false);
        Toast.makeText(getContext(), "지도를 탭하면 전체화면으로 볼 수 있습니다.", Toast.LENGTH_LONG).show();
        Button button =(Button) view.findViewById(R.id.change_map);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {s
                if(isNaverMap) {
                    centerMap = new Fragment_BotanicCenter();

                    if(!button.isShown()){
                        ((MainActivity) getActivity()).setCurveBottomBarVisibility();
                        button.setVisibility(View.VISIBLE); }

                    replaceFrgment(centerMap);
                    naverMap.onDestroy();
                    button.setText("외부 지도 보기");
                    isNaverMap = false;
                }

                else{
                    if(NetworkStatus.getConnectivityStatus(getContext()) == NetworkStatus.TYPE_NOT_CONNECTED) naverMap = new not_connect_internet();
                    else naverMap = new Fragment_Map((Button) view.findViewById(R.id.change_map));
                    replaceFrgment(naverMap);
                    centerMap.onDestroy();
                    button.setText("문화 센터 내부 지도 보기");
                    isNaverMap = true;
                }
            }
        });

       return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if(NetworkStatus.getConnectivityStatus(getContext()) == NetworkStatus.TYPE_NOT_CONNECTED) naverMap = new not_connect_internet();
        else naverMap = new Fragment_Map((Button) view.findViewById(R.id.change_map));
        replaceFrgment(naverMap);
    }

    private void replaceFrgment(Fragment map)
    {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.main_map, map).commit();
    }

}
