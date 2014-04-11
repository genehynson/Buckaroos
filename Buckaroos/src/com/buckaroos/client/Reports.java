package com.buckaroos.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

public class Reports extends Composite {

	private static ReportsUiBinder uiBinder = GWT.create(ReportsUiBinder.class);

	@UiField
	Button menu;
	FlexTable table;
	Label title;
	Label dash;
	DateBox fromDate, toDate;
	
    private ControllerInterface controller;
    private Panel vPanel, hPanel;
    private String beginDate, afterDate;
    private Map<String, Double> categoryTotals;
    private List<String> categoryNames;
    

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
    	dash = new Label();
    	dash.addStyleName("white-text");
    	beginDate = "Select date";
    	afterDate = "Select date";
    	dash.setText(" - ");
    	fromDate.addValueChangeHandler(new ValueChangeHandler<Date>() {
    		public void onValueChange(ValueChangeEvent<Date> event) {
    			Date date = event.getValue();
    			String pattern = "yyyy/MM/dd"; /*your pattern here*/ 
    	    	DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
    	    	DateTimeFormat df = new DateTimeFormat(pattern, info) {};  // <= trick here
    	        beginDate = df.format(date);
    	    	fromDate.getTextBox().setText(beginDate);
    	    	controller.changeDates(beginDate, afterDate);
    	    	System.out.println(beginDate);
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
    	    	controller.changeDates(beginDate, afterDate);
    		}
    	});
    	menu = new Button();
    	menu.setText("Account Overview");
		menu.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("page").clear();
				controller.createAccountOverview();
			}
		});
		table = new FlexTable();
		table.addStyleName("white-text");
		buildTable();
		hPanel = new HorizontalPanel();
		hPanel.add(fromDate);
		hPanel.add(dash);
		hPanel.add(toDate);
		vPanel = new VerticalPanel();
		vPanel.add(title);
		vPanel.add(hPanel);
		vPanel.add(table);
		vPanel.add(menu);
		RootPanel.get("page").add(vPanel);
    }
    
    private void buildTable() {
		String name = "";
		String total = "";
		NumberFormat us = NumberFormat.getCurrencyFormat();
		for (int i = 0; i < categoryNames.size(); i++) {
			name = categoryNames.get(i);
			total = us.format(categoryTotals.get(name));
			table.setText(i,0, name);
			table.setText(i,1, "Total: " + total);
			table.getCellFormatter().addStyleName(i, 0, "table-styling");
			table.getCellFormatter().addStyleName(i, 1, "table-styling");
		}
		RootPanel.get("page").clear();
		hPanel = new HorizontalPanel();
		hPanel.add(fromDate);
		hPanel.add(dash);
		hPanel.add(toDate);
		vPanel = new VerticalPanel();
		vPanel.add(title);
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

}
