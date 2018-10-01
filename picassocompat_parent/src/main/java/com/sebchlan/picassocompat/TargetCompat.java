package com.sebchlan.picassocompat;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public interface TargetCompat {
    /**
     * Callback when an image has been successfully loaded.
     * <p>
     * <strong>Note:</strong> You must not recycle the bitmap.
     */
    void onBitmapLoaded(Bitmap bitmap, PicassoCompat.LoadedFrom from);

    /**
     * Callback indicating the image could not be successfully loaded.
     */
    void onBitmapFailed(Drawable errorDrawable);

    /**
     * Callback invoked right before your request is submitted.
     */
    void onPrepareLoad(Drawable placeHolderDrawable);
}
