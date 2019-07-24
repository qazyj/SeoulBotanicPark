package com.example.botanic_park;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;


public class Fragment_Map extends Fragment implements MapView.POIItemEventListener{


    MapView mapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = new MapView(getContext());
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.56931290446582, 126.83245227349256 ), 3, true);

        ViewGroup mapViewContainer = (ViewGroup) view.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        mapView.setPOIItemEventListener(this);

        //mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        //mapView.setShowCurrentLocationMarker(true);

        setToiletMarker();
        setGarden();

        return view;
    }

    private void setToiletMarker()
    {
        setBotanicParkMark("화장실",0,37.571868996488575,126.83178753229976);
        setBotanicParkMark("화장실",0,37.5718392589002,126.83627024138387);

    }

    private void setGarden()
    {
        setBotanicParkMark("식물문화센터",1,37.56940934518748,126.83502476287038);
        setBotanicParkMark("치유의 정원",2,37.56867930442805,126.83485658315652);
        setBotanicParkMark("숲정원",3, 37.567565404738865,126.83402142477078);
        setBotanicParkMark("바람의 정원",4, 37.5676674540794,126.83291191946778);
        setBotanicParkMark("오늘의 정원",5, 37.568248938032475,126.83315398465993);
        setBotanicParkMark("추억의 정원",6, 37.56876686430842,126.83305095534219);
        setBotanicParkMark("정원사 정원",7,  37.56871846741127 ,126.83387171487031);
        setBotanicParkMark("사색 정원",8,  37.56946197667976 ,126.83400589684094);
        setBotanicParkMark("초대의 정원",9,  37.569061575852345 ,126.83439164445056);
    }

    private void setBotanicParkMark(String name, int tag, double lat, double lon)
    {
        MapPOIItem mark = new MapPOIItem();
        mark.setItemName(name);
        mark.setTag(tag);
        mark.setMapPoint(MapPoint.mapPointWithGeoCoord(lat, lon));

        switch (tag) {

            case 0:
                mark.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                break;

            default:
                mark.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                mark.setCustomImageResourceId(R.drawable.botanic_mark);
                break;
        }

        mapView.addPOIItem(mark);

    }


    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

        if(mapPOIItem.getTag() == 0) return;
        Intent intent = new Intent(getActivity(), Facilities_information.class);
        intent.putExtra("tag", mapPOIItem.getTag());
        startActivity(intent);

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }


}
