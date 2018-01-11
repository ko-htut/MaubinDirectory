package com.example.dell.maubindirectory.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.maubindirectory.Object.Pagoda;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.adapter.Pagoda_adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 12/08/2017.
 */

public class List_pagoda extends AppCompatActivity {

    RecyclerView recyclerView;

    Pagoda_adapter pagoda_adapter;
    ArrayList<Pagoda> pagodaList;
    Toolbar toolbar;

    SwipeRefreshLayout srf;

    final String JSONURL = "http://maubindirectory.000webhostapp.com/pagoda/pagoda.json";
    RequestQueue rq;
    ArrayList<Uri> uri_list;
    Pagoda pagoda;

    ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = (Toolbar) findViewById(R.id.pagoda_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        sp.setVisibility(View.GONE);
        pagodaList = new ArrayList<>();
        pagoda_adapter = new Pagoda_adapter(this, pagodaList);

        recyclerView = (RecyclerView) findViewById(R.id.pagoda_recycler);

        pd = new ProgressDialog(this);
        uri_list = new ArrayList<>();
        rq = Volley.newRequestQueue(this);

        pd.setMessage("Loading....");
        pd.setCancelable(false);
        pd.show();
        request();
        //pd.dismiss();


        srf = (SwipeRefreshLayout) findViewById(R.id.swipe_pagoda);
        srf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pagodaList.clear();
                uri_list.clear();
                request();
            }
        });


    }

    private void request() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSONURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jo = new JSONObject(response);
                            JSONArray jpagoda_arr = jo.getJSONArray("pagoda");
                            for (int i = 0; i < jpagoda_arr.length(); i++) {
                                JSONObject joo = jpagoda_arr.getJSONObject(i);
                                String name = joo.getString("pagoda_name");
                                JSONArray imageArr = joo.getJSONArray("pagoda_image");
                                for (int j = 0; j < imageArr.length(); j++) {
                                    JSONObject jso = imageArr.getJSONObject(j);
                                    Uri image = Uri.parse(jso.getString("image"));
                                    uri_list.add(image);
                                }
//                                uri_list.add(Uri.parse("http://maubindirectory.000webhostapp.com/pawdawmu.png"));
                                String year = joo.getString("pagoda_year");
                                double lati = joo.getDouble("lati");
                                double lon = joo.getDouble("long");
                                String open = joo.getString("open_time");
                                String close = joo.getString("close_time");
                                String address = joo.getString("address");
                                double height = joo.getDouble("height");

                                pagoda = new Pagoda(name, uri_list, year, lati, lon, open, close, address, height);
                                pagodaList.add(pagoda);
                                uri_list = new ArrayList<>();

                                //Log.i("namefdsf", pagodaList.get(0).getPago_name());
                            }

                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(List_pagoda.this);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(pagoda_adapter);
                            pd.dismiss();
                            srf.setRefreshing(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                            srf.setRefreshing(false);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        srf.setRefreshing(false);
                        new AlertDialog.Builder(List_pagoda.this)
                                .setTitle("Error!")
                                .setMessage("Occur error.\nPlease check your connection.")
                                .setPositiveButton("Try Again",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {

                                                pagodaList.clear();
                                                uri_list.clear();
                                                dialog.dismiss();
                                                pd.show();
                                                request();

                                            }
                                        })
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        List_pagoda.this.finish();
                                    }
                                }).setCancelable(false).show();

                    }
                });

        rq.add(stringRequest);
    }

}

