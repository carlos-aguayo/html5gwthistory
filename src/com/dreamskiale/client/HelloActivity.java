package com.dreamskiale.client;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class HelloActivity extends AbstractActivity implements HelloView.Presenter {
  // Used to obtain views, eventBus, placeController
  // Alternatively, could be injected via GIN
  private ClientFactory clientFactory;
  // Name that will be appended to "Hello,"
  private String name;

  public HelloActivity(HelloPlace place, ClientFactory clientFactory) {
    this.name = place.getHelloName();
    this.clientFactory = clientFactory;
  }

  /**
   * Invoked by the ActivityManager to start a new Activity
   */
  @Override
  public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
    HelloView helloView = clientFactory.getHelloView();
    helloView.setName(name);
    helloView.setPresenter(this);
    containerWidget.setWidget(helloView.asWidget());
  }

  /**
   * Navigate to a new Place in the browser
   */
  public void goTo(Place place) {
    clientFactory.getPlaceController().goTo(place);
  }
}