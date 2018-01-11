package com.example.dell.maubindirectory.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12/08/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragement_list = new ArrayList<Fragment>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return fragement_list.get(position);
    }

    @Override
    public int getCount() {
        return fragement_list.size();
    }

    public void addFrag(Fragment fragment) {
        fragement_list.add(fragment);
    }
}
