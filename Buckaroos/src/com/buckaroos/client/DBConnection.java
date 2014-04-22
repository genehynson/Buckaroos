package com.buckaroos.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.buckaroos.server.Account;
import com.buckaroos.server.AccountTransaction;
import com.buckaroos.server.User;
import com.buckaroos.utility.Money;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("dBConnection")
public interface DBConnection extends RemoteService {

    User addUser(User user);

    User getUser(String username);

    void updateUser(String username, String password, String email);

    void addAccount(String username, Account account);

    Account getAccount(String username, String accountName);

    ArrayList<Account> getAllAccounts(String username);

    void addTransaction(String username, String accountName, double amount,
            String transactionType, String currencyType, String category,
            String transactionDate, String transactionTime);

    void updateAccountBalance(String username, String accountName, double amount);

    HashMap<String, Double> getSpendingCategoryInfo(String username,
            String accountName, String startDate, String endDate);

    HashMap<String, Double> getIncomeSourceInfo(String username,
            String accountName, String startDate, String endDate);

    AccountTransaction getTransaction(String username, String accountName,
            double amount, String category, String transactionDate,
            String transactionTime);

    List<AccountTransaction> getAllTransactions(String username,
            String accountName);

    void deleteTransaction(String username, String accountName, double amount,
            String category, String transactionDate, String transactionTime);

    void sendResetPassword(String recipientEmail, String password,
            String username);

    void rollBackTransaction(String username, String accountName,
            double amount, String category, String transactionDate,
            String transactionTime);

    void sendWelcomeEmail(String recipientEmail, String username);

    void sendTransactionHistoryOfAllUserAccounts(String recipientEmail,
            String username, List<AccountTransaction> transactions);

    HashMap<String, Double> getCashFlowReportInfo(String username,
            String startDate, String endDate);

    void deleteAccount(String username, String accountName);

    boolean isPasswordCorrect(String username, String enteredPassword);

    Map<String, Double> getAccountListingReportInfo(String username);

    Map<String, List<AccountTransaction>> getTransactionHistoryInfo(
            String username, String accountName, String startDate,
            String endDate);

    List<AccountTransaction> getCurrentTransactions(String username,
            String accountName);

    void setUpForCurrencyConversion();

    double convertCurrency(Enum<Money> fromCurrency, Enum<Money> toCurrency,
            double amount);
}
