package com.sebchlan.picassocompat;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.concurrent.ExecutorService;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public interface PicassoCompat {

    /** Cancel any existing requests for the specified target {@link ImageView}. */
    void cancelRequest(@NonNull ImageView view);

    /** Cancel any existing requests for the specified {@link TargetCompat} instance. */
    void cancelRequest(@NonNull TargetCompat targetCompat);

    /**
     * Cancel any existing requests with given tag. You can set a tag
     * on new requests with {@link RequestCreatorCompat#tag(Object)}.
     *
     * @see RequestCreatorCompat#tag(Object)
     */
    void cancelTag(@NonNull Object tag);

    /**
     * Pause existing requests with the given tag. Use {@link #resumeTag(Object)}
     * to resume requests with the given tag.
     *
     * @see #resumeTag(Object)
     * @see RequestCreatorCompat#tag(Object)
     */
    void pauseTag(@NonNull Object tag);

    /**
     * Resume paused requests with the given tag. Use {@link #pauseTag(Object)}
     * to pause requests with the given tag.
     *
     * @see #pauseTag(Object)
     * @see RequestCreatorCompat#tag(Object)
     */
    void resumeTag(@NonNull Object tag);

    /**
     * Start an image request using the specified URI.
     * <p>
     * Passing {@code null} as a {@code uri} will not trigger any request but will set a placeholder,
     * if one is specified.
     *
     * @see #load(File)
     * @see #load(String)
     * @see #load(int)
     */
    RequestCreatorCompat load(@Nullable Uri uri);

    /**
     * Start an image request using the specified path. This is a convenience method for calling
     * {@link #load(Uri)}.
     * <p>
     * This path may be a remote URL, file resource (prefixed with {@code file:}), content resource
     * (prefixed with {@code content:}), or android resource (prefixed with {@code android.resource:}.
     * <p>
     * Passing {@code null} as a {@code path} will not trigger any request but will set a
     * placeholder, if one is specified.
     *
     * @see #load(Uri)
     * @see #load(File)
     * @see #load(int)
     * @throws IllegalArgumentException if {@code path} is empty or blank string.
     */
    RequestCreatorCompat load(@Nullable String path);

    /**
     * Start an image request using the specified image file. This is a convenience method for
     * calling {@link #load(Uri)}.
     * <p>
     * Passing {@code null} as a {@code file} will not trigger any request but will set a
     * placeholder, if one is specified.
     * <p>
     * Equivalent to calling {@link #load(Uri) load(Uri.fromFile(file))}.
     *
     * @see #load(Uri)
     * @see #load(String)
     * @see #load(int)
     */
    RequestCreatorCompat load(@Nullable File file);

    /**
     * Start an image request using the specified drawable resource ID.
     *
     * @see #load(Uri)
     * @see #load(String)
     * @see #load(File)
     */
    RequestCreatorCompat load(@DrawableRes int resourceId);

    /**
     * Invalidate all memory cached images for the specified {@code uri}.
     *
     * @see #invalidate(String)
     * @see #invalidate(File)
     */
    void invalidate(@Nullable Uri uri);

    /**
     * Invalidate all memory cached images for the specified {@code path}. You can also pass a
     * {@linkplain RequestCreatorCompat#stableKey stable key}.
     *
     * @see #invalidate(Uri)
     * @see #invalidate(File)
     */
    void invalidate(@Nullable String path);

    /**
     * Invalidate all memory cached images for the specified {@code file}.
     *
     * @see #invalidate(Uri)
     * @see #invalidate(String)
     */
    void invalidate(@NonNull File file);

    /** Toggle whether to display debug indicators on images. */
    void setIndicatorsEnabled(boolean enabled);

    /** {@code true} if debug indicators should are displayed on images. */
    boolean getIndicatorsEnabled();

    /**
     * Toggle whether debug logging is enabled.
     * <p>
     * <b>WARNING:</b> Enabling this will result in excessive object allocation. This should be only
     * be used for debugging Picasso behavior. Do NOT pass {@code BuildConfig.DEBUG}.
     */
    void setLoggingEnabled(boolean enabled);

    /** {@code true} if debug logging is enabled. */
    boolean isLoggingEnabled();

    /** Stops this instance from accepting further requests. */
    void shutdown();

    /** Start building a new {@link Picasso} instance. */
    interface Builder {

        /**
         * Specify the default {@link Bitmap.Config} used when decoding images. This can be overridden
         * on a per-request basis using {@link RequestCreatorCompat#config(Bitmap.Config) config(..)}.
         */
        Builder defaultBitmapConfig(@NonNull Bitmap.Config bitmapConfig);

        /** Specify the {@link OkHttpClient} that will be used for downloading images. */
        Builder client(@NonNull OkHttpClient client);

        /** Specify the {@link Call.Factory} that will be used for downloading images. */
        Builder callFactory(@NonNull Call.Factory factory);

        /**
         * Specify the executor service for loading images in the background.
         * <p>
         * Note: Calling {@link Picasso#shutdown() shutdown()} will not shutdown supplied executors.
         */
        Builder executor(@NonNull ExecutorService executorService);

        /** Specify a listener for interesting events. */
        Builder listener(@NonNull Listener listener);

        /** Toggle whether to display debug indicators on images. */
        Builder indicatorsEnabled(boolean enabled);

        /**
         * Toggle whether debug logging is enabled.
         * <p>
         * <b>WARNING:</b> Enabling this will result in excessive object allocation. This should be only
         * be used for debugging purposes. Do NOT pass {@code BuildConfig.DEBUG}.
         */
        Builder loggingEnabled(boolean enabled);

        /** Create the {@link Picasso} instance. */
        PicassoCompat build();
    }

    /** Describes where the image was loaded from. */
    enum LoadedFrom {
        MEMORY(Color.GREEN),
        DISK(Color.BLUE),
        NETWORK(Color.RED);

        final int debugColor;

        LoadedFrom(int debugColor) {
            this.debugColor = debugColor;
        }
    }

    /**
     * The priority of a request.
     */
    enum Priority {
        LOW,
        NORMAL,
        HIGH
    }

    /** Callbacks for Picasso events. */
    interface Listener {
        /**
         * Invoked when an image has failed to load. This is useful for reporting image failures to a
         * remote analytics service, for example.
         */
        void onImageLoadFailed(@NonNull Uri uri, @NonNull Exception exception);
    }

}
