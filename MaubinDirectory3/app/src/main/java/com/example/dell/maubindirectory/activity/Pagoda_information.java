package com.example.dell.maubindirectory.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.adapter.SlideAdapter;
import com.example.dell.maubindirectory.support.Ratio;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by User on 12/08/2017.
 */

public class Pagoda_information extends AppCompatActivity implements OnMapReadyCallback {

    Toolbar toolbar;
    TextView pagoda_year;
    TextView pagoda_open;
    TextView pagoda_address;
    TextView pagoda_height;
    TextView pagoda_name;

    SupportMapFragment mv;

    ViewPager viewPager;
    CircleIndicator indicator;
    ArrayList<Uri> myList;
    private static int currentPage = 0;
    Ratio ratio;

    String name;
    String open;
    String address;
    String height;
    String year;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagoda_info);

        toolbar = (Toolbar) findViewById(R.id.pagoda_info_tool);
        toolbar.setTitle(getIntent().getStringExtra("pagoda_name"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pagoda_year = (TextView) findViewById(R.id.pagoda_year);
        pagoda_open = (TextView) findViewById(R.id.open_time_pagoda);
        pagoda_address = (TextView) findViewById(R.id.pagoda_address);
        pagoda_height = (TextView) findViewById(R.id.pagoda_height);
        pagoda_name = (TextView) findViewById(R.id.pagoda_name);

        year = getIntent().getStringExtra("pagoda_year");
        open = getIntent().getStringExtra("pagoda_open") + "-" + getIntent().getStringExtra("pagoda_close");
        address = getIntent().getStringExtra("pagoda_address");
        height = getIntent().getStringExtra("pagoda_height") + " feet";
        name = getIntent().getStringExtra("pagoda_name") + " (Pagoda)";
        pagoda_year.setText(getIntent().getStringExtra("pagoda_year"));
        pagoda_open.setText(getIntent().getStringExtra("pagoda_open") + "-" + getIntent().getStringExtra("pagoda_close"));
        pagoda_address.setText(getIntent().getStringExtra("pagoda_address"));
        pagoda_height.setText(getIntent().getStringExtra("pagoda_height") + " feet");

        pagoda_name.setText(getIntent().getStringExtra("pagoda_name") + " (Pagoda)");
        myList = new ArrayList<>();
        myList = (ArrayList<Uri>) getIntent().getSerializableExtra("image_list");

        viewPager = (ViewPager) findViewById(R.id.info_pager_pagoda);
        indicator = (CircleIndicator) findViewById(R.id.info_indicator_pagoda);

        ratio = new Ratio(this);
        ratio.calculateSize(viewPager, this, 720, 480);
        //  indicator.setViewPager(viewPager);
        init();

        mv = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.pagoda_map);
        //mv.onCreate(savedInstanceState);
        mv.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        double lati = Double.parseDouble(getIntent().getStringExtra("pagoda_lati"));
        double longu = Double.parseDouble(getIntent().getStringExtra("pagoda_long"));

        LatLng coordinate = new LatLng(lati, longu);
        googleMap.addMarker(new MarkerOptions().position(coordinate)).setTitle(getIntent().getStringExtra("pagoda_name"));
        //googleMap.addMarker(new MarkerOptions().position(coordinate)).setIcon((BitmapDescriptorFactory.fromResource(R.drawable.pagoda)));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 15.0f));
    }


    private void init() {
        //mPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new SlideAdapter(Pagoda_information.this, myList));
        //CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == myList.size()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");

            String shareBody = "Pagoda - " + name + "\nStart Year - " + year + "\n" + "Open time - " + open + "\n" + "Height - " + height + "\nAddress - " + address;

            shareBody += "\n\nShare from \"Maubin Directory\" app.";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share from Maubin Directory app");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share"));
        }
        return super.onOptionsItemSelected(item);
    }
}
