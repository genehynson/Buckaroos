package com.buckaroos.client;

import java.util.Date;
import java.util.List;

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
	Label title, date, time, hours, minutes, amountTitle, categoryTitle, dollar, selectCurrency;
	TextBox amount, category;
	RadioButton withdraw, deposit;
	ListBox hourBox, minuteBox, currencyBox;
	DatePicker datePicker;
	
	private ControllerInterface controller;
    private int hour, minute;
    private Panel vPanel, timePanel, radioPanel, buttonPanel, amountPanel, currencyLabelPanel;
	private List<String> currencies;

	
	public Transaction() {
		initWidget(uiBinder.createAndBindUi(this));
		controller = new UserAccountController();
		datePicker = new DatePicker();
		datePicker.addStyleName("white-text");
		selectCurrency = new Label();
		selectCurrency.setText("Select Currency:");
		selectCurrency.addStyleName("white-text");
    	hourBox = new ListBox();
    	minuteBox = new ListBox();
    	currencyBox = new ListBox();
		currencies = controller.getCurrencyAbrev();
		for (String currency : currencies) {
			currencyBox.addItem(currency);
		}
		currencyBox.setItemSelected(0, true);
    	dollar = new Label();
    	dollar.setText("$");
    	dollar.addStyleName("white-text");
    	title = new Label();
    	title.setText("Add New Transaction");
    	title.addStyleName("white-text");
    	title.addStyleName("title");
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
    	minutes.setText(" : ");
    	minutes.addStyleName("white-text");
    	
    	
    	amount = new TextBox();
		amount.addStyleName("field-box");
    	category = new TextBox();
		category.addStyleName("field-box");

    	withdraw = new RadioButton("type");
    	deposit = new RadioButton("type");
    	withdraw.setText("Withdraw");
    	withdraw.addStyleName("white-text");
    	deposit.setText("Deposit");
    	deposit.addStyleName("white-text");
    	
    	createTimeBox();
    	save = new Button();
    	save.setText("Save");
    	save.addStyleName("blue-button");
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
					System.out.println(controller.getCurrentUser().getUsername());
					System.out.println(controller.getCurrentAccount().getName());
					if (withdraw.getValue()) {
						controller.addWithdrawal(newAmount, currencyBox.getItemText(currencyBox.getSelectedIndex()), categoryText,
								chosen);
					} else if (deposit.getValue()) {
						controller.addDeposit(newAmount, currencyBox.getItemText(currencyBox.getSelectedIndex()), categoryText, chosen);

					}
				}
			}
		});
		back = new Button();
		back.setText("Back");
		back.addStyleName("blue-button");
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
//		timePanel.add(hours);
		timePanel.add(hourBox);
		timePanel.add(minutes);
		timePanel.add(minuteBox);
		
		currencyLabelPanel = new HorizontalPanel();
		currencyLabelPanel.add(amountTitle);
		currencyLabelPanel.add(selectCurrency);
		
		amountPanel = new HorizontalPanel();
//		amountPanel.add(dollar);
		amountPanel.add(amount);
		amountPanel.add(currencyBox);

		buttonPanel = new HorizontalPanel();
		buttonPanel.add(save);
		buttonPanel.add(back);
		
		vPanel = new VerticalPanel();
		vPanel.add(title);
		vPanel.add(radioPanel);
		vPanel.add(time);
		vPanel.add(timePanel);
		vPanel.add(date);
		vPanel.add(datePicker);
		vPanel.add(currencyLabelPanel);
		vPanel.add(amountPanel);
		vPanel.add(categoryTitle);
		vPanel.add(category);
		vPanel.add(buttonPanel);
		RootPanel.get("page").add(vPanel);
		
		
	}
	
	public void setValues(String typeValue, double amountValue, String categoryValue, Date dateValue, String timeValue) {
		amount.setText(String.valueOf(amountValue));
		category.setText(categoryValue);
		datePicker.setValue(dateValue);
		if (typeValue.equals("Deposit")) {
			deposit.setValue(true);
		} else {
			withdraw.setValue(true);
		}
		hourBox.setSelectedIndex(Integer.parseInt(timeValue.substring(0, 1)));
		minuteBox.setSelectedIndex(Integer.parseInt(timeValue.substring(3, 4)));
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
