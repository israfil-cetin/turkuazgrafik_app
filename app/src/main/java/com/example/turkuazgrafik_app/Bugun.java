package com.example.turkuazgrafik_app;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Bugun extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View returnView = inflater.inflate(R.layout.bugunsayfa, container, false);
        TextView txtOne = (TextView) returnView.findViewById(R.id.tv_hasta_sayisi_yazi);
        txtOne.setText(((MainActivity)getActivity()).agir_hasta_sayisi);

        return returnView;
    }


}