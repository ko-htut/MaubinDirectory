package com.example.dell.maubindirectory.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.dell.maubindirectory.R;
import com.example.dell.maubindirectory.activity.List_ATM;
import com.example.dell.maubindirectory.activity.List_bank;
import com.example.dell.maubindirectory.activity.List_market;
import com.example.dell.maubindirectory.activity.List_pagoda;
import com.example.dell.maubindirectory.activity.List_pump;
import com.example.dell.maubindirectory.activity.List_uni;
import com.example.dell.maubindirectory.support.MyBounceInterpolator;
import com.example.dell.maubindirectory.support.Ratio;

/**
 * Created by User on 12/08/2017.
 */

public class Fragment_item_list1 extends Fragment implements View.OnClickListener {

    Context context;

    ImageView pagoda_btn, bank_btn, atm_btn, uni_btn, pump_btn, market_btn;
    Ratio ratio;
    Animation myAnim;
    MyBounceInterpolator interpolator;

    public Fragment_item_list1() {

    }

    public Fragment_item_list1(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.from(context).inflate(R.layout.fragment_items1, container, false);

        ratio = new Ratio(v.getContext());
        pagoda_btn = (ImageView) v.findViewById(R.id.pagoda_btn);
        bank_btn = (ImageView) v.findViewById(R.id.bank_btn);
        atm_btn = (ImageView) v.findViewById(R.id.atm_btn);
        uni_btn = (ImageView) v.findViewById(R.id.uni_btn);
        pump_btn = (ImageView) v.findViewById(R.id.pump_btn);
        market_btn = (ImageView) v.findViewById(R.id.market_btn);


        View viewArr[] = {pagoda_btn, bank_btn, atm_btn, uni_btn, pump_btn, market_btn};
        ratio.calculateSize(viewArr, getActivity(), 200, 200);

        myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);

        pagoda_btn.setOnClickListener(this);
        bank_btn.setOnClickListener(this);
        atm_btn.setOnClickListener(this);
        uni_btn.setOnClickListener(this);
        pump_btn.setOnClickListener(this);
        market_btn.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagoda_btn:
                animationLoad(v, new Intent(context.getApplicationContext(), List_pagoda.class));
                break;
            case R.id.bank_btn:
                animationLoad(v, new Intent(context.getApplicationContext(), List_bank.class));
                break;
            case R.id.atm_btn:
                animationLoad(v, new Intent(context.getApplicationContext(), List_ATM.class));
                break;
            case R.id.uni_btn:
                animationLoad(v, new Intent(context.getApplicationContext(), List_uni.class));
                break;
            case R.id.pump_btn:
                animationLoad(v, new Intent(context.getApplicationContext(), List_pump.class));
                break;
            case R.id.market_btn:
                animationLoad(v, new Intent(context.getApplicationContext(), List_market.class));
                break;

        }
    }


    private void animationLoad(View v, final Intent i) {
        v.startAnimation(myAnim);
        interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
