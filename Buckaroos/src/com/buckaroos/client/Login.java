package com.buckaroos.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
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

public class Login extends Composite {

	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

	interface LoginUiBinder extends UiBinder<Widget, Login> {
	}

	@UiField
	TextBox etUser, etPass;
	Button bLogin, back, sendEmail;
	Label title, username, password, subtitle1, subtitle2, passwordReset;

	private Panel vPanel, hPanel;
    private ControllerInterface controller = new UserAccountController();
    private Audio sound;
    
	public Login() {
		initWidget(uiBinder.createAndBindUi(this));
		buildLogin();
	}
	
	public void displayNotCorrect() {
		Window.alert("Invalid login info.");
	}
	
	private void buildLogin() {
		title = new Label();
		title.setText("buckaroos");
		passwordReset = new Label("Forgot password?");
		passwordReset.addStyleName("white-text");
		passwordReset.addStyleName("hyperlink");
		passwordReset.addStyleName("clickable");
		passwordReset.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				buildForgotPassword();
			}
		});
		subtitle1 = new Label();
		subtitle1.setText("Enter your login username and password.");
		subtitle2 = new Label();
		subtitle2.setText("* Indicates required fields.");
		username = new Label();
		username.setText("* Username:");
		password = new Label();
		password.setText("* Password:");
		bLogin = new Button();
		bLogin.setText("Sign in");
		bLogin.addStyleName("blue-button");
		back = new Button();
		back.setText("Back");
		back.addStyleName("tile-button");
		title.addStyleName("faceletters");
		title.addStyleName("white-text");
		subtitle1.addStyleName("white-text");
		subtitle1.addStyleName("btm-padding");
		subtitle2.addStyleName("white-text");
		subtitle2.addStyleName("btm-padding");
		username.addStyleName("white-text");
		password.addStyleName("white-text");
		etUser = new TextBox();
		etUser.setText("Enter your login");
		etUser.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				etUser.setText("");
			}
		});
		etPass = new PasswordTextBox();
		vPanel = new VerticalPanel();
		bLogin.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				controller.loginUser(etUser.getText(), etPass.getText());
				sound = Audio.createIfSupported();
				sound.setSrc("sounds/click.mp3");
				sound.play();
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
		hPanel.add(bLogin);
		hPanel.add(back);
		etUser.addStyleName("field-box");
		etPass.addStyleName("field-box");
		vPanel.add(title);
		vPanel.add(subtitle1);
		vPanel.add(username);
		vPanel.add(etUser);
		vPanel.add(password);
		vPanel.add(etPass);
		vPanel.add(hPanel);
		vPanel.add(passwordReset);
		vPanel.add(subtitle2);
        RootPanel.get("page").clear();
		RootPanel.get("page").add(vPanel);
	}
	private void buildForgotPassword() {
		sendEmail = new Button("Send Email");
		sendEmail.addStyleName("blue-button");
		back = new Button("Back");
		back.addStyleName("blue-button");
		back.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				buildLogin();
			}
		});
		sendEmail.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				controller.resetPassword(etUser.getText());
				buildLogin();
			}
		});
		subtitle1.setText("Enter your username and click 'Send Email'");
		hPanel = new HorizontalPanel();
		vPanel = new VerticalPanel();
		hPanel.add(sendEmail);
		hPanel.add(back);
		vPanel.add(title);
		vPanel.add(subtitle1);
		vPanel.add(username);
		vPanel.add(etUser);
		vPanel.add(hPanel);
		vPanel.add(subtitle2);
        RootPanel.get("page").clear();
		RootPanel.get("page").add(vPanel);
		

		
	}
}

