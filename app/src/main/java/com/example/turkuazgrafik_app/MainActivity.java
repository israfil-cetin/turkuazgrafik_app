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
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.huawei.hms.analytics.HiAnalytics;
import com.huawei.hms.analytics.HiAnalyticsInstance;
import com.huawei.hms.analytics.HiAnalyticsTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private TextView mTextViewResult;
    private RequestQueue mQueue;



    // lazy for java , these are for animations
    private Animation retateOpen;

    public synchronized Animation retateOpenMet() {
        if (retateOpen == null) {
            retateOpen = new AnimationUtils().loadAnimation(this, R.anim.rotate_open_anim);
        }
        return retateOpen;
    }

    private Animation retateClose;

    public synchronized Animation retateCloseMet() {
        if (retateClose == null) {
            retateClose = new AnimationUtils().loadAnimation(this, R.anim.rotate_close_anim);
        }
        return retateClose;
    }



    private Animation fromBottom;

    public synchronized Animation fromBottomMet() {
        if (fromBottom == null) {
            fromBottom = new AnimationUtils().loadAnimation(this, R.anim.from_botom_anim);
        }
        return fromBottom;
    }

    private Animation toBottom;

    public synchronized Animation toBottomMet() {
        if (toBottom == null) {
            toBottom = new AnimationUtils().loadAnimation(this, R.anim.to_bottom_anim);
        }
        return toBottom;
    }

    HiAnalyticsInstance instance;

    private TextView today_time;
    ViewPager viewPager;
    Bugun fragobj;
    BuHafta fragobj2;
    Toplam fragobj3;

    String agir_hasta_sayisi;
    String gunluk_hasta;
    String gunluk_iyilesen;
    String gunluk_test;
    String gunluk_vaka;
    String gunluk_vefat;
    String ortalama_temasli_tespit_suresi;
    String tarih;
    String toplam_hasta;
    String toplam_iyilesen;
    String toplam_test;
    String toplam_vefat;
    String hastalarda_zaturre_oran;
    String yatak_doluluk_orani;
    String eriskin_yogun_bakim_doluluk_orani;
    String ventilator_doluluk_orani;
    String filyasyon_orani;

    boolean clicked;

    FloatingActionButton btn_add;
    FloatingActionButton btn_refresh;
    FloatingActionButton btn_twitter;
    FloatingActionButton btn_telegram;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //volley req queue
        mQueue = Volley.newRequestQueue(this);



        // float buttons
        btn_add = findViewById(R.id.add_btn);
        btn_refresh = findViewById(R.id.refresh_btn);
        btn_twitter = findViewById(R.id.twitter_btn);
        btn_telegram = findViewById(R.id.telegram_btn);

        btn_add.setOnClickListener(this);
        btn_refresh.setOnClickListener(this);
        btn_twitter.setOnClickListener(this);
        btn_telegram.setOnClickListener(this);
        // float buttons



        // analytic kit
        HiAnalyticsTools.enableLog();
        instance = HiAnalytics.getInstance(this);
        instance.setAnalyticsEnabled(true);

        instance.setAutoCollectionEnabled(true);


        Bundle bundle = new Bundle();
        bundle.putString("user_AAID",String.valueOf(instance.getAAID()));
        instance.onEvent("begin_examination", bundle);
        // analytic kit


        today_time = findViewById(R.id.today_time);

        // push kit
        MyReceiver receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.turkuazgrafik_app.ON_NEW_TOKEN");
        MainActivity.this.registerReceiver(receiver, filter);
        // push kit


        // vppager fragment
        viewPager = findViewById(R.id.vpPager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));

        viewPager.addOnPageChangeListener(this);
        // vppager fragment


        // get data from https://covid19.saglik.gov.tr/
        volleyGet();



    }

    private static String format(String s) {
        if (s.length() <= 3)
            return s;
        int first = (s.length() - 1) % 3 + 1;
        StringBuilder buf = new StringBuilder(s.substring(0, first));
        for (int i = first; i < s.length(); i += 3)
            buf.append('.').append(s.substring(i, i + 3));
        return buf.toString();
    }

    // this func. post request to pythoneverywhere.com server
    // it was the api server writed by me with python
    public void volleyPost(String token) {
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

    public void volleyGet() {
        String url = "https://covid.discountr.info/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    agir_hasta_sayisi = response.getString("agir_hasta_sayisi");
                    gunluk_hasta = format(response.getString("gunluk_hasta"));
                    gunluk_iyilesen = format(response.getString("gunluk_iyilesen"));
                    gunluk_test = format(response.getString("gunluk_test"));
                    gunluk_vaka = format(response.getString("gunluk_vaka"));
                    gunluk_vefat = format(response.getString("gunluk_vefat"));
                    ortalama_temasli_tespit_suresi = response.getString("ortalama_temasli_tespit_suresi") + " Saat";
                    tarih = response.getString("tarih");
                    toplam_hasta = format(response.getString("toplam_hasta"));
                    toplam_iyilesen = format(response.getString("toplam_iyilesen"));
                    toplam_test = format(response.getString("toplam_test"));
                    toplam_vefat = format(response.getString("toplam_vefat"));
                    hastalarda_zaturre_oran = "%" + response.getString("hastalarda_zaturre_oran").replace(".", ",");
                    yatak_doluluk_orani = "%" + response.getString("yatak_doluluk_orani").replace(".", ",");
                    eriskin_yogun_bakim_doluluk_orani = "%" + response.getString("eriskin_yogun_bakim_doluluk_orani").replace(".", ",");
                    ventilator_doluluk_orani = "%" + response.getString("ventilator_doluluk_orani").replace(".", ",");
                    filyasyon_orani = "%" + response.getString("filyasyon_orani").replace(".", ",");

                    today_time.setText(timeformat(tarih));

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

                    // these 2 getsup funcs are for fragments
                    // if these 2 methods do not exist, fragmest won't show datas
                    getsup(1);
                    getsup(2);


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
    // TODO kendime not burası 2. kez yazılmış bir metot değil mi? Burayı düzelt
    public void volleyGet2() {
        String url = "https://covid.discountr.info/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    agir_hasta_sayisi = response.getString("agir_hasta_sayisi");
                    gunluk_hasta = format(response.getString("gunluk_hasta"));
                    gunluk_iyilesen = format(response.getString("gunluk_iyilesen"));
                    gunluk_test = format(response.getString("gunluk_test"));
                    gunluk_vaka = format(response.getString("gunluk_vaka"));
                    gunluk_vefat = format(response.getString("gunluk_vefat"));
                    ortalama_temasli_tespit_suresi = response.getString("ortalama_temasli_tespit_suresi") + " Saat";
                    tarih = response.getString("tarih");
                    toplam_hasta = format(response.getString("toplam_hasta"));
                    toplam_iyilesen = format(response.getString("toplam_iyilesen"));
                    toplam_test = format(response.getString("toplam_test"));
                    toplam_vefat = format(response.getString("toplam_vefat"));
                    hastalarda_zaturre_oran = "%" + response.getString("hastalarda_zaturre_oran").replace(".", ",");
                    yatak_doluluk_orani = "%" + response.getString("yatak_doluluk_orani").replace(".", ",");
                    eriskin_yogun_bakim_doluluk_orani = "%" + response.getString("eriskin_yogun_bakim_doluluk_orani").replace(".", ",");
                    ventilator_doluluk_orani = "%" + response.getString("ventilator_doluluk_orani").replace(".", ",");
                    filyasyon_orani = "%" + response.getString("filyasyon_orani").replace(".", ",");

                    today_time.setText(timeformat(tarih));

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

                    Log.d("test2",ventilator_doluluk_orani);



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

    // this converts iso time format to the simple time format
    public String timeformat(String dtStart) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        try {

            Date d = sd.parse(dtStart);
            sd = new SimpleDateFormat("dd MM yyyy");
            return sd.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // for framelayout SupportFragmentManager, it shows to data
    public void getsup(int expression) {

        switch (expression) {
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, /*id of your frame layout*/
                                fragobj /*instance of the fragment created in your activity*/)

                        .commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container2, /*id of your frame layout*/
                                fragobj2 /*instance of the fragment created in your activity*/)
                        .commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container3, /*id of your frame layout*/
                                fragobj3 /*instance of the fragment created in your activity*/)
                        .commit();
                break;

        }


    }

    // Float button's Animation methods
    private void  onAddButtonClicked(){
        setVisiblty(clicked);
        setAnimation(clicked);
        setClickable(clicked);
        clicked = !clicked;
    }

    private void  setVisiblty(boolean clicked){

        if (!clicked){
            btn_refresh.setVisibility(View.VISIBLE);
            btn_twitter.setVisibility(View.VISIBLE);
            btn_telegram.setVisibility(View.VISIBLE);
        } else {
            btn_refresh.setVisibility(View.INVISIBLE);
            btn_twitter.setVisibility(View.INVISIBLE);
            btn_telegram.setVisibility(View.INVISIBLE);
        }
    }

    private void  setAnimation(boolean clicked){

        if (!clicked){

            btn_refresh.startAnimation(fromBottomMet());
            btn_twitter.startAnimation(fromBottomMet());
            btn_telegram.startAnimation(fromBottomMet());
            btn_add.startAnimation(retateOpenMet());
        }else{
            btn_refresh.startAnimation(toBottomMet());
            btn_twitter.startAnimation(toBottomMet());
            btn_telegram.startAnimation(toBottomMet());
            btn_add.startAnimation(retateCloseMet());
        }

    }
    private void setClickable(boolean clicked){
        if (!clicked){


            btn_twitter.setClickable(true);
            btn_refresh.setClickable(true);
            btn_telegram.setClickable(true);

        }else{
            btn_twitter.setClickable(false);
            btn_refresh.setClickable(false);
            btn_telegram.setClickable(false);
        }
    }
    // Float button's Animation methods



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:
//                Toast.makeText(this, "Button 1 Clicked", Toast.LENGTH_SHORT).show();
                onAddButtonClicked();
                break;
            case R.id.refresh_btn:
                volleyGet2();
                break;
            case R.id.twitter_btn:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/trturkuazgrafik"));
                startActivity(browserIntent);
                break;
            case R.id.telegram_btn:
                Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/turkuazgrafik"));
                startActivity(browserIntent2);
                break;
        }
    }


    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.example.turkuazgrafik_app.ON_NEW_TOKEN".equals(intent.getAction())) {
                String token = intent.getStringExtra("token");
//                tvToken.setText(token);
                volleyPost(token);
            }
        }
    }
}



