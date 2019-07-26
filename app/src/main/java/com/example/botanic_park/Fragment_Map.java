package com.example.botanic_park;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

public class Fragment_Map extends Fragment implements OnMapReadyCallback{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private MapView mapView;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private InfoWindow infoWindow;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        return view;
    }


    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        UiSettings uiSettings = naverMap.getUiSettings();
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        naverMap.setOnMapClickListener(new NaverMapClick());
        getNormalMarker(naverMap,37.571868996488575,126.83178753229976,"화장실");
        setInfowindowMarker(naverMap,37.56940934518748,126.83502476287038,"식물문화센터");
        infoWindow = getInfoWindew("정보");
    }

    private void setInfowindowMarker(NaverMap naverMap, double latitude, double longitude, String caption)
    {
        final Marker marker = getNormalMarker(naverMap,latitude,longitude,caption);
        marker.setTag(R.array.tema_garden);
        marker.setOnClickListener(new MarkerClick());
    }


    private Marker getNormalMarker(NaverMap naverMap, double latitude, double longitude, String caption)
    {
        Marker marker = new Marker();
        marker.setWidth(Marker.SIZE_AUTO);
        marker.setHeight(Marker.SIZE_AUTO);
        marker.setHideCollidedSymbols(true);
        marker.setHideCollidedMarkers(true);
        marker.setPosition(new LatLng(latitude, longitude));
        marker.setMap(naverMap);
        marker.setCaptionText(caption);

        return marker;
    }

    private InfoWindow getInfoWindew(final String describe)
    {
        InfoWindow infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(getContext()) {
            @NonNull
            @Override

            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return describe;
            }

        });

        infoWindow.setOnClickListener(new InfowindowClick());

        return infoWindow;
    }

    class NaverMapClick implements NaverMap.OnMapClickListener{

        @Override
        public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
            infoWindow.close();
        }
    }

    class MarkerClick implements Overlay.OnClickListener{

        @Override
        public boolean onClick(@NonNull Overlay overlay) {
            Marker marker = (Marker)overlay;

            if(marker.getInfoWindow() == null){
                infoWindow.open(marker);
            } else {
                infoWindow.close();
            }

            return true;
        }
    }

    class InfowindowClick implements Overlay.OnClickListener{

        @Override
        public boolean onClick(@NonNull Overlay overlay) {
            InfoWindow infoWindow = (InfoWindow) overlay;
            Marker marker = infoWindow.getMarker();

            Intent intent = new Intent(getActivity(), Facilities_information.class);
            startActivity(intent);

            return true;
        }
    }

}
