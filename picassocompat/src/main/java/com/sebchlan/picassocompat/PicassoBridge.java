package com.sebchlan.picassocompat;

import android.content.Context;
import android.util.Log;

import java.util.Locale;

public class PicassoBridge {

    private static LibDetector.ImgLib PICASSO_VERSION = null;

    public static PicassoCompat init(Context context) {

        if (PICASSO_VERSION == null) {
            PICASSO_VERSION = LibDetector.detectLib();
            Log.d("PicassoCompat", String.format(Locale.ENGLISH, "Picasso detected: '%s'", PICASSO_VERSION));
        }

        switch (PICASSO_VERSION) {
            case Picasso252:
                return new PicassoCompat252(context);
            case Picasso271828:
                return new PicassoCompat271828(context);
        }

        throw new RuntimeException("Add Picasso to your project");
    }
}
