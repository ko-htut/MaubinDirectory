package com.example.dell.maubindirectory.Object;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by User on 12/08/2017.
 */

public class Pagoda {
    String pago_name;
    ArrayList<Uri> pago_image_Arr;
    String start_year;
    double lati;
    double longu;
    String opentime;
    String closetime;
    String address;
    double height;

    public Pagoda(String pago_name, ArrayList<Uri> pago_image_Arr, String start_year, double lati, double longu, String opentime, String closetime, String address, double height) {
        this.pago_name = pago_name;
        this.pago_image_Arr = pago_image_Arr;
        this.start_year = start_year;
        this.lati = lati;
        this.longu = longu;
        this.opentime = opentime;
        this.closetime = closetime;
        this.address = address;
        this.height = height;
    }

    public String getPago_name() {
        return pago_name;
    }

    public void setPago_name(String pago_name) {
        this.pago_name = pago_name;
    }

    public ArrayList<Uri> getPago_image_Arr() {
        return pago_image_Arr;
    }

    public void setPago_image_Arr(ArrayList<Uri> pago_image_Arr) {
        this.pago_image_Arr = pago_image_Arr;
    }

    public String getStart_year() {
        return start_year;
    }

    public void setStart_year(String start_year) {
        this.start_year = start_year;
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

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getClosetime() {
        return closetime;
    }

    public void setClosetime(String closetime) {
        this.closetime = closetime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}

