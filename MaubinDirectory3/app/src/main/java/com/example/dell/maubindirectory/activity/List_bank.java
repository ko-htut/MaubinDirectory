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
import com.example.dell.maubindirectory.Object.Bank;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.adapter.Bank_adpater;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 13/08/2017.
 */

public class List_bank extends AppCompatActivity {
    final String JSONURL = "http://maubindirectory.000webhostapp.com/bank/bank.json";
    RecyclerView recyclerView;

    Bank_adpater bank_adapter;
    ArrayList<Bank> bank_list;
    Toolbar toolbar;

    SwipeRefreshLayout srf;

    RequestQueue rq;
    ArrayList<Uri> uri_list;
    Bank bank;

    ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toolbar = (Toolbar) findViewById(R.id.pagoda_toolbar);
        toolbar.setTitle("Bank");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        sp.setVisibility(View.GONE);
        bank_list = new ArrayList<>();
        bank_adapter = new Bank_adpater(bank_list, this);
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
                bank_list.clear();
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
                            JSONArray jpagoda_arrs = jos.getJSONArray("bank");
                            for (int i = 0; i < jpagoda_arrs.length(); i++) {
                                JSONObject joo = jpagoda_arrs.getJSONObject(i);
                                String name = joo.getString("bank_name");
                                JSONArray imageArr2 = joo.getJSONArray("bankimage");
                                for (int j = 0; j < imageArr2.length(); j++) {
                                    JSONObject jso = imageArr2.getJSONObject(j);
                                    Uri image = Uri.parse(jso.getString("bimage"));
                                    uri_list.add(image);
                                }
                                String phone = joo.getString("phone");
                                String open = joo.getString("open_time");
                                String close = joo.getString("close_time");
                                double lati = joo.getDouble("lati");
                                double lon = joo.getDouble("long");
                                String address = joo.getString("address");

                                bank = new Bank(name, uri_list, open, close, phone, lati, lon, address);
                                bank_list.add(bank);
                                uri_list = new ArrayList<>();
                            }


                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(List_bank.this);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(bank_adapter);
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
                        new AlertDialog.Builder(List_bank.this)
                                .setTitle("Error!")
                                .setMessage("Occur error.\nPlease check your connection.")
                                .setPositiveButton("Try Again",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {

                                                bank_list.clear();
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
                                        List_bank.this.finish();
                                    }
                                }).setCancelable(false).show();

                    }
                });

        rq.add(stringRequest);
    }
}
