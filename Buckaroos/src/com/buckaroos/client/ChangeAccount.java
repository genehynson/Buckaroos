package com.buckaroos.client;

import java.util.ArrayList;
import java.util.List;

import com.buckaroos.server.Account;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Button;
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
	Button createAccount;
	
    private List<Account> userAccounts;
    private ControllerInterface controller;
    private Panel vPanel;
    
    
	public ChangeAccount(List<Account> userAccounts) {
		initWidget(uiBinder.createAndBindUi(this));
		createAccount = new Button();
		createAccount.setText("Create New Account");
		createAccount.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("page").clear();
				controller.createCreateAccount();
			}
		});
		title = new Label();
		title.setText("Select Account");
		controller = new UserAccountController();
		table = new FlexTable();
		vPanel = new VerticalPanel();
		if (userAccounts == null) {
			System.out.println("List is null!");
		} else {
			for (int i = 0; i < userAccounts.size(); i++) {
				table.setText(i, 0, userAccounts.get(i).getName());
				table.setText(i, 1, String.valueOf(userAccounts.get(i).getBalance()));
			}
		}
		table.addClickHandler(new ClickHandler() {
		    public void onClick(ClickEvent event) {
		        Cell cell = table.getCellForEvent(event);
		        int receiverRowIndex = cell.getRowIndex();
		        controller.setCurrentAccount(controller.getUserAccount(table.getText(receiverRowIndex, 0)));
		        RootPanel.get("page").clear();
		        controller.createAccountOverview();
		    }
		});
		title.addStyleName("white-text");
		table.addStyleName("white-text");
		vPanel.add(title);
		vPanel.add(table);
		vPanel.add(createAccount);
		RootPanel.get("page").add(vPanel);
	}

}
