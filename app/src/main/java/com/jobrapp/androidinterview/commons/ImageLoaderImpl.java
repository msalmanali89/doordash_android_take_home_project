package com.jobrapp.androidinterview.commons;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.jobrapp.androidinterview.interfaces.ImageLoader;

public class ImageLoaderImpl implements ImageLoader {

    @Override
    public void load(Context context, String path, int placeholder, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }



                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        DrawableImageViewTarget glideTarget = (DrawableImageViewTarget) target;
                        final ImageView iv = glideTarget.getView();
                        int width = iv.getMeasuredWidth();
                        int targetHeight = width * resource.getIntrinsicHeight() / resource.getIntrinsicWidth();
                        if(iv.getLayoutParams().height != targetHeight) {
                            //iv.getLayoutParams().height = targetHeight;
                            //iv.requestLayout();
                        }

                        iv.requestLayout();

                        return false;
                    }
                })
                .into(imageView);
    }

}
