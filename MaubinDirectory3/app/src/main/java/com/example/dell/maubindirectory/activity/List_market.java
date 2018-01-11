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
import com.example.dell.maubindirectory.Object.Market;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.adapter.Market_adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 15/08/2017.
 */

public class List_market extends AppCompatActivity {

    final String JSONURL = "http://maubindirectory.000webhostapp.com/market/market.json";
    RecyclerView recyclerView;

    Market_adapter market_adapter;
    ArrayList<Market> market_list;
    Toolbar toolbar;

    SwipeRefreshLayout srf;

    RequestQueue rq;
    ArrayList<Uri> uri_list;
    Market market;

    ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = (Toolbar) findViewById(R.id.pagoda_toolbar);
        toolbar.setTitle("Markets");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        market_list = new ArrayList<>();
        market_adapter = new Market_adapter(market_list, this);
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        sp.setVisibility(View.GONE);
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
                market_list.clear();
                uri_list.clear();
                request();
            }
        });


    }

    private void request() {
        {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, JSONURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {

                                JSONObject jom = new JSONObject(response);
                                JSONArray jpagoda_arrm = jom.getJSONArray("market");
                                for (int i = 0; i < jpagoda_arrm.length(); i++) {
                                    JSONObject joom = jpagoda_arrm.getJSONObject(i);
                                    String name = joom.getString("market_name");
                                    String type = joom.getString("type");
                                    JSONArray imageArr2 = joom.getJSONArray("marketimage");
                                    for (int j = 0; j < imageArr2.length(); j++) {
                                        JSONObject jso = imageArr2.getJSONObject(j);
                                        Uri image = Uri.parse(jso.getString("bimage"));
                                        uri_list.add(image);
                                    }
                                    String phone = joom.getString("phone");
                                    String open = joom.getString("open_time");
                                    String close = joom.getString("close_time");
                                    double lati = joom.getDouble("lati");
                                    double lon = joom.getDouble("long");
                                    String address = joom.getString("address");

                                    market = new Market(name, uri_list, address, open, close, type, phone, lati, lon);
                                    market_list.add(market);
                                    uri_list = new ArrayList<>();
                                }


                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(List_market.this);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(market_adapter);
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
                            new AlertDialog.Builder(List_market.this)
                                    .setTitle("Error!")
                                    .setMessage("Occur error.\nPlease check your connection.")
                                    .setPositiveButton("Try Again",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                                    int which) {

                                                    market_list.clear();
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
                                            List_market.this.finish();
                                        }
                                    }).setCancelable(false).show();

                        }
                    });

            rq.add(stringRequest);
        }

    }

}
