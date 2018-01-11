package com.example.dell.maubindirectory.Object;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by User on 16/08/2017.
 */

public class Board {
    String name;
    String price_per_month;
    String gender;
    String phone;
    String address;
    double lati;
    double longu;

    public Board(String name, String price_per_month, String gender, String phone, String address, double lati, double longu) {
        this.name = name;
        this.price_per_month = price_per_month;
        this.gender = gender;
        this.phone = phone;
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

    public String getPrice_per_month() {
        return price_per_month;
    }

    public void setPrice_per_month(String price_per_month) {
        this.price_per_month = price_per_month;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
