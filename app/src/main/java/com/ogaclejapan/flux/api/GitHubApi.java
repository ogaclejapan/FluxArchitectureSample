package com.ogaclejapan.flux.api;

import com.ogaclejapan.flux.models.User;
import com.ogaclejapan.flux.utils.PaginatedList;
import rx.Observable;

public interface GitHubApi {

  int DEFAULT_PAGE = 1;

  Observable<PaginatedList<User>> followers(String userId, int page);
}

