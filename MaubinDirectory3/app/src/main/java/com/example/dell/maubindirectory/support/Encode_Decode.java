package com.example.dell.maubindirectory.support;

import android.util.Base64;

/**
 * Created by User on 15/08/2017.
 */

public class Encode_Decode {

    public Encode_Decode() {
    }

    public String encoding_string(String s,byte[] data) {
        String base64String = "";
        try {
            base64String = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {

        }
        return base64String;
    }

    public String decoding_string(String str) {
        String base64String = "";
        try {
            byte[] data = Base64.decode(str, Base64.DEFAULT);
            base64String = new String(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64String;
    }
}
