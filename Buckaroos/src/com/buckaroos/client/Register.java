package com.buckaroos.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Register extends Composite {

	private static RegisterUiBinder uiBinder = GWT
			.create(RegisterUiBinder.class);

	interface RegisterUiBinder extends UiBinder<Widget, Register> {
	}
	@UiField
	Label title, username, email, password, subtitle1, subtitle2;
	TextBox etName, etEmail, etPass;
	Button bRegister;
	
	private UserAccountController controller;
	private Panel vPanel;

	public Register() {
		initWidget(uiBinder.createAndBindUi(this));
		controller = new UserAccountController();
		title = new Label();
		title.setText("buckaroos");
		subtitle1 = new Label();
		subtitle1.setText("Create your buckaroos login");
		subtitle2 = new Label();
		subtitle2.setText("* Indicates required fields");
		username = new Label();
		username.setText("* Username:");
		email = new Label();
		email.setText("* Email:");
		password = new Label();
		password.setText("* Password:");
		etName = new TextBox();
		etEmail = new TextBox();
		etPass = new PasswordTextBox();
		etName.addStyleName("field-box");
		etEmail.addStyleName("field-box");
		etPass.addStyleName("field-box");
		bRegister = new Button();
		bRegister.setText("Register");
		bRegister.addStyleName("tile-button");
		title.addStyleName("faceletters");
		title.addStyleName("white-text");
		email.addStyleName("white-text");
		subtitle1.addStyleName("white-text");
		subtitle1.addStyleName("btm-padding");
		subtitle2.addStyleName("white-text");
		subtitle2.addStyleName("btm-padding");
		username.addStyleName("white-text");
		password.addStyleName("white-text");
		vPanel = new VerticalPanel();
		bRegister.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!etName.getText().toString().equals("")
						&& !etEmail.getText().toString().equals("")
						&& !etPass.getText().toString().equals("")) {
					controller.registerUser(etName.getText(), etPass
							.getText(), etEmail.getText());
				}
			}
		});
		vPanel.add(title);
		vPanel.add(subtitle1);
		vPanel.add(username);
		vPanel.add(etName);
		vPanel.add(email);
		vPanel.add(etEmail);
		vPanel.add(password);
		vPanel.add(etPass);
		vPanel.add(bRegister);
		vPanel.add(subtitle2);
		RootPanel.get("page").add(vPanel);
		
	}
	
	public void displayAllFieldRequired() {
		Window.alert("All fields required");
	}

	public void displayAccountAlreadyExists() {
		Window.alert("Account already exists.");
	}
}
