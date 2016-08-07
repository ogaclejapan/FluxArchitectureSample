package com.ogaclejapan.flux.api;

import android.net.Uri;

import com.ogaclejapan.flux.models.User;
import com.ogaclejapan.flux.utils.PaginatedArrayList;
import com.ogaclejapan.flux.utils.PaginatedList;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Headers;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public class GitHubApiClient implements GitHubApi {

  public interface Service {

    @GET("users/{user}/followers")
    Observable<Response<List<User>>> listFollowers(
        @Path("user") String userId,
        @Query("page") int page);

  }

  private static final Pattern NEXT_PAGE_LINK = Pattern.compile(".*<(.+)>; rel=\"next\".*");
  private static final Pattern LAST_PAGE_LINK = Pattern.compile(".*<(.+)>; rel=\"last\".*");

  private final Service service;

  public GitHubApiClient(Retrofit retrofit) {
    this(retrofit.create(Service.class));
  }

  /* package */ GitHubApiClient(Service service) {
    this.service = service;
  }

  @Override
  public Observable<PaginatedList<User>> followers(String userId, int page) {
    return service.listFollowers(userId, page)
        .flatMap(response -> {
          if (response.isSuccessful()) {
            return Observable.just(toPaginatedList(response.body(), response.headers()));
          } else if (response.code() == HttpURLConnection.HTTP_NOT_FOUND) {
            return Observable.just(PaginatedArrayList.empty());
          } else {
            return Observable.error(new HttpException(response));
          }
        });
  }

  private <T> PaginatedList<T> toPaginatedList(List<T> items, Headers headers) {
    int nextPage = Integer.MAX_VALUE;
    int lastPage = Integer.MIN_VALUE;

    String link = headers.get("Link");
    if (link == null) {
      return new PaginatedArrayList<>(items, nextPage, lastPage);
    }

    Matcher nextMatcher = NEXT_PAGE_LINK.matcher(link);
    if (nextMatcher.matches()) {
      Uri nextUri = Uri.parse(nextMatcher.group(nextMatcher.groupCount()));
      String next = nextUri.getQueryParameter("page");
      nextPage = (next != null) ? Integer.parseInt(next) : nextPage;
    }

    Matcher lastMatcher = LAST_PAGE_LINK.matcher(link);
    if (lastMatcher.matches()) {
      Uri lastUri = Uri.parse(lastMatcher.group(lastMatcher.groupCount()));
      String last = lastUri.getQueryParameter("page");
      lastPage = (last != null) ? Integer.parseInt(last) : lastPage;
    }

    return new PaginatedArrayList<>(items, nextPage, lastPage);
  }

}
