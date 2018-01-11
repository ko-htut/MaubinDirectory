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
import com.example.dell.maubindirectory.activity.List_bank;
import com.example.dell.maubindirectory.activity.List_board;
import com.example.dell.maubindirectory.activity.List_guest;
import com.example.dell.maubindirectory.activity.List_hospital;
import com.example.dell.maubindirectory.activity.List_office;
import com.example.dell.maubindirectory.activity.List_other;
import com.example.dell.maubindirectory.activity.List_pagoda;
import com.example.dell.maubindirectory.activity.List_res;
import com.example.dell.maubindirectory.activity.List_uni;
import com.example.dell.maubindirectory.support.MyBounceInterpolator;
import com.example.dell.maubindirectory.support.Ratio;

/**
 * Created by User on 12/08/2017.
 */

public class Fragment_item_list2 extends Fragment implements View.OnClickListener {

    Context context;

    ImageView guest_btn, restaurant_btn, office_btn, relax_btn, hospital_btn, boarding_btn;
    Ratio ratio;
    Animation myAnim;
    MyBounceInterpolator interpolator;

    public Fragment_item_list2() {

    }

    public Fragment_item_list2(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.from(context).inflate(R.layout.fragment_items2, container, false);

        ratio = new Ratio(v.getContext());
        guest_btn = (ImageView) v.findViewById(R.id.btn1);
        restaurant_btn = (ImageView) v.findViewById(R.id.btn2);
        relax_btn = (ImageView) v.findViewById(R.id.btn3);
        office_btn = (ImageView) v.findViewById(R.id.btn4);
        hospital_btn = (ImageView) v.findViewById(R.id.btn5);
        boarding_btn = (ImageView) v.findViewById(R.id.btn6);

        View viewArr[] = {guest_btn, restaurant_btn, office_btn, relax_btn, hospital_btn, boarding_btn};
        ratio.calculateSize(viewArr, getActivity(), 200, 200);

        myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);

        guest_btn.setOnClickListener(this);
        restaurant_btn.setOnClickListener(this);
        office_btn.setOnClickListener(this);
        relax_btn.setOnClickListener(this);
        hospital_btn.setOnClickListener(this);
        boarding_btn.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                animationLoad(v, new Intent(context.getApplicationContext(), List_guest.class));
                break;
            case R.id.btn2:
                animationLoad(v, new Intent(context.getApplicationContext(), List_res.class));
                break;
            case R.id.btn3:
                animationLoad(v, new Intent(context.getApplicationContext(), List_other.class));
                break;
            case R.id.btn4:
                animationLoad(v, new Intent(context.getApplicationContext(), List_office.class));
                break;
            case R.id.btn5:
                animationLoad(v, new Intent(context.getApplicationContext(), List_hospital.class));
                break;
            case R.id.btn6:
                animationLoad(v, new Intent(context.getApplicationContext(), List_board.class));
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
