package com.example.botanic_park;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.*;
import com.example.botanic_park.PlantSearch.DetailPopUpActivity;
import com.example.botanic_park.PlantSearch.Fragment_Plant_Book;
import com.example.botanic_park.PlantSearch.PlantBookItem;
import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;

public class Fragment_Home extends Fragment {
    private ArrayList<PlantBookItem> plantsToday;

    public Fragment_Home() {
    }

    public static Fragment_Home newInstance() {
        Fragment_Home fragment = new Fragment_Home();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setPlantsToday();   // 오늘의 식물 선정

        GridView gridView = view.findViewById(R.id.gridview_plant_today);
        PlantTodayAdapter plantTodayAdapter = new PlantTodayAdapter(getContext(),
                R.layout.item_plant_today, plantsToday);
        gridView.setAdapter(plantTodayAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), DetailPopUpActivity.class);
                intent.putExtra(Fragment_Plant_Book.SELECTED_ITEM_KEY, plantsToday.get(i));
                startActivity(intent);

                Log.d("테스트", "onClick");

            }
        });
        return view;
    }

    private void setPlantsToday(){
        ArrayList<PlantBookItem> list = AppManager.getInstance().getList();
        plantsToday = new ArrayList<>();
        plantsToday.add(list.get(0));
        plantsToday.add(list.get(1));
        plantsToday.add(list.get(2));
        Log.d("테스트", "setPlantsToday");
        /*
        for(int i=0; i<3; i++){
            int random = (int) (Math.random() * list.size());
            plantsToday.add(list.get(random));
            Log.d("테스트", plantsToday.get(i).getName_ko());
        }
        */

    }

}

class PlantTodayAdapter extends BaseAdapter {
    private ArrayList<PlantBookItem> itemList;
    private Context context;

    int layout;
    LayoutInflater layoutInflater;

    public PlantTodayAdapter(Context context, int layout, ArrayList<PlantBookItem> itemList) {
        this.itemList = itemList;
        this.layout = layout;
        this.context = context;

        layoutInflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = layoutInflater.inflate(layout, null);
        PlantBookItem item = itemList.get(i);

        TextView textView = view.findViewById(R.id.name);
        textView.setText(item.getName_ko());
        return view;
    }

}


