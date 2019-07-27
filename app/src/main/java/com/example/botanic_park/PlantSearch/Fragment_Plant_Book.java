package com.example.botanic_park.PlantSearch;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.botanic_park.MainActivity;
import com.example.botanic_park.PermissionCheck;
import com.example.botanic_park.R;
import com.example.botanic_park.SSLConnect;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Fragment_Plant_Book extends Fragment {
    public static final int PERMISSION_REQUEST_CODE = 0;

    public static final String PLANT_LIST_KEY = "plant list";
    public static final String SELECTED_ITEM_KEY = "selected item";

    PlantBookExpandableGridView plantBookGridView;
    ArrayList<PlantBookItem> list;

    boolean isGetAllPermissons = true;

    public Fragment_Plant_Book() {
    }

    public static Fragment_Plant_Book newInstance(ArrayList<PlantBookItem> list) {
        Fragment_Plant_Book fragment = new Fragment_Plant_Book();
        Bundle args = new Bundle();
        args.putSerializable(PLANT_LIST_KEY, list);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.list = (ArrayList<PlantBookItem>) getArguments().getSerializable(PLANT_LIST_KEY);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_book, container, false);

        ImageButton cameraSearchBtn = view.findViewById(R.id.camera_search_btn);
        cameraSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] permissions = {
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                ArrayList<String> ungranted_permissions = new ArrayList<>();

                for(String permission : permissions){
                    if(!PermissionCheck.isGrantedPermission(getActivity(), permission))
                        ungranted_permissions.add(permission);
                }


                if(ungranted_permissions.size() > 0) {
                    // 권한 요청
                    PermissionCheck.requestPermissions(getActivity(),
                            ungranted_permissions.toArray(new String[ungranted_permissions.size()]), PERMISSION_REQUEST_CODE);
                } else{
                    startCameraActivity();
                }
            }
        });

        EditText editText = view.findViewById(R.id.search_edit_text);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                switch (i) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 검색 동작
                        Intent intent = new Intent();
                        break;
                    default:
                        // 기본 엔터키 동작
                        return false;
                }
                return true;
            }
        });

        plantBookGridView = view.findViewById(R.id.gridview_plant_book);

        PlantBookAdapter plantBookAdapter = new PlantBookAdapter(getContext(), R.layout.item_plant_preview, list);
        plantBookGridView.setAdapter(plantBookAdapter); // 어댑터를 그리드 뷰에 적용
        plantBookGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 아이템 클릭시 상세 페이지로 넘어감
                Intent intent = new Intent(getContext(), DetailPopUpActivity.class);
                intent.putExtra(SELECTED_ITEM_KEY, list.get(i));
                startActivity(intent);
            }
        });

        return view;
    }

    private void startCameraActivity(){
        Intent intent = new Intent(getActivity(), CameraSearchActivity.class);
        startActivity(intent);   // 카메라 액티비티 띄움
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (PermissionCheck.verifyPermission(grantResults)) {
                // 동의 했을 경우
                startCameraActivity();
            } else {
                // 거부 했을 경우
                Toast.makeText(getContext(), "권한 동의가 필요합니다.", Toast.LENGTH_SHORT).show();

            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
/*
// 권한 체크, 요청을 담당
class PermissionCheck {
    public static final int PERMISSION_REQUEST_CODE = 0;

    public static boolean isGrantedPermission(Activity activity, String permission) {
        int permissionResult = ActivityCompat.checkSelfPermission(activity, permission);
        if (permissionResult == PackageManager.PERMISSION_GRANTED)
            return true;

        return false;
    }

    public static void requestPermissions(Activity activity, String[] permissions) {
        ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE);
    }

    public static boolean verifyPermission(int[] grantResults) {
        // 모든 권한이 허가 받았는지 확인
        if (grantResults.length < 1) {
            return false;
        }
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }
}
*/
class PlantBookAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<PlantBookItem> list;  // item 목록
    LayoutInflater layoutInflater;

    View itemView;          // item이 뿌려질 뷰
    PlantBookItem item;     // item 정보 객체
    Bitmap bitmap;          // item 안에 들어가는 이미지
    Drawable drawable;

    public PlantBookAdapter(Context context, int layout, ArrayList<PlantBookItem> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;

        layoutInflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = layoutInflater.inflate(layout, null);

        itemView = view;
        item = list.get(i);
        //Log.d("debug output", item.toString());

        ImageView imageView = view.findViewById(R.id.image_thumb);
        //Glide.with(view).load(item.getImg_url()).thumbnail(0.1f).into(imageView);

        TextView koNameView = view.findViewById(R.id.name_ko);
        koNameView.setText(item.getName_ko());


        return view;

    }

}

