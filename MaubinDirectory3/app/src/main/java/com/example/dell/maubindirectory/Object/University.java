package com.example.dell.maubindirectory.Object;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by User on 13/08/2017.
 */

public class University {
    String name;
    String rector;
    ArrayList<Uri> uni_image;
    String first_sem;
    String second_sem;
    String major;
    String phone;
    double lati;
    double longu;
    String address;

    public University(String name, String rector, ArrayList<Uri> uni_image, String first_sem, String second_sem, String major, String phone, double lati, double longu, String address) {
        this.name = name;
        this.rector = rector;
        this.uni_image = uni_image;
        this.first_sem = first_sem;
        this.second_sem = second_sem;
        this.major = major;
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

    public String getRector() {
        return rector;
    }

    public void setRector(String rector) {
        this.rector = rector;
    }

    public ArrayList<Uri> getUni_image() {
        return uni_image;
    }

    public void setUni_image(ArrayList<Uri> uni_image) {
        this.uni_image = uni_image;
    }

    public String getFirst_sem() {
        return first_sem;
    }

    public void setFirst_sem(String first_sem) {
        this.first_sem = first_sem;
    }

    public String getSecond_sem() {
        return second_sem;
    }

    public void setSecond_sem(String second_sem) {
        this.second_sem = second_sem;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
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
