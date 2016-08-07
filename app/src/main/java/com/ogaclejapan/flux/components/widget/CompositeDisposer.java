package com.ogaclejapan.flux.components.widget;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class CompositeDisposer extends Disposer implements Disposer.Group {

  private final Set<Disposable> target = new HashSet<>();

  @Override
  public void add(Disposable... disposables) {
    target.addAll(Arrays.asList(disposables));
  }

  @Override
  public void remove(Disposable... disposables) {
    target.removeAll(Arrays.asList(disposables));
  }

  @Override
  public void dispose() {
    if (target.size() > 0) {
      for (Disposable d : target) {
        d.dispose();
      }
      target.clear();
    }
  }
}
