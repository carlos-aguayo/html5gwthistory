package com.dreamskiale.client;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class GoodbyeActivity extends AbstractActivity implements GoodbyeView.Presenter {
  // Used to obtain views, eventBus, placeController
  // Alternatively, could be injected via GIN
  private ClientFactory clientFactory;
  // Name that will be appended to "Hello,"
  private String name;

  public GoodbyeActivity(GoodbyePlace place, ClientFactory clientFactory) {
    this.name = place.getGoodbyeName();
    this.clientFactory = clientFactory;
  }

  /**
   * Invoked by the ActivityManager to start a new Activity
   */
  @Override
  public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
    GoodbyeView goodbyeView = clientFactory.getGoodbyeView();
    goodbyeView.setName(name);
    goodbyeView.setPresenter(this);
    containerWidget.setWidget(goodbyeView.asWidget());
  }

  /**
   * Navigate to a new Place in the browser
   */
  public void goTo(Place place) {
    clientFactory.getPlaceController().goTo(place);
  }
}