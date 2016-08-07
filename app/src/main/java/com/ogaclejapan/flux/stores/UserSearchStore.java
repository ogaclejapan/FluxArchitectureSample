package com.ogaclejapan.flux.stores;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.ogaclejapan.flux.components.callback.OnFieldChangedCallback;
import com.ogaclejapan.flux.components.callback.OnListChangedCallback;
import com.ogaclejapan.flux.components.widget.Disposer;
import com.ogaclejapan.flux.components.widget.Disposers;
import com.ogaclejapan.flux.dispatcher.Dispatcher;
import com.ogaclejapan.flux.events.SearchLoadingStateChangedEvent;
import com.ogaclejapan.flux.events.SearchResultListChangedEvent;
import com.ogaclejapan.flux.models.LoadingState;
import com.ogaclejapan.flux.models.User;
import com.ogaclejapan.flux.modules.ActivityLifecycleHook;
import com.ogaclejapan.flux.modules.ActivityScope;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

@ActivityScope
public class UserSearchStore {

  private final ObservableField<LoadingState> state = new ObservableField<>(LoadingState.LOADABLE);

  private final ObservableList<User> list = new ObservableArrayList<>();

  private String userId;
  private int nextPage = 0;

  @Inject
  public UserSearchStore(Dispatcher dispatcher, ActivityLifecycleHook hook) {
    hook.addOnCreate(() -> dispatcher.register(this));
    hook.addOnDestroy(() -> dispatcher.unregister(this));
  }

  public LoadingState getState() {
    return state.get();
  }

  public String getUserId() {
    return userId;
  }

  public int getItemCount() {
    return list.size();
  }

  public User getItemAt(int index) {
    return list.get(index);
  }

  public int getNextPage() {
    return nextPage;
  }

  public Disposer addOnLoadingStateChanged(OnFieldChangedCallback<LoadingState> cb) {
    state.addOnPropertyChangedCallback(cb);
    return Disposers.from(() -> removeOnLoadingStateChanged(cb));
  }

  public void removeOnLoadingStateChanged(OnFieldChangedCallback<LoadingState> cb) {
    state.removeOnPropertyChangedCallback(cb);
  }

  public Disposer addOnListChanged(OnListChangedCallback<User> cb) {
    list.addOnListChangedCallback(cb);
    return Disposers.from(() -> removeOnListChanged(cb));
  }

  public void removeOnListChanged(OnListChangedCallback<User> cb) {
    list.removeOnListChangedCallback(cb);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void on(SearchLoadingStateChangedEvent event) {
    state.set(event.state);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void on(SearchResultListChangedEvent event) {
    if (!event.userId.equals(userId)) {
      list.clear();
    }
    userId = event.userId;
    nextPage = event.nextPage;
    list.addAll(event.followers);
  }

}
