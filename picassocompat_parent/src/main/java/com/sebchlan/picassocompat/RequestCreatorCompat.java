package com.sebchlan.picassocompat;

import android.app.Notification;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.RemoteViews;

import java.io.IOException;
import java.lang.annotation.Target;
import java.util.List;

public interface RequestCreatorCompat {

    /**
     * Explicitly opt-out to having a placeholder set when calling {@code into}.
     * <p>
     * By default, Picasso will either set a supplied placeholder or clear the target
     * {@link ImageView} in order to ensure behavior in situations where views are recycled. This
     * method will prevent that behavior and retain any already set image.
     */
    RequestCreatorCompat noPlaceholder();

    /**
     * A placeholder drawable to be used while the image is being loaded. If the requested image is
     * not immediately available in the memory cache then this resource will be set on the target
     * {@link ImageView}.
     */
    RequestCreatorCompat placeholder(int placeholderResId);

    /**
     * A placeholder drawable to be used while the image is being loaded. If the requested image is
     * not immediately available in the memory cache then this resource will be set on the target
     * {@link ImageView}.
     * <p>
     * If you are not using a placeholder image but want to clear an existing image (such as when
     * used in an {@link android.widget.Adapter adapter}), pass in {@code null}.
     */
    RequestCreatorCompat placeholder(Drawable placeholderDrawable);

    /** An error drawable to be used if the request image could not be loaded. */
    RequestCreatorCompat error(int errorResId);

    /** An error drawable to be used if the request image could not be loaded. */
    RequestCreatorCompat error(Drawable errorDrawable);

    /**
     * Assign a tag to this request. Tags are an easy way to logically associate
     * related requests that can be managed together e.g. paused, resumed,
     * or canceled.
     * <p>
     * You can either use simple {@link String} tags or objects that naturally
     * define the scope of your requests within your app such as a
     * {@link android.content.Context}, an {@link android.app.Activity}, or a
     * {@link android.app.Fragment}.
     *
     * <strong>WARNING:</strong>: Picasso will keep a reference to the tag for
     * as long as this tag is paused and/or has active requests. Look out for
     * potential leaks.
     *
     * @see PicassoCompat#cancelTag(Object)
     * @see PicassoCompat#pauseTag(Object)
     * @see PicassoCompat#resumeTag(Object)
     */
    RequestCreatorCompat tag(Object tag);

    /**
     * Attempt to resize the image to fit exactly into the target {@link ImageView}'s bounds. This
     * will result in delayed execution of the request until the {@link ImageView} has been laid out.
     * <p>
     * <em>Note:</em> This method works only when your target is an {@link ImageView}.
     */
    RequestCreatorCompat fit();

    /** Resize the image to the specified dimension size. */
    RequestCreatorCompat resizeDimen(int targetWidthResId, int targetHeightResId);

    /** Resize the image to the specified size in pixels. */
    RequestCreatorCompat resize(int targetWidth, int targetHeight);

    /**
     * Crops an image inside of the bounds specified by {@link #resize(int, int)} rather than
     * distorting the aspect ratio. This cropping technique scales the image so that it fills the
     * requested bounds and then crops the extra.
     */
    RequestCreatorCompat centerCrop();

    /**
     * Centers an image inside of the bounds specified by {@link #resize(int, int)}. This scales
     * the image so that both dimensions are equal to or less than the requested bounds.
     */
    RequestCreatorCompat centerInside();

    /**
     * Only resize an image if the original image size is bigger than the target size
     * specified by {@link #resize(int, int)}.
     */
    RequestCreatorCompat onlyScaleDown();

    /** Rotate the image by the specified degrees. */
    RequestCreatorCompat rotate(float degrees);

    /** Rotate the image by the specified degrees around a pivot point. */
    RequestCreatorCompat rotate(float degrees, float pivotX, float pivotY);

    /**
     * Attempt to decode the image using the specified config.
     */
    RequestCreatorCompat config(Bitmap.Config config);

    /**
     * Sets the stable key for this request to be used instead of the URI or resource ID when
     * caching. Two requests with the same value are considered to be for the same resource.
     */
    RequestCreatorCompat stableKey(String stableKey);

    /**
     * Set the priority of this request.
     * <p>
     * This will affect the order in which the requests execute but does not guarantee it.
     * By default, all requests have {@link PicassoCompat.Priority#NORMAL} priority, except for
     * {@link #fetch()} requests, which have {@link PicassoCompat.Priority#LOW} priority by default.
     */
    RequestCreatorCompat priority(PicassoCompat.Priority priority);

    /**
     * Add a custom transformation to be applied to the image.
     * <p>
     * Custom transformations will always be run after the built-in transformations.
     */
    // TODO show example of calling resize after a transform in the javadoc
    RequestCreatorCompat transform(TransformationCompat transformation);

    /**
     * Add a list of custom transformations to be applied to the image.
     * <p>
     * Custom transformations will always be run after the built-in transformations.
     */
    RequestCreatorCompat transform(List<? extends TransformationCompat> transformations);


    /** Disable brief fade in of images loaded from the disk cache or network. */
    RequestCreatorCompat noFade();

    /**
     * Synchronously fulfill this request. Must not be called from the main thread.
     */
    Bitmap get() throws IOException;

    /**
     * Asynchronously fulfills the request without a {@link ImageView} or {@link Target}. This is
     * useful when you want to warm up the cache with an image.
     * <p>
     * <em>Note:</em> It is safe to invoke this method from any thread.
     */
    void fetch();

    /**
     * Asynchronously fulfills the request without a {@link ImageView} or {@link Target},
     * and invokes the target {@link CallbackCompat} with the result. This is useful when you want to warm
     * up the cache with an image.
     * <p>
     * <em>Note:</em> The {@link CallbackCompat} param is a strong reference and will prevent your
     * {@link android.app.Activity} or {@link android.app.Fragment} from being garbage collected
     * until the request is completed.
     */
    void fetch(CallbackCompat callback);

    /**
     * Asynchronously fulfills the request into the specified {@link Target}. In most cases, you
     * should use this when you are dealing with a custom {@link android.view.View View} or view
     * holder which should implement the {@link Target} interface.
     * <p>
     * Implementing on a {@link android.view.View View}:
     * <blockquote><pre>
     * public class ProfileView extends FrameLayout implements Target {
     *   {@literal @}Override public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
     *     setBackgroundDrawable(new BitmapDrawable(bitmap));
     *   }
     *
     *   {@literal @}Override public void onBitmapFailed() {
     *     setBackgroundResource(R.drawable.profile_error);
     *   }
     *
     *   {@literal @}Override public void onPrepareLoad(Drawable placeHolderDrawable) {
     *     frame.setBackgroundDrawable(placeHolderDrawable);
     *   }
     * }
     * </pre></blockquote>
     * Implementing on a view holder object for use inside of an adapter:
     * <blockquote><pre>
     * public class ViewHolder implements Target {
     *   public FrameLayout frame;
     *   public TextView name;
     *
     *   {@literal @}Override public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
     *     frame.setBackgroundDrawable(new BitmapDrawable(bitmap));
     *   }
     *
     *   {@literal @}Override public void onBitmapFailed() {
     *     frame.setBackgroundResource(R.drawable.profile_error);
     *   }
     *
     *   {@literal @}Override public void onPrepareLoad(Drawable placeHolderDrawable) {
     *     frame.setBackgroundDrawable(placeHolderDrawable);
     *   }
     * }
     * </pre></blockquote>
     * <p>
     * <em>Note:</em> This method keeps a weak reference to the {@link Target} instance and will be
     * garbage collected if you do not keep a strong reference to it. To receive callbacks when an
     * image is loaded use {@link #into(ImageView, Callback)}.
     */
    void into(TargetCompat target);

    /**
     * Asynchronously fulfills the request into the specified {@link RemoteViews} object with the
     * given {@code viewId}. This is used for loading bitmaps into a {@link Notification}.
     */
    void into(RemoteViews remoteViews, int viewId, int notificationId,
              Notification notification);

    /**
     * Asynchronously fulfills the request into the specified {@link RemoteViews} object with the
     * given {@code viewId}. This is used for loading bitmaps into all instances of a widget.
     */
    void into(RemoteViews remoteViews, int viewId, int[] appWidgetIds);
    /**
     * Asynchronously fulfills the request into the specified {@link ImageView}.
     * <p>
     * <em>Note:</em> This method keeps a weak reference to the {@link ImageView} instance and will
     * automatically support object recycling.
     */
    void into(ImageView target);

    /**
     * Asynchronously fulfills the request into the specified {@link ImageView} and invokes the
     * target {@link CallbackCompat} if it's not {@code null}.
     * <p>
     * <em>Note:</em> The {@link CallbackCompat} param is a strong reference and will prevent your
     * {@link android.app.Activity} or {@link android.app.Fragment} from being garbage collected. If
     * you use this method, it is <b>strongly</b> recommended you invoke an adjacent
     * {@link PicassoCompat#cancelRequest(ImageView)} call to prevent temporary leaking.
     */
    void into(ImageView target, CallbackCompat callback);
}
