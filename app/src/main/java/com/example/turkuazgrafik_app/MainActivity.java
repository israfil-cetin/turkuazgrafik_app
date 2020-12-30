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
    String agir_hasta_sayisi;
    String gunluk_hasta;
    String gunluk_iyilesen ;
    String gunluk_test;
    String gunluk_vaka ;
    String gunluk_vefat ;
    String ortalama_temasli_tespit_suresi ;
    String tarih ;
    String toplam_hasta ;
    String firstName ;
    String toplam_iyilesen ;
    String toplam_test ;
    String toplam_vefat ;




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
        String postUrl = "http://icetin.pythonanywhere.com/api/task-create/";
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
        String url = "http://covid.discountr.info/";
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
                    firstName = response.getString("agir_hasta_sayisi");
                    toplam_iyilesen = response.getString("toplam_iyilesen");
                    toplam_test = response.getString("toplam_test");
                    toplam_vefat = response.getString("toplam_vefat");

                    Bundle bundle = new Bundle();
                    bundle.putString("agir_hasta_sayisi", agir_hasta_sayisi);
                    // set Fragmentclass Arguments
                    fragobj = new Bugun();
                    fragobj.setArguments(bundle);



                    Log.d("tagggg",agir_hasta_sayisi);

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, /*id of your frame layout*/
                                    fragobj /*instance of the fragment created in your activity*/)
                            .commit();




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



