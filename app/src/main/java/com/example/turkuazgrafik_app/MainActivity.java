package com.example.turkuazgrafik_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private TextView tvToken;
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    ViewPager viewPager;
    Bugun fragobj;
    BuHafta fragobj2;
    Toplam fragobj3;
    String agir_hasta_sayisi;
    String gunluk_hasta;
    String gunluk_iyilesen ;
    String gunluk_test;
    String gunluk_vaka ;
    String gunluk_vefat ;
    String ortalama_temasli_tespit_suresi ;
    String tarih ;
    String toplam_hasta ;
    String toplam_iyilesen ;
    String toplam_test ;
    String toplam_vefat ;
    String hastalarda_zaturre_oran ;
    String yatak_doluluk_orani ;
    String eriskin_yogun_bakim_doluluk_orani ;
    String ventilator_doluluk_orani ;
    String filyasyon_orani ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);






        tvToken = findViewById(R.id.mytoken);

        MyReceiver receiver = new MyReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.example.turkuazgrafik_app.ON_NEW_TOKEN");
        MainActivity.this.registerReceiver(receiver,filter);


        viewPager = findViewById(R.id.vpPager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));

        viewPager.addOnPageChangeListener(this);




        volleyGet();


    }

    public void volleyPost(String token){
        String postUrl = "https://icetin.pythonanywhere.com/api/task-create/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("title", token);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
    public void volleyGet(){
        String url = "https://covid.discountr.info/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    agir_hasta_sayisi = response.getString("agir_hasta_sayisi");
                    gunluk_hasta = response.getString("gunluk_hasta");
                    gunluk_iyilesen = response.getString("gunluk_iyilesen");
                    gunluk_test = response.getString("gunluk_test");
                    gunluk_vaka = response.getString("gunluk_vaka");
                    gunluk_vefat = response.getString("gunluk_vefat");
                    ortalama_temasli_tespit_suresi = response.getString("ortalama_temasli_tespit_suresi");
                    tarih = response.getString("tarih");
                    toplam_hasta = response.getString("toplam_hasta");
                    toplam_iyilesen = response.getString("toplam_iyilesen");
                    toplam_test = response.getString("toplam_test");
                    toplam_vefat = response.getString("toplam_vefat");
                    hastalarda_zaturre_oran = response.getString("hastalarda_zaturre_oran");
                    yatak_doluluk_orani = response.getString("yatak_doluluk_orani");
                    eriskin_yogun_bakim_doluluk_orani = response.getString("eriskin_yogun_bakim_doluluk_orani");
                    ventilator_doluluk_orani = response.getString("ventilator_doluluk_orani");
                    filyasyon_orani = response.getString("filyasyon_orani");


                    Bundle bundle = new Bundle();
                    bundle.putString("gunluk_test", gunluk_test);
                    bundle.putString("gunluk_vaka", gunluk_vaka);
                    bundle.putString("gunluk_hasta", gunluk_hasta);
                    bundle.putString("gunluk_vefat", gunluk_vefat);
                    bundle.putString("gunluk_iyilesen", gunluk_iyilesen);

                    Bundle bundle2 = new Bundle();
                    bundle2.putString("hastalarda_zaturre_oran", hastalarda_zaturre_oran);
                    bundle2.putString("yatak_doluluk_orani", yatak_doluluk_orani);
                    bundle2.putString("eriskin_yogun_bakim_doluluk_orani", eriskin_yogun_bakim_doluluk_orani);
                    bundle2.putString("ventilator_doluluk_orani", ventilator_doluluk_orani);
                    bundle2.putString("ortalama_temasli_tespit_suresi", ortalama_temasli_tespit_suresi);
                    bundle2.putString("filyasyon_orani", filyasyon_orani);

                    Bundle bundle3 = new Bundle();
                    bundle3.putString("toplam_test", toplam_test);
                    bundle3.putString("toplam_hasta", toplam_hasta);
                    bundle3.putString("toplam_vefat", toplam_vefat);
                    bundle3.putString("agir_hasta_sayisi", agir_hasta_sayisi);
                    bundle3.putString("toplam_iyilesen", toplam_iyilesen);


                    // set Fragmentclass Arguments
                    fragobj = new Bugun();
                    fragobj.setArguments(bundle);
                    fragobj2 = new BuHafta();
                    fragobj2.setArguments(bundle2);

                    fragobj3 = new Toplam();
                    fragobj3.setArguments(bundle3);



                    ////Burası container'a gönderiyor. Yoksa sayfa boş olucaktır.
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, /*id of your frame layout*/
                                    fragobj /*instance of the fragment created in your activity*/)

                            .commit();

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container2, /*id of your frame layout*/
                                    fragobj2 /*instance of the fragment created in your activity*/)
                            .commit();

//                    getSupportFragmentManager().beginTransaction()
//                            .add(R.id.fragment_container3, /*id of your frame layout*/
//                                    fragobj3 /*instance of the fragment created in your activity*/)
//                            .commit();




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

        mQueue.add(request);
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

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.example.turkuazgrafik_app.ON_NEW_TOKEN".equals(intent.getAction())) {
                String token = intent.getStringExtra("token");
                tvToken.setText(token);
                volleyPost(token);
            }
        }
    }
}



