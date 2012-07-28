package com.dreamskiale.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class HelloPlace extends Place {
  private String helloName;

  public HelloPlace(String token) {
    this.helloName = token;
  }

  public String getHelloName() {
    return helloName;
  }

  @Prefix("hello")
  public static class Tokenizer implements PlaceTokenizer<HelloPlace> {
    @Override
    public String getToken(HelloPlace place) {
      return place.getHelloName();
    }

    @Override
    public HelloPlace getPlace(String token) {
      return new HelloPlace(token);
    }
  }
}