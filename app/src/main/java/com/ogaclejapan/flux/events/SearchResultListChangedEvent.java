package com.ogaclejapan.flux.events;

import com.ogaclejapan.flux.models.User;

import java.util.List;

public class SearchResultListChangedEvent {

  public final String userId;
  public final List<User> followers;
  public final int nextPage;

  public SearchResultListChangedEvent(String userId, List<User> followers, int nextPage) {
    this.userId = userId;
    this.followers = followers;
    this.nextPage = nextPage;
  }

}
