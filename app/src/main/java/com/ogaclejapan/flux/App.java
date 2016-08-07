package com.ogaclejapan.flux;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.ogaclejapan.flux.modules.AppComponent;
import com.ogaclejapan.flux.modules.AppComponentProvider;
import com.ogaclejapan.flux.modules.AppModule;
import com.ogaclejapan.flux.modules.DaggerAppComponent;
import com.ogaclejapan.flux.modules.NetworkModule;

public class App extends Application implements AppComponentProvider {

  private AppComponent appComponent;

  @Override
  public AppComponent getAppComponent() {
    if (appComponent == null) {
      appComponent = DaggerAppComponent.builder()
          .appModule(getAppModule())
          .networkModule(getNetworkModule())
          .build();
    }
    return appComponent;
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    AndroidThreeTen.init(this);
  }

  @Override
  public final void onCreate() {
    super.onCreate();
    getAppComponent().inject(this);
    initialize();
  }

  @Override
  public void onTrimMemory(int level) {
    super.onTrimMemory(level);
    Glide.get(this).trimMemory(level);
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    Glide.get(this).clearMemory();
  }

  protected AppModule getAppModule() {
    return new AppModule(this);
  }

  protected NetworkModule getNetworkModule() {
    return new NetworkModule();
  }

  protected void initialize() {
    initTimber();
  }

  protected void initTimber() {
    //Timber.plant(new CrashlyticsTree());
  }

}
