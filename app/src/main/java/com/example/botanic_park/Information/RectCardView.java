package com.example.botanic_park.Information;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;

public class RectCardView extends CardView {
    public RectCardView(@NonNull Context context) {
        super(context);
    }

    public RectCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RectCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}

class RectImageView extends AppCompatImageView {

    public RectImageView(Context context) {
        super(context);
    }

    public RectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}