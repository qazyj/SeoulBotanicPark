package com.example.botanic_park;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.graphics.Color;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.botanic_park.Information.Fragment_Information;
import com.example.botanic_park.Map.Fragment_Map;
import com.example.botanic_park.PlantSearch.Fragment_Plant_Book;
import com.example.botanic_park.PlantSearch.PlantBookItem;

import com.example.botanic_park.PaymentAndQR.PaymentPopUpActivity;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private Fragment_Home fragment_Home;
    private Fragment_Map fragment_Map;
    private Fragment_Plant_Book fragment_Plant_Book;
    private Fragment_Information fragment_Information;

    private ArrayList<PlantBookItem> list = null;

    private CurveBottomBar curveBottomBar;


    FloatingActionButton floatingActionButton;
    public static boolean isFristCalled = true;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 로딩 액티비티에서 식물 리스트 정보 받아옴
        Intent intent = getIntent();
        list = (ArrayList<PlantBookItem>) intent.getSerializableExtra(LoadingActivity.PLANT_LIST_KEY);

        //상태 바 색 바꿔줌
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#FAFAFA"));
        setContentView(R.layout.activity_main);

        // 프래그먼트 객체 생성
        fragment_Home = new Fragment_Home();
        fragment_Map = new Fragment_Map();
        fragment_Plant_Book = Fragment_Plant_Book.newInstance(list);
        fragment_Information = new Fragment_Information();

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, fragment_Home).commit();

        // 하단 메뉴 설정
        floatingActionButton = findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, PaymentPopUpActivity.class);
                startActivity(intent1); // QR 액티비티 띄움
            }
        });

        curveBottomBar = findViewById(R.id.customBottomBar);
        curveBottomBar.inflateMenu(R.menu.navigation);
        curveBottomBar.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 프레그먼트에서 오버라이드된 메소드 사용
        // 프레그먼트에서 이 메소드를 override하면 동작하지 않음

        if (requestCode == Fragment_Plant_Book.PERMISSION_REQUEST_CODE) {
            // 식물 이미지 검색 권한
            fragment_Plant_Book.onRequestPermissionsResult(requestCode, permissions, grantResults);
        } else {
            // 지도 위치 탐색 권한
            fragment_Map.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // 하단 메뉴 선택 리스너
    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.home:
                    transaction.replace(R.id.frame_container, fragment_Home).commit();
                    break;
                case R.id.map:
                    transaction.replace(R.id.frame_container, fragment_Map).commit();
                    break;
                case R.id.plant_book:
                    transaction.replace(R.id.frame_container, fragment_Plant_Book).commit();
                    break;
                case R.id.information:
                    transaction.replace(R.id.frame_container, fragment_Information).commit();
                    break;
            }
            return true;
        }
    }

    @SuppressLint("RestrictedApi")
    public void setCurveBottomBarVisibility() {

        if (curveBottomBar.isShown()) {
            curveBottomBar.setVisibility(View.GONE);
            floatingActionButton.setVisibility(View.GONE);
        } else {
            curveBottomBar.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.VISIBLE);
        }

    }
}

