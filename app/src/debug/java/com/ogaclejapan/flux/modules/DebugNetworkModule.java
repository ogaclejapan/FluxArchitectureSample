package com.ogaclejapan.flux.modules;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class DebugNetworkModule extends NetworkModule {

  @Override public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
    OkHttpClient client = super.provideOkHttpClient(loggingInterceptor);
    return client.newBuilder().addNetworkInterceptor(new StethoInterceptor()).build();
  }
}
