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
import com.example.dell.maubindirectory.Object.Restaurant;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.adapter.Restaurant_adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 15/08/2017.
 */

public class List_res extends AppCompatActivity {


    final String JSONURL = "http://maubindirectory.000webhostapp.com/Restaurant/res.json";
    RecyclerView recyclerView;

    Restaurant_adapter res_adapter;
    ArrayList<Restaurant> res_list;
    Toolbar toolbar;

    SwipeRefreshLayout srf;

    RequestQueue rq;
    ArrayList<Uri> uri_list;
    Restaurant restaurant;

    ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = (Toolbar) findViewById(R.id.pagoda_toolbar);
        toolbar.setTitle("Restaurant");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        sp.setVisibility(View.GONE);
        res_list = new ArrayList<>();
        res_adapter = new Restaurant_adapter(res_list, this);
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
                res_list.clear();
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
                                JSONObject jor = new JSONObject(response);
                                JSONArray jres_arrm = jor.getJSONArray("restaurant");
                                for (int i = 0; i < jres_arrm.length(); i++) {
                                    JSONObject joor = jres_arrm.getJSONObject(i);
                                    String name = joor.getString("restaurant_name");
                                    String type = joor.getString("type");
                                    JSONArray imageArr2 = joor.getJSONArray("restaurantimage");
                                    for (int j = 0; j < imageArr2.length(); j++) {
                                        JSONObject jso = imageArr2.getJSONObject(j);
                                        Uri image = Uri.parse(jso.getString("rimage"));
                                        uri_list.add(image);
                                    }
                                    String phone = joor.getString("phone");
                                    String open = joor.getString("open_time");
                                    String close = joor.getString("close_time");
                                    boolean order = joor.getBoolean("order");
                                    double lati = joor.getDouble("lati");
                                    double lon = joor.getDouble("long");
                                    String address = joor.getString("address");

                                    restaurant = new Restaurant(name, uri_list, type, phone, open, close, address, order, lati, lon);
                                    res_list.add(restaurant);
                                    uri_list = new ArrayList<>();
                                }


                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(List_res.this);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(res_adapter);
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
                            new AlertDialog.Builder(List_res.this)
                                    .setTitle("Error!")
                                    .setMessage("Occur error.\nPlease check your connection.")
                                    .setPositiveButton("Try Again",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                                    int which) {

                                                    res_list.clear();
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
                                            List_res.this.finish();
                                        }
                                    }).setCancelable(false).show();

                        }
                    });

            rq.add(stringRequest);
        }

    }
}
