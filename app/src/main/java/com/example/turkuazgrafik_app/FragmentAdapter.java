package com.example.turkuazgrafik_app;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class FragmentAdapter extends FragmentPagerAdapter {
    ArrayList<String> basliklar;

    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
        basliklar = new ArrayList<>();
        basliklar.add("Bugün");
        basliklar.add("Bu Hafta");
        basliklar.add("Toplam");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: {
                return new Bugun();
            }
            case 1: {
                return new BuHafta();
            }
            case 2: {
                return new Toplam();
            }
            default: {
                return new Bugun();
            }

        }
    }

    @Override
    public int getCount() {
        return basliklar.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return basliklar.get(position);
    }
}
