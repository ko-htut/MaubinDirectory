package com.example.dell.maubindirectory.Object;

import android.net.Uri;

/**
 * Created by User on 15/08/2017.
 */

public class ATM {
    String name;
    Uri bank_logo;
    String open;
    double lati;
    double lonu;
    String address;

    public ATM(String name, Uri bank_logo, String open, double lati, double lonu, String address) {
        this.name = name;
        this.bank_logo = bank_logo;
        this.open = open;
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

    public Uri getBank_logo() {
        return bank_logo;
    }

    public void setBank_logo(Uri bank_logo) {
        this.bank_logo = bank_logo;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
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
