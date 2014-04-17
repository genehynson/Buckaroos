package com.buckaroos.client;

import com.buckaroos.server.User;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Settings extends Composite {

	private static SettingsUiBinder uiBinder = GWT
			.create(SettingsUiBinder.class);

	interface SettingsUiBinder extends UiBinder<Widget, Settings> {
	}
	
	private ControllerInterface controller;
	private Panel vPanel, hPanel;
	
	@UiField
	Label title, email, password, subtitle;
	TextBox etPass, etEmail;
	Button save, back;
	
	public Settings(User user) {
		initWidget(uiBinder.createAndBindUi(this));	
		controller = new UserAccountController();
		title = new Label();
		title.setText("buckaroos");
		subtitle = new Label();
		subtitle.setText("Change whatever information you'd like and then click 'Save'!");
		subtitle.addStyleName("white-text");
		email = new Label();
		email.setText("Email:");
		password = new Label();
		password.setText("Password:");
		etEmail = new TextBox();
		etEmail.setText(user.getEmail());
		etPass = new PasswordTextBox();
		etPass.setText(user.getPassword());
		etEmail.addStyleName("field-box");
		etPass.addStyleName("field-box");
		save = new Button();
		save.setText("Save");
		save.addStyleName("blue-button");
		back = new Button();
		back.setText("Back");
		back.addStyleName("tile-button");
		title.addStyleName("faceletters");
		title.addStyleName("white-text");
		email.addStyleName("white-text");
		password.addStyleName("white-text");
		vPanel = new VerticalPanel();
		save.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!etEmail.getText().toString().equals("")
						&& !etPass.getText().toString().equals("")) {
					controller.updateUser(controller.getCurrentUser().getUsername(), etPass
							.getText(), etEmail.getText());
				}
			}
		});
		back.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("page").clear();
				WelcomeScreen ws = new WelcomeScreen();
			}
		});
		hPanel = new HorizontalPanel();
		hPanel.add(save);
		hPanel.add(back);
		vPanel.add(title);
		vPanel.add(subtitle);
		vPanel.add(email);
		vPanel.add(etEmail);
		vPanel.add(password);
		vPanel.add(etPass);
		vPanel.add(hPanel);
		RootPanel.get("page").add(vPanel);
	
}


}
