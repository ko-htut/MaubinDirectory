package com.example.dell.maubindirectory.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.adapter.SlideAdapter;
import com.example.dell.maubindirectory.support.Ratio;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by User on 13/08/2017.
 */

public class Bank_information extends AppCompatActivity implements OnMapReadyCallback {


    Toolbar toolbar;
    TextView bank_open;
    TextView bank_address;
    TextView bank_phone;
    TextView bank_name;

    SupportMapFragment mv;

    ViewPager viewPager;
    CircleIndicator indicator;
    ArrayList<Uri> myList;
    private static int currentPage = 0;
    Ratio ratio;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_info);

        toolbar = (Toolbar) findViewById(R.id.bank_info_tool);
        toolbar.setTitle(getIntent().getStringExtra("bank_name"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bank_open = (TextView) findViewById(R.id.open_time_bank);
        bank_address = (TextView) findViewById(R.id.bank_address);
        bank_name = (TextView) findViewById(R.id.bank_name);
        bank_phone = (TextView) findViewById(R.id.bank_phone);

        bank_name.setText(getIntent().getStringExtra("bank_name"));
        bank_phone.setText(getIntent().getStringExtra("bank_phone"));
        bank_open.setText(getIntent().getStringExtra("bank_open") + "-" + getIntent().getStringExtra("bank_close"));
        bank_address.setText(getIntent().getStringExtra("bank_address"));
        myList = new ArrayList<>();
        myList = (ArrayList<Uri>) getIntent().getSerializableExtra("bank_image_list");


        viewPager = (ViewPager) findViewById(R.id.info_pager_bank);
        indicator = (CircleIndicator) findViewById(R.id.info_indicator_bank);

        ratio = new Ratio(this);
        ratio.calculateSize(viewPager, this, 720, 480);
        //  indicator.setViewPager(viewPager);
        myList.remove(0);
        init();

        mv = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.bank_map);
        //mv.onCreate(savedInstanceState);
        mv.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        double lati = Double.parseDouble(getIntent().getStringExtra("bank_lati"));
        double longu = Double.parseDouble(getIntent().getStringExtra("bank_long"));

        LatLng coordinate = new LatLng(lati, longu);
        googleMap.addMarker(new MarkerOptions().position(coordinate)).setTitle(getIntent().getStringExtra("bank_name"));
        //googleMap.addMarker(new MarkerOptions().position(coordinate)).setIcon((BitmapDescriptorFactory.fromResource(R.drawable.bank)));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 15.0f));
    }


    private void init() {
        //mPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new SlideAdapter(Bank_information.this, myList));
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

            String shareBody = "Bank - " + getIntent().getStringExtra("bank_name") + "\n" + "Phone - " + getIntent().getStringExtra("bank_phone") + "\nOpen time - " + getIntent().getStringExtra("bank_open") + "-" + getIntent().getStringExtra("bank_close") + "\nAddress - " + getIntent().getStringExtra("bank_address");

            shareBody += "\n\nShare from \"Maubin Directory\" app.";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share from Maubin Directory app");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share"));
        }
        return super.onOptionsItemSelected(item);
    }
}
