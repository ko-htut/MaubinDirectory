package com.example.dell.maubindirectory.Object;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by User on 15/08/2017.
 */

public class Market {

    String name;
    ArrayList<Uri> logo;
    String address;
    String open_time;
    String close_time;
    String type;
    String phone;
    double lati;
    double longti;

    public Market(String name, ArrayList<Uri> logo, String address, String open_time, String close_time, String type, String phone, double lati, double longti) {
        this.name = name;
        this.logo = logo;
        this.address = address;
        this.open_time = open_time;
        this.close_time = close_time;
        this.type = type;
        this.phone = phone;
        this.lati = lati;
        this.longti = longti;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Uri> getLogo() {
        return logo;
    }

    public void setLogo(ArrayList<Uri> logo) {
        this.logo = logo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public double getLongti() {
        return longti;
    }

    public void setLongti(double longti) {
        this.longti = longti;
    }
}
