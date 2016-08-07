package com.ogaclejapan.flux.utils;

import java.util.List;

public interface PaginatedList<E> extends List<E> {

  boolean hasMore();

  int nextPage();

  int lastPage();

}
