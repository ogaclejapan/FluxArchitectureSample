package com.ogaclejapan.flux;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.timber.StethoTree;
import com.ogaclejapan.flux.modules.DebugNetworkModule;
import com.ogaclejapan.flux.modules.NetworkModule;

import timber.log.Timber;

public class DebugApp extends App {

  @Override public void onTrimMemory(int level) {
    super.onTrimMemory(level);
    Timber.w("onTrimMemory: level=%d", level);
  }

  @Override public void onLowMemory() {
    super.onLowMemory();
    Timber.w("onLowMemory");
  }

  @Override protected NetworkModule getNetworkModule() {
    return new DebugNetworkModule();
  }

  @Override protected void initialize() {
    super.initialize();
    initStetho();
    initDebugLifecycleCallbacks();
  }

  @Override protected void initTimber() {
    Timber.plant(new Timber.DebugTree());
    Timber.plant(new StethoTree());
  }

  protected void initStetho() {
    Stetho.initializeWithDefaults(this);
  }

  protected void initDebugLifecycleCallbacks() {
    registerActivityLifecycleCallbacks(new DebugActivityLifecycle());
  }
}
