package com.sebchlan.picassocompat;

import android.net.Uri;
import android.support.annotation.NonNull;

public interface ListenerCompat {
    /**
     * Invoked when an image has failed to load. This is useful for reporting image failures to a
     * remote analytics service, for example.
     */
    void onImageLoadFailed(@NonNull Uri uri, @NonNull Exception exception);
}