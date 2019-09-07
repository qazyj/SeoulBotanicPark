package com.example.botanic_park.Map;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import com.example.botanic_park.MainActivity;
import com.example.botanic_park.R;
import com.github.clans.fab.FloatingActionMenu;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.*;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.LocationButtonView;
import com.naver.maps.map.widget.ZoomControlView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Fragment_Map extends Fragment implements OnMapReadyCallback {

    private Button parent_fragment_button;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    public final String GREEN_HOUSE = "온실";
    public static final String THEME_GARDEN = "주제 정원";
    public final String BOTANIC_CULTURE_CENTER = "문화 센터";
    public final String VISITOR_INFO = "방문자 센터";

    private MapView mapView;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private InfoWindow infoWindow;
    private MainActivity mainActivity;
    private LocationButtonView locationButtonView;
    private ZoomControlView zoomControlView;
    private FrameLayout frame;

    private int markerState = 1;
    private List<Marker> ticketBox_Markers = new ArrayList<>();
    private List<Marker> playground_Markers = new ArrayList<>();
    private List<Marker> bicycle_Markers = new ArrayList<>();
    private List<Marker> parking_Markers = new ArrayList<>();

    FloatingActionMenu floatingMenu;
    com.github.clans.fab.FloatingActionButton all, tickebox, playground, bicycle, parking;


    public Fragment_Map(Button button) {
        parent_fragment_button = button;
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

        frame = view.findViewById(R.id.background);

        mapView.getMapAsync(naverMap -> {

            locationButtonView = getActivity().findViewById(R.id.location);
            locationButtonView.setMap(naverMap);
            zoomControlView = getView().findViewById(R.id.zoom);
            zoomControlView.setMap(naverMap);

        });

        mapView.getMapAsync(this);
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        floatingMenu = (FloatingActionMenu) view.findViewById(R.id.menu);

        floatingMenu.setIconAnimated(false);

        floatingMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (floatingMenu.isOpened()) closeFloatingMenu();
                else openFloatingMenu();
            }

        });

        frame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (floatingMenu.isOpened()) {
                    closeFloatingMenu();
                    return true;
                }

                return false;
            }
        });
        
        all = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.all_pins);
        tickebox = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.ticket_box);
        playground = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.playground);
        bicycle = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.bicycle);
        parking = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.parking);
        ;

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
        naverMap.setOnMapClickListener(new naverMapClick());
        naverMap.setLocationSource(new TrackingModeListener(this, LOCATION_PERMISSION_REQUEST_CODE));

        naverMap.getUiSettings().setZoomControlEnabled(false);

        getInfowindowMarker(37.5694308, 126.8350116, "온실", 80, 80, R.drawable.greenhouse, getResources().getStringArray(R.array.green_house));
        getInfowindowMarker(37.5682113, 126.8337287, "주제정원", 80, 80, R.drawable.garden, getResources().getStringArray(R.array.theme_garden));
        getInfowindowMarker(37.5696359, 126.8350098, "문화센터", 80, 80, R.drawable.botanic_center, getResources().getStringArray(R.array.botnic_culture_center));
        getInfowindowMarker(37.5662934, 126.8296977, "방문자센터", 80, 80, R.drawable.information, getResources().getStringArray(R.array.visitor_info)).setSubCaptionText("카페·화장실");

        all.setOnClickListener(new floatingMenuClick());
        tickebox.setOnClickListener(new floatingMenuClick());
        playground.setOnClickListener(new floatingMenuClick());
        bicycle.setOnClickListener(new floatingMenuClick());
        parking.setOnClickListener(new floatingMenuClick());

        setThemaGardenMarker();
        setWashingRoomMarkers();
        setParkingMarkers();
        setBicycleRackMarkers();
        setPlaygroundkMarkers();
        setTicketBoxMarkers();

        infoWindow = getInfoWindow("정보");
        setPolygone();
    }


    /*----오버레이 생성 메소드----*/

    private Marker getInfowindowMarker(double latitude, double longitude, String caption, int width, int height, int resources, String[] information) {
        Marker marker = getImageMarker(latitude, longitude, caption, width, height, resources);
        marker.setTag(information);
        marker.setOnClickListener(new MarkerClick());
        marker.setHideCollidedMarkers(false);
        marker.setHideCollidedCaptions(true);
        marker.setZIndex(300);
        return marker;
    }

    private Marker getWashingRoomMarker(double latitude, double longitude) {
        Marker marker = getNormalMarker(latitude, longitude, "", 70, 70);
        marker.setIcon(OverlayImage.fromResource(R.drawable.wc));
        marker.setZIndex(-2);
        marker.setMinZoom(14);
        return marker;
    }

    private Marker getMarkerClassified(double latitude, double longitude, String caption, int resource, int z_index, String subCaption) {
        Marker marker = getImageMarker(latitude, longitude, caption, 70, 110, resource);
        marker.setZIndex(z_index);
        marker.setMinZoom(14);
        marker.setSubCaptionText(subCaption);
        marker.setCaptionTextSize(9);
        marker.setSubCaptionTextSize(8);
        return marker;
    }

    private Marker getImageMarker(double latitude, double longitude, String caption, int width, int height, int resource) {
        Marker marker = getNormalMarker(latitude, longitude, caption, width, height);
        marker.setIcon(OverlayImage.fromResource(resource));
        return marker;
    }

    private Marker getNormalMarker(double latitude, double longitude, String caption, int width, int height) {
        Marker marker = new Marker();
        marker.setWidth(width);
        marker.setHeight(height);
        marker.setHideCollidedSymbols(true);
        marker.setHideCollidedMarkers(true);
        marker.setPosition(new LatLng(latitude, longitude));
        marker.setMap(naverMap);
        marker.setCaptionText(caption);
        marker.setZIndex(2);

        marker.setOnClickListener(overlay -> {
            Marker mark = (Marker) overlay;
            naverMap.setCameraPosition(new CameraPosition(mark.getPosition(), 16));
            if (infoWindow.getMarker() != null) infoWindow.close();
            return true;
        });

        marker.setSubCaptionColor(Color.BLUE);
        marker.setSubCaptionTextSize(10);

        return marker;
    }


    private Marker setGardenMark(String gardenName, double latitude, double longitude) {
        Marker garden = getNormalMarker(latitude, longitude, gardenName, Marker.SIZE_AUTO, Marker.SIZE_AUTO);
        garden.setZIndex(0);
        garden.setHideCollidedMarkers(true);
        garden.setForceShowIcon(false);
        garden.setWidth(1);
        garden.setHeight(1);

        garden.setSubCaptionTextSize(9);
        garden.setCaptionColor(Color.GRAY);

        garden.setMinZoom(15);

        return garden;
    }

    private InfoWindow getInfoWindow(final String describe)  // infowindow
    {
        InfoWindow infoWindow = new InfoWindow();
        renameInfoWindow(infoWindow, describe);
        infoWindow.setOnClickListener(new InfowindowClick());

        return infoWindow;
    }

    private void renameInfoWindow(InfoWindow infoWindow, String describe) {
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(getContext()) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return describe;
            }

        });
    }


    private void setPolygone() {
        PolylineOverlay polyline = new PolylineOverlay();
        polyline.setCoords(Arrays.asList(
                new LatLng(37.567925, 126.832514),
                new LatLng(37.5674657, 126.832184),
                new LatLng(37.567184, 126.8325716),
                new LatLng(37.5672061, 126.8328912),
                new LatLng(37.5671507, 126.8330588),
                new LatLng(37.5668952, 126.8329167),
                new LatLng(37.5666871, 126.8331705),
                new LatLng(37.5668223, 126.8335741),
                new LatLng(37.5671661, 126.834191),
                new LatLng(37.5673363, 126.8344046),
                new LatLng(37.5677949, 126.8346072),
                new LatLng(37.5680845, 126.8348282),
                new LatLng(37.5684359, 126.835196),
                new LatLng(37.5687838, 126.8353599),
                new LatLng(37.568786, 126.8353514),
                new LatLng(37.5692877, 126.8355989),
                new LatLng(37.5696706, 126.8355672),
                new LatLng(37.5699208, 126.8351941),
                new LatLng(37.5699228, 126.8347411),
                new LatLng(37.5699347, 126.8340503),
                new LatLng(37.5699324, 126.8340446),
                new LatLng(37.5695101, 126.8334878),
                new LatLng(37.5690383, 126.8333335),
                new LatLng(37.5689097, 126.8333064),
                new LatLng(37.567925, 126.832514)));

        polyline.setWidth(6);
        polyline.setJoinType(PolylineOverlay.LineJoin.Round);
        polyline.setMap(naverMap);
        polyline.setColor(Color.RED);
        polyline.setPattern(10, 8);
        polyline.setZIndex(-1);
        polyline.setMinZoom(15);
    }

    /*------------*/

    private void setThemaGardenMarker()  // 주제정원 마크
    {

        /* 주제 정원 */
        setGardenMark("주제원", 37.5672022, 126.8330045);
        setGardenMark("치유의 정원", 37.56867930442805, 126.83485658315652);
        setGardenMark("숲정원", 37.567565404738865, 126.83402142477078);
        setGardenMark("바람의 정원", 37.5676674540794, 126.83291191946778);
        setGardenMark("오늘의 정원", 37.568248938032475, 126.83315398465993);
        setGardenMark("추억의 정원", 37.56876686430842, 126.83305095534219);
        setGardenMark("정원사 정원", 37.56871846741127, 126.83387171487031);
        setGardenMark("사색 정원", 37.56946197667976, 126.83400589684094);
        setGardenMark("초대의 정원", 37.569061575852345, 126.83439164445056);
        setGardenMark("VR카페", 37.5684276, 126.8345194).setSubCaptionText("VR·카페·화장실");

        /* 일반 정원 */
        setGardenMark("초지원", 37.5659305, 126.8312749);
        setGardenMark("숲문화원", 37.5646662, 126.8324412);
        setGardenMark("둘레숲", 37.5649573, 126.8336954);
        setGardenMark("숲문화학교", 37.5653003, 126.8329903).setSubCaptionText("편의점·화장실");
        
        setGardenMark("재배온실2", 37.5658154, 126.8332292);
        setGardenMark("아이리스원", 37.5683496, 126.8322809);
        setGardenMark("물가 가로수길", 37.5691639, 126.8328341);
        setGardenMark("어린이정원학교", 37.5704747, 126.834327).setSubCaptionText("카페·화장실");
        setGardenMark("어린이정원", 37.5706668, 126.8344231);
    }

    private void setParkingMarkers() {
        parking_Markers.add(getMarkerClassified(37.5693741, 126.836136, "주차장", R.drawable.parking, 3, null));
        parking_Markers.add(getMarkerClassified(37.5661036, 126.8268346, "주차장", R.drawable.parking, 3, null));
        parking_Markers.add(getMarkerClassified(37.5719838, 126.8357986, "주차장", R.drawable.parking, 3, null));

    }

    private void setBicycleRackMarkers() {
        bicycle_Markers.add(getMarkerClassified(37.565238, 126.8339364, "자전거\n보관대", R.drawable.bicycle, 3, null));
        bicycle_Markers.add(getMarkerClassified(37.564231, 126.8327791, "자전거\n보관대", R.drawable.bicycle, 3, null));
        bicycle_Markers.add(getMarkerClassified(37.5641653, 126.8307302, "자전거\n보관대", R.drawable.bicycle, 3, null));
        bicycle_Markers.add(getMarkerClassified(37.5643772, 126.8283618, "자전거\n보관대", R.drawable.bicycle, 3, null));
        bicycle_Markers.add(getMarkerClassified(37.576978, 126.8363459, "자전거\n보관대", R.drawable.bicycle, 3, null));
    }

    private void setPlaygroundkMarkers() {
        playground_Markers.add(getMarkerClassified(37.5650949, 126.8325307, "놀이터", R.drawable.playground, 3, null));
        playground_Markers.add(getMarkerClassified(37.5676737, 126.8315028, "놀이터", R.drawable.playground, 3, null));
        playground_Markers.add(getMarkerClassified(37.5705915, 126.8318617, "물놀이터", R.drawable.playground_water, 3, null));

    }

    private void setTicketBoxMarkers() {
        ticketBox_Markers.add(getMarkerClassified(37.5672022, 126.8330045, "주제원 입구", R.drawable.ticketbox, 4, "화장실"));
        ticketBox_Markers.add(getMarkerClassified(37.5694526, 126.8356767, "온실 입구", R.drawable.ticketbox, 4, "방문자 안내·화장실"));
    }

    private void setWashingRoomMarkers() {
        getWashingRoomMarker(37.5719325, 126.8318979);
        getWashingRoomMarker(37.5771753, 126.8345548);
        getWashingRoomMarker(37.5714124, 126.8371654);
    }

    /* Markers */
    void selectMarkers(boolean visibility) {
        if (getResources().getInteger(R.integer.ticket_box) % markerState == 0)
            setMarkerVisibility(ticketBox_Markers, visibility);

        if (getResources().getInteger(R.integer.playground) % markerState == 0)
            setMarkerVisibility(playground_Markers, visibility);

        if (getResources().getInteger(R.integer.parking) % markerState == 0)
            setMarkerVisibility(parking_Markers, visibility);

        if (getResources().getInteger(R.integer.bicycle) % markerState == 0)
            setMarkerVisibility(bicycle_Markers, visibility);
    }

    void setMarkerVisibility(List<Marker> markers, boolean visibility) {
        for (Marker marker : markers)
            marker.setVisible(visibility);
    }


    /*플로팅 버튼 */

    private void closeFloatingMenu() {
        floatingMenu.close(true);
        floatingMenu.getMenuIconView().setImageResource(R.drawable.icon_pin_white);
        frame.setBackgroundColor(Color.parseColor("#00FFFFFF"));

    }

    private void openFloatingMenu() {
        floatingMenu.open(true);
        floatingMenu.getMenuIconView().setImageResource(R.drawable.ic_close);
        frame.setBackgroundColor(Color.parseColor("#BB000000"));
    }

    public void excuteWebBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        startActivity(intent);
    }

    /*---- 네이버 지도 커스텀 ----*/

    private void changeObjectVisibility() {
        if (locationButtonView.isShown()) {
            locationButtonView.setVisibility(View.GONE);
            zoomControlView.setVisibility(View.GONE);
            floatingMenu.setVisibility(View.GONE);
            parent_fragment_button.setVisibility(View.GONE);

        } else {
            locationButtonView.setVisibility(View.VISIBLE);
            zoomControlView.setVisibility(View.VISIBLE);
            floatingMenu.setVisibility(View.VISIBLE);
            parent_fragment_button.setVisibility(View.VISIBLE);
        }
    }

    class MarkerClick implements Overlay.OnClickListener { // 마커 클릭을 위한 리스너

        @Override
        public boolean onClick(@NonNull Overlay overlay) {
            Marker marker = (Marker) overlay;

            if (marker.getInfoWindow() == null) {
                infoWindow.open(marker);
                renameInfoWindow(infoWindow, ((String[]) marker.getTag())[1]);
                naverMap.setCameraPosition(new CameraPosition(marker.getPosition(), 16));

            } else {

                infoWindow.close();
            }

            return true;
        }
    }

    class InfowindowClick implements Overlay.OnClickListener { // 정보창 클릭을 위한 리스너

        @Override
        public boolean onClick(@NonNull Overlay overlay) {
            InfoWindow infoWindow = (InfoWindow) overlay;
            Marker marker = infoWindow.getMarker();
            String[] information = (String[]) marker.getTag();
            Toast.makeText(getContext(), information[0], Toast.LENGTH_LONG).show();

            if (information[0].equals(BOTANIC_CULTURE_CENTER)) {
                parent_fragment_button.callOnClick();
                return true;
            }

            if (information[0].equals(GREEN_HOUSE)) {
                excuteWebBrowser("http://botanicpark.seoul.go.kr/front/img/greenhouse_ripplet_02.pdf");
                return true;
            }

            if (information[0].equals(VISITOR_INFO)) {
                excuteWebBrowser("http://botanicpark.seoul.go.kr/front/img/%EC%95%88%EB%82%B4%EB%8F%84.pdf");
                return true;
            }

            Intent intent = new Intent(getActivity(), Facilities_information.class);
            intent.putExtra("information", information);
            startActivity(intent);

            return true;
        }
    }

    class TrackingModeListener extends FusedLocationSource { // 위치 정보 클래스 오버라이드

        public TrackingModeListener(@NonNull Fragment fragment, int permissionRequestCode) {
            super(fragment, permissionRequestCode);
        }

        @Override
        public void deactivate() {  // 현재 위치 해제 시에 피벗 위치로 카메라 이동
            naverMap.setCameraPosition(new CameraPosition(new LatLng(37.5702109, 126.8318991), 14.3));
        }
    }

    class naverMapClick implements NaverMap.OnMapClickListener { // 맵 클릭을 위한 리스너

        @Override
        public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
            if (infoWindow.getMarker() != null) infoWindow.close();

            else {
                mainActivity.setCurveBottomBarVisibility();
                changeObjectVisibility();
            }
        }
    }

    class floatingMenuClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int newState = Integer.parseInt(v.getTag().toString());
            if (markerState == newState) return;
            selectMarkers(false);
            markerState = newState;
            selectMarkers(true);
            closeFloatingMenu();
        }
    }


}
