package com.example.botanic_park;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

/* 식물 도감 아이템에 적용되는 레이아웃 */

class PlantBookItemLayout extends LinearLayout {

    public PlantBookItemLayout(Context context) {
        super(context);
    }

    public PlantBookItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlantBookItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlantBookItemLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 너비에 맞춰 정사각형으로 만듦
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

}

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

    public PlantBookExpandableGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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