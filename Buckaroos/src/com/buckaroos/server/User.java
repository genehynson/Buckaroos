package com.buckaroos.server;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Class designed to store the user's credentials
 * 
 * @author Jordan
 * @version 1.0
 */
public class User implements IsSerializable {

    private String accountName;
    private String password;
    private String email;

    public User(String accountName, String password, String email) {
        this.accountName = accountName;
        this.password = password;
        this.email = email;
    }
    
    @SuppressWarnings("unused")
	private User() {
    	
    }

    /**
     * Return login account name
     * @return
     */
	public String getUsername() {
        return accountName;
    }

	/**
	 * Set login account name
	 * @param accountName
	 */
	public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

	/**
	 * Return password
	 * @return
	 */
	public String getPassword() {
        return password;
    }
	
	/**
	 * Set login password
	 * @param password
	 */
    public void setPassword(String password) {
        this.password = password;
    }

   /**
    * Returns user's email
    * @return
    */
	public String getEmail() {
        return email;
    }

	/**
	 * Sets user email
	 * @param email
	 */
	public void setEmail(String email) {
        this.email = email;
    }

	@Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (o instanceof User) {
            if (((User) o).getUsername().equalsIgnoreCase(
                    this.getUsername())
                    && ((User) o).getEmail() == this.getEmail()) {
                return true;
            }
        }
        return false;
    }

}