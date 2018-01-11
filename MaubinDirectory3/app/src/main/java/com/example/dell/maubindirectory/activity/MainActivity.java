package com.example.dell.maubindirectory.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.adapter.ViewPagerAdapter;
import com.example.dell.maubindirectory.fragment.Fragment_item_list1;
import com.example.dell.maubindirectory.fragment.Fragment_item_list2;
import com.example.dell.maubindirectory.support.NetworkStateReceiver;
import com.example.dell.maubindirectory.support.Ratio;

import me.relex.circleindicator.CircleIndicator;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NetworkStateReceiver.NetworkStateReceiverListener {


    ViewFlipper vf;
    ImageView image1, image2, image3, image4, image5;
    View content_view;
    Ratio ratio;
    TextView noInternet;
    NetworkStateReceiver networkStateReceiver;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //start my code
        ratio = new Ratio(this.getApplicationContext());
        //floation action button
        /*
        fab_menu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        fab_hospital = (FloatingActionButton) findViewById(R.id.fab_hospital);
        fab_police = (FloatingActionButton) findViewById(R.id.fab_police);
        fab_fire = (FloatingActionButton) findViewById(R.id.fab_fire);
        fab_menu.setClosedOnTouchOutside(true);
        fab_hospital.setOnClickListener(this);
        fab_police.setOnClickListener(this);
        fab_fire.setOnClickListener(this);*/

        //view flipper
        content_view = (View) findViewById(R.id.content_main_id);
        vf = (ViewFlipper) content_view.findViewById(R.id.main_view_flip);
        ratio.calculateSize(vf, this, 720, 480);

        image1 = (ImageView) content_view.findViewById(R.id.image1);
        image2 = (ImageView) content_view.findViewById(R.id.image2);
        image3 = (ImageView) content_view.findViewById(R.id.image3);
        image4 = (ImageView) content_view.findViewById(R.id.image4);
        image5 = (ImageView) content_view.findViewById(R.id.image5);


        vf.refreshDrawableState();

        Glide.with(this).load(Uri.parse("http://maubindirectory.000webhostapp.com/Intro/image1.png")).into(image1);
        Glide.with(this).load(Uri.parse("http://maubindirectory.000webhostapp.com/Intro/image2.png")).into(image2);
        Glide.with(this).load(Uri.parse("http://maubindirectory.000webhostapp.com/Intro/image3.png")).into(image3);
        Glide.with(this).load(Uri.parse("http://maubindirectory.000webhostapp.com/Intro/image4.png")).into(image4);
        Glide.with(this).load(Uri.parse("http://maubindirectory.000webhostapp.com/Intro/image5.png")).into(image5);

        vf.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_from_right));
        vf.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_to_left));
        vf.startFlipping();
        vf.setFlipInterval(5000);

        noInternet = (TextView) content_view.findViewById(R.id.noconnection_text);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));


        //View Pager
        viewPager = (ViewPager) content_view.findViewById(R.id.pager);
        circleIndicator = (CircleIndicator) content_view.findViewById(R.id.indicator);
        setUpViewPager(viewPager);
        circleIndicator.setViewPager(viewPager);

    }

    private void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Fragment_item_list1(this.getApplicationContext()));
        adapter.addFrag(new Fragment_item_list2(this.getApplicationContext()));
        viewPager.setAdapter(adapter);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please press again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_send) {
            try {
                Intent _intent = new Intent(Intent.ACTION_SEND);
                _intent.setType("text/html");
                _intent.setClassName("com.google.android.gm","com.google.android.gm.ComposeActivityGmail");
                _intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"htetaunglin2020@gmail.com"});
                _intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback for Maubin Directory App");
                _intent.putExtra(Intent.EXTRA_TEXT,"");
                startActivity(Intent.createChooser(_intent,"Feedback"));
            }catch (Exception e){
                Intent _intent = new Intent(Intent.ACTION_SEND);
                _intent.setType("text/html");
                _intent.setClassName("com.google.android.gm","com.google.android.gm.ComposeActivityGmail");
                _intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"htetaunglin2020@gmail.com","chitchithmwe1112@gmail.com","mznlt.whitecolor@gmail.com"});
                _intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback for Maubin Directory App");
                _intent.putExtra(Intent.EXTRA_TEXT,"");
                startActivity(Intent.createChooser(_intent,"Feedback"));
            }
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this, About.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void networkAvailable() {
        noInternet.setText("Connected");
        noInternet.setBackgroundColor(Color.parseColor("#009f00"));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                noInternet.setVisibility(View.GONE);
            }
        }, 3000);
    }

    @Override
    public void networkUnavailable() {

        noInternet.setText("No access connection");
        noInternet.setBackgroundColor(Color.parseColor("#ff0000"));
        noInternet.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkStateReceiver.removeListener(this);
        this.unregisterReceiver(networkStateReceiver);
    }


}
