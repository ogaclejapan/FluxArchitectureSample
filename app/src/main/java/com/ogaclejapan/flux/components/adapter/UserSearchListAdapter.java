package com.ogaclejapan.flux.components.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ogaclejapan.flux.actions.ActivityAction;
import com.ogaclejapan.flux.components.callback.OnListChangedCallback;
import com.ogaclejapan.flux.databinding.RowSearchBinding;
import com.ogaclejapan.flux.models.User;
import com.ogaclejapan.flux.modules.ActivityLifecycleHook;
import com.ogaclejapan.flux.modules.ActivityScope;
import com.ogaclejapan.flux.stores.UserSearchStore;

import javax.inject.Inject;

@ActivityScope
public class UserSearchListAdapter extends RecyclerView.Adapter<UserSearchListAdapter.ViewHolder> {

  @Inject ActivityAction activityAction;

  private final UserSearchStore store;

  @Inject
  public UserSearchListAdapter(UserSearchStore store, ActivityLifecycleHook hook) {
    this.store = store;
    OnListChangedCallback<User> cb = OnListChangedCallback.delegateTo(this);
    hook.addOnCreate(() -> store.addOnListChanged(cb));
    hook.addOnDestroy(() -> store.removeOnListChanged(cb));
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    return new ViewHolder(RowSearchBinding.inflate(inflater, parent, false));
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    User user = store.getItemAt(position);
    holder.itemView.setOnClickListener(v -> activityAction.showUserDetail(user));
    holder.binding.setUser(user);
    holder.binding.executePendingBindings();
  }

  @Override
  public int getItemCount() {
    return store.getItemCount();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    final RowSearchBinding binding;

    ViewHolder(RowSearchBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }

}
