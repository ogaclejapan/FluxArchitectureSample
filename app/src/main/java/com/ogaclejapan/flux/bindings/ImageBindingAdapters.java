package com.ogaclejapan.flux.bindings;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ogaclejapan.flux.utils.GlideRequestListener;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ImageBindingAdapters {

  @BindingAdapter({ "image_crop_circle" })
  public static void loadImage(ImageView view, String url) {
    Glide.with(view.getContext())
        .load(url)
        .listener(GlideRequestListener.DEFAULT)
        .bitmapTransform(new CropCircleTransformation(view.getContext()))
        .into(view);
  }

}
