package com.example.botanic_park.PlantSearch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

/* 식물 도감을 보여주는 그리드뷰 */
class PlantBookExpandableGridView extends GridView{

    public PlantBookExpandableGridView(Context context) {
        super(context);
    }

    public PlantBookExpandableGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlantBookExpandableGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 높이 재조정
        int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();

    }
}

/* 식물 검색 결과를 보여주는 리스트뷰 */
class PlantExpandableListView extends ListView {

    public PlantExpandableListView(Context context) {
        super(context);
    }

    public PlantExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlantExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 높이 재조정
        int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();

    }
}