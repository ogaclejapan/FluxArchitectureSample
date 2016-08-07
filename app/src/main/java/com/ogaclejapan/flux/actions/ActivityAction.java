package com.ogaclejapan.flux.actions;

import android.app.Activity;
import android.content.Intent;
import com.ogaclejapan.flux.components.activity.UserDetailActivity;
import com.ogaclejapan.flux.models.User;
import com.ogaclejapan.flux.modules.ActivityScope;
import javax.inject.Inject;

@ActivityScope
public class ActivityAction {

  private final Activity activity;

  @Inject public ActivityAction(Activity activity) {
    this.activity = activity;
  }

  public void showUserDetail(User user) {
    show(UserDetailActivity.newIntent(activity, user.login, user.avatar_url));
  }

  private void show(Intent intent) {
    activity.startActivity(intent);
  }
}
