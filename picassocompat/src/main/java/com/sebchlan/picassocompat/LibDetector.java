package com.sebchlan.picassocompat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Method;

class LibDetector {

    private final static String PICASSO = "com.squareup.picasso.Picasso";
    private final static String PICASSO_INIT_271828 = "get";
    private final static String PICASSO_INIT_252 = "with";

    static ImgLib detectLib() {

        final Class<?> picasso = getClass(PICASSO);

        if (picasso != null) {
            final Method[] declaredMethods = picasso.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.getName().equals(PICASSO_INIT_252)) {
                    return ImgLib.Picasso252;

                } else if (method.getName().equals(PICASSO_INIT_271828)) {
                    return ImgLib.Picasso271828;
                }
            }

        }

        return ImgLib.None;
    }

    @Nullable
    private static Class<?> getClass(@NonNull String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            return null;
        }
    }

    enum ImgLib {
        Picasso252, Picasso271828, None
    }

}
