package com.ogaclejapan.flux.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PaginatedArrayList<E> extends ArrayList<E> implements PaginatedList<E> {

  private final int nextPage;
  private final int lastPage;

  public PaginatedArrayList(Collection<E> items, int nextPage, int lastPage) {
    super(items);
    this.nextPage = nextPage;
    this.lastPage = lastPage;
  }

  public static <E> PaginatedArrayList<E> empty() {
    return new PaginatedArrayList<>(Collections.emptyList(), Integer.MAX_VALUE, Integer.MIN_VALUE);
  }

  @Override
  public boolean hasMore() {
    return nextPage <= lastPage;
  }

  @Override
  public int nextPage() {
    return nextPage;
  }

  @Override
  public int lastPage() {
    return lastPage;
  }

}
