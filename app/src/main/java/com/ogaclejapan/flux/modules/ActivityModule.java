package com.ogaclejapan.flux.modules;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

  private Activity activity;
  private FragmentManager fm;

  public ActivityModule(AppCompatActivity activity) {
    this.activity = activity;
    this.fm = activity.getSupportFragmentManager();
  }

  @ActivityScope
  @Provides
  public Activity provideActivity() {
    return activity;
  }

  @ActivityScope
  @Provides
  public FragmentManager provideFragmentManager() {
    return fm;
  }

  @ActivityScope
  @Provides
  public ActivityLifecycleHook provideActivityLifecycleHook() {
    return new ActivityLifecycleHook();
  }

}
