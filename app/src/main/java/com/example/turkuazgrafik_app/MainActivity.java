package com.example.turkuazgrafik_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private TextView mTextViewResult;
    private RequestQueue mQueue;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.vpPager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));

        viewPager.addOnPageChangeListener(this);


        mQueue = Volley.newRequestQueue(this);

        jsonParse();


    }

    private void jsonParse() {
        String url = "http://covid.discountr.info/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String agir_hasta_sayisi = response.getString("agir_hasta_sayisi");
                            String gunluk_hasta = response.getString("gunluk_hasta");
                            String gunluk_iyilesen = response.getString("gunluk_iyilesen");
                            String gunluk_test = response.getString("gunluk_test");
                            String gunluk_vaka = response.getString("gunluk_vaka");
                            String gunluk_vefat = response.getString("gunluk_vefat");
                            String ortalama_temasli_tespit_suresi = response.getString("ortalama_temasli_tespit_suresi");
                            String tarih = response.getString("tarih");
                            String toplam_hasta = response.getString("toplam_hasta");
                            String firstName = response.getString("agir_hasta_sayisi");
                            String toplam_iyilesen = response.getString("toplam_iyilesen");
                            String toplam_test = response.getString("toplam_test");
                            String toplam_vefat = response.getString("toplam_vefat");


//                            mTextViewResult.append(agir_hasta_sayisi+ " " +gunluk_hasta);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

//        mQueue.add(request);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}