package com.example.dell.maubindirectory.Object;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by User on 15/08/2017.
 */

public class Other {
    String name;
    ArrayList<Uri> image_list;
    String address;
    double lati;
    double longu;

    public Other(String name, ArrayList<Uri> image_list, String address, double lati, double longu) {
        this.name = name;
        this.image_list = image_list;
        this.address = address;
        this.lati = lati;
        this.longu = longu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Uri> getImage_list() {
        return image_list;
    }

    public void setImage_list(ArrayList<Uri> image_list) {
        this.image_list = image_list;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public double getLongu() {
        return longu;
    }

    public void setLongu(double longu) {
        this.longu = longu;
    }
}
