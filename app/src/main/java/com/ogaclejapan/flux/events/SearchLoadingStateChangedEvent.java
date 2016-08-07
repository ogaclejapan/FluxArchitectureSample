package com.ogaclejapan.flux.events;

import com.ogaclejapan.flux.models.LoadingState;

public class SearchLoadingStateChangedEvent {

  public final LoadingState state;

  public SearchLoadingStateChangedEvent(LoadingState state) {
    this.state = state;
  }

}
