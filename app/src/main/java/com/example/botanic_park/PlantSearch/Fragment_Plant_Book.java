package com.example.botanic_park.PlantSearch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.botanic_park.*;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class Fragment_Plant_Book extends Fragment {
    public static final int PERMISSION_REQUEST_CODE = 2000;
    public static final int START_ACTIVITY_CODE = 3000;

    public static final String SELECTED_ITEM_KEY = "selected item";
    public static final String SEARCH_WORD_KEY = "search word";

    PlantBookExpandableGridView plantBookGridView;
    ArrayList<PlantBookItem> list;

    public Fragment_Plant_Book() {
    }

    public static Fragment_Plant_Book newInstance() {
        Fragment_Plant_Book fragment = new Fragment_Plant_Book();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = AppManager.getInstance().getList();
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
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

                for (String permission : permissions) {
                    if (!PermissionCheck.isGrantedPermission(getActivity(), permission))
                        ungranted_permissions.add(permission);
                }


                if (ungranted_permissions.size() > 0) {
                    // 권한 요청
                    PermissionCheck.requestPermissions(getActivity(),
                            ungranted_permissions.toArray(new String[ungranted_permissions.size()]), PERMISSION_REQUEST_CODE);
                } else {
                    startCameraActivity();
                }
            }
        });

        final EditText editText = view.findViewById(R.id.search_edit_text);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                switch (i) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 텍스트 검색 동작
                        String searchword = String.valueOf(editText.getText());
                        if(searchword.isEmpty()) {
                            Toast.makeText(getContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        } else{
                            ArrayList<String> searchWordList = new ArrayList<>();
                            searchWordList.add(searchword);

                            Intent intent = new Intent(getContext(), SearchResultActivity.class);
                            intent.putExtra(SearchResultActivity.RESULT_TYPE, SearchResultActivity.TEXT_SEARCH);
                            intent.putExtra(SEARCH_WORD_KEY, searchWordList);
                            startActivityForResult(intent, START_ACTIVITY_CODE);  // 검색 결과 리스트 창 띄움
                        }
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        plantBookGridView = view.findViewById(R.id.gridview_plant_book);

        PlantBookAdapter plantBookAdapter = new PlantBookAdapter(getContext(),
                R.layout.item_plant,  list.subList(0,40), PlantBookAdapter.SHOW_ONLY_KOREAN_NAME);
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

    private void startCameraActivity() {
        Intent intent = new Intent(getActivity(), CameraSearchActivity.class);
        startActivityForResult(intent, START_ACTIVITY_CODE);   // 카메라 액티비티 띄움
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 프래그먼트 화면 갱신
        for(Fragment fragment: getFragmentManager().getFragments()){
            if(fragment.isVisible()){
                final FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.detach(fragment).attach(fragment);
                transaction.commit();
                break;
            }
        }
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

class PlantBookAdapter extends BaseAdapter {
    public static final int SHOW_ONLY_KOREAN_NAME = 1;
    public static final int SHOW_ALL_NAME = 2;

    Context context;
    int layout;
    List<PlantBookItem> list;  // item 목록
    LayoutInflater layoutInflater;
    int showType;   // 아이템을 보여주는 방식

    PlantBookItem item;     // item 정보 객체

    public PlantBookAdapter(Context context, int layout, List<PlantBookItem> list, int showType) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.showType = showType;

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

        item = list.get(i);
        ImageView imageView = view.findViewById(R.id.image_thumb);

        try {
            Field field = R.drawable.class.getField("species_" + item.getId());
            int drawableID = field.getInt(null);

            MultiTransformation multi  = new MultiTransformation(
                    new CenterCrop(),
                    new RoundedCornersTransformation(45, 0,
                    RoundedCornersTransformation.CornerType.TOP)
            );

            if(item.isCollected()) {
                view.setBackground(ContextCompat.getDrawable(context, R.drawable.border_active));
                Glide.with(view)
                        .load(drawableID)
                        .apply(bitmapTransform(multi))
                        .thumbnail(0.1f).into(imageView);
            } else {
                view.setBackground(ContextCompat.getDrawable(context, R.drawable.border_normal));
                Glide.with(view)
                        .load(drawableID)
                        .apply(bitmapTransform(new RoundedCornersTransformation(45, 0,
                                RoundedCornersTransformation.CornerType.BOTTOM)))
                        .apply(bitmapTransform(new ColorFilterTransformation(
                                Color.argb(200, 210,210,210))))
                        .thumbnail(0.1f).into(imageView);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        TextView koNameView = view.findViewById(R.id.name_ko);
        koNameView.setText(item.getName_ko());

        // 모든 이름을 보여주는 경우
        if (showType == SHOW_ALL_NAME) {
            TextView enNameView = view.findViewById(R.id.name_en);
            enNameView.setVisibility(View.VISIBLE);
            enNameView.setText(item.getName_en());

            TextView scNameView = view.findViewById(R.id.name_sc);
            scNameView.setVisibility(View.VISIBLE);
            scNameView.setText(item.getName_sc());
        }

        return view;
    }

}

