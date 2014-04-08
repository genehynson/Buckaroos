package com.buckaroos.utility;

/**
 * This class supports conversion from one type of currency to another.
 * Supported currencies are USD, AUD, BRL, CAD, CNY, EUR, GBP, JPY, INR, CHF,
 * RUB, MXN, AED, and BDT.
 * 
 * @author Jordan LeRoux
 * @version 1.0
 */
public class CurrencyConverter implements CurrencyConverterInterface {

    private static final double FROMUSDTOAUD = 1.1206;
    private static final double FROMUSDTOBRL = 2.3443;
    private static final double FROMUSDTOCAD = 1.1064;
    private static final double FROMUSDTOCNY = 6.1451;
    private static final double FROMUSDTOEUR = 0.7245;
    private static final double FROMUSDTOGBP = 0.5972;
    private static final double FROMUSDTOJPY = 101.8;
    private static final double FROMUSDTOINR = 61.7575;
    private static final double FROMUSDTOCHF = 0.8803;
    private static final double FROMUSDTORUB = 35.8641;
    private static final double FROMUSDTOMXN = 13.2492;
    private static final double FROMUSDTOAED = 3.673;
    private static final double FROMUSDTOBDT = 77.715;

    @Override
    public double convertCurrency(Enum<Money> fromCurrency,
            Enum<Money> toCurrency, double amount) {
        double newAmount = 0;
        if (fromCurrency != Money.valueOf("USD")
                && toCurrency != Money.valueOf("USD")) {
            double fromCurrencyInDollars = convertToDollars(fromCurrency,
                    amount);
            newAmount = convertCurrency(Money.USD, toCurrency,
                    fromCurrencyInDollars);
        } else if (fromCurrency != Money.valueOf("USD")
                && toCurrency == Money.valueOf("USD")) {
            newAmount = convertToDollars(fromCurrency, amount);
        } else if (fromCurrency == Money.valueOf("USD")
                && toCurrency != Money.valueOf("USD")) {
            newAmount = convertFromDollars(toCurrency, amount);
        } else if (fromCurrency == Money.valueOf("USD")
                && toCurrency == Money.valueOf("USD")) {
            newAmount = amount;
        }
        return newAmount;
    }

    /*
     * Converts the amount of money provided using the exchange rate of the
     * currency taken in to US Dollars.
     * 
     * @param fromCurrency The currency to convert from
     * @param amount The amount of money to convert
     * @return The value of the amount provided in US Dollars
     */
    private double convertToDollars(Enum<Money> fromCurrency, double amount) {
        double valueInDollars = -1;
        double conversionRate = 0;
        if (fromCurrency == Money.valueOf("AUD")) {
            conversionRate = 1 / FROMUSDTOAUD;
            valueInDollars = amount * conversionRate;
        } else if (fromCurrency == Money.valueOf("BRL")) {
            conversionRate = 1 / FROMUSDTOBRL;
            valueInDollars = amount * conversionRate;
        } else if (fromCurrency == Money.valueOf("CAD")) {
            conversionRate = 1 / FROMUSDTOCAD;
            valueInDollars = amount * conversionRate;
        } else if (fromCurrency == Money.valueOf("CNY")) {
            conversionRate = 1 / FROMUSDTOCNY;
            valueInDollars = amount * conversionRate;
        } else if (fromCurrency == Money.valueOf("EUR")) {
            conversionRate = 1 / FROMUSDTOEUR;
            valueInDollars = amount * conversionRate;
        } else if (fromCurrency == Money.valueOf("GBP")) {
            conversionRate = 1 / FROMUSDTOGBP;
            valueInDollars = amount * conversionRate;
        } else if (fromCurrency == Money.valueOf("JPY")) {
            conversionRate = 1 / FROMUSDTOJPY;
            valueInDollars = amount * conversionRate;
        } else if (fromCurrency == Money.valueOf("INR")) {
            conversionRate = 1 / FROMUSDTOINR;
            valueInDollars = amount * conversionRate;
        } else if (fromCurrency == Money.valueOf("CHF")) {
            conversionRate = 1 / FROMUSDTOCHF;
            valueInDollars = amount * conversionRate;
        } else if (fromCurrency == Money.valueOf("RUB")) {
            conversionRate = 1 / FROMUSDTORUB;
            valueInDollars = amount * conversionRate;
        } else if (fromCurrency == Money.valueOf("MXN")) {
            conversionRate = 1 / FROMUSDTOMXN;
            valueInDollars = amount * conversionRate;
        } else if (fromCurrency == Money.valueOf("AED")) {
            conversionRate = 1 / FROMUSDTOAED;
            valueInDollars = amount * conversionRate;
        } else if (fromCurrency == Money.valueOf("BDT")) {
        	conversionRate = 1 / FROMUSDTOBDT;
        	valueInDollars = amount * conversionRate;
        }
        return valueInDollars;
    }

    /*
     * Converts the amount of money provided using the exchange rate of the
     * currency taken in to the type of currency provided.
     * 
     * @param toCurrency The currency to convert to
     * @param amount The amount of money to convert
     * @return The value of the amount provided in the type of currency provided
     */
    private double convertFromDollars(Enum<Money> toCurrency, double amount) {
        double newValue = -1;
        double conversionRate = 0;
        if (toCurrency == Money.valueOf("AUD")) {
            conversionRate = FROMUSDTOAUD;
            newValue = amount * conversionRate;
        } else if (toCurrency == Money.valueOf("BRL")) {
            conversionRate = FROMUSDTOBRL;
            newValue = amount * conversionRate;
        } else if (toCurrency == Money.valueOf("CAD")) {
            conversionRate = FROMUSDTOCAD;
            newValue = amount * conversionRate;
        } else if (toCurrency == Money.valueOf("CNY")) {
            conversionRate = FROMUSDTOCNY;
            newValue = amount * conversionRate;
        } else if (toCurrency == Money.valueOf("EUR")) {
            conversionRate = FROMUSDTOEUR;
            newValue = amount * conversionRate;
        } else if (toCurrency == Money.valueOf("GBP")) {
            conversionRate = FROMUSDTOGBP;
            newValue = amount * conversionRate;
        } else if (toCurrency == Money.valueOf("JPY")) {
            conversionRate = FROMUSDTOJPY;
            newValue = amount * conversionRate;
        } else if (toCurrency == Money.valueOf("INR")) {
            conversionRate = FROMUSDTOINR;
            newValue = amount * conversionRate;
        } else if (toCurrency == Money.valueOf("CHF")) {
            conversionRate = FROMUSDTOCHF;
            newValue = amount * conversionRate;
        } else if (toCurrency == Money.valueOf("RUB")) {
            conversionRate = FROMUSDTORUB;
            newValue = amount * conversionRate;
        } else if (toCurrency == Money.valueOf("MXN")) {
            conversionRate = FROMUSDTOMXN;
            newValue = amount * conversionRate;
        } else if (toCurrency == Money.valueOf("AED")) {
            conversionRate = FROMUSDTOAED;
            newValue = amount * conversionRate;
        } else if (toCurrency == Money.valueOf("BDT")) {
            conversionRate = FROMUSDTOBDT;
            newValue = amount * conversionRate;
        }
        return newValue;
    }

}
