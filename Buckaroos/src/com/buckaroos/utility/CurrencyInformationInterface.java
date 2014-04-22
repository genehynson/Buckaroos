package com.buckaroos.utility;

public interface CurrencyInformationInterface {

    /**
     * Returns the full name of currency when provided with the currency's enum
     * 
     * @param currencyAbbrev The abbreviation of the currency
     * @return The full name of the currency
     */
    public abstract String getFullCurrencyName(Enum<Money> currencyAbbrev);

    /**
     * Returns the symbol of the currency when provided with the currency's enum
     * 
     * @param currencyAbbrev The abbreviation of the currency
     * @return The symbol of the currency
     */
    public abstract String getSymbolOfCurrency(Enum<Money> currencyAbbrev);

    /**
     * Returns the currency code as a String
     * 
     * @param currencyAbbrev The abbreviation of the currency
     * @return The currency code of the currency
     */
    public abstract String getCurrencyCode(Enum<Money> currencyAbbrev);

}