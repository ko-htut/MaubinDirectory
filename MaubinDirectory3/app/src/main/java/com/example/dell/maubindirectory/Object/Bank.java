package com.example.dell.maubindirectory.Object;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by User on 13/08/2017.
 */

public class Bank {
    String name;
    ArrayList<Uri> bank_image_list;
    String opentime;
    String closetime;
    String phone;
    double lati;
    double longu;
    String address;


    public Bank(String name, ArrayList<Uri> bank_image_list, String opentime, String closetime, String phone, double lati, double longu, String address) {
        this.name = name;
        this.bank_image_list = bank_image_list;
        this.opentime = opentime;
        this.closetime = closetime;
        this.phone = phone;
        this.lati = lati;
        this.longu = longu;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Uri> getBank_image_list() {
        return bank_image_list;
    }

    public void setBank_image_list(ArrayList<Uri> bank_image_list) {
        this.bank_image_list = bank_image_list;
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

    public double getLongu() {
        return longu;
    }

    public void setLongu(double longu) {
        this.longu = longu;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
