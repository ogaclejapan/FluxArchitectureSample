package com.ogaclejapan.flux.modules;

import android.app.Application;
import android.content.Context;

import com.ogaclejapan.flux.BuildConfig;
import com.ogaclejapan.flux.dispatcher.Dispatcher;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

  private final Application app;
  private final Dispatcher dispatcher;

  public AppModule(Application application) {
    this.app = application;
    this.dispatcher = new Dispatcher();
  }

  @Singleton
  @Provides
  Context provideContext() {
    return app;
  }

  @Singleton
  @Provides
  Application provideApplication() {
    return app;
  }

  @SuppressWarnings("ConstantConditions")
  @Singleton
  @Provides
  RefWatcher provideRefWatcher() {
    return BuildConfig.ENABLE_LEAKCANARY ? LeakCanary.install(app) : RefWatcher.DISABLED;
  }

  @Singleton
  @Provides
  Dispatcher provideDispatcher() {
    return dispatcher;
  }

}
