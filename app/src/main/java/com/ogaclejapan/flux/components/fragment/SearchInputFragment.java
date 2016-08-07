package com.ogaclejapan.flux.components.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.annimon.stream.Optional;
import com.ogaclejapan.flux.R;
import com.ogaclejapan.flux.actions.UserSearchAction;
import com.ogaclejapan.flux.components.callback.OnFieldChangedCallback;
import com.ogaclejapan.flux.databinding.FragmentSearchInputBinding;
import com.ogaclejapan.flux.models.LoadingState;
import com.ogaclejapan.flux.modules.Components;
import com.ogaclejapan.flux.stores.UserSearchStore;

import javax.inject.Inject;

public class SearchInputFragment extends BaseFragment {

  @Inject UserSearchStore userSearchStore;
  @Inject UserSearchAction userSearchAction;

  private final OnFieldChangedCallback<LoadingState> loadingStateChanged =
      new OnFieldChangedCallback<LoadingState>() {
        @Override public void onChanged(LoadingState state) {
          binding.searchButton.setEnabled(state != LoadingState.LOADING);
        }
      };

  private FragmentSearchInputBinding binding;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Components.from(getActivity()).inject(this);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_search_input, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    binding = DataBindingUtil.bind(view);
    binding.searchButton.setOnClickListener(v -> {
      hideKeyboard(binding.searchInputText.getWindowToken());
      Optional.ofNullable(binding.searchInputText.getText())
          .map(Editable::toString)
          .filter(it -> !it.isEmpty())
          .ifPresent(userSearchAction::findFollower);
    });

    userSearchStore.addOnLoadingStateChanged(loadingStateChanged).addTo(this);
  }

  private void hideKeyboard(IBinder windowToken) {
    InputMethodManager manager =
        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    manager.hideSoftInputFromWindow(windowToken, 0);
  }
}
