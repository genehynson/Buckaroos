package com.buckaroos.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.buckaroos.server.Account;
import com.buckaroos.server.AccountTransaction;
import com.buckaroos.server.User;
import com.buckaroos.utility.Money;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DBConnectionAsync {

    void addUser(User user, AsyncCallback<User> callback);

    void getUser(String username, AsyncCallback<User> callback);

    void updateUser(String username, String password, String email,
            AsyncCallback callback);

    void addAccount(String username, Account account, AsyncCallback callback);

    void getAccount(String username, String accountName,
            AsyncCallback<Account> callback);

    void getAllAccounts(String username,
            AsyncCallback<ArrayList<Account>> callbackListAccounts);

    void addTransaction(String username, String accountName, double amount,
            String transactionType, String currencyType, String category,
            String transactionDate, String transactionTime,
            AsyncCallback callback);

    void updateAccountBalance(String username, String accountName,
            double amount, AsyncCallback callback);

    void getSpendingCategoryInfo(String username, String accountName,
            String startDate, String endDate,
            AsyncCallback<HashMap<String, Double>> callback);

    void getIncomeSourceInfo(String username, String accountName,
            String startDate, String endDate,
            AsyncCallback<HashMap<String, Double>> callback);

    void getTransaction(String username, String accountName, double amount,
            String category, String transactionDate, String transactionTime,
            AsyncCallback<AccountTransaction> callback);

    void getAllTransactions(String username, String accountName,
            AsyncCallback<List<AccountTransaction>> callback);

    void deleteTransaction(String username, String accountName, double amount,
            String category, String transactionDate, String transactionTime,
            AsyncCallback callback);

    void rollBackTransaction(String username, String accountName,
            double amount, String category, String transactionDate,
            String transactionTime, AsyncCallback callback);

    void sendResetPassword(String recipientEmail, String password,
            String username, AsyncCallback callback);

    void sendWelcomeEmail(String recipientEmail, String username,
            AsyncCallback callback);

    void sendTransactionHistoryOfAllUserAccounts(String recipientEmail,
            String username, List<AccountTransaction> transactions,
            AsyncCallback callback);

    void getCashFlowReportInfo(String username, String startDate,
            String endDate, AsyncCallback<HashMap<String, Double>> callback);

    void deleteAccount(String username, String accountName,
            AsyncCallback callback);

    void isPasswordCorrect(String username, String enteredPassword,
            AsyncCallback<Boolean> callback);

    void getAccountListingReportInfo(String username,
            AsyncCallback<Map<String, Double>> callback);

    void getTransactionHistoryInfo(String username, String accountName,
            String startDate, String endDate,
            AsyncCallback<Map<String, List<AccountTransaction>>> callback);

    void getCurrentTransactions(String username, String accountName,
            AsyncCallback<List<AccountTransaction>> callback);
    
    void editTransaction(AccountTransaction oldTransaction,
            String username, String accountName, double amount,
            String category, String transactionDate, String transactionTime,
            String transType, AsyncCallback callback);
}
