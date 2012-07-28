package com.dreamskiale.client.history;

/*
 *    Copyright 2012 Thomas Broyer <t.broyer@ltgt.net>
 *    Carlos Aguayo <carlos.aguayo@gmail.com>
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
 * A replacement for {@link Historian} that uses HTML5 History API, {@code pushState} and {@code onpopstate}.
 *
 * @see https://developer.mozilla.org/en/DOM/Manipulating_the_browser_history
 * @see https://github.com/balupton/history.js/wiki/The-State-of-the-HTML5-History-API
 * 
 * @see https://groups.google.com/forum/?fromgroups#!topic/google-web-toolkit/xsf_GCnH36Q
 * @see https://gist.github.com/1883821
 * @see http://groups.google.com/group/google-web-toolkit/browse_thread/thread/919a7847e0d5370c/d647f781023e41b2?lnk=gst&amp;q=pushstate&pli=1
 * 
 * @author Thomas Broyer
 * @author carlos.aguayo
 */
public class Html5Historian extends CustomHistorian {

  @Override
  public String getPath(String path, String hash) {
    if (!isBlank(hash)) {
      // replace the state so we can get rid of the hashbang and get the real url
      replaceState(hash);
      return hash;
    } else {
      return path;
    }
  }
  
  @Override
  public String getUrlSeparator() {
    return "/";
  }

  @Override
  protected void goTo(String url) {
    pushState(url);
  }
  
  private native void pushState(String url) /*-{
    url += $wnd.location.search;
    $wnd.history.pushState(null, $doc.title, url);
  }-*/;

  private native void replaceState(String url) /*-{
    url += $wnd.location.search;
    $wnd.history.replaceState(null, $doc.title, url);
  }-*/;

  @Override
  protected native void addHistoryEventHandler() /*-{
    var that = this;
    var oldHandler = $wnd.onpopstate;
    $wnd.onpopstate = $entry(function(e) {
      that.@com.dreamskiale.client.history.CustomHistorian::onHistoryChange()();
      if (oldHandler) {
        oldHandler();
      }
    });
  }-*/;

}