package com.dreamskiale.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class GoodbyeViewImpl extends Composite implements GoodbyeView {

  private static GoodbyeViewImplUiBinder uiBinder = GWT.create(GoodbyeViewImplUiBinder.class);

  interface GoodbyeViewImplUiBinder extends UiBinder<Widget, GoodbyeViewImpl> {
  }

  @UiField
  SpanElement nameSpan;
  @UiField
  Anchor helloLink;
  private Presenter presenter;
  private String name;

  public GoodbyeViewImpl() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public void setName(String name) {
    this.name = name;
    nameSpan.setInnerText(name);
  }

  @UiHandler("helloLink")
  void onClickGoodbye(ClickEvent e) {
    presenter.goTo(new HelloPlace(name));
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;

  }
}