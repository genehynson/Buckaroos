package com.buckaroos.client;

import java.util.ArrayList;
import java.util.List;

import com.buckaroos.server.Account;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChangeAccount extends Composite {

	private static ChangeAccountUiBinder uiBinder = GWT
			.create(ChangeAccountUiBinder.class);

	interface ChangeAccountUiBinder extends UiBinder<Widget, ChangeAccount> {
	}

	@UiField
	FlexTable table;
	Label title;
	Button createAccount, settings, logout;
	
    private List<Account> userAccounts;
    private ControllerInterface controller;
    private Panel vPanel, hPanel;
    
    
	public ChangeAccount(List<Account> listUserAccounts) {
		initWidget(uiBinder.createAndBindUi(this));
		this.userAccounts = listUserAccounts;
		controller = new UserAccountController();
		createAccount = new Button();
		createAccount.setText("Create New Account");
		createAccount.addStyleName("blue-button");

		createAccount.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("page").clear();
				controller.createCreateAccount();
			}
		});
		settings = new Button();
		settings.setText("Settings");
		settings.addStyleName("blue-button");

		settings.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("page").clear();
				Settings s = new Settings(controller.getCurrentUser());
			}
		});
		logout = new Button();
		logout.setText("Logout");
		logout.addStyleName("blue-button");

		logout.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("page").clear();
				WelcomeScreen ws = new WelcomeScreen();
			}
		});
		title = new Label();
		title.setText("Select an Account");
		title.addStyleName("title");
		controller = new UserAccountController();
		table = new FlexTable();
		vPanel = new VerticalPanel();
		if (userAccounts == null) {
			System.out.println("List is null!");
		} else {
			table.setText(0, 0, "Name");
			table.setText(0, 1, "Balance");
			for (int i = 2; i < userAccounts.size() + 1; i++) {
				int index = i - 1;
				table.setText(i, 0, userAccounts.get(index).getName());
				table.setText(i, 1, String.valueOf(userAccounts.get(index).getBalance()));
				table.getCellFormatter().addStyleName(i, 0, "table-styling");
				table.getCellFormatter().addStyleName(i, 1, "table-styling");
			}
		}
		table.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {
		        Cell cell = table.getCellForEvent(event);
		        int receiverRowIndex = cell.getRowIndex();
		        if (receiverRowIndex != 0) {
		        	controller.setCurrentAccount(controller.getUserAccount(table.getText(receiverRowIndex, 0)));
		        	RootPanel.get("page").clear();
		        	controller.createAccountOverview();
		        }
		    }
		});
		hPanel = new HorizontalPanel();
		hPanel.add(createAccount);
		hPanel.add(settings);
		hPanel.add(logout);
		title.addStyleName("white-text");
		table.addStyleName("white-text");
		vPanel.add(title);
		vPanel.add(table);
		vPanel.add(hPanel);
		RootPanel.get("page").add(vPanel);
	}

}
