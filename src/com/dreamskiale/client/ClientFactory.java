package com.dreamskiale.client;

import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

public interface ClientFactory {
  EventBus getEventBus();

  PlaceController getPlaceController();

  HelloView getHelloView();

  GoodbyeView getGoodbyeView();
}