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

public class Res_information extends AppCompatActivity implements OnMapReadyCallback {

    Toolbar toolbar;
    TextView _open;
    TextView _address;
    TextView _phone;
    TextView _name;
    TextView _type;
    TextView _order;


    SupportMapFragment mv;

    ViewPager viewPager;
    CircleIndicator indicator;
    ArrayList<Uri> myList;
    private static int currentPage = 0;
    Ratio ratio;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_info);

        toolbar = (Toolbar) findViewById(R.id.res_info_tool);
        toolbar.setTitle(getIntent().getStringExtra("name"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ratio = new Ratio(this);


        _open = (TextView) findViewById(R.id.open_time_res);
        _address = (TextView) findViewById(R.id.res_address);
        _name = (TextView) findViewById(R.id.res_name);
        _phone = (TextView) findViewById(R.id.res_phone);
        _type = (TextView) findViewById(R.id.res_type);
        _order = (TextView) findViewById(R.id.res_order);


        _name.setText(getIntent().getStringExtra("name"));
        _phone.setText(getIntent().getStringExtra("phone"));
        _open.setText(getIntent().getStringExtra("open") + "-" + getIntent().getStringExtra("close"));
        _address.setText(getIntent().getStringExtra("address"));
        _type.setText(getIntent().getStringExtra("type"));
        boolean b = Boolean.parseBoolean(getIntent().getStringExtra("order"));
        if (b) {
            _order.setText("Order is available.");
        } else {
            _order.setText("Order is non-available.");
        }

        myList = new ArrayList<>();
        myList = (ArrayList<Uri>) getIntent().getSerializableExtra("image_list");


        viewPager = (ViewPager) findViewById(R.id.info_pager_res);
        indicator = (CircleIndicator) findViewById(R.id.info_indicator_res);

        ratio = new Ratio(this);
        ratio.calculateSize(viewPager, this, 720, 480);
        myList.remove(0);
        init();

        mv = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.res_map);
        mv.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        double lati = Double.parseDouble(getIntent().getStringExtra("lati"));
        double longu = Double.parseDouble(getIntent().getStringExtra("long"));

        LatLng coordinate = new LatLng(lati, longu);
        googleMap.addMarker(new MarkerOptions().position(coordinate)).setTitle(getIntent().getStringExtra("name"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 15.0f));
    }


    private void init() {
        viewPager.setAdapter(new SlideAdapter(Res_information.this, myList));
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

            String shareBody = "Restaurant - " + getIntent().getStringExtra("name") + "\nCategory - " + getIntent().getStringExtra("type") + "\n" + "Open time - " + getIntent().getStringExtra("open") + "-" + getIntent().getStringExtra("close") + "\n" + "Phone - " + getIntent().getStringExtra("phone") + "\nAddress - " + getIntent().getStringExtra("address");

            shareBody += "\n\nShare from \"Maubin Directory\" app.";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share from Maubin Directory app");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share"));
        }
        return super.onOptionsItemSelected(item);
    }

}
