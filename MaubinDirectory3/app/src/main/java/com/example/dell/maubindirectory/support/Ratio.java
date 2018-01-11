package com.example.dell.maubindirectory.support;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Htet Aung Lin on 28/11/2016.
 */

public class Ratio {
    static int mywidth = 720;
    static int myheight = 1280;
    private static int imagewidth;
    Ratio ratio;
    Context context;

    int width = 0, height = 0;

    public Ratio() {
        super();
    }

    public Ratio(Context _context) {
        super();
        this.context = _context;
    }

    public static int getwidth(int screenWidth, int i) {
        float res = (float) screenWidth / mywidth;
        int width = (int) (res * i);
        return width;
    }


    public static int getheight(int screenHeight, int j) {
        float res = (float) screenHeight / myheight;
        int width = (int) (res * j);
        return width;
    }

    public static int getimagewidth(int slotwidth) {
        float res = (float) slotwidth / 3;
        int minus = (int) (res - 20);
        return minus;
    }

    public void calculateSize(View view, Activity activity, int view_width, int view_height) {
        this.ratio = new Ratio();
        DisplayMetrics matrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(matrics);
        ViewGroup.LayoutParams fix = view.getLayoutParams();
        width = matrics.widthPixels;
        height = matrics.heightPixels;
        fix.width = ratio.getwidth(width, view_width);
        fix.height = ratio.getheight(height, view_height);
        view.setLayoutParams(fix);
    }

    public void calculateSize(View[] viewArr, Activity activity, int view_width, int view_height) {

        for (View view : viewArr) {
            this.ratio = new Ratio();
            DisplayMetrics matrics = new DisplayMetrics();
//            Activity activitys = (Activity) context;
            activity.getWindowManager().getDefaultDisplay().getMetrics(matrics);
            ViewGroup.LayoutParams fix = view.getLayoutParams();
            width = matrics.widthPixels;
            height = matrics.heightPixels;
            fix.width = ratio.getwidth(width, view_width);
            fix.height = ratio.getheight(height, view_height);
            view.setLayoutParams(fix);
        }
    }
}
