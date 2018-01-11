package com.example.dell.maubindirectory.Object;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by User on 15/08/2017.
 */

public class Hospital {

    String name;
    Uri image;
    String phone;
    String emergency;
    double lati;
    double lonu;
    String address;

    public Hospital(String name, Uri image, String phone, String emergency, double lati, double lonu, String address) {
        this.name = name;
        this.image = image;
        this.phone = phone;
        this.emergency = emergency;
        this.lati = lati;
        this.lonu = lonu;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public double getLonu() {
        return lonu;
    }

    public void setLonu(double lonu) {
        this.lonu = lonu;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
