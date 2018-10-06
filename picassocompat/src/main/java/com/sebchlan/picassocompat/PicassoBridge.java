package com.sebchlan.picassocompat;

import android.content.Context;
import android.util.Log;

import java.util.Locale;

public class PicassoBridge {

    private static LibDetector.ImgLib PICASSO_VERSION = null;

    public static PicassoCompat init(Context context) {

        switch (detectLib()) {
            case Picasso252:
                return new PicassoCompat252(context);
            case Picasso271828:
                return new PicassoCompat271828();
        }

        throw new RuntimeException("Add Picasso to your project");
    }

    public static PicassoCompat.Builder builder(Context context) {

        switch (detectLib()) {
            case Picasso252:
                return new PicassoCompat252.Builder(context);
            case Picasso271828:
                return new PicassoCompat271828.Builder(context);
        }

        throw new RuntimeException("Add Picasso to your project");
    }


    private static LibDetector.ImgLib detectLib() {
        if (PICASSO_VERSION == null) {
            PICASSO_VERSION = LibDetector.detectLib();
            Log.d("PicassoCompat", String.format(Locale.ENGLISH, "Picasso detected: '%s'", PICASSO_VERSION));
        }

        return PICASSO_VERSION;
    }
}
