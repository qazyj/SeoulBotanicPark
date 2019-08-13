package com.example.botanic_park.Map;

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

    private final int FIRST_FLOOR = 2;
    private final int SECOND_FLOOR = 3;
    private final int FOURTH_FLOOR = 4;
    private final int GREENHOUSE = 1;

    FrameLayout frame;
    FloatingActionMenu floatingMenu;
    com.github.clans.fab.FloatingActionButton firstFloor, secondFloor, fourthFloor;
    View zoomItem;
    ZoomView zoomView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_botanic_center, container, false);
        zoomItem = getLayoutInflater().inflate(R.layout.zoom_item, null);

        ImageView markerImage = zoomItem.findViewById(R.id.center_mark);
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

        RelativeLayout contain = (RelativeLayout) view.findViewById(R.id.contain);
        contain.addView(zoomView);

        return view;

    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState)
    {
        firstFloor.setOnClickListener(new floatingMenuClick());
        secondFloor.setOnClickListener(new floatingMenuClick());
        fourthFloor.setOnClickListener(new floatingMenuClick());

    }

    private final View.OnTouchListener changeColorListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
            int color = bmp.getPixel((int) event.getX(), (int) event.getY());
            if (color == Color.TRANSPARENT)
                return false;
            else {
                //code to execute
                Toast.makeText(getContext(), "헤헤", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
    };

    private void setZoomItem(int where)
    {
        zoomView.smoothZoomTo(1f,zoomView.getWidth()/2f,zoomView.getHeight()/2f);

        switch (where)
        {
            case FIRST_FLOOR:
                setZoomItemImage(R.drawable.center_1f,R.drawable.seed_library_mark);
                break;

            case SECOND_FLOOR:
                setZoomItemImage(R.drawable.center_2f, R.drawable.library_mark);
                break;

            case FOURTH_FLOOR:
                setZoomItemImage(R.drawable.center_4f, 0);
                break;
        }

    }

    private void setZoomItemImage(int floorResource, int markerResource)
    {
        ((ImageView) zoomItem.findViewById(R.id.floor)).setImageResource(floorResource);
        ((ImageView) zoomItem.findViewById(R.id.center_mark)).setImageResource(markerResource);
    }

    private void closeFloatingMenu()
    {
        floatingMenu.close(true);
        floatingMenu.getMenuIconView().setImageResource(R.drawable.icon_pin_white);
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
