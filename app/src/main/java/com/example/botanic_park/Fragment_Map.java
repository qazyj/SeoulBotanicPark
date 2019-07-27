package com.example.botanic_park;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.LocationButtonView;
import com.naver.maps.map.widget.ZoomControlView;


public class Fragment_Map extends Fragment implements OnMapReadyCallback{

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private MapView mapView;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private InfoWindow infoWindow;
    private MainActivity mainActivity;
    private LocationButtonView locationButtonView;
    private ZoomControlView zoomControlView;

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
        mainActivity = (MainActivity) getActivity();
        return view;
    }


    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(naverMap -> {
            locationButtonView = getActivity().findViewById(R.id.location);
            locationButtonView.setMap(naverMap);
            zoomControlView = getView().findViewById(R.id.zoom);
            zoomControlView.setMap(naverMap);
        });
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
        naverMap.setOnMapClickListener(new NaverMapClick());
        naverMap.setLocationSource(new TrackingModeListener(this,LOCATION_PERMISSION_REQUEST_CODE));

        naverMap.getUiSettings().setZoomControlEnabled(false);
        getNormalMarker(naverMap,37.571868996488575,126.83178753229976,"화장실");
        setInfowindowMarker(naverMap,37.56940934518748,126.83502476287038,"식물문화센터");
        getNormalMarker(naverMap,37.568248938032475,126.83315398465993,"주제정원");

        setThemaGardenMarker();

        infoWindow = getInfoWindow("정보");
    }


    /*----오버레이 생성 메소드----*/
    private Marker setInfowindowMarker(NaverMap naverMap, double latitude, double longitude, String caption)
    {
        final Marker marker = getNormalMarker(naverMap,latitude,longitude,caption);
        marker.setTag(R.array.tema_garden);
        marker.setOnClickListener(new MarkerClick());

        return marker;
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
        marker.setZIndex(2);
        marker.setOnClickListener(overlay -> {
            Marker mark = (Marker) overlay;
            naverMap.setCameraPosition(new CameraPosition(mark.getPosition(),16));
            return true;
        });

        return marker;
    }

    private void setGardenMark(String gardenName, double latitude, double longitude)
    {
        Marker garden = getNormalMarker(naverMap,latitude,longitude,gardenName);
        garden.setZIndex(0);
        garden.setHideCollidedMarkers(true);
        garden.setForceShowIcon(false);
        garden.setIconTintColor(Color.BLUE);
    }

    private InfoWindow getInfoWindow(final String describe)  // infowindow
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

    /*------------*/

    private void setThemaGardenMarker()  // 주제정원 마크
    {

        setGardenMark("치유의 정원",37.56867930442805,126.83485658315652);
        setGardenMark("숲정원",37.567565404738865,126.83402142477078);
        setGardenMark("바람의 정원",37.5676674540794,126.83291191946778);
        setGardenMark("오늘의 정원",37.568248938032475,126.83315398465993);
        setGardenMark("추억의 정원",37.56876686430842,126.83305095534219);
        setGardenMark("정원사 정원",37.56871846741127 ,126.83387171487031);
        setGardenMark("사색 정원",37.56946197667976 ,126.83400589684094);
        setGardenMark("초대의 정원",37.569061575852345 ,126.83439164445056);

    }

    /*---- 네이버 지도 커스텀 ----*/

    private void locationButtonEnabled()
    {
       if( locationButtonView.isShown())
       {
           locationButtonView.setVisibility(View.GONE);
           zoomControlView.setVisibility(View.GONE);

       }  else { locationButtonView.setVisibility(View.VISIBLE);
       zoomControlView.setVisibility(View.VISIBLE);
       }

    }


    class NaverMapClick implements NaverMap.OnMapClickListener{ // 맵 클릭을 위한 리스너

        @Override
        public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
            if(infoWindow.getMarker() != null) infoWindow.close();
            else
            {
                mainActivity.setCurveBottomBarVisibility();
                locationButtonEnabled();
            }
        }
    }

    class MarkerClick implements Overlay.OnClickListener{ // 마커 클릭을 위한 리스너

        @Override
        public boolean onClick(@NonNull Overlay overlay) {
            Marker marker = (Marker)overlay;

            if(marker.getInfoWindow() == null){
                infoWindow.open(marker);
                naverMap.setCameraPosition(new CameraPosition(marker.getPosition(),16));

            } else {
                infoWindow.close();
            }

            return true;
        }
    }

    class InfowindowClick implements Overlay.OnClickListener{ // 정보창 클릭을 위한 리스너

        @Override
        public boolean onClick(@NonNull Overlay overlay) {
            InfoWindow infoWindow = (InfoWindow) overlay;
            Marker marker = infoWindow.getMarker();
            Toast.makeText(getContext(), marker.getCaptionText(), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getActivity(), Facilities_information.class);
            startActivity(intent);

            return true;
        }
    }

    class TrackingModeListener extends FusedLocationSource { // 위치 정보 클래스 오버라이드 메소드

        public TrackingModeListener(@NonNull Fragment fragment, int permissionRequestCode) {
            super(fragment, permissionRequestCode);
        }

        @Override
        public void deactivate() {  // 현재 위치 해제 시에 피벗 위치로 카메라 이동
            naverMap.setCameraPosition(new CameraPosition(new LatLng(37.56801290446582,126.83245227349256),15));
        }
    }

}
