package com.ogaclejapan.flux.modules;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class Components {

  public static AppComponent from(Context context) {
    return provider(context.getApplicationContext()).getAppComponent();
  }

  public static AppComponent from(Application application) {
    return provider(application).getAppComponent();
  }

  public static ActivityComponent from(Activity activity) {
    return provider(activity).getActivityComponent();
  }

  private static AppComponentProvider provider(Context context) {
    return AppComponentProvider.class.cast(context);
  }

  private static AppComponentProvider provider(Application application) {
    return AppComponentProvider.class.cast(application);
  }

  private static ActivityComponentProvider provider(Activity activity) {
    return ActivityComponentProvider.class.cast(activity);
  }

}

