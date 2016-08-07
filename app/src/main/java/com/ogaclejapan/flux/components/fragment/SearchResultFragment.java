package com.ogaclejapan.flux.components.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.ogaclejapan.flux.R;
import com.ogaclejapan.flux.actions.UserSearchAction;
import com.ogaclejapan.flux.components.adapter.UserSearchListAdapter;
import com.ogaclejapan.flux.components.callback.OnFieldChangedCallback;
import com.ogaclejapan.flux.components.callback.OnListChangedCallback;
import com.ogaclejapan.flux.databinding.FragmentSearchResultBinding;
import com.ogaclejapan.flux.models.LoadingState;
import com.ogaclejapan.flux.models.User;
import com.ogaclejapan.flux.modules.Components;
import com.ogaclejapan.flux.stores.UserSearchStore;

import javax.inject.Inject;

public class SearchResultFragment extends BaseFragment implements MugenCallbacks {

  private static final int LOAD_OFFSET = 10;

  @Inject UserSearchAction userSearchAction;
  @Inject UserSearchStore userSearchStore;
  @Inject UserSearchListAdapter userSearchListAdapter;

  private final OnFieldChangedCallback<LoadingState> loadingStateChanged =
      new OnFieldChangedCallback<LoadingState>() {
        @Override
        public void onChanged(LoadingState state) {
          bdg.setIsLoading(state == LoadingState.LOADING);
        }
      };

  private final OnListChangedCallback<User> resultListChanged =
      new OnListChangedCallback<User>() {
        @Override
        public void onChanged(ObservableList<User> sender) {
          bdg.setItemCount(sender.size());
        }
      };

  private FragmentSearchResultBinding bdg;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Components.from(getActivity()).inject(this);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_search_result, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    bdg = DataBindingUtil.bind(view);

    bdg.searchResultList.setLayoutManager(new LinearLayoutManager(getContext()));
    bdg.searchResultList.setAdapter(userSearchListAdapter);
    Mugen.with(bdg.searchResultList, this).start().setLoadMoreOffset(LOAD_OFFSET);

    userSearchStore.addOnLoadingStateChanged(loadingStateChanged).addTo(this);
    userSearchStore.addOnListChanged(resultListChanged).addTo(this);
  }

  @Override
  public void onLoadMore() {
    userSearchAction.findFollower(userSearchStore.getUserId(), userSearchStore.getNextPage());
  }

  @Override
  public boolean isLoading() {
    return userSearchStore.getState() == LoadingState.LOADING;
  }

  @Override
  public boolean hasLoadedAllItems() {
    LoadingState state = userSearchStore.getState();
    return state == LoadingState.FINISHED || state == LoadingState.CANCELED;
  }

}
