package com.sebchlan.picassocompat;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import okhttp3.Call;
import okhttp3.OkHttpClient;


public class PicassoCompat271828 implements PicassoCompat {

    private final Map<TargetCompat, Target> targetMap = new HashMap<>();
    private final Picasso picasso;

    PicassoCompat271828() {
        this(Picasso.get());
    }

    private PicassoCompat271828(Picasso picasso) {
        this.picasso = picasso;
    }

    @Override
    public void cancelRequest(@NonNull ImageView view) {
        picasso.cancelRequest(view);
    }

    @Override
    public void cancelRequest(@NonNull TargetCompat targetCompat) {
        if (targetMap.containsKey(targetCompat)) {
            picasso.cancelRequest(targetMap.get(targetCompat));
        }
    }

    @Override
    public void cancelTag(@NonNull Object tag) {
        picasso.cancelTag(tag);
    }

    @Override
    public void pauseTag(@NonNull Object tag) {
        picasso.pauseTag(tag);
    }

    @Override
    public void resumeTag(@NonNull Object tag) {
        picasso.resumeTag(tag);
    }

    @Override
    public RequestCreatorCompat load(@Nullable Uri uri) {
        return new RequestCreatorCompat271828(picasso, uri);
    }

    @Override
    public RequestCreatorCompat load(@Nullable String path) {
        return new RequestCreatorCompat271828(picasso, path);
    }

    @Override
    public RequestCreatorCompat load(@Nullable File file) {
        return new RequestCreatorCompat271828(picasso, file);
    }

    @Override
    public RequestCreatorCompat load(int resourceId) {
        return new RequestCreatorCompat271828(picasso, resourceId);
    }

    @Override
    public void invalidate(@Nullable Uri uri) {
        picasso.invalidate(uri);
    }

    @Override
    public void invalidate(@Nullable String path) {
        picasso.invalidate(path);
    }

    @Override
    public void invalidate(@NonNull File file) {
        picasso.invalidate(file);
    }

    @Override
    public void setIndicatorsEnabled(boolean enabled) {
        picasso.setIndicatorsEnabled(enabled);
    }

    @Override
    public boolean getIndicatorsEnabled() {
        return picasso.areIndicatorsEnabled();
    }

    @Override
    public void setLoggingEnabled(boolean enabled) {
        picasso.setLoggingEnabled(enabled);
    }

    @Override
    public boolean isLoggingEnabled() {
        return picasso.isLoggingEnabled();
    }

    @Override
    public void shutdown() {
        picasso.shutdown();
    }


    static class Builder implements PicassoCompat.Builder {

        private Picasso.Builder builder;

        Builder(Context context) {
            builder = new Picasso.Builder(context);
        }

        @Override
        public PicassoCompat.Builder defaultBitmapConfig(@NonNull Bitmap.Config bitmapConfig) {
            builder.defaultBitmapConfig(bitmapConfig);
            return this;
        }

        @Override
        public PicassoCompat.Builder client(@NonNull OkHttpClient client) {
            builder.downloader(new OkHttp3Downloader(client));
            return this;
        }

        @Override
        public PicassoCompat.Builder callFactory(@NonNull Call.Factory factory) {
            builder.downloader(new OkHttp3Downloader(factory));
            return this;
        }

        @Override
        public PicassoCompat.Builder executor(@NonNull ExecutorService executorService) {
            builder.executor(executorService);
            return this;
        }

        @Override
        public PicassoCompat.Builder listener(@NonNull final Listener listener) {
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    listener.onImageLoadFailed(uri, exception);
                }
            });
            return this;
        }

        @Override
        public PicassoCompat.Builder indicatorsEnabled(boolean enabled) {
            builder.indicatorsEnabled(enabled);
            return this;
        }

        @Override
        public PicassoCompat.Builder loggingEnabled(boolean enabled) {
            builder.loggingEnabled(enabled);
            return this;
        }

        @Override
        public PicassoCompat build() {
            return new PicassoCompat271828(builder.build());
        }
    }

    class RequestCreatorCompat271828 implements RequestCreatorCompat {

        private final RequestCreator requestCreator;

        RequestCreatorCompat271828(Picasso picasso, Uri uri) {
            requestCreator = picasso.load(uri);
        }

        RequestCreatorCompat271828(Picasso picasso, String path) {
            requestCreator = picasso.load(path);
        }

        RequestCreatorCompat271828(Picasso picasso, File file) {
            requestCreator = picasso.load(file);
        }

        RequestCreatorCompat271828(Picasso picasso, int resourceId) {
            requestCreator = picasso.load(resourceId);
        }

        @Override
        public RequestCreatorCompat noPlaceholder() {
            requestCreator.noPlaceholder();
            return this;
        }

        @Override
        public RequestCreatorCompat placeholder(int placeholderResId) {
            requestCreator.placeholder(placeholderResId);
            return this;
        }

        @Override
        public RequestCreatorCompat placeholder(Drawable placeholderDrawable) {
            requestCreator.placeholder(placeholderDrawable);
            return this;
        }

        @Override
        public RequestCreatorCompat error(int errorResId) {
            requestCreator.error(errorResId);
            return this;
        }

        @Override
        public RequestCreatorCompat error(Drawable errorDrawable) {
            requestCreator.error(errorDrawable);
            return this;
        }

        @Override
        public RequestCreatorCompat tag(Object tag) {
            requestCreator.tag(tag);
            return null;
        }

        @Override
        public RequestCreatorCompat fit() {
            requestCreator.fit();
            return this;
        }

        @Override
        public RequestCreatorCompat resizeDimen(int targetWidthResId, int targetHeightResId) {
            requestCreator.resizeDimen(targetWidthResId, targetHeightResId);
            return this;
        }

        @Override
        public RequestCreatorCompat resize(int targetWidth, int targetHeight) {
            requestCreator.resize(targetWidth, targetHeight);
            return this;
        }

        @Override
        public RequestCreatorCompat centerCrop() {
            requestCreator.centerCrop();
            return this;
        }

        @Override
        public RequestCreatorCompat centerInside() {
            requestCreator.centerInside();
            return this;
        }

        @Override
        public RequestCreatorCompat onlyScaleDown() {
            requestCreator.onlyScaleDown();
            return this;
        }

        @Override
        public RequestCreatorCompat rotate(float degrees) {
            requestCreator.rotate(degrees);
            return this;
        }

        @Override
        public RequestCreatorCompat rotate(float degrees, float pivotX, float pivotY) {
            requestCreator.rotate(degrees, pivotX, pivotY);
            return this;
        }

        @Override
        public RequestCreatorCompat config(Bitmap.Config config) {
            requestCreator.config(config);
            return this;
        }

        @Override
        public RequestCreatorCompat stableKey(String stableKey) {
            requestCreator.stableKey(stableKey);
            return this;
        }

        @Override
        public RequestCreatorCompat priority(PicassoCompat.Priority priority) {
            final Picasso.Priority picassoPriority;
            switch (priority) {
                case LOW:
                    picassoPriority = Picasso.Priority.LOW;
                    break;
                case HIGH:
                    picassoPriority = Picasso.Priority.HIGH;
                    break;
                case NORMAL:
                    picassoPriority = Picasso.Priority.NORMAL;
                    break;
                default:
                    picassoPriority = null;
                    break;
            }
            requestCreator.priority(picassoPriority);
            return this;
        }

        @Override
        public RequestCreatorCompat transform(TransformationCompat transformation) {
            requestCreator.transform(new TransformationConverter(transformation));
            return this;
        }

        @Override
        public RequestCreatorCompat transform(List<? extends TransformationCompat> transformations) {
            final List<Transformation> picassoTransformations = new ArrayList<>(transformations.size());
            for (TransformationCompat t : transformations) {
                picassoTransformations.add(new TransformationConverter(t));
            }
            requestCreator.transform(picassoTransformations);
            return this;
        }

        @Override
        public RequestCreatorCompat noFade() {
            requestCreator.noFade();
            return this;
        }

        @Override
        public Bitmap get() throws IOException {
            return requestCreator.get();
        }

        @Override
        public void fetch() {
            requestCreator.fetch();
        }

        @Override
        public void fetch(CallbackCompat callback) {
            requestCreator.fetch(new CallbackConverter(callback));
        }

        @Override
        public void into(TargetCompat target) {
            if (targetMap.containsKey(target)) {
                requestCreator.into(targetMap.get(target));
            } else {
                final TargetConverter targetConverter = new TargetConverter(target);
                targetMap.put(target, targetConverter);
                requestCreator.into(targetConverter);
            }
        }

        @Override
        public void into(RemoteViews remoteViews, int viewId, int notificationId, Notification notification) {
            requestCreator.into(remoteViews, viewId, notificationId, notification);
        }

        @Override
        public void into(RemoteViews remoteViews, int viewId, int[] appWidgetIds) {
            requestCreator.into(remoteViews, viewId, appWidgetIds);
        }

        @Override
        public void into(ImageView target) {
            requestCreator.into(target);
        }

        @Override
        public void into(ImageView target, CallbackCompat callback) {
            requestCreator.into(target, new CallbackConverter(callback));
        }
    }

    private static class TargetConverter implements Target {

        private final TargetCompat targetCompat;

        private TargetConverter(TargetCompat targetCompat) {
            this.targetCompat = targetCompat;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            final PicassoCompat.LoadedFrom source;
            switch (from) {
                case DISK:
                    source = LoadedFrom.DISK;
                    break;
                case MEMORY:
                    source = LoadedFrom.MEMORY;
                    break;
                case NETWORK:
                    source = LoadedFrom.NETWORK;
                    break;
                default:
                    source = null;
                    break;
            }

            if (targetCompat != null) {
                targetCompat.onBitmapLoaded(bitmap, source);
            }
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            if (targetCompat != null) {
                targetCompat.onBitmapFailed(errorDrawable);
            }
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            if (targetCompat != null) {
                targetCompat.onPrepareLoad(placeHolderDrawable);
            }
        }
    }

    private static class CallbackConverter implements Callback {

        private final CallbackCompat callbackCompat;

        private CallbackConverter(CallbackCompat callbackCompat) {
            this.callbackCompat = callbackCompat;
        }

        @Override
        public void onSuccess() {
            if (callbackCompat != null) {
                callbackCompat.onSuccess();
            }
        }

        @Override
        public void onError(Exception e) {
            if (callbackCompat != null) {
                callbackCompat.onError();
            }
        }

    }

    private static class TransformationConverter implements Transformation {

        private final TransformationCompat transformationCompat;

        TransformationConverter(TransformationCompat transformationCompat) {
            this.transformationCompat = transformationCompat;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            return transformationCompat.transform(source);
        }

        @Override
        public String key() {
            return transformationCompat.key();
        }
    }

}
