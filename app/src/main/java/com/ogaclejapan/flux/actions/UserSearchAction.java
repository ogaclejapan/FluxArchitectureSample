package com.ogaclejapan.flux.actions;

import com.ogaclejapan.flux.api.GitHubApi;
import com.ogaclejapan.flux.dispatcher.Dispatcher;
import com.ogaclejapan.flux.events.SearchLoadingStateChangedEvent;
import com.ogaclejapan.flux.events.SearchResultListChangedEvent;
import com.ogaclejapan.flux.models.LoadingState;
import com.ogaclejapan.flux.modules.ActivityScope;

import javax.inject.Inject;

import timber.log.Timber;

@ActivityScope
public class UserSearchAction {

  @Inject GitHubApi gitHubApi;

  private final Dispatcher dispatcher;

  @Inject public UserSearchAction(Dispatcher dispatcher) {
    this.dispatcher = dispatcher;
  }

  public void findFollower(String userId) {
    findFollower(userId, GitHubApi.DEFAULT_PAGE);
  }

  public void findFollower(String userId, int nextPage) {
    gitHubApi.followers(userId, nextPage)
        .doOnSubscribe(() -> dispatchState(LoadingState.LOADING))
        .subscribe(users -> {
          dispatcher.dispatch(new SearchResultListChangedEvent(
              userId, users, users.nextPage()));
          dispatchState(users.hasMore()
              ? LoadingState.LOADABLE
              : LoadingState.FINISHED);
        }, e -> {
          Timber.e(e, "Failed to find follower");
        });
  }

  private void dispatchState(LoadingState state) {
    dispatcher.dispatch(new SearchLoadingStateChangedEvent(state));
  }
}
