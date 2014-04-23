package com.buckaroos.server;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * The "bank" accounts for user.
 * 
 * @author Gene
 */
public class Account implements IsSerializable  {

    private String name;
    private double balance = 0;
    private double interestRate;
    private String username;
    private String nickName;

    public Account(String username, String name, String nickName,
            double amount, double interestRate) {
        this.username = username;
        this.setName(name);
        this.balance = amount;
        this.nickName = nickName;
        this.setInterestRate(interestRate);
    }
    
    @SuppressWarnings("unused")
	private Account() {
    	
    }

    /**
     * Returns balance of this account
     * 
     * @return
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets account balance
     * 
     * @param balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * get name of account
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * set name of account
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (o instanceof Account) {
            if (((Account) o).getName().equalsIgnoreCase(this.getName())
                    && ((Account) o).getUsername().equalsIgnoreCase(this.getUsername())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Username: " + username + "\nAccountName: " + name
                + "\nNickName: " + nickName + "\nBalance: " + balance
                + "\nInterest Rate: " + interestRate;
    }

    /**
     * Returns interest rate
     * 
     * @return
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Returns nickname
     * 
     * @return
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Sets nickname
     * 
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Set interest rate
     * 
     * @param interestRate
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Returns this account's username
     * 
     * @return
     */
    public String getUsername() {
        return username;
    }

}
