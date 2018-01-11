package com.example.dell.maubindirectory.Object;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by User on 15/08/2017.
 */

public class GuestHouse {

    String name;
    String type;
    String address;
    String phone;
    double lati;
    double longti;
    ArrayList<Uri> image_list;

    public GuestHouse(String name, String type, String address, String phone, double lati, double longti, ArrayList<Uri> image_list) {
        this.name = name;
        this.type = type;
        this.address = address;
        this.phone = phone;
        this.lati = lati;
        this.longti = longti;
        this.image_list = image_list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public ArrayList<Uri> getImage_list() {
        return image_list;
    }

    public void setImage_list(ArrayList<Uri> image_list) {
        this.image_list = image_list;
    }
}
