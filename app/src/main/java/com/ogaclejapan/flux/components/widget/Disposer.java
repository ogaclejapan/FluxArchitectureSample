package com.ogaclejapan.flux.components.widget;

public abstract class Disposer implements Disposable {

  public void addTo(Group group) {
    group.add(this);
  }

  @Override
  public abstract void dispose();

  public interface Group extends Disposable {

    void add(Disposable... disposables);

    void remove(Disposable... disposables);
  }
}
