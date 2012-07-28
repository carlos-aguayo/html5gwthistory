package com.dreamskiale.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class GoodbyePlace extends Place {
  private String goodbyeName;

  public GoodbyePlace(String token) {
    this.goodbyeName = token;
  }

  public String getGoodbyeName() {
    return goodbyeName;
  }

  @Prefix("goodbye")
  public static class Tokenizer implements PlaceTokenizer<GoodbyePlace> {
    @Override
    public String getToken(GoodbyePlace place) {
      return place.getGoodbyeName();
    }

    @Override
    public GoodbyePlace getPlace(String token) {
      return new GoodbyePlace(token);
    }
  }
}