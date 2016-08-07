package com.ogaclejapan.flux.modules;

import com.ogaclejapan.flux.App;
import com.ogaclejapan.flux.AppGlideModule;
import com.ogaclejapan.flux.api.GitHubApi;
import com.ogaclejapan.flux.dispatcher.Dispatcher;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    AppModule.class, NetworkModule.class
})
public interface AppComponent {

  void inject(App app);

  void inject(AppGlideModule module);

  RefWatcher refWatcher();

  Dispatcher dispatcher();

  GitHubApi githubApi();

}
