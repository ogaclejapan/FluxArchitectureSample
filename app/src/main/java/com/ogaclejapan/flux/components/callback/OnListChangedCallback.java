package com.ogaclejapan.flux.components.callback;

import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;

import java.lang.ref.WeakReference;

public abstract class OnListChangedCallback<T>
    extends ObservableList.OnListChangedCallback<ObservableList<T>> {

  public static <T> OnListChangedCallback<T> delegateTo(RecyclerView.Adapter<?> adaptee) {
    return new RecyclerViewAdapterDelegate<>(adaptee);
  }

  @Override
  public abstract void onChanged(ObservableList<T> sender);

  @Override
  public void onItemRangeChanged(ObservableList<T> sender, int positionStart,
      int itemCount) {
    onChanged(sender);
  }

  @Override
  public void onItemRangeInserted(ObservableList<T> sender, int positionStart,
      int itemCount) {
    onChanged(sender);
  }

  @Override
  public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition,
      int itemCount) {
    onChanged(sender);
  }

  @Override
  public void onItemRangeRemoved(ObservableList<T> sender, int positionStart,
      int itemCount) {
    onChanged(sender);
  }

  /* package */ static class RecyclerViewAdapterDelegate<T> extends OnListChangedCallback<T> {

    WeakReference<RecyclerView.Adapter<?>> weakRef;

    RecyclerViewAdapterDelegate(RecyclerView.Adapter<?> adaptee) {
      this.weakRef = new WeakReference<>(adaptee);
    }

    @Override
    public void onChanged(ObservableList<T> sender) {
      final RecyclerView.Adapter<?> adaptee = weakRef.get();
      if (adaptee != null) {
        adaptee.notifyDataSetChanged();
      }
    }

    @Override
    public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
      final RecyclerView.Adapter<?> adaptee = weakRef.get();
      if (adaptee != null) {
        adaptee.notifyItemRangeChanged(positionStart, itemCount);
      }
    }

    @Override
    public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
      final RecyclerView.Adapter<?> adaptee = weakRef.get();
      if (adaptee != null) {
        adaptee.notifyItemRangeInserted(positionStart, itemCount);
      }
    }

    @Override
    public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition,
        int itemCount) {
      final RecyclerView.Adapter<?> adaptee = weakRef.get();
      if (adaptee != null) {
        adaptee.notifyItemMoved(fromPosition, toPosition);
      }
    }

    @Override
    public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
      final RecyclerView.Adapter<?> adaptee = weakRef.get();
      if (adaptee != null) {
        adaptee.notifyItemRangeRemoved(positionStart, itemCount);
      }
    }
  }

}
