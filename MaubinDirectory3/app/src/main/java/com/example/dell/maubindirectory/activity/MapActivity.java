package com.example.dell.maubindirectory.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.dell.maubindirectory.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by User on 15/08/2017.
 */

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    double lati, longu;
    String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        lati = Double.parseDouble(getIntent().getStringExtra("lati"));
        longu = Double.parseDouble(getIntent().getStringExtra("longu"));
        name = getIntent().getStringExtra("name");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(lati, longu);
        googleMap.addMarker(new MarkerOptions().position(sydney).title(name));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15.0f));
    }
}
