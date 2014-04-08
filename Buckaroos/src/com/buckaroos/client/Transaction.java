package com.buckaroos.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class Transaction extends Composite {

	private static TransactionUiBinder uiBinder = GWT
			.create(TransactionUiBinder.class);

	interface TransactionUiBinder extends UiBinder<Widget, Transaction> {
	}
	
	@UiField
	Button save, back;
	Label title, date, time, hours, minutes, amountTitle, categoryTitle, dollar;
	TextBox amount, category;
	RadioButton withdraw, deposit;
	ListBox hourBox, minuteBox;
	DatePicker datePicker;
	
	private ControllerInterface controller;
    private int hour, minute;
    private Panel vPanel, timePanel, radioPanel, buttonPanel, amountPanel;
	
	public Transaction() {
		initWidget(uiBinder.createAndBindUi(this));
		controller = new UserAccountController();
		datePicker = new DatePicker();
		datePicker.addStyleName("white-text");
    	hourBox = new ListBox();
    	minuteBox = new ListBox();
    	
    	dollar = new Label();
    	dollar.setText("$");
    	dollar.addStyleName("white-text");
    	title = new Label();
    	title.setText("Add New Transaction");
    	title.addStyleName("white-text");
    	date = new Label();
    	date.setText("Select Date:");
    	date.addStyleName("white-text");
    	time = new Label();
    	time.setText("Select Time:");
    	time.addStyleName("white-text");
    	amountTitle = new Label();
    	amountTitle.setText("Amount: ");
    	amountTitle.addStyleName("white-text");
    	categoryTitle = new Label();
    	categoryTitle.setText("Category: ");
    	categoryTitle.addStyleName("white-text");
    	hours = new Label();
    	hours.setText("Hours:");
    	hours.addStyleName("white-text");
    	minutes = new Label();
    	minutes.setText("Minutes: ");
    	minutes.addStyleName("white-text");
    	
    	
    	amount = new TextBox();
    	category = new TextBox();
    	
    	withdraw = new RadioButton("type");
    	deposit = new RadioButton("type");
    	withdraw.setText("Withdraw");
    	withdraw.addStyleName("white-text");
    	deposit.setText("Deposit");
    	deposit.addStyleName("white-text");
    	
    	createTimeBox();
    	
    	save = new Button();
    	save.setText("Save");
		save.addClickHandler(new ClickHandler() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(ClickEvent event) {
				hour = hourBox.getSelectedIndex() + 1;
				minute = minuteBox.getSelectedIndex() + 1;
				double newAmount;
				Date chosen = datePicker.getValue();
				chosen.setHours(hour);
				chosen.setMinutes(minute);
				if (!amount.getText().toString().equals("")) {
					newAmount = Double.parseDouble(amount.getText().toString());
					String categoryText = category.getText().toString().toLowerCase();
					if (withdraw.getValue()) {
						controller.addWithdrawal(newAmount, "dollars", categoryText,
								chosen);
					} else if (deposit.getValue()) {
						controller.addDeposit(newAmount, "dollars", categoryText, chosen);

					}
				}
			}
		});
		back = new Button();
		back.setText("Back");
		back.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("page").clear();
				controller.createAccountOverview();
			}
		});
		
		radioPanel = new HorizontalPanel();
		radioPanel.add(withdraw);
		radioPanel.add(deposit);
		
		timePanel = new HorizontalPanel();
		timePanel.add(hours);
		timePanel.add(hourBox);
		timePanel.add(minutes);
		timePanel.add(minuteBox);
		
		buttonPanel = new HorizontalPanel();
		buttonPanel.add(save);
		buttonPanel.add(back);
		
		amountPanel = new HorizontalPanel();
		amountPanel.add(dollar);
		amountPanel.add(amount);
		
		vPanel = new VerticalPanel();
		vPanel.add(title);
		vPanel.add(radioPanel);
		vPanel.add(time);
		vPanel.add(timePanel);
		vPanel.add(date);
		vPanel.add(datePicker);
		vPanel.add(amountTitle);
		vPanel.add(amountPanel);
		vPanel.add(categoryTitle);
		vPanel.add(category);
		vPanel.add(buttonPanel);
		RootPanel.get("page").add(vPanel);
		
		
	}

	private void createTimeBox() {
		for (int j = 1; j <= 24; j++) {
			hourBox.addItem(String.valueOf(j));
		}
		for (int i = 0; i < 60; i++) {
			if (i < 10) {
				minuteBox.addItem("0" + String.valueOf(i));
			} else {
				minuteBox.addItem(String.valueOf(i));							
			}
		}
	}

}
