package com.buckaroos.server;

import java.sql.Date;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Each transaction has an amount and type (withdraw/deposit)
 * 
 * @author Gene
 * 
 */
public class AccountTransaction implements IsSerializable {

    private double amount;
    private String type;
    private String currency;
    private String category;
    private String dateOfTransaction;
    private Date dateCreated;
    private String time;

    public AccountTransaction(double amount, String currency, String type,
            String category, Date creationDate, String dateOfTransaction,
            String time) {
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.category = category;
        this.dateCreated = creationDate;
        this.dateOfTransaction = dateOfTransaction;
        this.time = time;
    }
    
    @SuppressWarnings("unused")
	private AccountTransaction() {
    	
    }

    /**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * @param dateOfTransaction the dateOfTransaction to set
	 */
	public void setDateOfTransaction(String dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	@Override
    public String toString() {
        return type + " of  " + amount + " in " + currency + " for/from "
                + category + " on " + dateOfTransaction + " at " + time;
    }
    
    /**
     * Returns date of transaction set by user
     * @return
     */
    public String getDate() {
    	return dateOfTransaction;
    }
    
    /**
     * Returns type of transaction
     * @return
     */
    public String getType() {
    	return type;
    }
    
    /**
     * Returns amount of transaction
     * @return
     */
    public double getAmount() {
    	return amount;
    }
    
    public String getCurrency() {
    	return currency;
    }
    
    /**
     * Returns transaction's category
     * @return
     */
    public String getCategory() {
    	return category;
    }

    /**
     * Returns the creation date of transaction
     * @return
     */
    public Date getCreationDate() {
        return dateCreated;
    }
    
    public String getTime() {
    	return time;
    }

	public boolean isRolledBack() {
		// TODO copy new AccountTransaction
		return false;
	}
}
