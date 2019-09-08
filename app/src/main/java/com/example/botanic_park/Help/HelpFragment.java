package com.example.botanic_park.Help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.botanic_park.R;

public class HelpFragment extends Fragment {
    private int page;

    public HelpFragment() {
    }

    public static HelpFragment newInstance(int page) {
        HelpFragment fragment = new HelpFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("page", 0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_help, container, false);

        ImageView imageView = view.findViewById(R.id.image_help);
        Glide.with(view).load(R.drawable.today_plant).centerInside().into(imageView);

        return view;
    }
}
