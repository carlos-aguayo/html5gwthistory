package com.dreamskiale.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface GoodbyeView extends IsWidget {

  public interface Presenter {
    void goTo(Place place);
  }

  void setName(String goodbyeName);

  void setPresenter(Presenter presenter);

}