package com.example.dell.maubindirectory.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Htet Aung Lin on 10/04/2017.
 */

public class Facebook {

    public static Intent getOpenFacebookIntent(Context context, String uri, String uribrowser) {

        try {

            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);

            return new Intent("android.intent.action.VIEW", Uri.parse(uri));

        } catch (Exception e) {

            return new Intent("android.intent.action.VIEW", Uri.parse(uribrowser));

        }

    }

}
