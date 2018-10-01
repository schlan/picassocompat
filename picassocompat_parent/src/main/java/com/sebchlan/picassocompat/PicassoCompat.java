package com.sebchlan.picassocompat;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import java.io.File;
import java.util.concurrent.ExecutorService;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public interface PicassoCompat {

    void cancelRequest(@NonNull ImageView view);
    void cancelRequest(@NonNull TargetCompat targetCompat);
    void cancelTag(@NonNull Object tag);
    void pauseTag(@NonNull Object tag);
    void resumeTag(@NonNull Object tag);

    RequestCreatorCompat load(@Nullable Uri uri);
    RequestCreatorCompat load(@Nullable String path);
    RequestCreatorCompat load(@Nullable File file);
    RequestCreatorCompat load(@DrawableRes int resourceId);

    void invalidate(@Nullable Uri uri);
    void invalidate(@Nullable String path);
    void invalidate(@NonNull File file);
    void setIndicatorsEnabled(boolean enabled);
    boolean getIndicatorsEnabled();
    void setLoggingEnabled(boolean enabled);
    boolean isLoggingEnabled();
    void shutdown();

    interface Builder {
        Builder defaultBitmapConfig(@NonNull Bitmap.Config bitmapConfig);
        Builder client(@NonNull OkHttpClient client);
        Builder callFactory(@NonNull Call.Factory factory);
        Builder executor(@NonNull ExecutorService executorService);
        Builder listener(@NonNull ListenerCompat listener);
        Builder indicatorsEnabled(boolean enabled);
        Builder loggingEnabled(boolean enabled);
        PicassoCompat build();
    }

    enum LoadedFrom {
        MEMORY(Color.GREEN),
        DISK(Color.BLUE),
        NETWORK(Color.RED);

        final int debugColor;

        LoadedFrom(int debugColor) {
            this.debugColor = debugColor;
        }
    }

    enum Priority {
        LOW,
        NORMAL,
        HIGH
    }
}
