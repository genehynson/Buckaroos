package com.buckaroos.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class WelcomeScreen extends Composite implements EntryPoint {

	private static WelcomeScreenUiBinder uiBinder = GWT
			.create(WelcomeScreenUiBinder.class);

	interface WelcomeScreenUiBinder extends UiBinder<Widget, WelcomeScreen> {
	}

	private HorizontalPanel hpanel1;
	private HorizontalPanel hpanel2;
	private FlowPanel fpanel;	
	private UserAccountController controller;
	private DBConnectionAsync rpc;

	public WelcomeScreen() {
		initWidget(uiBinder.createAndBindUi(this));
    	String moduleRelativeURL = GWT.getModuleBaseURL() + "dBConnection";
		controller = new UserAccountController(moduleRelativeURL);
		onModuleLoad();
	}
	
    
    @UiField
    Button bReg, bLog, home;
    Label title;
    Label subtitle;

	@Override
	public void onModuleLoad() {
        title = new Label();
        subtitle = new Label();
        home = new Button();
        home.setText("buckaroos");
        home.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
		        RootPanel.get("page").clear();
		        WelcomeScreen ws = new WelcomeScreen();
			}
		});
        title.addStyleName("faceletters");
        title.addStyleName("white-text");
        subtitle.addStyleName("white-text");
        subtitle.addStyleName("btm-padding");
        bReg = new Button();
        bLog = new Button();
        bReg.addStyleName("tile-button");
        bLog.addStyleName("blue-button");
        hpanel1 = new HorizontalPanel();
        hpanel2 = new HorizontalPanel();
        fpanel = new FlowPanel();

        bReg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
		        RootPanel.get("page").clear();
		        controller.createRegister();
			}
		});
        bLog.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
		        RootPanel.get("page").clear();
		        controller.createLogin();
			}
		});
        bLog.setText("Login");
        bReg.setText("Register");
        title.setText("buckaroos");
        subtitle.setText("Your personal financial planning made easier.");
        fpanel.add(title);
        fpanel.add(subtitle);
        hpanel1.add(bLog);
        hpanel2.add(bReg);
        fpanel.add(hpanel1);
        hpanel1.add(hpanel2);
        RootPanel.get("page").clear();
        RootPanel.get("page").add(fpanel);
        
//        RootPanel.get("home").add(home);
		
	}

}
