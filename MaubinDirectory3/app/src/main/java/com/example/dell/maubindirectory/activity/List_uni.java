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
import com.example.dell.maubindirectory.Object.University;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.adapter.University_adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 13/08/2017.
 */

public class List_uni extends AppCompatActivity {
    final String JSONURL = "http://maubindirectory.000webhostapp.com/uni/uni.json";
    RecyclerView recyclerView;
    University_adapter adapter;
    ArrayList<University> uni_list;
    University university;

    SwipeRefreshLayout srf;

    RequestQueue rq;
    ArrayList<Uri> uri_list;

    ProgressDialog pd;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toolbar = (Toolbar) findViewById(R.id.pagoda_toolbar);
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        sp.setVisibility(View.GONE);
        toolbar.setTitle("Universities");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        uni_list = new ArrayList<>();
        adapter = new University_adapter(this, uni_list);
        recyclerView = (RecyclerView) findViewById(R.id.pagoda_recycler);
        pd = new ProgressDialog(this);
        uri_list = new ArrayList<>();
        rq = Volley.newRequestQueue(this);

        pd.setMessage("Loading....");
        pd.setCancelable(false);
        pd.show();
        request();

        srf = (SwipeRefreshLayout) findViewById(R.id.swipe_pagoda);
        srf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                uni_list.clear();
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

                            JSONObject jou = new JSONObject(response);
                            JSONArray jpagoda_arru = jou.getJSONArray("uni");
                            for (int i = 0; i < jpagoda_arru.length(); i++) {
                                JSONObject joo = jpagoda_arru.getJSONObject(i);
                                String name = joo.getString("uni_name");
                                String rector = joo.getString("rector");
                                JSONArray imageArr2 = joo.getJSONArray("uni_image");
                                for (int j = 0; j < imageArr2.length(); j++) {
                                    JSONObject jso = imageArr2.getJSONObject(j);
                                    Uri image = Uri.parse(jso.getString("imageu"));
                                    uri_list.add(image);
                                }
                                String phone = joo.getString("phone");
                                String firstsem = joo.getString("firstsem");
                                String secondsem = joo.getString("secondsem");
                                String major = joo.getString("major");
                                double lati = joo.getDouble("lati");
                                double lon = joo.getDouble("long");
                                String address = joo.getString("address");


                                university = new University(name, rector, uri_list, firstsem, secondsem, major, phone, lati, lon, address);
                                uni_list.add(university);
                                uri_list = new ArrayList<>();
                            }

                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(List_uni.this);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);
                            pd.dismiss();
                            srf.setRefreshing(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.dismiss();
                        srf.setRefreshing(false);
                        new AlertDialog.Builder(List_uni.this)
                                .setTitle("Error!")
                                .setMessage("Occur error.\nPlease check your connection.")
                                .setPositiveButton("Try Again",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {

                                                uni_list.clear();
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
                                        List_uni.this.finish();
                                    }
                                }).setCancelable(false).show();

                    }
                });

        rq.add(stringRequest);
    }
}
