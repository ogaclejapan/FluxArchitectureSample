package com.ogaclejapan.flux;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpGlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.model.GlideUrl;
import com.ogaclejapan.flux.modules.Components;

import java.io.InputStream;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

public class AppGlideModule extends OkHttpGlideModule {

  @Inject OkHttpClient client;

  @Override
  public void applyOptions(Context context, GlideBuilder builder) {
    builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
  }

  @Override
  public void registerComponents(Context context, Glide glide) {
    Components.from(context).inject(this);
    glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
  }

}
