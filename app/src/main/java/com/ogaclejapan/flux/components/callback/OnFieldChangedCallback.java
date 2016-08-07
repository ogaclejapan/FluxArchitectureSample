package com.ogaclejapan.flux.components.callback;

import android.databinding.Observable;
import android.databinding.ObservableField;

public abstract class OnFieldChangedCallback<T> extends Observable.OnPropertyChangedCallback {

  @SuppressWarnings("unchecked")
  @Override
  public void onPropertyChanged(Observable sender, int propertyId) {
    if (sender instanceof ObservableField) {
      onChanged(((ObservableField<T>) sender).get());
    } else {
      throw new IllegalStateException("Must be ObservableField");
    }
  }

  public abstract void onChanged(T value);
}
