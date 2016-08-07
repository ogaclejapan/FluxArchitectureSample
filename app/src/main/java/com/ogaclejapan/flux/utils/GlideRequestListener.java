package com.ogaclejapan.flux.utils;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ogaclejapan.flux.BuildConfig;

import timber.log.Timber;

public class GlideRequestListener implements RequestListener<String, GlideDrawable> {

  public static final GlideRequestListener DEFAULT =
      BuildConfig.DEBUG ? new GlideRequestListener() : null;

  @Override
  public boolean onException(Exception e, String model, Target<GlideDrawable> target,
      boolean isFirstResource) {
    Timber.e("%s: %s", e.getMessage(), model);
    return false;
  }

  @Override
  public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
      boolean isFromMemoryCache, boolean isFirstResource) {
    Timber.v("isFromMemoryCache=%b: %s", isFromMemoryCache, model);
    return false;
  }
}
