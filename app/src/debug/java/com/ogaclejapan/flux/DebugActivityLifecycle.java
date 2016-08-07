package com.ogaclejapan.flux;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import timber.log.Timber;

public class DebugActivityLifecycle implements Application.ActivityLifecycleCallbacks {

  private static final String LOG_FORMAT = "%s ⟳ %s";

  @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    Timber.v(LOG_FORMAT, activity.toString(), "onActivityCreated");
  }

  @Override public void onActivityStarted(Activity activity) {
    Timber.v(LOG_FORMAT, activity.toString(), "onActivityStarted");
  }

  @Override public void onActivityResumed(Activity activity) {
    Timber.v(LOG_FORMAT, activity.toString(), "onActivityResumed");
  }

  @Override public void onActivityPaused(Activity activity) {
    Timber.v(LOG_FORMAT, activity.toString(), "onActivityPaused");
  }

  @Override public void onActivityStopped(Activity activity) {
    Timber.v(LOG_FORMAT, activity.toString(), "onActivityStopped");
  }

  @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    Timber.v(LOG_FORMAT, activity.toString(), "onActivitySaveInstanceState");
  }

  @Override public void onActivityDestroyed(Activity activity) {
    Timber.v(LOG_FORMAT, activity.toString(), "onActivityDestroyed");
  }
}
