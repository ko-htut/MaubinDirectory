package com.example.dell.maubindirectory.activity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.example.dell.maubindirectory.Object.Market;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.adapter.Market_adapter;
import com.example.dell.maubindirectory.adapter.image_adpater;

import java.util.ArrayList;

/**
 * Created by User on 17/08/2017.
 */

public class List_image extends AppCompatActivity {
    RecyclerView recyclerView;

    Toolbar toolbar;

    ArrayList<Uri> uri;
    image_adpater image_adpater;

    ProgressDialog pd;
    SwipeRefreshLayout s;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = (Toolbar) findViewById(R.id.pagoda_toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        sp.setVisibility(View.GONE);
        recyclerView = (RecyclerView) findViewById(R.id.pagoda_recycler);
        uri = new ArrayList<>();
        uri = (ArrayList<Uri>) getIntent().getSerializableExtra("image_list");
        image_adpater = new image_adpater(uri, this);
        s = (SwipeRefreshLayout) findViewById(R.id.swipe_pagoda);
        s.setRefreshing(false);
        s.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                s.setRefreshing(false);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(List_image.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(image_adpater);
    }
}
