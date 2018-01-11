package com.example.dell.maubindirectory.Object;

import android.net.Uri;

/**
 * Created by User on 15/08/2017.
 */

public class Office {
    Uri logo;
    String name;
    String phone;
    String address;
    double lati;
    double longu;

    public Office(Uri logo, String name, String phone, String address, double lati, double longu) {
        this.logo = logo;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.lati = lati;
        this.longu = longu;
    }

    public Uri getLogo() {
        return logo;
    }

    public void setLogo(Uri logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
