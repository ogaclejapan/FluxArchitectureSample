package com.ogaclejapan.flux.dispatcher;

import com.ogaclejapan.flux.BuildConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbusperf.AppEventBusIndex;

public class Dispatcher {

  private final EventBus bus;

  public Dispatcher() {
    bus = EventBus.builder()
        .addIndex(new AppEventBusIndex())
        .throwSubscriberException(BuildConfig.DEBUG)
        .build();
  }

  public void dispatch(Object payload) {
    bus.post(payload);
  }

  public void register(Object observer) {
    bus.register(observer);
  }

  public void unregister(Object observer) {
    bus.unregister(observer);
  }
}
