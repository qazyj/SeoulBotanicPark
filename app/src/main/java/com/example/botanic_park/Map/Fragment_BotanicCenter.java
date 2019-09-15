package com.example.botanic_park.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;

import android.widget.*;
import androidx.fragment.app.Fragment;
import com.example.botanic_park.R;
import com.github.clans.fab.FloatingActionMenu;
import pl.polidea.view.ZoomView;



public class Fragment_BotanicCenter extends Fragment
{

    private long lastTouch  = 0;

    private final int FIRST_FLOOR = 2;
    private final int SECOND_FLOOR = 3;
    private final int FOURTH_FLOOR = 4;
    private final int GREENHOUSE = 1;

    public static final String LIBRARY = "식물전문도서관";
    public static final String SEED_LIBRARY = "씨앗도서관";

    FrameLayout frame;
    FloatingActionMenu floatingMenu;
    com.github.clans.fab.FloatingActionButton firstFloor, secondFloor, fourthFloor;
    View zoomItem;
    ZoomView zoomView;
    ImageView markerImage;
    RelativeLayout contain;
    
    int nowfloor = 1;
    int floorResources = R.mipmap.first_floor_icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_botanic_center, container, false);
        zoomItem = getLayoutInflater().inflate(R.layout.zoom_item_1f, null);

        markerImage = zoomItem.findViewById(R.id.center_mark);
        markerImage.setDrawingCacheEnabled(true);
        markerImage.setOnTouchListener(changeColorListener);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        zoomView = new ZoomView(getActivity());
        zoomView.addView(zoomItem);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMiniMapEnabled(false);

        frame = view.findViewById(R.id.background);

        floatingMenu = (FloatingActionMenu) view.findViewById(R.id.menu);

        floatingMenu.setIconAnimated(false);

        floatingMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(floatingMenu.isOpened()) closeFloatingMenu();
                else openFloatingMenu();
            }

        });

        frame.setOnTouchListener( new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (floatingMenu.isOpened())
                {
                    closeFloatingMenu();
                    return true;
                }

                return false;
            }
        });

        firstFloor = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.firstfloor);
        secondFloor = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.secondfloor);
        fourthFloor = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fourthfloor);

        contain = (RelativeLayout) view.findViewById(R.id.contain);
        contain.addView(zoomView);

        return view;

    }

    public  void setNewZoomView(int zoomItemResource)
    {
        contain.removeView(zoomView);
        zoomView=null;
        zoomItem = getLayoutInflater().inflate(zoomItemResource, null);
        markerImage = zoomItem.findViewById(R.id.center_mark);
        markerImage.setDrawingCacheEnabled(true);
        markerImage.setOnTouchListener(changeColorListener);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        zoomView = new ZoomView(getActivity());
        zoomView.addView(zoomItem);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMiniMapEnabled(false);

        contain.addView(zoomView);

    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState)
    {
        firstFloor.setOnClickListener(new floatingMenuClick());
        secondFloor.setOnClickListener(new floatingMenuClick());
        fourthFloor.setOnClickListener(new floatingMenuClick());

    }

    private View.OnTouchListener changeColorListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
            int color = bmp.getPixel((int) event.getX(), (int) event.getY());
            if (color == Color.TRANSPARENT)
                return false;
            else {

                if(System.currentTimeMillis() < lastTouch + 500) return false;
                lastTouch = System.currentTimeMillis();

                    switch (nowfloor)
                {
                    case 1:
                        showActivity(getResources().getStringArray(R.array.seed_library_info));
                        break;
                    case 2:
                        showActivity(getResources().getStringArray(R.array.library_info));
                        break;
                    case 4:
                        return false;
                }
                return true;
            }
        }
    };

    private void showActivity(String[] information)
    {
        Toast.makeText(getContext(), information[0], Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(), Facilities_information.class);
        intent.putExtra("information", information);
        startActivity(intent);
    }
    private void setZoomItem(int where)
    {
        zoomView.smoothZoomTo(1f,zoomView.getWidth()/2f,zoomView.getHeight()/2f);

        switch (where)
        {
            case FIRST_FLOOR:
                setNewZoomView(R.layout.zoom_item_1f);
                floorResources = R.mipmap.first_floor_icon;
                nowfloor = 1;
                break;

            case SECOND_FLOOR:
                setNewZoomView(R.layout.zoom_item_2f);
                nowfloor = 2;
                floorResources = R.mipmap.second_floor_icon;
                break;

            case FOURTH_FLOOR:
                setNewZoomView(R.layout.zoom_item_4f);
                nowfloor = 4;
                floorResources = R.mipmap.fourth_floor_icon;
                break;
        }

        floatingMenu.getMenuIconView().setImageResource(floorResources);
    }

    private void closeFloatingMenu()
    {
        floatingMenu.close(true);
        floatingMenu.getMenuIconView().setImageResource(floorResources);
        frame.setBackgroundColor(Color.parseColor("#00FFFFFF"));

    }
    private void openFloatingMenu()
    {
        floatingMenu.open(true);
        floatingMenu.getMenuIconView().setImageResource(R.drawable.ic_close);
        frame.setBackgroundColor(Color.parseColor( "#BB000000"));
    }

    class floatingMenuClick  implements View.OnClickListener{

        @Override
        public void onClick(View v) {
           setZoomItem(Integer.parseInt(v.getTag().toString()));
            closeFloatingMenu();
        }
    }
}
