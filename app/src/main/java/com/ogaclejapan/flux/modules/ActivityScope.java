package com.ogaclejapan.flux.modules;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * ActivityScope is per activity scope
 * Created by Suguru Namura on 2015/07/17.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
