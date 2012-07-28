package com.dreamskiale.client.history;

/*
 *    Copyright 2012 Carlos Aguayo <carlos.aguayo@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.URL;
import com.google.gwt.place.shared.PlaceHistoryHandler.Historian;
import com.google.gwt.user.client.Window;

/**
 * A template replacement for {@link Historian}.
 * 
 * @author carlos.aguayo
 */
public abstract class CustomHistorian implements Historian,
// allows the use of ValueChangeEvent.fire()
    HasValueChangeHandlers<String> {

  protected final SimpleEventBus handlers = new SimpleEventBus();
  private static String initialToken = "";

  public CustomHistorian() {
    addHistoryEventHandler();
  }

  /**
   * This method is responsible for adding an event to list when we go back in history
   */
  protected abstract void addHistoryEventHandler();

  /**
   * This method is responsible for extracting the path to use as history from the url.
   */
  protected abstract String getPath(String path, String hash);

  /**
   * The separator used to divide from the url and the place from gwt.
   * 
   * Example: A hashbang history would return "!/", so the uri ends up being "/#!/" plus the uri.
   */
  protected abstract String getUrlSeparator();

  /**
   * This should update the browser url with the given url
   */
  protected abstract void goTo(String url);

  /**
   * This method is responsible for reading the browser url and converting it into a token that GWT Places can use.
   * 
   * The token must be in the format of "prefix:value"
   * 
   * value must be url decoded
   */
  @Override
  public final String getToken() {
    String path = getWindowLocationPath();
    String hash = getWindowLocationHash();
    if (!isBlank(hash)) {
      String hashSeparator = "#" + getUrlSeparator();
      if (hash.startsWith(hashSeparator)) {
        hash = hash.substring(hashSeparator.length());
      } else {
        // Doesn't start with a hash bang, it's an invalid url
        return "";
      }
    }

    String gwtPlaceToken = fromPathToToken(getPath(path, hash));

    // in here we should have "prefix:value"
    // ensure value is url decoded
    if (!isBlank(gwtPlaceToken) && !gwtPlaceToken.equals(":")) {
      String[] s = gwtPlaceToken.split(":");
      return s[0] + ":" + (s.length == 2 ? URL.decodeQueryString(s[1]) : "");
    } else {
      return "";
    }
  }

  /**
   * This method generates the url that will be used for updating the browser url.
   * 
   * @return A full path valid url properly escaped
   */
  protected final String fromGwtPlaceTokenToUrl(String gwtPlaceToken) {
    String url;
    if (!isBlank(gwtPlaceToken)) {
      String[] prefixValue = gwtPlaceToken.split(":");
      url = prefixValue[0] + "/" + (prefixValue.length == 2 ? URL.encodeQueryString(prefixValue[1]) : "");
    } else {
      url = initialToken;
    }
    if (isBlank(url)) {
      url = "";
    }
    return getUrlSeparator() + url;
  }

  /**
   * Method invoked whenever history gets updated
   */
  protected void onHistoryChange() {
    ValueChangeEvent.fire(this, getToken());
  }

  /**
   * input: "a/b" output: "a:b"
   * 
   * input: "a/b/" output: "a:b"
   * 
   * input: "a/b/c" output: "a/b:c"
   * 
   * input: "a/" output: "a:"
   * 
   * input: "a" output: "a:"
   */
  private String fromPathToToken(String path) {
    if (!isBlank(path)) {
      String colon;
      if (path.endsWith("/") && path.length() > 1) {
        path = path.substring(0, path.length() - 2);
      }
      int i = path.lastIndexOf('/');
      if (i != -1) {
        StringBuilder sb = new StringBuilder(path);
        sb.setCharAt(i, ':');
        colon = sb.toString();
      } else {
        colon = path + ":";
      }
      return colon;
    }
    return "";
  }

  @Override
  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> valueChangeHandler) {
    return this.handlers.addHandler(ValueChangeEvent.getType(), valueChangeHandler);
  }

  @Override
  public final void newItem(String token, boolean issueEvent) {
    if (getToken().equals(token)) {
      return;
    }
    goTo(fromGwtPlaceTokenToUrl(token));
    if (issueEvent) {
      ValueChangeEvent.fire(this, getToken());
    }
  }

  @Override
  public void fireEvent(GwtEvent<?> event) {
    this.handlers.fireEvent(event);
  }

  protected String getWindowLocationHash() {
    return Window.Location.getHash();
  }

  protected String getWindowLocationPath() {
    return Window.Location.getPath();
  }

  public static void setInitialToken(String token) {
    initialToken = !isBlank(token) ? token : "";
  }

  protected static boolean isBlank(String s) {
    return s == null || s.trim().equals("");
  }

}