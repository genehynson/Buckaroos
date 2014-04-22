package com.buckaroos.utility;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * This class supports conversion from one type of currency to another.
 * Supported currencies are USD, AUD, BRL, CAD, CNY, EUR, GBP, JPY, INR, CHF,
 * RUB, MXN, AED, and BDT.
 * 
 * @author Jordan LeRoux
 * @version 1.1
 */
@SuppressWarnings("deprecation")
public class CurrencyConverter implements CurrencyConverterInterface {

    private static double FROMUSDTOAUD = 1.1206;
    private static double FROMUSDTOBRL = 2.3443;
    private static double FROMUSDTOCAD = 1.1064;
    private static double FROMUSDTOCNY = 6.1451;
    private static double FROMUSDTOEUR = 0.7245;
    private static double FROMUSDTOGBP = 0.5972;
    private static double FROMUSDTOJPY = 101.8;
    private static double FROMUSDTOINR = 61.7575;
    private static double FROMUSDTOCHF = 0.8803;
    private static double FROMUSDTORUB = 35.8641;
    private static double FROMUSDTOMXN = 13.2492;
    private static double FROMUSDTOAED = 3.673;
    private static double FROMUSDTOBDT = 77.715;

    /**
     * Fetch the current exchange rates whenever constructed
     */
    public CurrencyConverter() {
        // Consider adding a table to the database and then storing a date in
        // the database and compare the date in the database with the current
        // date and if they don't match to the hour, then set the exchange
        // rates.
        // This would prevent us having to update the exchange rates every time
        // a conversion is called because this can be quite slow when first
        // constructed.
        setExchangeRates();
    }

    @Override
    public double convertCurrency(Enum<Money> fromCurrency,
            Enum<Money> toCurrency, double amount) {
        if (fromCurrency == null || toCurrency == null) {
            throw new IllegalArgumentException("Can't convert from null or to "
                    + "null currency.");
        }
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
        } else if (fromCurrency == toCurrency) {
            newAmount = amount;
        }
        return newAmount;
    }

    /*
     * Gets the current exchange rate for the currencies passed in.
     * 
     * @param fromCurrency The currency to convert from.
     * 
     * @param toCurrency The currency to convert to.
     * 
     * @return The exchange rate for the currencies.
     * 
     * @throws ClientProtocolException
     * 
     * @throws IOException
     */
    private static float getExchangeRates(Enum<Money> fromCurrency,
            Enum<Money> toCurrency) throws ClientProtocolException, IOException {
        @SuppressWarnings("resource")
        HttpClient client = new DefaultHttpClient();

        HttpGet a = new HttpGet("http://quote.yahoo.com/d/quotes.csv?s="
                + fromCurrency + toCurrency + "=X&f=l1&e=.csv");
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        String responseBody = client.execute(a, responseHandler);

        client.getConnectionManager().shutdown();
        return Float.parseFloat(responseBody);
    }

    /*
     * Sets the current exchange rates for every currency that Buckaroos
     * supports.
     */
    private static void setExchangeRates() {
        try {
            FROMUSDTOAUD = getExchangeRates(Money.USD, Money.AUD);
            FROMUSDTOBRL = getExchangeRates(Money.USD, Money.BRL);
            FROMUSDTOCAD = getExchangeRates(Money.USD, Money.CAD);
            FROMUSDTOCNY = getExchangeRates(Money.USD, Money.CNY);
            FROMUSDTOEUR = getExchangeRates(Money.USD, Money.EUR);
            FROMUSDTOGBP = getExchangeRates(Money.USD, Money.GBP);
            FROMUSDTOJPY = getExchangeRates(Money.USD, Money.JPY);
            FROMUSDTOINR = getExchangeRates(Money.USD, Money.INR);
            FROMUSDTOCHF = getExchangeRates(Money.USD, Money.CHF);
            FROMUSDTORUB = getExchangeRates(Money.USD, Money.RUB);
            FROMUSDTOMXN = getExchangeRates(Money.USD, Money.MXN);
            FROMUSDTOAED = getExchangeRates(Money.USD, Money.AED);
            FROMUSDTOBDT = getExchangeRates(Money.USD, Money.BDT);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * Converts the amount of money provided using the exchange rate of the
     * currency taken in to US Dollars.
     * 
     * @param fromCurrency The currency to convert from
     * 
     * @param amount The amount of money to convert
     * 
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
     * 
     * @param amount The amount of money to convert
     * 
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
