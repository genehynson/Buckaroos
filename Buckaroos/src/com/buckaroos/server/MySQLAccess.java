package com.buckaroos.server;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.buckaroos.client.DBConnection;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


//Download mysql server from here: http://dev.mysql.com/downloads/mysql/
//and then set the server up on your computer to use for testing.
//Once you create a password, change yourPasswordHere to whatever your password
//is in the constructor

/**
 * This class establishes a connection with a MYSQL Server and provides the java
 * code necessary to communicate with that server
 * 
 * @author Jordan
 * @version 1.0
 */
public class MySQLAccess extends RemoteServiceServlet implements DBConnection {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement query = null;

    /**
     * This connects the application with the database through a JDBC Driver
     * 
     * @throws SQLException Throws a SQLException if the connection to the DB
     *             fails
     */
    public MySQLAccess() throws SQLException {
        // this will load the MySQL driver, each DB has its own driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("have the driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Driver for JDBC not in build path");
        }
        // setup the connection with the DB. This will change when
        // connecting to Deloitte's server
        connect = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/test"
                        + "?user=root&password=buckaroos");
    }

    /*
     * Creates a statement connected to the server
     * 
     * @return True if the creation was succesful, false otherwise
     */
    private boolean createStatementForConnection() {
        try {
            statement = connect.createStatement();
            return true;
        } catch (SQLException e1) {
            System.out.println("Could not establish connection to server");
            e1.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a user to the database
     * 
     * @param user The user to be added
     * @throws SQLException Throws an exception if a user with the same username
     *             passed in already exists
     */
    public void addUser(User user) {
        // Will have to change test to whatever the name of the DB is that is
        // hosted by Deloitte
        try {
        	query = connect
        			.prepareStatement("insert into test.Credentials values (?, ?, ?)");
			query.setString(1, user.getUsername());
			query.setString(2, user.getPassword());
			query.setString(3, user.getEmail());
			query.executeUpdate();
			System.out.println("hello?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    /**
     * Retrieves a user's information from the database
     * 
     * @param username The name of the user to be retrieved
     * @return The user if it exists, null otherwise
     */
    public User getUser(String username) {
        User foundUser = null;
        createStatementForConnection();
        try {
            String selectQuery = "SELECT * FROM Credentials "
                    + "WHERE Username = '" + username + "'";
            ResultSet aResultSet = statement.executeQuery(selectQuery);
            if (aResultSet.first()) {
                String email = aResultSet.getString("Email");
                // This interest rate will be out of 1 where 1 is 100% interest
                String password = aResultSet.getString("Password");
                foundUser = new User(username, password, email);
            }
        } catch (SQLException e) {
            System.out.println("Username not found");
            e.printStackTrace();
        }
        return foundUser;
    }

    /**
     * Updates a user's credentials. Pass in the previous password or email if
     * you only want to update one of them
     * 
     * @param username The username whose credentials are being updated
     * @param password The new password
     * @param email The new email
     */
    public void updateUser(String username, String password, String email) {
        createStatementForConnection();
        try {
            query = connect
                    .prepareStatement("UPDATE Credentials SET Password = '"
                            + password + "', Email = '" + email
                            + "' WHERE Username = '" + username + "'");
            System.out.println(query);
            query.executeUpdate();
        } catch (SQLException e) {
            System.out.println("No such username");
            e.printStackTrace();
        }

    }

    /**
     * Adds an account to the database. Note: This is an account of a user, it
     * is different than the account the user logs in with
     * 
     * @param account The account to be added
     * @param user The user that the account is associated with
     * @throws SQLException Throws an exception if a user with the same username
     *             and accountName passed in already exists
     */
    public void addAccount(String username, Account account) {
        try {
        	query = connect
        			.prepareStatement("insert into test.Accounts (Username, "
        					+ "AccountName, Balance, InterestRate, AccountNickName)"
        					+ " values (?, ?, ?, ?, ?)");
        	query.setString(1, username);
        	query.setString(2, account.getName());
			query.setDouble(3, account.getBalance());
			query.setDouble(4, account.getInterestRate());
			query.setString(5, account.getNickName());
			query.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    /**
     * Retrieves an account from the database
     * 
     * @param username The username the account is associated with
     * @param accountName The name of the account to retrieve
     * @return The account being retrieved, null if it does not exist
     */
    public Account getAccount(String username, String accountName) {
        Account foundAccount = null;
        createStatementForConnection();
        try {
            String selectQuery = "SELECT * FROM Accounts "
                    + "WHERE Username = '" + username + "' AND AccountName = '"
                    + accountName + "' GROUP BY AccountName";
            ResultSet aResultSet = statement.executeQuery(selectQuery);
            if (aResultSet.first()) {
                double balance = aResultSet.getDouble("Balance");
                // This interest rate will be out of 1 where 1 is 100% interest
                double interestRate = aResultSet.getDouble("InterestRate");
                String nickName = aResultSet.getString("AccountNickName");
                foundAccount = new Account(username, accountName, nickName,
                        balance, interestRate);
            }
        } catch (SQLException e) {
            System.out.println("Account not found");
            e.printStackTrace();
        }
        return foundAccount;
    }

    /**
     * Retrieves all accounts from the database
     * 
     * @param username The username with which the accounts are associated
     * @return An ArrayList containing all the accounts in the database
     *         associated with a particular user
     */
    public ArrayList<Account> getAllAccounts(String username) {
        ArrayList<Account> accList = new ArrayList<Account>();
        createStatementForConnection();
        try {
            String selectQuery = "SELECT * FROM Accounts "
                    + "WHERE Username = '" + username
                    + "' GROUP BY AccountName";
            ResultSet aResultSet = statement.executeQuery(selectQuery);
            while (aResultSet.next()) {
                String accountName = aResultSet.getString("AccountName");
                double balance = aResultSet.getDouble("Balance");
                // This interest rate will be out of 1 where 1 is 100% interest
                double interestRate = aResultSet.getDouble("InterestRate");
                String nickName = aResultSet.getString("AccountNickName");
                Account foundAccount = new Account(username, accountName,
                        nickName, balance, interestRate);
                accList.add(foundAccount);
            }
        } catch (SQLException e) {
            System.out.println("Account not found");
            e.printStackTrace();
        }
        return accList;
    }

    /**
     * * Adds a transaction to the database
     * 
     * @param username The account name that the user uses to log in with
     * @param accountName The account the transaction is associated with
     * @param amount The amount of the transaction
     * @param transactionType Specifies if the transaction is a withdrawal or a
     *            deposit
     * @param currencyType The type of currency used in the transaction
     * @param category The expense or income category
     * @throws SQLException Throws an exception if a user tries to add a
     *             transaction that already exists in the database or if the
     *             account's balance failed to update
     */
    public void addTransaction(String username, String accountName,
            double amount, String transactionType, String currencyType,
            String category, String transactionDate, String transactionTime) {
        try {
			query = connect
			        .prepareStatement("insert into test.Transactions (Username, "
			                + "AccountName, Amount, TransactionType, CurrencyType, "
			                + "Category, TransactionDate, TransactionTime)"
			                + " values (?, ?, ?, ?, ?, ?, ?, ?)");
			query.setString(1, username);
			query.setString(2, accountName);
			query.setDouble(3, amount);
			query.setString(4, transactionType);
			query.setString(5, currencyType);
			query.setString(6, category);
			query.setString(7, transactionDate);
			query.setString(8, transactionTime);
			query.executeUpdate();
			System.out.println("Passed execute update");
		} catch (SQLException e) {
			e.printStackTrace();
		}
        if (transactionType.equals("Withdrawal")) {
            amount = -amount;
        }
        updateAccountBalance(username, accountName, amount);
    }

    /**
     * Updates an account with a new balance
     * 
     * @param username The username of the account that is logged in
     * @param accountName The name of the account whose balance is to be updated
     * @param amount The amount to add/subtract from the balance
     */
    public void updateAccountBalance(String username, String accountName,
            double amount) {
        // Add a way to account for currencyType when updating balance
        createStatementForConnection();
        try {
            String selectQuery = "SELECT Balance FROM test.Accounts "
                    + " WHERE Username = '" + username
                    + "' AND AccountName = '" + accountName + "'";
            ResultSet aResultSet = statement.executeQuery(selectQuery);
            while (aResultSet.next()) {
                double oldBalance = aResultSet.getDouble("Balance");
                double newBalance = oldBalance + amount;
                query = connect
                        .prepareStatement("UPDATE Accounts SET Balance = "
                                + newBalance + " WHERE Username = '" + username
                                + "' AND AccountName = '" + accountName + "'");
                query.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the spending categories and the amount spent in those
     * categories associated with a username and account between a startDate and
     * endDate
     * 
     * @param username The username with which the spending categories are to be
     *            retrieved from
     * @param accountName The accountName with which the spending categories are
     *            to be retrieved from
     * @param startDate The beginning of the date range to retrieve transactions
     *            from
     * @param endDate The end of the date range to retrieve transactions from
     * @return A hashmap containing the spending categories as keys and the
     *         amount spent as values or null if a database error occurs
     */
    public HashMap<String, Double> getSpendingCategoryInfo(String username,
            String accountName, String startDate, String endDate) {
        return getTransactionCategoryInfo(username, accountName, startDate,
                endDate, "Withdrawal");
    }

    /**
     * Retrieves the income sources and the amount earned in those categories
     * associated with a username and account between a startDate and endDate
     * 
     * @param username The username with which the income sources are to be
     *            retrieved from
     * @param accountName The accountName with which the income sources are to
     *            be retrieved from
     * @param startDate The beginning of the date range to retrieve transactions
     *            from
     * @param endDate The end of the date range to retrieve transactions from
     * @return A hashmap containing the income sources as keys and the amount
     *         earned as values or null if a database error occurs
     */
    public HashMap<String, Double> getIncomeSourceInfo(String username,
            String accountName, String startDate, String endDate) {
        return getTransactionCategoryInfo(username, accountName, startDate,
                endDate, "Deposit");
    }

    /*
     * Called by getIncomeSourceInfo and getSpendingCategoryInfo. Performs a
     * select query to retrieve the appropriate information needed to produce
     * spending category and income source reports. Takes in a transactionType
     * to know which type of report to retrieve info for. Returns null if a
     * database error occurs
     */
    private HashMap<String, Double> getTransactionCategoryInfo(String username,
            String accountName, String startDate, String endDate,
            String transType) {
        // The date passed in needs to be formatted as YYYY/MM/DD
        createStatementForConnection();
        try {
            HashMap<String, Double> categoryMap = new HashMap<>();
            String selectQuery = "SELECT Category, SUM(Amount) AS amount FROM "
                    + "Transactions WHERE Username = '" + username + "' AND "
                    + "AccountName = '" + accountName + "' AND "
                    + "TransactionType = '" + transType
                    + "' AND TransactionDate > '" + startDate
                    + "' AND TransactionDate < '" + endDate + "' GROUP "
                    + "BY Category";
            ResultSet aResultSet = statement.executeQuery(selectQuery);
            while (aResultSet.next()) {
                String category = aResultSet.getString(1);
                double totalSpending = aResultSet.getDouble(2);
                categoryMap.put(category, totalSpending);
            }
            return categoryMap;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves a transaction from the database
     * 
     * @param username The username for whom the transaction is being retrieved
     * @param accountName The accountName from which to fetch the transaction
     * @param amount The amount of the transaction being retrieved
     * @param category The category of the transaction being retrieved
     * @param transactionDate The date the transaction occurred
     * @param transactionTime The time the transaction occurred
     * @return A transaction from the database if found, null otherwise
     * @throws SQLException Throws an exception if the transaction being
     *             retrieved does not exist in the database
     */
    public AccountTransaction getTransaction(String username,
            String accountName, double amount, String category,
            String transactionDate, String transactionTime) {
        AccountTransaction returnTransaction = null;
        createStatementForConnection();
        try {
            String selectQuery = "SELECT * FROM Transactions "
                    + "WHERE Username = '" + username + "' AND AccountName = '"
                    + accountName + "' AND Amount = '" + amount
                    + "' AND Category " + "= '" + category
                    + "' AND TransactionDate = '" + transactionDate
                    + "' AND TransactionTime = '" + transactionTime + "'";
            ResultSet aResultSet = statement.executeQuery(selectQuery);
            if (aResultSet.first()) {
                double amountFound = aResultSet.getDouble("Amount");
                String currencyType = aResultSet.getString("CurrencyType");
                String transactionType = aResultSet
                        .getString("TransactionType");
                String categoryFound = aResultSet.getString("Category");
                Date creationDate = aResultSet.getDate("CreationDate");
                String transactionDateFound = aResultSet
                        .getString("TransactionDate");
                String transactionTimeFound = aResultSet
                        .getString("TransactionTime");
                returnTransaction = new AccountTransaction(amountFound,
                        currencyType, transactionType, categoryFound,
                        creationDate, transactionDateFound,
                        transactionTimeFound);
            }
        } catch (SQLException e) {
            System.out.println("Transaction not found");
            e.printStackTrace();
        }
        return returnTransaction;
    }

    /**
     * Retrieves all transactions associated with a particular user and account
     * 
     * @param username The username for which the transactions are being
     *            retrieved
     * @param accountName The accountName with which the transactions are
     *            associated
     * @return An ArrayList containing all the transactions in the database
     *         associated with the passed in username and accountName
     */
    public List<AccountTransaction> getAllTransactions(String username,
            String accountName) {
        ArrayList<AccountTransaction> transList = new ArrayList<>();
        createStatementForConnection();
        try {
            String selectQuery = "SELECT * FROM Transactions "
                    + "WHERE Username = '" + username + "' AND AccountName = '"
                    + accountName
                    + "' GROUP BY TransactionDate, TransactionTime";
            ResultSet aResultSet = statement.executeQuery(selectQuery);
            while (aResultSet.next()) {
                double amountFound = aResultSet.getDouble("Amount");
                String currencyType = aResultSet.getString("CurrencyType");
                String transactionType = aResultSet
                        .getString("TransactionType");
                String categoryFound = aResultSet.getString("Category");
                Date creationDate = aResultSet.getDate("CreationDate");
                String transactionDateFound = aResultSet
                        .getString("TransactionDate");
                String transactionTimeFound = aResultSet
                        .getString("TransactionTime");
                AccountTransaction foundTransaction = new AccountTransaction(
                        amountFound, currencyType, transactionType,
                        categoryFound, creationDate, transactionDateFound,
                        transactionTimeFound);
                transList.add(foundTransaction);
            }
        } catch (SQLException e) {
            System.out.println("Username not found");
            e.printStackTrace();
        }
        return transList;
    }

    /**
     * Removes a transaction from the database and updates the account's balance
     * to reflect that change
     * 
     * @param username The username with which the transaction is associated
     * @param accountName The name of the account to which the transaction was
     *            deposited or withdrawn
     * @param amount The amount of the transaction
     * @param category The category of the transaction
     * @param transactionDate The date the transaction occurred
     * @param transactionTime The time the transaction occurred
     * @throws SQLException Throws an exception if no such transaction exists
     */
    public void removeTransaction(String username, String accountName,
            double amount, String category, String transactionDate,
            String transactionTime) {
        AccountTransaction theTransaction = getTransaction(username,
                accountName, amount, category, transactionDate, transactionTime);
        double amountToModify = theTransaction.getAmount();
        String transactionType = theTransaction.getType();
        if (transactionType.equals("Deposit")) {
            // Make the amount negative to update the balance with a
            // negative amount since we are removing a deposit
            amountToModify = -amountToModify;
        }
        updateAccountBalance(username, accountName, amountToModify);
        if (transactionType.equals("Deposit")) {
            // Make the amount positive again if it is a deposit or the
            // transaction won't be recognized and won't be removed from the
            // database
            amountToModify = -amountToModify;
        }
        String deleteQuery = "DELETE FROM Transactions WHERE Username = '"
                + username + "' AND AccountName = '" + accountName
                + "' AND Amount " + "= '" + amountToModify
                + "' AND Category = '" + category + "' AND"
                + " TransactionDate = '" + transactionDate
                + "' AND TransactionTime" + " = '" + transactionTime + "'";
        try {
        	query = connect.prepareStatement(deleteQuery);
			query.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

}