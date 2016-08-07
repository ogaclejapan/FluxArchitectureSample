package com.ogaclejapan.flux.components.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ogaclejapan.flux.components.widget.Disposable;
import com.ogaclejapan.flux.components.widget.Disposer;
import com.ogaclejapan.flux.components.widget.Disposers;
import com.ogaclejapan.flux.modules.ActivityComponent;
import com.ogaclejapan.flux.modules.ActivityComponentProvider;
import com.ogaclejapan.flux.modules.ActivityLifecycleHook;
import com.ogaclejapan.flux.modules.ActivityModule;
import com.ogaclejapan.flux.modules.Components;
import com.ogaclejapan.flux.modules.DaggerActivityComponent;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity
    implements ActivityComponentProvider, Disposer.Group {

  @Inject ActivityLifecycleHook activityLifecycleHook;
  @Inject RefWatcher refWatcher;

  private final Disposer.Group disposerGroup = Disposers.newGroup();

  private ActivityComponent activityComponent;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivityComponent().inject(this);
    activityLifecycleHook.dispatchOnCreate();
  }

  @Override
  protected void onStart() {
    super.onStart();
    activityLifecycleHook.dispatchOnStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
    activityLifecycleHook.dispatchOnResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    activityLifecycleHook.dispatchOnPause();
  }

  @Override
  protected void onStop() {
    super.onStop();
    activityLifecycleHook.dispatchOnStop();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    activityLifecycleHook.dispatchOnDestroy();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public ActivityComponent getActivityComponent() {
    if (activityComponent == null) {
      activityComponent = DaggerActivityComponent.builder()
          .appComponent(Components.from(getApplication()))
          .activityModule(getActivityModule())
          .build();
    }
    return activityComponent;
  }

  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }

  @Override
  public void add(Disposable... disposables) {
    disposerGroup.add(disposables);
  }

  @Override
  public void remove(Disposable... disposables) {
    disposerGroup.remove(disposables);
  }

  @Override
  public void dispose() {
    disposerGroup.dispose();
  }

}
