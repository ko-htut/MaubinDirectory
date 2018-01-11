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
 * Created by User on 15/08/2017.
 */

public class Guest_informatin extends AppCompatActivity implements OnMapReadyCallback {

    Toolbar toolbar;
    TextView guest_address;
    TextView guest_phone;
    TextView guest_name;
    TextView guest_type;


    SupportMapFragment mv;

    ViewPager viewPager;
    CircleIndicator indicator;
    ArrayList<Uri> myList;
    private static int currentPage = 0;
    Ratio ratio;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_info);

        toolbar = (Toolbar) findViewById(R.id.guest_info_tool);
        toolbar.setTitle(getIntent().getStringExtra("guest_name"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ratio = new Ratio(this);

        guest_address = (TextView) findViewById(R.id.guest_address);
        guest_name = (TextView) findViewById(R.id.guest_name);
        guest_phone = (TextView) findViewById(R.id.guest_phone);
        guest_type = (TextView) findViewById(R.id.guest_type);


        guest_name.setText(getIntent().getStringExtra("guest_name"));
        guest_phone.setText(getIntent().getStringExtra("guest_phone"));
        guest_address.setText(getIntent().getStringExtra("guest_address"));
        guest_type.setText(getIntent().getStringExtra("guest_type"));
        myList = new ArrayList<>();
        myList = (ArrayList<Uri>) getIntent().getSerializableExtra("guest_image_list");

        viewPager = (ViewPager) findViewById(R.id.info_pager_guest);
        indicator = (CircleIndicator) findViewById(R.id.info_indicator_guest);

        ratio = new Ratio(this);
        ratio.calculateSize(viewPager, this, 720, 480);
        myList.remove(0);
        init();

        mv = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.guest_map);
        mv.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        double lati = Double.parseDouble(getIntent().getStringExtra("guest_lati"));
        double longu = Double.parseDouble(getIntent().getStringExtra("guest_long"));

        LatLng coordinate = new LatLng(lati, longu);
        googleMap.addMarker(new MarkerOptions().position(coordinate)).setTitle(getIntent().getStringExtra("guest_name"));
        //googleMap.addMarker(new MarkerOptions().position(coordinate)).setIcon((BitmapDescriptorFactory.fromResource(R.drawable.bank)));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 15.0f));
    }


    private void init() {
        //mPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new SlideAdapter(Guest_informatin.this, myList));
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

            String shareBody = "Guest House - " + getIntent().getStringExtra("guest_name") + "\nCategory - " + getIntent().getStringExtra("guest_type") + "\nPhone - " + getIntent().getStringExtra("guest_phone") + "\nAddress - " + getIntent().getStringExtra("guest_address");

            shareBody += "\n\nShare from \"Maubin Directory\" app.";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share from Maubin Directory app");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share"));
        }
        return super.onOptionsItemSelected(item);
    }

}
