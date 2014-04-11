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
	Label accountName, title;
	Button menu, report, addTransaction, edit, delete;
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
		title = new Label();
		title.setText("Account Overview");
		title.addStyleName("white-text");
		menu = new Button();
		menu.setText("Select Account");
		report = new Button();
		report.setText("Reports");
		addTransaction = new Button();
		addTransaction.setText("New Transaction");
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
		delete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Cell cell = table.getCellForEvent(event);
				receiverRowIndex = cell.getRowIndex();
				controller.deleteTransaction(transactions.get(receiverRowIndex));
			}
		});
		for (int i = 0; i < transactions.size(); i++) {
			table.setText(i, 0, transactions.get(i).getCategory());
			table.setText(i, 1, String.valueOf(transactions.get(i).getAmount()));
			table.getCellFormatter().addStyleName(i, 0, "table-styling");
			table.getCellFormatter().addStyleName(i, 1, "table-styling");
		}
		table.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Cell cell = table.getCellForEvent(event);
				receiverRowIndex = cell.getRowIndex();
				table.setWidget(receiverRowIndex, 2, edit);
				table.setWidget(receiverRowIndex, 3, delete);
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
		vPanel.add(title);
		vPanel.add(table);
		vPanel.add(hPanel);
		RootPanel.get("page").add(vPanel);
		
	}
}