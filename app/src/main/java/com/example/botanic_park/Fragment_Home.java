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
import com.bumptech.glide.Glide;
import com.example.botanic_park.PlantSearch.DetailPopUpActivity;
import com.example.botanic_park.PlantSearch.Fragment_Plant_Book;
import com.example.botanic_park.PlantSearch.PlantBookItem;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;


import java.util.ArrayList;
import java.util.TimerTask;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SliderView sliderView = view.findViewById(R.id.imageSlider);
        SliderAdapter sliderAdapter = new SliderAdapter(getContext());
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.startAutoCycle();
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

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
            }
        });
        return view;
    }

    private void setPlantsToday(){
        ArrayList<PlantBookItem> list = AppManager.getInstance().getList();
        plantsToday = new ArrayList<>();
        plantsToday.add(getNewItem(list.get(0)));
        plantsToday.add(getNewItem(list.get(1)));
        plantsToday.add(getNewItem(list.get(2)));

        AppManager.getInstance().setPlantsToday(plantsToday);
        /*
        for(int i=0; i<3; i++){
            int random = (int) (Math.random() * list.size());
            plantsToday.add(list.get(random));
            Log.d("테스트", plantsToday.get(i).getName_ko());
        }
        */

    }

    private PlantBookItem getNewItem(PlantBookItem item){
        return new PlantBookItem(item.getId(), item.getImg_url(),
                item.getName_ko(), item.getName_sc(), item.getName_en(),
                item.getType(), item.getBlossom(), item.getDetails());
    }

}

class SliderAdapter extends SliderViewAdapter<SliderAdapter.ViewHolder>{
    private Context context;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_slider, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        switch (position) {
            case 0:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.home_resize_1)
                        .into(viewHolder.imageView);
                break;
            case 1:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.home_resize_2)
                        .into(viewHolder.imageView);
                break;
            case 2:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.home_resize_3)
                        .into(viewHolder.imageView);
                break;

        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    class ViewHolder extends SliderViewAdapter.ViewHolder{
        View itemView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_home_background);
            this.itemView = itemView;
        }
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


