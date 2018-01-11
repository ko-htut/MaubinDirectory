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
import com.example.dell.maubindirectory.Object.ATM;
import com.example.dell.maubindirectory.Object.Market;
import com.example.dell.maubindirectory.Object.Other;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.adapter.Market_adapter;
import com.example.dell.maubindirectory.adapter.Other_adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 15/08/2017.
 */

public class List_other extends AppCompatActivity {
    final String JSONURL = "http://maubindirectory.000webhostapp.com/other/other.json";
    RecyclerView recyclerView;

    Other_adapter other_adapter;
    ArrayList<Other> other_list;
    Toolbar toolbar;

    SwipeRefreshLayout srf;

    RequestQueue rq;
    ArrayList<Uri> uri_list;
    Other other;

    ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toolbar = (Toolbar) findViewById(R.id.pagoda_toolbar);
        toolbar.setTitle("Places");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        sp.setVisibility(View.GONE);
        other_list = new ArrayList<>();
        other_adapter = new Other_adapter(other_list, this);
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
                other_list.clear();
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
                            JSONObject jos = new JSONObject(response);
                            JSONArray jpagoda_arrs = jos.getJSONArray("other");

                            for (int i = 0; i < jpagoda_arrs.length(); i++) {
                                JSONObject jatm = jpagoda_arrs.getJSONObject(i);
                                String name = jatm.getString("other_name");
                                JSONArray imageArr = jatm.getJSONArray("other_image");
                                for (int j = 0; j < imageArr.length(); j++) {
                                    JSONObject jso = imageArr.getJSONObject(j);
                                    Uri images = Uri.parse(jso.getString("oimage"));
                                    uri_list.add(images);
                                }
                                double lati = jatm.getDouble("lati");
                                double lon = jatm.getDouble("long");
                                String address = jatm.getString("address");

                                //atm = new ATM(name, image, open, lati, lon, address);
                                other = new Other(name, uri_list, address, lati, lon);
                                other_list.add(other);
                                uri_list = new ArrayList<>();
                            }


                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(List_other.this);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(other_adapter);
                            pd.dismiss();
                            srf.setRefreshing(false);
                        } catch (Exception e) {
                            srf.setRefreshing(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        {
                            pd.dismiss();
                            srf.setRefreshing(false);
                            new AlertDialog.Builder(List_other.this)
                                    .setTitle("Error!")
                                    .setMessage("Occur error.\nPlease check your connection.")
                                    .setPositiveButton("Try Again",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                                    int which) {
                                                    other_list.clear();
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
                                            List_other.this.finish();
                                        }
                                    }).setCancelable(false).show();
                        }
                    }
                }
        );

        rq.add(stringRequest);
    }
}
