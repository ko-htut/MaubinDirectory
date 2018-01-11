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
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.maubindirectory.Object.GuestHouse;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.adapter.Guest_adpater;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 15/08/2017.
 */

public class List_guest extends AppCompatActivity {
    final String JSONURL = "http://maubindirectory.000webhostapp.com/guest/guest.json";
    RecyclerView recyclerView;

    Guest_adpater guest_adapter;
    ArrayList<GuestHouse> guest_list;
    Toolbar toolbar;

    SwipeRefreshLayout srf;

    RequestQueue rq;
    ArrayList<Uri> uri_list;
    GuestHouse guestHouse;

    ProgressDialog pd;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = (Toolbar) findViewById(R.id.pagoda_toolbar);
        toolbar.setTitle("Guest House");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        sp.setVisibility(View.GONE);
        guest_list = new ArrayList<>();
        guest_adapter = new Guest_adpater(guest_list, this);
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
                guest_list.clear();
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


                            try{
                                JSONObject jog = new JSONObject(response);
                                JSONArray jguest_arrm = jog.getJSONArray("guestHouse");
                                for (int i = 0; i < jguest_arrm.length(); i++) {
                                    JSONObject joog = jguest_arrm.getJSONObject(i);
                                    String name = joog.getString("guestHouse_name");
                                    String type = joog.getString("type");
                                    JSONArray imageArr2 = joog.getJSONArray("guest_house_image");
                                    for (int j = 0; j < imageArr2.length(); j++) {
                                        JSONObject jso = imageArr2.getJSONObject(j);
                                        Uri image = Uri.parse(jso.getString("gimage"));
                                        uri_list.add(image);
                                    }
                                    String phone = joog.getString("phone");
                                    double lati = joog.getDouble("lati");
                                    double lon = joog.getDouble("long");
                                    String address = joog.getString("address");


                                    guestHouse = new GuestHouse(name, type, address, phone, lati, lon, uri_list);
                                    guest_list.add(guestHouse);
                                    uri_list = new ArrayList<>();
                                }


                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(List_guest.this);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(guest_adapter);
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
                            new AlertDialog.Builder(List_guest.this)
                                    .setTitle("Error!")
                                    .setMessage("Occur error.\nPlease check your connection.")
                                    .setPositiveButton("Try Again",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                                    int which) {

                                                    guest_list.clear();
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
                                            List_guest.this.finish();
                                        }
                                    }).setCancelable(false).show();

                        }
                    });

            rq.add(stringRequest);
        }

    }

}
