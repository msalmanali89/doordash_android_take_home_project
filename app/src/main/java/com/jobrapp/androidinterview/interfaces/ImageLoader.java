package com.jobrapp.androidinterview.interfaces;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

public interface ImageLoader {

    void load(Context context,
              String path,
              @DrawableRes int placeholder,
              ImageView imageView);
}

