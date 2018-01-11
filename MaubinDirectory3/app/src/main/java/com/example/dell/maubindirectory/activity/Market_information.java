package com.example.dell.maubindirectory.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class Market_information extends AppCompatActivity implements OnMapReadyCallback {

    Toolbar toolbar;
    TextView market_open;
    TextView market_address;
    TextView market_phone;
    TextView market_name;
    TextView market_type;


    SupportMapFragment mv;

    ViewPager viewPager;
    CircleIndicator indicator;
    ArrayList<Uri> myList;
    private static int currentPage = 0;
    Ratio ratio;

    String name;
    String phone;
    String address;
    String type;
    String open;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_info);

        toolbar = (Toolbar) findViewById(R.id.market_info_tool);
        toolbar.setTitle(getIntent().getStringExtra("market_name"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ratio = new Ratio(this);


        market_open = (TextView) findViewById(R.id.open_time_market);
        market_address = (TextView) findViewById(R.id.market_address);
        market_name = (TextView) findViewById(R.id.market_name);
        market_phone = (TextView) findViewById(R.id.market_phone);
        market_type = (TextView) findViewById(R.id.market_type);

        name = getIntent().getStringExtra("market_name");
        phone = getIntent().getStringExtra("market_phone");
        open = getIntent().getStringExtra("market_open") + "-" + getIntent().getStringExtra("market_close");
        address = getIntent().getStringExtra("market_address");
        type = getIntent().getStringExtra("market_type");
        market_name.setText(name);
        market_phone.setText(phone);
        market_open.setText(open);
        market_address.setText(address);
        market_type.setText(type);
        myList = new ArrayList<>();
        myList = (ArrayList<Uri>) getIntent().getSerializableExtra("market_image_list");


        viewPager = (ViewPager) findViewById(R.id.info_pager_market);
        indicator = (CircleIndicator) findViewById(R.id.info_indicator_market);

        ratio = new Ratio(this);
        ratio.calculateSize(viewPager, this, 720, 480);
        myList.remove(0);
        init();

        mv = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.market_map);
        mv.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        double lati = Double.parseDouble(getIntent().getStringExtra("market_lati"));
        double longu = Double.parseDouble(getIntent().getStringExtra("market_long"));

        LatLng coordinate = new LatLng(lati, longu);
        googleMap.addMarker(new MarkerOptions().position(coordinate)).setTitle(getIntent().getStringExtra("market_name"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 15.0f));


    }


    private void init() {
        viewPager.setAdapter(new SlideAdapter(Market_information.this, myList));
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

            String shareBody = "Market - " + name + "\nType - " + type + "\n" + "Open time - " + open + "\n" + "Phone - " + phone + "\nAddress - " + address;

            shareBody += "\n\nShare from \"Maubin Directory\" app.";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share from Maubin Directory app");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share"));
        }
        return super.onOptionsItemSelected(item);
    }
}
