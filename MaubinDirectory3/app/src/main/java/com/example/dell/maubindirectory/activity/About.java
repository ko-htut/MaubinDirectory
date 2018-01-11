package com.example.dell.maubindirectory.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.support.Facebook;
import com.example.dell.maubindirectory.support.Ratio;
import com.flaviofaria.kenburnsview.KenBurnsView;

/**
 * Created by User on 16/08/2017.
 */

public class About extends AppCompatActivity {
    KenBurnsView cover;
    Ratio ratio;
    ImageView icon, developer_photo, developer_photo2, logo_back;
    TextView app_name;
    ScrollView sv;
    Button facebook, facebook2;
    Toolbar tool;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        tool = (Toolbar) findViewById(R.id.about_toolbar);
        tool.setTitle("About");
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        app_name = (TextView) findViewById(R.id.app_name_about);

        icon = (ImageView) findViewById(R.id.icon_from_about);
        developer_photo = (ImageView) findViewById(R.id.deveoper_photo);
        developer_photo2 = (ImageView) findViewById(R.id.deveoper_photo2);
        logo_back = (ImageView) findViewById(R.id.logo_back);
        sv = (ScrollView) findViewById(R.id.scroll_about_app);

        ratio = new Ratio(About.this);

        cover = (KenBurnsView) findViewById(R.id.backgound_cover);

        facebook = (Button) findViewById(R.id.facebook_link);
        facebook2 = (Button) findViewById(R.id.facebook_link2);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                About.this.startActivity(Facebook.getOpenFacebookIntent(About.this, "fb://profile/100008632337396", "http://mobile.facebook.com/profile.php?id=100008632337396"));
            }
        });

        facebook2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                About.this.startActivity(Facebook.getOpenFacebookIntent(About.this, "fb://profile/100010059974192", "http://mobile.facebook.com/profile.php?id=100010059974192"));
            }
        });

        ratio.calculateSize(icon, this, 160, 160);
        ratio.calculateSize(developer_photo, this, 140, 140);
        ratio.calculateSize(developer_photo2, this, 140, 140);

        ratio.calculateSize(logo_back, this, 720, 205);
        //ratio.calculateSize(cover, 720, 461);
    }

}
