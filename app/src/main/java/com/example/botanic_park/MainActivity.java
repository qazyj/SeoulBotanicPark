package com.example.botanic_park;

import android.app.Activity;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Color;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.botanic_park.Information.Fragment_Information;
import com.example.botanic_park.Map.Fragment_main_map;
import com.example.botanic_park.PaymentAndQR.PaymentPopUpActivity;
import com.example.botanic_park.PaymentAndQR.QRPopUpActivity;
import com.example.botanic_park.PlantSearch.*;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;


public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private Fragment_Home fragment_Home;
    private Fragment_main_map fragment_Map;
    private Fragment_Plant_Book fragment_Plant_Book;
    private Fragment_Information fragment_Information;

    private CurveBottomBar curveBottomBar;
    FloatingActionButton floatingActionButton;
    BackPressCloseHandler backPressCloseHandler;

    long lastTime = 0;

    public int limitTime = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backPressCloseHandler = new BackPressCloseHandler(this);

        //상태 바 색 바꿔줌
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#FAFAFA"));
        setContentView(R.layout.activity_main);
        AppManager.getInstance().setMainActivity(this);

        // 초기 프래그먼트 설정
        fragment_Home = Fragment_Home.newInstance();

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, fragment_Home).commit();


        // 하단 메뉴 설정
        floatingActionButton = findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(floatingButtonClick);
        AppManager.getInstance().setMenuFloatingActionButton(floatingActionButton);

        curveBottomBar = findViewById(R.id.customBottomBar);
        curveBottomBar.inflateMenu(R.menu.navigation);
        curveBottomBar.setOnNavigationItemSelectedListener(new ItemSelectedListener());


        setTodayLimitTime();
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        onSaveData(AppManager.getInstance().getList()); // 도감 저장
        super.onDestroy();
    }

    // 식물 list 저장
    private void onSaveData(ArrayList<PlantBookItem> list) {
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<ArrayList<PlantBookItem>>() {
        }.getType();
        String json = gson.toJson(list, listType);  // arraylist -> json string

        SharedPreferences sp = getSharedPreferences("Botanic Park", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("list", json); // JSON으로 변환한 객체를 저장한다.
        editor.commit(); // 완료한다.
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
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
                    if (fragment_Home == null) {
                        fragment_Home = Fragment_Home.newInstance();
                        fragmentManager.beginTransaction().add(R.id.frame_container, fragment_Home).commit();
                    }
                    if (fragment_Home != null)
                        fragmentManager.beginTransaction().show(fragment_Home).commit();
                    if (fragment_Map != null)
                        fragmentManager.beginTransaction().hide(fragment_Map).commit();
                    if (fragment_Plant_Book != null)
                        fragmentManager.beginTransaction().hide(fragment_Plant_Book).commit();
                    if (fragment_Information != null)
                        fragmentManager.beginTransaction().hide(fragment_Information).commit();
                    break;
                case R.id.map:
                    if (fragment_Map == null) {
                        fragment_Map = Fragment_main_map.newInstance();
                        fragmentManager.beginTransaction().add(R.id.frame_container, fragment_Map).commit();
                    }
                    if (fragment_Home != null)
                        fragmentManager.beginTransaction().hide(fragment_Home).commit();
                    if (fragment_Map != null)
                        fragmentManager.beginTransaction().show(fragment_Map).commit();
                    if (fragment_Plant_Book != null)
                        fragmentManager.beginTransaction().hide(fragment_Plant_Book).commit();
                    if (fragment_Information != null)
                        fragmentManager.beginTransaction().hide(fragment_Information).commit();
                    break;
                case R.id.plant_book:
                    if (fragment_Plant_Book == null) {
                        fragment_Plant_Book = Fragment_Plant_Book.newInstance();
                        fragmentManager.beginTransaction().add(R.id.frame_container, fragment_Plant_Book).commit();
                    }
                    if (fragment_Home != null)
                        fragmentManager.beginTransaction().hide(fragment_Home).commit();
                    if (fragment_Map != null)
                        fragmentManager.beginTransaction().hide(fragment_Map).commit();
                    if (fragment_Plant_Book != null)
                        fragmentManager.beginTransaction().show(fragment_Plant_Book).commit();
                    if (fragment_Information != null)
                        fragmentManager.beginTransaction().hide(fragment_Information).commit();
                    break;

                case R.id.information:
                    if (fragment_Information == null) {
                        fragment_Information = Fragment_Information.newInstance();
                        fragmentManager.beginTransaction().add(R.id.frame_container, fragment_Information).commit();
                    }
                    if (fragment_Home != null)
                        fragmentManager.beginTransaction().hide(fragment_Home).commit();
                    if (fragment_Map != null)
                        fragmentManager.beginTransaction().hide(fragment_Map).commit();
                    if (fragment_Plant_Book != null)
                        fragmentManager.beginTransaction().hide(fragment_Plant_Book).commit();
                    if (fragment_Information != null)
                        fragmentManager.beginTransaction().show(fragment_Information).commit();
                    break;
            }
            return true;
        }
    }

    public void setCurveBottomBarVisibility() {
        if (curveBottomBar.isShown()) {
            curveBottomBar.setVisibility(View.GONE);
            floatingActionButton.hide();
        } else {
            curveBottomBar.setVisibility(View.VISIBLE);
            floatingActionButton.show();
        }
    }

    public void changeCurveBottomBarVisibility(boolean show) {
        if (!show) {
            curveBottomBar.setVisibility(View.GONE);
            floatingActionButton.hide();
        } else {
            curveBottomBar.setVisibility(View.VISIBLE);
            floatingActionButton.show();
        }
    }

    public void setDateOfPayment() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Date date = new Date();
        String currentDate = formatter.format(date);

        SharedPreferences sp = getSharedPreferences("Botanic Park", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("dateOfPayment", currentDate);
        editor.commit();

        Log.d("현재 날짜", currentDate);


    }

    public Boolean didPay() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Date date = new Date();
        String currentDate = formatter.format(date);

        SharedPreferences pref = getSharedPreferences("Botanic Park", MODE_PRIVATE);
        String dateOfPayment = pref.getString("dateOfPayment", "00000000");
        Log.d("현재 날짜", currentDate);
        Log.d("저장 날짜", dateOfPayment);

        return dateOfPayment.equals(currentDate);
    }

    private View.OnClickListener floatingButtonClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(System.currentTimeMillis() <= lastTime + 500) return;
            lastTime = System.currentTimeMillis();
            Intent intent;
            if (didPay()) intent = new Intent(MainActivity.this, QRPopUpActivity.class);
            else intent = new Intent(MainActivity.this, PaymentPopUpActivity.class);
            startActivity(intent); // QR 액티비티 띄움

        }
    };

    private void setTodayLimitTime()
    {
        int nMonth;

        TimeZone jst = TimeZone.getTimeZone("Asia/Seoul");
        Calendar calendar = Calendar.getInstance(jst);

        nMonth = calendar.get(Calendar.MONTH) + 1;

        if(nMonth < 3 || nMonth > 10) limitTime --;
    }
}

class BackPressCloseHandler {
    private long backKeyPressedTime = 0;
    private Toast toast;
    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }

}



