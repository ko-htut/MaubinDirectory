package com.example.dell.maubindirectory.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

public class University_information extends AppCompatActivity implements OnMapReadyCallback {


    Toolbar toolbar;
    TextView uni_major;
    TextView uni_year;
    TextView uni_rector;
    TextView uni_name;
    TextView uni_address;
    TextView uni_phone;
    ImageView uni_logo;

    SupportMapFragment mv;

    ViewPager viewPager;
    CircleIndicator indicator;
    ArrayList<Uri> myList;
    private static int currentPage = 0;
    Ratio ratio;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uni_info);

        toolbar = (Toolbar) findViewById(R.id.uni_info_tool);
        toolbar.setTitle(getIntent().getStringExtra("uni_name"));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uni_name = (TextView) findViewById(R.id.uni_info_name);
        uni_rector = (TextView) findViewById(R.id.uni_rector);
        uni_year = (TextView) findViewById(R.id.uni_year);
        uni_major = (TextView) findViewById(R.id.uni_major);
        uni_address = (TextView) findViewById(R.id.uni_address);
        uni_logo = (ImageView) findViewById(R.id.uni_info_logo);
        uni_phone = (TextView) findViewById(R.id.uni_phone);

        //TODO
        uni_name.setText(getIntent().getStringExtra("uni_name"));
        uni_rector.setText(getIntent().getStringExtra("uni_rector"));
        uni_year.setText("First Semester : " + getIntent().getStringExtra("first_sem") + "\n" + "Second Semester : " + getIntent().getStringExtra("second_sem"));
        uni_major.setText(getIntent().getStringExtra("uni_major"));
        uni_address.setText(getIntent().getStringExtra("uni_address"));
        uni_phone.setText(getIntent().getStringExtra("uni_phone"));


        /**
         *
         * _intent.putExtra("uni_name", uni_list.get(position).getName());
         _intent.putExtra("uni_rector", uni_list.get(position).getRector());
         _intent.putExtra("first_sem", uni_list.get(position).getFirst_sem());
         _intent.putExtra("second_sem", uni_list.get(position).getSecond_sem());
         _intent.putExtra("uni_phone", uni_list.get(position).getPhone());
         _intent.putExtra("uni_lati", String.valueOf(uni_list.get(position).getLati()));
         _intent.putExtra("uni_long", String.valueOf(uni_list.get(position).getLongu()));
         _intent.putExtra("uni_address", uni_list.get(position).getAddress());
         _intent.putExtra("uni_major", uni_list.get(position).getMajor());
         */


        myList = new ArrayList<>();
        myList = (ArrayList<Uri>) getIntent().getSerializableExtra("image_list");

        Glide.with(this).load(myList.get(0)).into(uni_logo);

        viewPager = (ViewPager) findViewById(R.id.info_pager_uni);
        indicator = (CircleIndicator) findViewById(R.id.info_indicator_uni);

        ratio = new Ratio(this);
        ratio.calculateSize(viewPager, this, 720, 480);
        //  indicator.setViewPager(viewPager);

        myList.remove(0);
        init();

        mv = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.uni_map);
        //mv.onCreate(savedInstanceState);
        mv.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        double lati = Double.parseDouble(getIntent().getStringExtra("uni_lati"));
        double longu = Double.parseDouble(getIntent().getStringExtra("uni_long"));

        LatLng coordinate = new LatLng(lati, longu);
        googleMap.addMarker(new MarkerOptions().position(coordinate)).setTitle(getIntent().getStringExtra("uni_name"));
        //googleMap.addMarker(new MarkerOptions().position(coordinate)).setIcon((BitmapDescriptorFactory.fromResource(R.drawable.pagoda)));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 15.0f));
    }


    private void init() {
        //mPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new SlideAdapter(University_information.this, myList));
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

            String shareBody = "University - " + getIntent().getStringExtra("uni_name") + "\nRector - " + getIntent().getStringExtra("uni_rector") + "\n" + "Open time - \n" + "First Semester : " + getIntent().getStringExtra("first_sem") + "\n" + "Second Semester : " + getIntent().getStringExtra("second_sem") + "\nMajor - " + getIntent().getStringExtra("uni_major") + "\n" + "Phone - " + getIntent().getStringExtra("uni_address") + "\nAddress - " + getIntent().getStringExtra("uni_phone");

            shareBody += "\n\nShare from \"Maubin Directory\" app.";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share from Maubin Directory app");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share"));
        }
        return super.onOptionsItemSelected(item);
    }

}
