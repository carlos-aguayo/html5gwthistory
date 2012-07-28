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

import com.google.gwt.place.shared.PlaceHistoryHandler.Historian;

/**
 * A concrete replacement for {@link Historian} that shows the GWT Place Token
 * in a "hashbang" format (ˆ la Twitter).
 * 
 * @author carlos.aguayo
 */
public class HashBangHistorian extends CustomHistorian {
  
  private String currentUrl = "";

  @Override
  public String getPath(String path, String hash) {
    return hash;
  }
  
  @Override
  public String getUrlSeparator() {
    return "!/";
  }

  @Override
  protected void goTo(String url) {
    currentUrl = url;
    updateHash(url);
  }

  private native void updateHash(String url) /*-{
    $wnd.location.hash = url;
  }-*/;

  @Override
  protected native void addHistoryEventHandler() /*-{
    var that = this;
    var oldHandler = $wnd.onhashchange;
    $wnd.onhashchange = $entry(function(e) {
      var hash = $wnd.location.hash.substring(1);
      var currentUrl = that.@com.dreamskiale.client.history.HashBangHistorian::currentUrl;
      // We use this event to detect when the user is going back using the back button as it will trigger
      // a hash change and we can go back to the previous state.
      // However, this event also gets triggered whenever we are going forward in history, in that case
      // we don't want to invoke this method, so that's why we compare it with the current url.
      if (hash !== currentUrl) {
        that.@com.dreamskiale.client.history.CustomHistorian::onHistoryChange()();
        if (oldHandler) {
          oldHandler();
        }
      }
    });
  }-*/;
  
}