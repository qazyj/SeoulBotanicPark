package com.example.botanic_park.PlantSearch;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.botanic_park.AppManager;
import com.example.botanic_park.R;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/* 식물 검색 결과 액티비티 */
public class SearchResultActivity extends AppCompatActivity {
    SearchWordAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<String> searchWordList;

    ArrayList<PlantBookItem> list;          // 전체 식물 리스트
    ArrayList<PlantBookItem> searchList;    // 검색결과 저장 리스트

    LinearLayout noResult;  // 결과 없음 메세지
    LinearLayout resultItem;    // 이미지 검색 시 결과 아이템

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        searchWordList = (ArrayList<String>) intent.getSerializableExtra(Fragment_Plant_Book.SEARCH_WORD_KEY);

        list = AppManager.getInstance().getList();
        searchList = new ArrayList<PlantBookItem>();
        //getSearchResult(searchWordList.get(0));

        Log.d("테스트", searchWordList.size() + "");
        for(int i=0; i<searchWordList.size(); i++){
            Log.d("테스트", i + ": " + searchWordList.get(i));
            getSearchResult(searchWordList.get(i));
        }


        noResult = findViewById(R.id.no_result_message);
        resultItem = findViewById(R.id.content_search_result);
        showSearchResult();

        recyclerView = findViewById(R.id.searchword_recyclerView);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new SearchWordAdapter(searchWordList, this, itemClickListener);
        recyclerView.setAdapter(adapter);

        ItemDecoration decoration = new ItemDecoration();
        recyclerView.addItemDecoration(decoration);
    }

    private View.OnClickListener itemClickListener = new View.OnClickListener() {
        // 검색어 해시태그 클릭 리스너
        @Override
        public void onClick(View view) {
            String name = (String) view.getTag();

            // 웹에 검색창 띄움
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, name);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                // 설치된 웹브라우저가 없는 경우
                Toast.makeText(getApplicationContext(), "설치된 웹 브라우저가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void getSearchResult(String searchword) {
        // 텍스트 검색 결과 리스트
        if (searchword == null)
            return;

        // 단어 분할
        String[] words = searchword.split(" ");
        for (PlantBookItem item : list) {
            for (String word : words) {
                if (item.getName_ko().contains(word)
                        || item.getName_en().contains(word)
                        || item.getName_sc().contains(word)) {
                    searchList.add(item);
                    //searchWordList.add(searchList.size()-1, item.getName_ko());  // 텍스트 검색은 검색 결과 넣어줌
                }
            }
        }
    }

    private void showSearchResult() {
        if (searchList.size() <= 0) {   // 검색 결과 없음 메시지 보여줌
            noResult.setVisibility(View.VISIBLE);
            resultItem.setVisibility(View.GONE);
            return;
        } else {
            imageSearchResult();
        }
    }

    private void imageSearchResult() {
        resultItem.setVisibility(View.VISIBLE);
        noResult.setVisibility(View.GONE);

        PlantBookItem item = searchList.get(0);
        setData(item); // 첫번재 결과만 보여줌

        addToPlantBook(item);   // 도감에 등록
    }

    private void addToPlantBook(PlantBookItem plantBookItem) {
        ArrayList<PlantBookItem> list = AppManager.getInstance().getList();
        ArrayList<PlantBookItem> plantsToday = AppManager.getInstance().getPlantsToday();

        for (PlantBookItem item : list) {
            if (item.equals(plantBookItem)) {
                if (!item.isCollected()) {
                    item.setCollected(true);
                    Toast.makeText(getApplicationContext(), "도감에 등록되었습니다.", Toast.LENGTH_SHORT).show();
                }
                if (plantsToday.contains(item)) {
                    int index = plantsToday.indexOf(item);
                    plantsToday.get(index).setCollected(true);
                    Toast.makeText(getApplicationContext(), "오늘의 식물을 획득하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        // 앱메니저에 반영 (굳이 해야하나 잘 모르겠다)
        AppManager.getInstance().setList(list);
        AppManager.getInstance().setPlantsToday(plantsToday);
    }

    private void setData(PlantBookItem selectedItem) {
        // 이미지 결과 검색 보여줌
        TextView name_ko = findViewById(R.id.content_name);
        name_ko.setText(selectedItem.getName_ko());

        TextView name_sc = findViewById(R.id.content_scientific_name);
        name_sc.setText(selectedItem.getName_sc());

        TextView name_en = findViewById(R.id.content_name_en);
        name_en.setText(selectedItem.getName_en());

        TextView type = findViewById(R.id.content_type);
        type.setText(selectedItem.getType());

        TextView blossom = findViewById(R.id.content_blossom);
        blossom.setText(selectedItem.getBlossom());

        TextView details = findViewById(R.id.details);
        details.setText(selectedItem.getDetails());

        ImageView imageView = findViewById(R.id.image_detail);
        try {
            Field field = R.drawable.class.getField("species_" + selectedItem.getId());
            int drawableID = field.getInt(null);

            MultiTransformation multi = new MultiTransformation(
                    new CenterCrop(),
                    new RoundedCornersTransformation(45, 0,
                            RoundedCornersTransformation.CornerType.TOP)
            );
            Glide.with(imageView)
                    .load(drawableID)
                    .apply(bitmapTransform(multi))
                    .thumbnail(0.1f)
                    .into(imageView);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    class ItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1)
                outRect.right = 10; // 맨 마지막 아이템만 제외하고 margin 줌
        }
    }
}

class SearchWordAdapter extends RecyclerView.Adapter<SearchWordAdapter.ViewHolder> {
    private ArrayList<String> itemList;
    private Context context;
    private View.OnClickListener onClickListener;

    public SearchWordAdapter(ArrayList<String> itemList, Context context, View.OnClickListener onClickListener) {
        this.itemList = itemList;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_search_word, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String item = itemList.get(i);

        viewHolder.textView.setText("# " + item);
        viewHolder.textView.setTag(item);
        viewHolder.textView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.searchword_textView);
        }
    }
}




