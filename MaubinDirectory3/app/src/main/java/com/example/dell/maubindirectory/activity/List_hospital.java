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
import com.example.dell.maubindirectory.Object.GuestHouse;
import com.example.dell.maubindirectory.Object.Hospital;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.adapter.ATM_adapter;
import com.example.dell.maubindirectory.adapter.Hospital_adpater;
import com.example.dell.maubindirectory.adapter.Market_adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 15/08/2017.
 */

public class List_hospital extends AppCompatActivity {


    final String JSONURL = "http://maubindirectory.000webhostapp.com/hospital/hospital.json";
    RecyclerView recyclerView;

    Hospital_adpater hospital_adapter;
    ArrayList<Hospital> hospital_list;
    Toolbar toolbar;

    SwipeRefreshLayout srf;

    RequestQueue rq;
    Hospital hospital;

    ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        toolbar = (Toolbar) findViewById(R.id.pagoda_toolbar);
        toolbar.setTitle("Hospitals");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        sp.setVisibility(View.GONE);
        hospital_list = new ArrayList<>();
        hospital_adapter = new Hospital_adpater(hospital_list, this);
        recyclerView = (RecyclerView) findViewById(R.id.pagoda_recycler);
        pd = new ProgressDialog(this);
        rq = Volley.newRequestQueue(this);

        pd.setMessage("Loading....");
        pd.setCancelable(false);
        pd.show();
        request();

        srf = (SwipeRefreshLayout) findViewById(R.id.swipe_pagoda);
        srf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                hospital_list.clear();
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
                                JSONObject joh = new JSONObject(response);
                                JSONArray jh_arrm = joh.getJSONArray("hospital");
                                for (int i = 0; i < jh_arrm.length(); i++) {
                                    JSONObject jooh = jh_arrm.getJSONObject(i);
                                    String name = jooh.getString("hospital_name");
                                    Uri image = Uri.parse(jooh.getString("hospital_image"));
                                    String phone = jooh.getString("phone");
                                    String emergency = jooh.getString("emergency");
                                    double lati = jooh.getDouble("lati");
                                    double lon = jooh.getDouble("long");
                                    String address = jooh.getString("address");

                                    hospital = new Hospital(name, image, phone, emergency, lati, lon, address);
                                    hospital_list.add(hospital);
                                }


                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(List_hospital.this);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(hospital_adapter);
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
                            new AlertDialog.Builder(List_hospital.this)
                                    .setTitle("Error!")
                                    .setMessage("Occur error.\nPlease check your connection.")
                                    .setPositiveButton("Try Again",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                                    int which) {

                                                    hospital_list.clear();

                                                    dialog.dismiss();
                                                    pd.show();
                                                    request();

                                                }
                                            })
                                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            List_hospital.this.finish();
                                        }
                                    }).setCancelable(false).show();

                        }
                    });

            rq.add(stringRequest);
        }

    }

}
