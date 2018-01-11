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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.maubindirectory.Object.ATM;
import com.example.dell.maubindirectory.Object.Bank;
import com.example.dell.maubindirectory.Object.Board;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.adapter.Bank_adpater;
import com.example.dell.maubindirectory.adapter.Board_adpater;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by User on 16/08/2017.
 */

public class List_board extends AppCompatActivity {
    final String JSONURL = "http://maubindirectory.000webhostapp.com/board/board.json";
    RecyclerView recyclerView;

    Board_adpater board_adapter;
    ArrayList<Board> board_list;
    Toolbar toolbar;

    SwipeRefreshLayout srf;
    RequestQueue rq;
    Board board;

    ProgressDialog pd;
    String gender_sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        toolbar = (Toolbar) findViewById(R.id.pagoda_toolbar);
        toolbar.setTitle("Hostel");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        sp.setVisibility(View.GONE);
        board_list = new ArrayList<>();
        board_adapter = new Board_adpater(board_list, this);
        recyclerView = (RecyclerView) findViewById(R.id.pagoda_recycler);
        pd = new ProgressDialog(this);
        rq = Volley.newRequestQueue(this);

        pd.setMessage("Loading....");
        pd.setCancelable(false);
        pd.show();
        request();

        gender_sp = "All";
        srf = (SwipeRefreshLayout) findViewById(R.id.swipe_pagoda);
        srf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                board_list.clear();
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
                            JSONArray jpagoda_arrs = jos.getJSONArray("board");

                            for (int i = 0; i < jpagoda_arrs.length(); i++) {
                                JSONObject jatm = jpagoda_arrs.getJSONObject(i);
                                String name = jatm.getString("board_name");
                                /*JSONArray imageArr3 = jatm.getJSONArray("board_image");
                                for (int j = 0; j < imageArr3.length(); j++) {
                                    JSONObject jso = imageArr3.getJSONObject(j);
                                    Uri image = Uri.parse(jso.getString("imageboard"));
                                    uri_list.add(image);
                                }*/
                                String phone = jatm.getString("board_phone");
                                String gender = jatm.getString("board_gender");
                                int price = jatm.getInt("board_price");
                                String pric = "";
                                if (price == 0) {
                                    pric = "-";
                                } else {
                                    pric = String.valueOf(price);
                                }
                                double lati = jatm.getDouble("lati");
                                double lon = jatm.getDouble("long");
                                String address = jatm.getString("address");
                                address = address.replace("\n","\n\t\t");

                                board = new Board(name, pric, gender, phone, address, lati, lon);

                                board_list.add(board);

/*
                                atm = new ATM(name, image, open, lati, lon, address);
                                atm_list.add(atm);*/
                            }


                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(List_board.this);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(board_adapter);
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
                        {
                            pd.dismiss();
                            srf.setRefreshing(false);
                            new AlertDialog.Builder(List_board.this)
                                    .setTitle("Error!")
                                    .setMessage("Occur error.\nPlease check your connection.")
                                    .setPositiveButton("Try Again",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                                    int which) {
                                                    board_list.clear();
                                                    dialog.dismiss();
                                                    pd.show();
                                                    request();

                                                }
                                            })
                                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            List_board.this.finish();
                                        }
                                    }).setCancelable(false).show();
                        }
                    }
                }
        );

        rq.add(stringRequest);

    }
}
