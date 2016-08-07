package com.ogaclejapan.flux.components.widget;

import rx.Subscription;

public final class Disposers {

  public static Disposer.Group newGroup() {
    return new CompositeDisposer();
  }

  public static Disposer from(Disposable d) {
    return new Disposer() {
      @Override
      public void dispose() {
        d.dispose();
      }
    };
  }

  public static Disposer from(Subscription s) {
    return new Disposer() {
      @Override
      public void dispose() {
        if (!s.isUnsubscribed()) {
          s.unsubscribe();
        }
      }
    };
  }
}
