package com.example.turkuazgrafik_app;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Toplam  extends Fragment {
    private String baslik;

    public static BuHafta newInstance(String baslik) {
        Bundle args = new Bundle();
        args.putString("BASLIK", baslik);
        BuHafta fragment = new BuHafta();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baslik = getArguments().getString("BASLIK");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.haftasayfa, container, false);
        TextView title = view.findViewById(R.id.tvBaslik);
        title.setText(baslik);
        return view;
    }
}