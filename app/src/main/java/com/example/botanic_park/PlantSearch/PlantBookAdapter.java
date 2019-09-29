package com.example.botanic_park.PlantSearch;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.botanic_park.R;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

import java.lang.reflect.Field;
import java.util.List;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class
PlantBookAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<PlantBookItem> list;  // item 목록
    LayoutInflater layoutInflater;

    PlantBookItem item;     // item 정보 객체

    public PlantBookAdapter(Context context, int layout, List<PlantBookItem> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

    }

    public void updateAdpater(List<PlantBookItem> list) {
        this.list = list;
        this.notifyDataSetChanged();
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
        TextView textView = view.findViewById(R.id.name_ko);

        try {
            Field field = R.drawable.class.getField("species_" + item.getId());
            int drawableID = field.getInt(null);

            if (item.isCollected()) {
                textView.setTextColor(view.getResources().getColor(R.color.colorNormalText));
                MultiTransformation multi = new MultiTransformation(
                        new CircleCrop()
                );
                view.setBackground(ContextCompat.getDrawable(context, R.drawable.border_active));
                Glide.with(view)
                        .load(drawableID)
                        .apply(bitmapTransform(multi))
                        .thumbnail(0.1f).into(imageView);
            } else {
                textView.setTextColor(view.getResources().getColor(R.color.no));
                MultiTransformation multi = new MultiTransformation(
                        new CircleCrop(),
                        new ColorFilterTransformation(
                                Color.argb(200, 210, 210, 210))
                );
                view.setBackground(ContextCompat.getDrawable(context, R.drawable.border_normal));
                Glide.with(view)
                        .load(drawableID)
                        .apply(bitmapTransform(multi))
                        .thumbnail(0.1f).into(imageView);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        TextView koNameView = view.findViewById(R.id.name_ko);
        koNameView.setText(item.getName_ko());

        return view;
    }

}