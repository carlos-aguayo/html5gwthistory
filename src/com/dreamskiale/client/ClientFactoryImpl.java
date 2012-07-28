package com.dreamskiale.client;

import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;

public class ClientFactoryImpl implements ClientFactory {

  private final EventBus eventBus = new SimpleEventBus();
  private final PlaceController placeController = new PlaceController(eventBus);
  private final HelloView helloView = new HelloViewImpl();
  private final GoodbyeView goodbyeView = new GoodbyeViewImpl();

  @Override
  public EventBus getEventBus() {
    return eventBus;
  }

  @Override
  public PlaceController getPlaceController() {
    return placeController;
  }

  @Override
  public HelloView getHelloView() {
    return helloView;
  }

  @Override
  public GoodbyeView getGoodbyeView() {
    return goodbyeView;
  }

}