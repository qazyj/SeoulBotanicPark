package com.example.botanic_park;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction transaction;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment_Home fragment_Home = new Fragment_Home();
    private Fragment_Map fragment_Map = new Fragment_Map();
    private Fragment_Plant_Book fragment_Plant_Book = new Fragment_Plant_Book();
    private Fragment_Information fragment_Information = new Fragment_Information();
    private Fragment_QRCode fragment_QRCode=new Fragment_QRCode();

    private CurveBottomBar cbb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //상태 바 색 바꿔줌
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#FAFAFA"));
        setContentView(R.layout.activity_main);

        cbb = findViewById(R.id.customBottomBar);
        cbb.inflateMenu(R.menu.navigation);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, fragment_Home).commitAllowingStateLoss();

        //FloatingActionButton click event
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingactionbutton);
        floatingActionButton.setOnClickListener(new FABClickListener());

        cbb.setOnNavigationItemSelectedListener(new ItemSelectedListener());
 }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.home:
                    transaction.replace(R.id.frame_container, fragment_Home).commitAllowingStateLoss();
                    break;
                case R.id.map:
                    transaction.replace(R.id.frame_container, fragment_Map).commitAllowingStateLoss();
                    break;
                case R.id.plant_book:
                    transaction.replace(R.id.frame_container, fragment_Plant_Book).commitAllowingStateLoss();
                    break;
                case R.id.information:
                    transaction.replace(R.id.frame_container, fragment_Information).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }

    class FABClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frame_container, fragment_QRCode).commitAllowingStateLoss();
        }
    }
}
