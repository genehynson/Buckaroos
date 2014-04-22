package com.buckaroos.client;

import java.util.List;

import com.buckaroos.server.AccountTransaction;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AccountOverview extends Composite {

	private static AccountOverviewUiBinder uiBinder = GWT
			.create(AccountOverviewUiBinder.class);

	interface AccountOverviewUiBinder extends UiBinder<Widget, AccountOverview> {
	}
	@UiField
	Label accountName, title, accountBalanceInfo;
	Button menu, report, addTransaction, edit, delete, deleteAccount;
	FlexTable table;
	
	private ControllerInterface controller;
	private Panel vPanel, hPanel;
	private int receiverRowIndex;
	private List<AccountTransaction> transactions;
	
	public AccountOverview(List<AccountTransaction> theTransactions) {
		initWidget(uiBinder.createAndBindUi(this));
		controller = new UserAccountController();
		transactions = theTransactions;
		accountName = new Label();
		accountName.addStyleName("white-text");
		accountBalanceInfo = new Label();
		accountBalanceInfo.addStyleName("white-text");
		accountBalanceInfo.setText("Account: " + controller.getCurrentAccount().getName() + ". Balance: $" + controller.getCurrentAccount().getBalance());
		title = new Label();
		title.setText("Account Overview");
		title.addStyleName("title");
		title.addStyleName("white-text");
		menu = new Button();
		menu.setText("Select Account");
		menu.addStyleName("blue-button");

		deleteAccount = new Button();
		deleteAccount.setText("Delete This Account");
		deleteAccount.addStyleName("blue-button");

		report = new Button();
		report.setText("Reports");
		report.addStyleName("blue-button");

		addTransaction = new Button();
		addTransaction.setText("New Transaction");
		addTransaction.addStyleName("blue-button");

		table = new FlexTable();
		table.addStyleName("white-text");
		accountName.setText(controller.getCurrentAccount().getName());
		edit = new Button("Edit");
		edit.addStyleName("black-text");
		delete = new Button("Delete");
		delete.addStyleName("black-text");
		edit.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Cell cell = table.getCellForEvent(event);
				receiverRowIndex = cell.getRowIndex();
				controller.editTransaction(transactions.get(receiverRowIndex));
				
			}
		});
		deleteAccount.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("page").clear();
				controller.deleteAccount();
			}
		});
		delete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Cell cell = table.getCellForEvent(event);
				receiverRowIndex = cell.getRowIndex();
				controller.deleteTransaction(transactions.get(receiverRowIndex));
			}
		});
		table.setText(0, 0, "Category");
		table.setText(0, 1, "Amount");
		table.setText(0, 2, "Currency");
		table.setText(0, 3, "Type");
		table.setText(0, 4, "Date");
		
		for (int i = 1; i < transactions.size(); i++) {
			int index = i-1;
			table.setText(i, 0, transactions.get(index).getCategory());
			table.setText(i, 1, String.valueOf(transactions.get(index).getAmount()));
			table.setText(i, 2, transactions.get(index).getCurrency());
			table.setText(i, 3, transactions.get(index).getType());
			table.setText(i, 4, transactions.get(index).getDate());

			table.getCellFormatter().addStyleName(i, 0, "table-styling");
			table.getCellFormatter().addStyleName(i, 1, "table-styling");
			table.getCellFormatter().addStyleName(i, 2, "table-styling");
			table.getCellFormatter().addStyleName(i, 3, "table-styling");
			table.getCellFormatter().addStyleName(i, 4, "table-styling");


		}
		table.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Cell cell = table.getCellForEvent(event);
				receiverRowIndex = cell.getRowIndex();
				if (receiverRowIndex != 0) {
					table.setWidget(receiverRowIndex, 5, edit);
					table.setWidget(receiverRowIndex, 6, delete);
				}
			}
		});
		
		menu.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("page").clear();
				controller.createChangeAccount();
			}
		});
		
		report.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("page").clear();
				controller.createReports();
			}
		});
		
		addTransaction.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("page").clear();
				Transaction t = new Transaction();
			}
		});
		vPanel = new VerticalPanel();
		hPanel = new HorizontalPanel();
		hPanel.add(menu);
		hPanel.add(report);
		hPanel.add(addTransaction);
		hPanel.add(deleteAccount);
		vPanel.add(title);
		vPanel.add(accountBalanceInfo);
		vPanel.add(table);
		vPanel.add(hPanel);
		RootPanel.get("page").add(vPanel);
		
	}
}