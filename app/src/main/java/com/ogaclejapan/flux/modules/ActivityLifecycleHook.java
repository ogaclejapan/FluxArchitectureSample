package com.ogaclejapan.flux.modules;

import android.support.annotation.IntDef;

import com.annimon.stream.Stream;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import java.util.Set;

public class ActivityLifecycleHook {

  private static final int NONE = 0;
  private static final int CREATE = 1;
  private static final int START = 1 << 1;
  private static final int RESUME = 1 << 2;
  private static final int PAUSE = 1 << 3;
  private static final int STOP = 1 << 4;
  private static final int DESTROY = 1 << 5;

  @Retention(RetentionPolicy.SOURCE)
  @IntDef({ ON_CREATE, ON_START, ON_RESUME, ON_PAUSE, ON_STOP, ON_DESTROY })
  @interface State {
  }

  private static final int ON_CREATE = CREATE;
  private static final int ON_START = CREATE | START;
  private static final int ON_RESUME = CREATE | START | RESUME;
  private static final int ON_PAUSE = PAUSE;
  private static final int ON_STOP = PAUSE | STOP;
  private static final int ON_DESTROY = PAUSE | STOP | DESTROY;

  private final Set<Runnable> onCreateHookActions = new HashSet<>();
  private final Set<Runnable> onStartHookActions = new HashSet<>();
  private final Set<Runnable> onResumeHookActions = new HashSet<>();
  private final Set<Runnable> onPauseHookActions = new HashSet<>();
  private final Set<Runnable> onStopHookActions = new HashSet<>();
  private final Set<Runnable> onDestroyHookActions = new HashSet<>();

  private @State int state = NONE;

  public void dispatchOnCreate() {
    state = ON_CREATE;
    Stream.of(onCreateHookActions).forEach(Runnable::run);
  }

  public void addOnCreate(Runnable action) {
    onCreateHookActions.add(action);
    if ((state & CREATE) > 0) {
      action.run();
    }
  }

  public void removeOnCreate(Runnable action) {
    if (onCreateHookActions.contains(action)) {
      onCreateHookActions.remove(action);
    }
  }

  public void dispatchOnStart() {
    state = ON_START;
    Stream.of(onStartHookActions).forEach(Runnable::run);
  }

  public void addOnStart(Runnable action) {
    onStartHookActions.add(action);
    if ((state & START) > 0) {
      action.run();
    }
  }

  public void removeOnStart(Runnable action) {
    if (onStartHookActions.contains(action)) {
      onStartHookActions.remove(action);
    }
  }

  public void dispatchOnResume() {
    state = ON_RESUME;
    Stream.of(onResumeHookActions).forEach(Runnable::run);
  }

  public void addOnResume(Runnable action) {
    onResumeHookActions.add(action);
    if ((state & RESUME) > 0) {
      action.run();
    }
  }

  public void removeOnResume(Runnable action) {
    if (onResumeHookActions.contains(action)) {
      onResumeHookActions.remove(action);
    }
  }

  public void dispatchOnPause() {
    state = ON_PAUSE;
    Stream.of(onPauseHookActions).forEach(Runnable::run);
  }

  public void addOnPause(Runnable action) {
    onPauseHookActions.add(action);
    if ((state & PAUSE) > 0) {
      action.run();
    }
  }

  public void removeOnPause(Runnable action) {
    if (onPauseHookActions.contains(action)) {
      onPauseHookActions.remove(action);
    }
  }

  public void dispatchOnStop() {
    state = ON_STOP;
    Stream.of(onStopHookActions).forEach(Runnable::run);
  }

  public void addOnStop(Runnable action) {
    onStopHookActions.add(action);
    if ((state & STOP) > 0) {
      action.run();
    }
  }

  public void removeOnStop(Runnable action) {
    if (onStopHookActions.contains(action)) {
      onStopHookActions.remove(action);
    }
  }

  public void dispatchOnDestroy() {
    state = ON_DESTROY;
    Stream.of(onDestroyHookActions).forEach(Runnable::run);
  }

  public void addOnDestroy(Runnable action) {
    onDestroyHookActions.add(action);
    if ((state & DESTROY) > 0) {
      action.run();
    }
  }

  public void removeOnDestroy(Runnable action) {
    if (onDestroyHookActions.contains(action)) {
      onDestroyHookActions.remove(action);
    }
  }
}
