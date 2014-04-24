package com.buckaroos.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.buckaroos.server.AccountTransaction;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class Reports extends Composite {

	private static ReportsUiBinder uiBinder = GWT.create(ReportsUiBinder.class);

	@UiField
	Button menu;
	FlexTable table;
	Label title;
	Label dash;
	DateBox fromDate, toDate;
	RadioButton spendingReport, cashFlow, incomeReport, transactionHistory;
	
    private ControllerInterface controller;
    private Panel vPanel, hPanel, radioButtonPanel;
    private String beginDate, afterDate;
    private Map<String, Double> categoryTotals;
    private List<String> categoryNames;
    private Map<String, List<AccountTransaction>> history;
    

    interface ReportsUiBinder extends UiBinder<Widget, Reports> {
    }

    public Reports(Map<String, Double> totals, List<String> names) {
    	System.out.println("made it to reports!");
    	initWidget(uiBinder.createAndBindUi(this));
    	controller = new UserAccountController();
    	categoryNames = names;
    	categoryTotals = totals;
    	fromDate = new DateBox();
    	fromDate.getDatePicker().addStyleName("white-text");
    	toDate = new DateBox();
    	toDate.getDatePicker().addStyleName("white-text");

    	title = new Label();
    	title.setText("Reports");
    	title.addStyleName("white-text");
    	title.addStyleName("title");
    	dash = new Label();
    	dash.addStyleName("white-text");
    	
    	spendingReport = new RadioButton("type");
    	spendingReport.setText("Spending Report");
    	spendingReport.addStyleName("white-text");
    	spendingReport.setValue(true);
    	spendingReport.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				controller.generateSpendingCategoryReport();
			}
		});
    	transactionHistory = new RadioButton("type");
    	transactionHistory.setText("Transaction History");
    	transactionHistory.addStyleName("white-text");
    	transactionHistory.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				controller.generateTransactionHistory();
			}
		});
    	cashFlow = new RadioButton("type");
    	cashFlow.setText("Cash Flow Report");
    	cashFlow.addStyleName("white-text");
    	cashFlow.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				controller.generateCashFlowReport();
			}
		});
    	incomeReport = new RadioButton("type");
    	incomeReport.setText("Income Source Report");
    	incomeReport.addStyleName("white-text");
    	incomeReport.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				controller.generateIncomeSourceReport();
			}
		});
    	
    	beginDate = "Select date";
    	afterDate = "Select date";
    	toDate.getTextBox().setText(afterDate);
    	fromDate.getTextBox().setText(beginDate);
    	dash.setText(" - ");
    	fromDate.addValueChangeHandler(new ValueChangeHandler<Date>() {
    		public void onValueChange(ValueChangeEvent<Date> event) {
    			Date date = event.getValue();
    			String pattern = "yyyy/MM/dd"; /*your pattern here*/ 
    	    	DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
    	    	DateTimeFormat df = new DateTimeFormat(pattern, info) {};  // <= trick here
    	        beginDate = df.format(date);
    	    	fromDate.getTextBox().setText(beginDate);
    	    	if (toDate.getTextBox().getText().equals("Select date")) {
    	    		toDate.getTextBox().setText("today");
    	    	}
    	    	controller.changeDates(beginDate, afterDate);
    	    	System.out.println(beginDate);
    	    	spendingReport.setValue(true);
    		}
    	});
    	toDate.addValueChangeHandler(new ValueChangeHandler<Date>() {
    		public void onValueChange(ValueChangeEvent<Date> event) {
    			Date date = event.getValue();
    			String pattern = "yyyy/MM/dd"; /*your pattern here*/ 
    	    	DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
    	    	DateTimeFormat df = new DateTimeFormat(pattern, info) {};  // <= trick here
    	        afterDate = df.format(date);
    	    	toDate.getTextBox().setText(afterDate);
    	    	if (fromDate.getTextBox().getText().equals("Select date")) {
    	    		fromDate.getTextBox().setText("today");
    	    	}
    	    	controller.changeDates(beginDate, afterDate);
    	    	spendingReport.setValue(true);
    		}
    	});
    	menu = new Button();
    	menu.setText("Account Overview");
    	menu.addStyleName("blue-button");
		menu.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("page").clear();
				controller.createAccountOverview();
			}
		});

		buildTable();
		
		radioButtonPanel = new VerticalPanel();
		radioButtonPanel.add(spendingReport);
		radioButtonPanel.add(cashFlow);
		radioButtonPanel.add(incomeReport);
		radioButtonPanel.add(transactionHistory);
		
		hPanel = new HorizontalPanel();
		hPanel.add(fromDate);
		hPanel.add(dash);
		hPanel.add(toDate);
		vPanel = new VerticalPanel();
		vPanel.add(title);
		vPanel.add(radioButtonPanel);
		vPanel.add(hPanel);
		vPanel.add(table);
		vPanel.add(menu);
		RootPanel.get("page").add(vPanel);
    }
    
    private void buildTable() {
		String name = "";
		String total = "";
		table = new FlexTable();
		table.addStyleName("white-text");
		for (int i = 0; i < categoryNames.size(); i++) {
			name = categoryNames.get(i);
			total = categoryTotals.get(name).toString();
			table.setText(i,0, name);
			table.setText(i,1, "Total: " + total);
			table.getCellFormatter().addStyleName(i, 0, "table-styling");
			table.getCellFormatter().addStyleName(i, 1, "table-styling");
		}
		RootPanel.get("page").clear();
		
		radioButtonPanel = new VerticalPanel();
		radioButtonPanel.add(spendingReport);
		radioButtonPanel.add(cashFlow);
		radioButtonPanel.add(incomeReport);
		radioButtonPanel.add(transactionHistory);
		
		hPanel = new HorizontalPanel();
		hPanel.add(fromDate);
		hPanel.add(dash);
		hPanel.add(toDate);
		vPanel = new VerticalPanel();
		vPanel.add(title);
		vPanel.add(radioButtonPanel);
		vPanel.add(hPanel);
		vPanel.add(table);
		vPanel.add(menu);
		RootPanel.get("page").add(vPanel);
    }
    
    private void buildTableHistory() {
    	RootPanel.get("page").clear();
		table = new FlexTable();
		table.addStyleName("white-text");
		int i = 0;
		for (AccountTransaction trans : history.get("RolledBack")) {
			table.setText(i,0, "RolledBack");
			table.setText(i,1, trans.getCategory());
			table.setText(i,2, "Total: " + trans.getAmount());
			table.getCellFormatter().addStyleName(i, 0, "table-styling");
			table.getCellFormatter().addStyleName(i, 1, "table-styling");
			table.getCellFormatter().addStyleName(i, 2, "table-styling");
			i++;
		}
		
		for (AccountTransaction trans : history.get("Committed")) {
			table.setText(i,0, "Committed");
			table.setText(i,1, trans.getCategory());
			table.setText(i,2, "Total: " + trans.getAmount());
			table.getCellFormatter().addStyleName(i, 0, "table-styling");
			table.getCellFormatter().addStyleName(i, 1, "table-styling");
			table.getCellFormatter().addStyleName(i, 2, "table-styling");
			i++;
		}
		
		radioButtonPanel = new VerticalPanel();
		radioButtonPanel.add(spendingReport);
		radioButtonPanel.add(cashFlow);
		radioButtonPanel.add(incomeReport);
		radioButtonPanel.add(transactionHistory);
		
		hPanel = new HorizontalPanel();
		hPanel.add(fromDate);
		hPanel.add(dash);
		hPanel.add(toDate);
		vPanel = new VerticalPanel();
		vPanel.add(title);
		vPanel.add(radioButtonPanel);
		vPanel.add(hPanel);
		vPanel.add(table);
		vPanel.add(menu);
		RootPanel.get("page").add(vPanel);
    }
		
		
    public void setTransactionLists(Map<String, Double> transactions, List<String> names) {
    	categoryTotals = transactions;
    	categoryNames = names;
    	buildTable();
    }
    
    public void setTransactionListsHistory(Map<String, List<AccountTransaction>> transactions) {
    	history = transactions;
    	buildTableHistory();
    }

}
