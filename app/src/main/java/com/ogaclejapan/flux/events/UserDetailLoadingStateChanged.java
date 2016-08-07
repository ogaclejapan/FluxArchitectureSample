package com.ogaclejapan.flux.events;

import com.ogaclejapan.flux.models.LoadingState;

public class UserDetailLoadingStateChanged {

  public final LoadingState state;

  public UserDetailLoadingStateChanged(LoadingState state) {
    this.state = state;
  }

}
