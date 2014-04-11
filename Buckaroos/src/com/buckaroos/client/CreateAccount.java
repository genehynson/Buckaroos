package com.buckaroos.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CreateAccount extends Composite {

	private static CreateAccountUiBinder uiBinder = GWT
			.create(CreateAccountUiBinder.class);

	interface CreateAccountUiBinder extends UiBinder<Widget, CreateAccount> {
	}
	@UiField
	Label title, accountNameTitle, accountNickNameTitle, startingBalanceTitle, interestRateTitle, selectCurrency;
	TextBox accountName, accountNickName, startingBalance, interestRate;
	Button create, cancel;
	
	private UserAccountController controller;
	private Panel vPanel, hPanel;

	public CreateAccount() {
		initWidget(uiBinder.createAndBindUi(this));
		controller = new UserAccountController();
		title = new Label();
		title.setText("Create New Account");
		title.addStyleName("white-text");
		selectCurrency = new Label();
		selectCurrency.setText("Select Currency:");
		selectCurrency.addStyleName("white-text");
		accountNameTitle = new Label();
		accountNameTitle.setText("Account Name");
		accountNameTitle.addStyleName("white-text");
		accountNickNameTitle = new Label();
		accountNickNameTitle.setText("Account Nick Name");
		accountNickNameTitle.addStyleName("white-text");
		startingBalanceTitle = new Label();
		startingBalanceTitle.setText("Starting Balance");
		startingBalanceTitle.addStyleName("white-text");
		interestRateTitle = new Label();
		interestRateTitle.setText("Interest Rate (.01 = 1%)");
		interestRateTitle.addStyleName("white-text");
		accountName = new TextBox();
		accountNickName = new TextBox();
		startingBalance = new TextBox();
		interestRate = new TextBox();
		accountName.addStyleName("field-box");
		accountNickName.addStyleName("field-box");
		startingBalance.addStyleName("field-box");
		interestRate.addStyleName("field-box");
		cancel = new Button();
		cancel.setText("Cancel");
		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("page").clear();
				controller.createChangeAccount();
			}
		});
		create = new Button();
		create.setText("Create Account");
		create.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				double balance = 0;
				double interest = 0;
				String nickname;
				if (!accountName.getText().toString().equals("")) {
					if (!startingBalance.getText().toString().equals("")) {
						balance = Double.parseDouble(startingBalance.getText()
								.toString());
					}
					if (!interestRate.getText().toString().equals("")) {
						interest = Double
								.parseDouble(interestRate.getText().toString());
					}
					if (!accountNickName.getText().toString().equals("")) {
						nickname = accountNickName.getText().toString();
					} else {
						nickname = accountName.getText().toString();
					}

					controller.addAccount(accountName.getText().toString(), nickname, balance,
							interest);
					
				}
			}
		});
		vPanel = new VerticalPanel();
		vPanel.add(title);
		vPanel.add(accountNameTitle);
		vPanel.add(accountName);
		vPanel.add(accountNickNameTitle);
		vPanel.add(accountNickName);
		vPanel.add(startingBalanceTitle);
		vPanel.add(startingBalance);
		vPanel.add(interestRateTitle);
		vPanel.add(interestRate);
		hPanel = new HorizontalPanel();
		hPanel.add(create);
		hPanel.add(cancel);
		vPanel.add(hPanel);
		RootPanel.get("page").add(vPanel);
	}
	
	public void displayAccountAlreadyExists() {
		Window.alert("Account already exists");
	}

}
