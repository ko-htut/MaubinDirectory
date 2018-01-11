package com.example.dell.maubindirectory.activity;

import android.app.AlertDialog;
import android.app.ExpandableListActivity;
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
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.maubindirectory.Object.Bank;
import com.example.dell.maubindirectory.Object.Board;
import com.example.dell.maubindirectory.Object.Office;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.adapter.Bank_adpater;
import com.example.dell.maubindirectory.adapter.Board_adpater;
import com.example.dell.maubindirectory.adapter.Office_adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 16/08/2017.
 */

public class List_office extends AppCompatActivity {

    final String JSONURL = "http://maubindirectory.000webhostapp.com/office/office.json";
    RecyclerView recyclerView;

    ArrayList<Office> officeList;
    Toolbar toolbar;

    SwipeRefreshLayout srf;

    RequestQueue rq;
    Office office;
    ProgressDialog pd;


    Office_adapter office_adapter;

    ArrayList<String> listDataHeader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toolbar = (Toolbar) findViewById(R.id.pagoda_toolbar);
        toolbar.setTitle("Office");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        sp.setVisibility(View.GONE);
        officeList = new ArrayList<>();
        office_adapter = new Office_adapter(this, officeList);
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
                officeList.clear();
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
                            JSONArray jpagoda_arrs = jos.getJSONArray("office");

                            for (int i = 0; i < jpagoda_arrs.length(); i++) {
                                JSONObject jatm = jpagoda_arrs.getJSONObject(i);
                                String name = jatm.getString("office_name");
                                Uri image = Uri.parse(jatm.getString("office_logo"));

                                String phone = jatm.getString("office_phone");
                                double lati = jatm.getDouble("lati");
                                double lon = jatm.getDouble("long");
                                String address = jatm.getString("address");


                                office = new Office(image, name, phone, address, lati, lon);
                                officeList.add(office);

                            }
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(List_office.this);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(office_adapter);
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
                        {
                            pd.dismiss();
                            srf.setRefreshing(false);
                            new AlertDialog.Builder(List_office.this)
                                    .setTitle("Error!")
                                    .setMessage("Occur error.\nPlease check your connection.")
                                    .setPositiveButton("Try Again",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                                    int which) {
                                                    listDataHeader.clear();
                                                    officeList.clear();
                                                    dialog.dismiss();
                                                    pd.show();
                                                    request();

                                                }
                                            })
                                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            List_office.this.finish();
                                        }
                                    }).setCancelable(false).show();
                        }
                    }
                }
        );

        rq.add(stringRequest);
    }
}
