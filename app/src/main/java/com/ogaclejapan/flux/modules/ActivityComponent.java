package com.ogaclejapan.flux.modules;

import com.ogaclejapan.flux.components.activity.BaseActivity;
import com.ogaclejapan.flux.components.activity.UserDetailActivity;
import com.ogaclejapan.flux.components.fragment.BaseFragment;
import com.ogaclejapan.flux.components.fragment.SearchInputFragment;
import com.ogaclejapan.flux.components.fragment.SearchResultFragment;

import dagger.Component;

@ActivityScope
@Component(
    dependencies = AppComponent.class,
    modules = ActivityModule.class)
public interface ActivityComponent {

  void inject(BaseActivity activity);

  void inject(UserDetailActivity activity);

  void inject(BaseFragment fragment);

  void inject(SearchInputFragment fragment);

  void inject(SearchResultFragment fragment);
}
