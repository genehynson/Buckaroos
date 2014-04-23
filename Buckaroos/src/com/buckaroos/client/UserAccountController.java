package com.buckaroos.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.buckaroos.server.Account;
import com.buckaroos.server.AccountTransaction;
import com.buckaroos.server.User;
import com.buckaroos.utility.CurrencyConverter;
import com.buckaroos.utility.CurrencyConverterInterface;
import com.buckaroos.utility.CurrencyInformationProvider;
import com.buckaroos.utility.Money;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This class implements the ControllerInterface interface. This controller
 * object acts as a controller in the Model-View-Controller model. Controls the
 * communication between activities and User/Account.
 * 
 * @author Gene Hynson
 * @author Daniel Carnauba
 * @version 1.0
 */
public class UserAccountController implements ControllerInterface {

    private static User user = new User(" ", " ", " ");
    private static Account currentAccount;
    private static AccountTransaction currentTransaction;
    private static List<Account> userAccounts = new ArrayList<Account>();
    private static List<AccountTransaction> userTransactions;
    private static Map<String, Double> reportTransactions;
    private static List<String> reportTransactionNames;
    
    private String aPassword;
    private String aUsername;
    private String aEmail;
    private CurrencyInformationProvider currency;
    private List<String> currencies;
    private Money Money;
    
    private static AccountOverview accountOverview;
    private static Reports reports;
    private static ChangeAccount changeAccount;
    private static CreateAccount createAccount;
    private static Login login;
    private static Register register;  
    private static Transaction transaction;
    
    private static String beginDate;
    private static String theDate;
    private static String endDate;
    
    private static DBConnectionAsync db;
    private AsyncCallback<User> callbackUser;
    private AsyncCallback<Account> callbackAccount;
    private AsyncCallback<AccountTransaction> callbackTransaction;
    private AsyncCallback<List<AccountTransaction>> callbackListTransactions;
    private AsyncCallback<ArrayList<Account>> callbackListAccounts;
    private AsyncCallback<HashMap<String, Double>> callbackHashMap;
    private AsyncCallback<Double> callbackDouble; 
    private AsyncCallback<Boolean> callbackBoolean;
    private AsyncCallback callbackVoid;

    private boolean createChangeAccount = false;
    private boolean loginUser = false;
    private boolean registerUser = false;
    private boolean createAccountOverview = false;
    private boolean createReports = false;
    private boolean createCreateAccount = false;
    private boolean updateCurrentUser = false;
    private boolean doesLoginAccountExist = false;
    private boolean addAccount = false;
    private boolean addTransaction = false;
    private boolean editTransaction = false;
    private boolean changeDates = false;
    private boolean deleteTransaction = false;
    private boolean sendingResetPasswordEmail = false;
    private boolean sendingWelcomeEmail = false;
    private boolean updateUser = false;
    private boolean updateCurrentAccount = false;
    private boolean deleteAccount = false;
    private boolean convertCurrency = false;
    private boolean checkPassword = false;
    private boolean addFirstAccount = false;
    /**
     * Gets user/DB after login from CredientialConfirmer in Login activity.
     * 
     * @param user
     */
    @SuppressWarnings("static-access")
    public UserAccountController(User user) {
        this.user = user;
    	callbackUser = new CallbackHandler<User>();
    	callbackAccount = new CallbackHandler<Account>();
    	callbackTransaction = new CallbackHandler<AccountTransaction>();
    	callbackListTransactions = new CallbackHandler<List<AccountTransaction>>();
    	callbackListAccounts = new CallbackHandler<ArrayList<Account>>();
    	callbackHashMap = new CallbackHandler<HashMap<String, Double>>();
    	callbackVoid = new CallbackHandler();

    }

    public UserAccountController() {
    	callbackUser = new CallbackHandler<User>();
    	callbackAccount = new CallbackHandler<Account>();
    	callbackTransaction = new CallbackHandler<AccountTransaction>();
    	callbackListTransactions = new CallbackHandler<List<AccountTransaction>>();
    	callbackListAccounts = new CallbackHandler<ArrayList<Account>>();
    	callbackHashMap = new CallbackHandler<HashMap<String, Double>>();
    	callbackVoid = new CallbackHandler();

    }
    
    public UserAccountController(String url) {
    	callbackUser = new CallbackHandler<User>();
    	callbackAccount = new CallbackHandler<Account>();
    	callbackTransaction = new CallbackHandler<AccountTransaction>();
    	callbackListTransactions = new CallbackHandler<List<AccountTransaction>>();
    	callbackListAccounts = new CallbackHandler<ArrayList<Account>>();
    	callbackHashMap = new CallbackHandler<HashMap<String, Double>>();
    	callbackVoid = new CallbackHandler();

    	
    	reportTransactions = new HashMap<String, Double>();
    	reportTransactionNames = new ArrayList<String>();
    	
    	this.db = GWT.create(DBConnection.class);
    	ServiceDefTarget target = (ServiceDefTarget) this.db;
    	System.out.println(url);
    	target.setServiceEntryPoint(url);
    	
    	beginDate = new String();
    	endDate = new String();
    	theDate = new String();
    	
    }
    
//===============================
    //Add things
//===============================

    @Override
    public void addAccount(String accountName, String accountNickName,
            double amount, double interestRate) {
        currentAccount =
                new Account(user.getUsername(), accountName, accountNickName, amount, interestRate);
        addAccount = true;
        db.addAccount(user.getUsername(), currentAccount, callbackAccount);
    }

    @Override
    public void addWithdrawal(double amount, String currencyType,
            String category, Date date) {
        String dateString = convertDateToString(date);
        String timeString = convertTimeToString(date);
      //  amount = checkCurrency(amount, currencyType);
        addTransaction = true;
        db.addTransaction(user.getUsername(), currentAccount.getName(), amount,
                "Withdrawal", currencyType, category, dateString, timeString, callbackTransaction);
    }

    @Override
    public void addDeposit(double amount, String currencyType, String category,
            Date date) {
        String dateString = convertDateToString(date);
        String timeString = convertTimeToString(date);
  //      amount = checkCurrency(amount, currencyType);
        addTransaction = true;
        db.addTransaction(user.getUsername(), currentAccount.getName(), amount,
                "Deposit", currencyType, category, dateString, timeString, callbackTransaction);
    }
    
    private double checkCurrency(double amount, String currencyType) {
    	if (currencyType != "USD") {
    		convertCurrency = true;
//    		db.convertCurrency(Money.valueOf(currencyType), Money.USD, amount, callbackDouble);
    	}
    	return amount;
    }
    
    /**
     * Stores a new account and password that has been registered. If the
     * account name has been taken, the user is informed
     * 
     * @param accountName The account name to be created
     * @param password The password that corresponds with the new account
     */
    private void storeAccount(String accountName, String password, String email) {
    	User newUser = new User(accountName, password, email);
    	if (accountName != null && email != null && password != null) {
    		user = newUser;
			registerUser = true;
    		db.addUser(newUser, callbackUser);
    	}
    }
    
    @Override
    public void updateUser(String username, String password, String email) {
    	user.setAccountName(username);
    	user.setEmail(email);
    	user.setPassword(password);
    	db.updateUser(username, password, email, callbackUser);
    	updateUser = true;
    }
    
//=============================
    //convert things
//=============================

    @Override
    public String convertTimeToString(Date date) {
    	String pattern = "HH:mm"; /*your pattern here*/ 
    	DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
    	DateTimeFormat df = new DateTimeFormat(pattern, info) {};  // <= trick here
        return df.format(date);
    }

    @Override
    public String convertDateToString(Date date) {
    	String pattern = "yyyy/MM/dd"; /*your pattern here*/ 
    	DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
    	DateTimeFormat df = new DateTimeFormat(pattern, info) {};  // <= trick here
        return df.format(date);
    }

    @Override
    public Date convertStringToDate(String dateString) {
    	String pattern = "yyyy/MM/dd"; /*your pattern here*/ 
    	DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
    	DateTimeFormat df = new DateTimeFormat(pattern, info) {};  // <= trick here
        return df.parse(dateString);
    }
    
//=============================
    //check things
//=============================
    
    private void doesLoginAccountExist(String username) {
		doesLoginAccountExist = true;
    	db.getUser(username, callbackUser);
    }
    
    private boolean doesAccountExist(String accountName) {
    	for (Account acc : userAccounts) {
			if (acc.getName().equalsIgnoreCase(accountName)) {
				return true;
			}
		}
    	return false;
    }
    
    @Override
    public boolean hasAccount() {
    	if (userAccounts != null && !userAccounts.isEmpty()) {
    		return true;
    	}
    	return false;
    }
    
    private void isPasswordCorrect(String username, String aPassword) {
    	checkPassword = true;
    	db.isPasswordCorrect(username, aPassword, callbackVoid);
    }
    
//============================
    //set things
//============================
    
    public void setCurrentUser(User user) {
    	UserAccountController.user = user;
    }
    
    /**
     * Sets the begin date.
     * 
     * @param beginDate The beginDate to set.
     */
    public static void setBeginDate(String beginDate) {
    	UserAccountController.beginDate = beginDate;
    }
    
    /**
     * Sets the calendar date.
     * 
     * @param theDate The calendar date to set for a time period.
     */
    public static void setTheDate(String theDate) {
    	UserAccountController.theDate = theDate;
    }
    
    @Override
    public void setCurrentAccount(Account account) {
    	currentAccount = account;
    }
    
    /**
     * Sets the end date for a time period.
     * 
     * @param endDate The endDate to set for a time period.
     */
    public static void setEndDate(String endDate) {
    	UserAccountController.endDate = endDate;
    }
    
    
//=============================
    //get things
//=============================

    @Override
    public Account getCurrentAccount() {
        return currentAccount;
    }
    
    @Override
    public Account getUserAccount(String accountName) {
    	for (Account acc : userAccounts) {
    		if (acc.getName().equalsIgnoreCase(accountName)) {
    			return acc;
    		}
    	}
    	return null;
    }


    @Override
    public Account getFirstUserAccount() {
        return userAccounts.get(0);
    }

    @Override
    public DBConnectionAsync getDB() {
        return db;
    }


    @Override
    public void getLoginAccount(String username) {
    	db.getUser(username, callbackUser);
    }

    @Override
    public User getCurrentUser() {
        return user;
    }
    
    /**
     * Gets the begin date.
     * 
     * @return The beginDate.
     */
    public static String getBeginDate() {
    	return beginDate;
    }
    
    /**
     * Gets the calendar date object.
     * 
     * @return The calendar date object.
     */
    public static String getTheDate() {
    	return theDate;
    }
    
    /**
     * Gets the endDate for a time period.
     * 
     * @return The endDate. The end date for a time period.
     */
    public static String getEndDate() {
    	return endDate;
    }
    @Override
    public List<String> getCurrencyFullNames() {
    	currencies = new ArrayList<String>();
    	currency = new CurrencyInformationProvider();
    	currencies.add(currency.getFullCurrencyName(Money.USD));
    	currencies.add(currency.getFullCurrencyName(Money.EUR));
    	currencies.add(currency.getFullCurrencyName(Money.GBP));
    	currencies.add(currency.getFullCurrencyName(Money.CAD));
    	currencies.add(currency.getFullCurrencyName(Money.AUD));
    	currencies.add(currency.getFullCurrencyName(Money.JPY));
    	currencies.add(currency.getFullCurrencyName(Money.INR));
    	currencies.add(currency.getFullCurrencyName(Money.CHF));
    	currencies.add(currency.getFullCurrencyName(Money.RUB));
    	currencies.add(currency.getFullCurrencyName(Money.BRL));
    	currencies.add(currency.getFullCurrencyName(Money.MXN));
    	currencies.add(currency.getFullCurrencyName(Money.CNY));
    	currencies.add(currency.getFullCurrencyName(Money.AED));
    	currencies.add(currency.getFullCurrencyName(Money.BDT));
    	return currencies;
    }
    
    @Override
    public List<String> getCurrencySymbols() {
    	currencies = new ArrayList<String>();
    	currency = new CurrencyInformationProvider();
    	currencies.add(currency.getSymbolOfCurrency(Money.USD));
    	currencies.add(currency.getSymbolOfCurrency(Money.EUR));
    	currencies.add(currency.getSymbolOfCurrency(Money.GBP));
    	currencies.add(currency.getSymbolOfCurrency(Money.CAD));
    	currencies.add(currency.getSymbolOfCurrency(Money.AUD));
    	currencies.add(currency.getSymbolOfCurrency(Money.JPY));
    	currencies.add(currency.getSymbolOfCurrency(Money.INR));
    	currencies.add(currency.getSymbolOfCurrency(Money.CHF));
    	currencies.add(currency.getSymbolOfCurrency(Money.RUB));
    	currencies.add(currency.getSymbolOfCurrency(Money.BRL));
    	currencies.add(currency.getSymbolOfCurrency(Money.MXN));
    	currencies.add(currency.getSymbolOfCurrency(Money.CNY));
    	currencies.add(currency.getSymbolOfCurrency(Money.AED));
    	currencies.add(currency.getSymbolOfCurrency(Money.BDT));
    	return currencies;
    }
    
    @Override
    public List<String> getCurrencyAbrev() {
    	currencies = new ArrayList<String>();
    	currencies.add(Money.USD.toString());
    	currencies.add(Money.EUR.toString());
    	currencies.add(Money.GBP.toString());
    	currencies.add(Money.CAD.toString());
    	currencies.add(Money.AUD.toString());
    	currencies.add(Money.JPY.toString());
    	currencies.add(Money.INR.toString());
    	currencies.add(Money.CHF.toString());
    	currencies.add(Money.RUB.toString());
    	currencies.add(Money.BRL.toString());
    	currencies.add(Money.MXN.toString());
    	currencies.add(Money.CNY.toString());
    	currencies.add(Money.AED.toString());
    	currencies.add(Money.BDT.toString());
    	return currencies;
    }

//==========================
    //generate things
//==========================

    @Override
    public void generateSpendingCategoryReport() {
		changeDates = true;
    	db.getSpendingCategoryInfo(user.getUsername(),
    			currentAccount.getName(), beginDate,
    			endDate, callbackHashMap);
    }
    
    @Override
    public void generateIncomeSourceReport() {
    	changeDates = true;
    	db.getIncomeSourceInfo(user.getUsername(), currentAccount.getName(), beginDate, endDate, callbackHashMap);
    }
    
    @Override
    public void generateCashFlowReport() {
    	changeDates = true;
    	db.getCashFlowReportInfo(user.getUsername(), beginDate, endDate, callbackHashMap);
    }
    
    @Override
    public void resetPassword(String username) {
    	sendingResetPasswordEmail = true;
    	db.getUser(username, callbackUser);
    }
    
    @Override
    public void welcomeEmail(String username) {
    	sendingWelcomeEmail = true;
		db.sendWelcomeEmail(user.getEmail(), user.getEmail(), callbackVoid);
    }

//===========================
    //handle things
//===========================
    
    private class CallbackHandler<T> implements AsyncCallback<T> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Server request failed.");
		}

		@Override
		public void onSuccess(T result) {
			
			if (createChangeAccount) {
				
				userAccounts = (List<Account>) result;
				changeAccount = new ChangeAccount(userAccounts);
				createChangeAccount = false;
				
			} else if (createAccountOverview) {
				
				userTransactions = (List<AccountTransaction>) result;
				accountOverview = new AccountOverview(userTransactions);
				createAccountOverview = false;
				
			} else if (createReports) {
				
				reportTransactions = (Map<String, Double>) result;
				reportTransactionNames.addAll(reportTransactions.keySet());
				reports = new Reports(reportTransactions, reportTransactionNames);
				createReports = false;
				
			} else if (loginUser) {
				
				user = (User) result;
				if (result != null) {
					isPasswordCorrect(user.getUsername(), aPassword);
				} else {
					login.displayNotCorrect();
				}
				loginUser = false;
				
			} else if (checkPassword) {
				
				if ((Boolean) result) {
					RootPanel.get("page").clear();
					createChangeAccount();
				} else {
					login.displayNotCorrect();
				}
				
			} else if (registerUser) {
				
				if (result == null) {
					Window.alert("An error occured.");
				} else {
					RootPanel.get("page").clear();
					welcomeEmail(user.getUsername());
				}
				registerUser = false;
				
			} else if (updateCurrentUser) {
				
				user = (User) result;
				updateCurrentUser = false;
				
			} else if (updateCurrentAccount) {
				
				currentAccount = (Account) result;
				updateCurrentAccount = false;
				db.getAllTransactions(user.getUsername(), currentAccount.getName(), callbackListTransactions);
				createAccountOverview = true;
				
			} else if (doesLoginAccountExist) {
				
				if (result == null) {
					storeAccount(aUsername, aPassword, aEmail);
				} else {
					register.displayAccountAlreadyExists();
				}
				doesLoginAccountExist = false;
				
			} else if (createCreateAccount) {
				
				userAccounts = (List<Account>) result;
				createAccount = new CreateAccount();
				createCreateAccount = false;
				
			} else if (addAccount) {
				
				if (doesAccountExist(currentAccount.getName())) {
					createAccount.displayAccountAlreadyExists();
				} else {
					RootPanel.get("page").clear();
					createChangeAccount();
				}
				addAccount = false;
				
			} else if (addTransaction) {
				
				RootPanel.get("page").clear();
				createAccountOverview();
				addTransaction = false;
				
			} else if (changeDates) {
				
				reportTransactions = new HashMap<String, Double>();
				reportTransactions = (Map<String, Double>) result;
				reportTransactionNames = new ArrayList<String>();
				reportTransactionNames.addAll(reportTransactions.keySet());
				reports.setTransactionLists(reportTransactions, reportTransactionNames);
				changeDates = false;
				
			} else if (deleteTransaction) {
				
				RootPanel.get("page").clear();
				createAccountOverview();
				deleteTransaction = false;
			} else if (editTransaction) {
				editTransaction = false;
			} else if (deleteAccount) {
				
				RootPanel.get("page").clear();
				createChangeAccount();
				
			} else if (sendingResetPasswordEmail) {
				
				user = (User) result;
				if (user != null) {
					db.sendResetPassword(user.getEmail(), user.getPassword(), user.getUsername(), callbackVoid);
					Window.alert("Email sent.");
				} else {
					System.out.println("User is null");
					Window.alert("Not valid username. Please enter valid username and try again.");
				}
				sendingResetPasswordEmail = false;
				
			} else if (updateUser) {
				
				RootPanel.get("page").clear();
				createChangeAccount();
				updateUser = false;
				//do nothing
				
			} else if (convertCurrency) {
				
			} else if (sendingWelcomeEmail) {
				
				createCreateAccount();
				sendingWelcomeEmail = false;
				
			} else if (result == null) {
				
				System.out.println("Result is currently null");
			}
    	
		}
    }

	public void createChangeAccount() {
		createChangeAccount = true;
        db.getAllAccounts(user.getUsername(), callbackListAccounts);
	}
	
	public void createAccountOverview() {
		updateCurrentAccount = true;
        db.getAccount(user.getUsername(), currentAccount.getName(), callbackAccount);
	}
	
	public void createReports() {
		createReports = true;
		generateSpendingCategoryReport();
	}
	
	public void createRegister() {
		register = new Register();
	}
	
	public void createLogin() {
		login = new Login();
	}
	
	public void createCreateAccount() {
		createCreateAccount = true;
		db.getAllAccounts(user.getUsername(), callbackListAccounts);
	}
	
	public void loginUser(String accountName, String password) {
		loginUser = true;
		aPassword = password;
		db.getUser(accountName, callbackUser);
	}
	
	public void registerUser(String name, String password, String email) {
		aPassword = password;
		aUsername = name;
		aEmail = email;
    	doesLoginAccountExist(name);
	}
	
	public void changeDates(String beforeDate, String afterDate) {
		beginDate = beforeDate;
		endDate = afterDate;
		generateSpendingCategoryReport();
	}
	
	public void editTransaction(AccountTransaction t) {
		currentTransaction = t;
		RootPanel.get("page").clear();
		transaction = new Transaction();
		Date d = convertStringToDate(t.getDate());
		String time = d.getHours() + ":" + d.getMinutes();
		transaction.setValues(t.getType(), t.getAmount(), t.getCategory(), d, time);
		System.out.println(t.getType());
		System.out.println(t.getAmount());
		System.out.println(t.getCategory());
		System.out.println(d);
		System.out.println(time);
	}
	
	public void completeEditTransaction(double newAmount,
			String currency, String categoryText, Date chosen, String type) {
		Date time = new Date();
		time.setHours(chosen.getHours());
		time.setMinutes(chosen.getMinutes());
		editTransaction = true;
		System.out.println(type);
		System.out.println(newAmount);
		System.out.println(categoryText);
		System.out.println(chosen);
		System.out.println(time);
		db.editTransaction(currentTransaction, user.getUsername(), currentAccount.getName(), newAmount, categoryText, convertDateToString(chosen), convertDateToString(time), type, callbackVoid);
	}
	public void deleteTransaction(AccountTransaction t) {
		deleteTransaction = true;
		db.deleteTransaction(user.getUsername(), currentAccount.getName(), t.getAmount(), t.getCategory(), t.getTime(), t.getDate(), callbackTransaction);
	}
	
	public void deleteAccount() {
		deleteAccount = true;
		db.deleteAccount(user.getUsername(), currentAccount.getName(), callbackVoid);
	}
	
}
