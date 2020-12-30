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


        TextView txtOne = (TextView) returnView.findViewById(R.id.tv_test_sayisi_yazi);
        txtOne.setText(((MainActivity)getActivity()).gunluk_test);

        TextView txtTwo = (TextView) returnView.findViewById(R.id.tv_vaka_sayisi_yazi);
        txtTwo.setText(((MainActivity)getActivity()).gunluk_vaka);

        TextView txtTre = (TextView) returnView.findViewById(R.id.tv_hasta_sayisi_yazi);
        txtTre.setText(((MainActivity)getActivity()).gunluk_hasta);

        TextView txtFour = (TextView) returnView.findViewById(R.id.tv_vefat_sayisi_yazi);
        txtFour.setText(((MainActivity)getActivity()).gunluk_vefat);

        TextView txtFive = (TextView) returnView.findViewById(R.id.tv_iyilesen_sayisi_yazi);
        txtFive.setText(((MainActivity)getActivity()).gunluk_iyilesen);

        return returnView;
    }


}