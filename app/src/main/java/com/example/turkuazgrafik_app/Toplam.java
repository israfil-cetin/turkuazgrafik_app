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
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.toplamsayfa, container, false);
        TextView txtOne = (TextView) view.findViewById(R.id.tv_toplam_test_yazi);
        txtOne.setText(((MainActivity)getActivity()).toplam_test);

        TextView txtTwo = (TextView) view.findViewById(R.id.tv_toplam_hasta_yazi);
        txtTwo.setText(((MainActivity)getActivity()).toplam_hasta);

        TextView txtTre = (TextView) view.findViewById(R.id.tv_toplam_vefat_yazi);
        txtTre.setText(((MainActivity)getActivity()).toplam_vefat);

        TextView txtFour = (TextView) view.findViewById(R.id.tv_agir_hasta_sayisi_yazi);
        txtFour.setText(((MainActivity)getActivity()).agir_hasta_sayisi);

        TextView txtFive = (TextView) view.findViewById(R.id.tv_toplam_iyilesen_yazi);
        txtFive.setText(((MainActivity)getActivity()).toplam_iyilesen);
        return view;
    }
}
