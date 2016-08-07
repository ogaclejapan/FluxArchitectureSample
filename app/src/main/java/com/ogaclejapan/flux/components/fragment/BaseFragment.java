package com.ogaclejapan.flux.components.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ogaclejapan.flux.components.widget.Disposable;
import com.ogaclejapan.flux.components.widget.Disposer;
import com.ogaclejapan.flux.components.widget.Disposers;
import com.ogaclejapan.flux.modules.Components;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

public class BaseFragment extends Fragment implements Disposer.Group {

  private final Disposer.Group disposerGroup = Disposers.newGroup();

  @Inject RefWatcher refWatcher;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Components.from(getActivity()).inject(this);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    disposerGroup.dispose();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    refWatcher.watch(this);
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
