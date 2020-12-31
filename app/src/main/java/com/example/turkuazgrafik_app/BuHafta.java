package com.example.turkuazgrafik_app;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

    public class BuHafta  extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.haftasayfa, container, false);

            TextView txtOne = (TextView) view.findViewById(R.id.tv_has_zat_yazi);
            txtOne.setText(((MainActivity)getActivity()).hastalarda_zaturre_oran);

            TextView txtTwo = (TextView) view.findViewById(R.id.tv_yat_dol_yazi);
            txtTwo.setText(((MainActivity)getActivity()).yatak_doluluk_orani);

            TextView txtTre = (TextView) view.findViewById(R.id.tv_eris_yog_yazi);
            txtTre.setText(((MainActivity)getActivity()).eriskin_yogun_bakim_doluluk_orani);

            TextView txtFour = (TextView) view.findViewById(R.id.tv_vent_dol_yazi);
            txtFour.setText(((MainActivity)getActivity()).ventilator_doluluk_orani);

            TextView txtFive = (TextView) view.findViewById(R.id.tv_ort_tem_yazi);
            txtFive.setText(((MainActivity)getActivity()).ortalama_temasli_tespit_suresi);

            TextView txSix = (TextView) view.findViewById(R.id.tv_fililasyon_yazi);
            txSix.setText(((MainActivity)getActivity()).filyasyon_orani);
            return view;
        }
    }
