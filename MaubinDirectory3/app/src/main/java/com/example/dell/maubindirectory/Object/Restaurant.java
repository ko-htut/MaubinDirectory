package com.example.dell.maubindirectory.Object;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by User on 15/08/2017.
 */

public class Restaurant {
    String name;
    ArrayList<Uri> image_list;
    String type;
    String phone;
    String open;
    String close;
    String address;
    boolean order;
    double lati;
    double longu;

    public Restaurant(String name, ArrayList<Uri> image_list, String type, String phone, String open, String close, String address, boolean order, double lati, double longu) {
        this.name = name;
        this.image_list = image_list;
        this.type = type;
        this.phone = phone;
        this.open = open;
        this.close = close;
        this.address = address;
        this.order = order;
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

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
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
