package com.ogaclejapan.flux.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ogaclejapan.flux.BuildConfig;
import com.ogaclejapan.flux.api.GitHubApi;
import com.ogaclejapan.flux.api.GitHubApiClient;

import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

@Module
public class NetworkModule {

  @Singleton
  @Provides
  Gson provideGson() {
    return new GsonBuilder()
        // https://github.com/google/gson/issues/648
        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
        .create();
  }

  @Singleton
  @Provides
  HttpLoggingInterceptor provideOkHttpLoggingInterceptor() {
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    if (BuildConfig.DEBUG) {
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
    }
    return loggingInterceptor;
  }

  @Singleton
  @Provides
  OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
    return new OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor)
        .connectTimeout(10L, TimeUnit.SECONDS)
        .writeTimeout(10L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .build();
  }

  @Singleton
  @Provides
  Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
    return new Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build();
  }

  @Singleton
  @Provides
  GitHubApi provideGitHubApi(Retrofit retrofit) {
    return new GitHubApiClient(retrofit);
  }

}
