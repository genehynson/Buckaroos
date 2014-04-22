package com.buckaroos.utility;

/**
 * This class provides information about the currency types supported by the
 * Buckaroos application
 * 
 * @author Jordan LeRoux
 * @version 1.1
 */
public class CurrencyInformationProvider implements
        CurrencyInformationInterface {

    @Override
    public String getFullCurrencyName(Enum<Money> currencyAbbrev) {
        String fullNameOfCurrency = "";
        if (currencyAbbrev == Money.valueOf("AUD")) {
            fullNameOfCurrency = "Australia Dollars";
        } else if (currencyAbbrev == Money.valueOf("BRL")) {
            fullNameOfCurrency = "Brazil Reais";
        } else if (currencyAbbrev == Money.valueOf("CAD")) {
            fullNameOfCurrency = "Canada Dollars";
        } else if (currencyAbbrev == Money.valueOf("CNY")) {
            fullNameOfCurrency = "China Yuan Renminbi";
        } else if (currencyAbbrev == Money.valueOf("EUR")) {
            fullNameOfCurrency = "Euros";
        } else if (currencyAbbrev == Money.valueOf("GBP")) {
            fullNameOfCurrency = "United Kingdom Pounds";
        } else if (currencyAbbrev == Money.valueOf("JPY")) {
            fullNameOfCurrency = "Japan Yen";
        } else if (currencyAbbrev == Money.valueOf("INR")) {
            fullNameOfCurrency = "India Rupees";
        } else if (currencyAbbrev == Money.valueOf("CHF")) {
            fullNameOfCurrency = "Switzerland Francs";
        } else if (currencyAbbrev == Money.valueOf("RUB")) {
            fullNameOfCurrency = "Russia Rubles";
        } else if (currencyAbbrev == Money.valueOf("MXN")) {
            fullNameOfCurrency = "Mexico Pesos";
        } else if (currencyAbbrev == Money.valueOf("AED")) {
            fullNameOfCurrency = "United Arab Emirates Dirhams";
        } else if (currencyAbbrev == Money.valueOf("BDT")) {
            fullNameOfCurrency = "Bangladeshi Taka";
        }
        return fullNameOfCurrency;
    }

    @Override
    public String getSymbolOfCurrency(Enum<Money> currencyAbbrev) {
        // Locale locale = Locale.UK;
        // Currency curr = Currency.getInstance(locale);
        String symbol = "";
        if (currencyAbbrev == Money.valueOf("AUD")) {
            symbol = "AU$";
        } else if (currencyAbbrev == Money.valueOf("BRL")) {
            symbol = "R$";
        } else if (currencyAbbrev == Money.valueOf("CAD")) {
            symbol = "C$";
        } else if (currencyAbbrev == Money.valueOf("CNY")) {
            symbol = "C" + "\u00A5";
        } else if (currencyAbbrev == Money.valueOf("EUR")) {
            symbol = "\u20ac";
        } else if (currencyAbbrev == Money.valueOf("GBP")) {
            // unicode symbol is "\u00A3"
            symbol = "£";
        } else if (currencyAbbrev == Money.valueOf("JPY")) {
            symbol = "J" + "\u00A5";
        } else if (currencyAbbrev == Money.valueOf("INR")) {
            symbol = "INR";
        } else if (currencyAbbrev == Money.valueOf("CHF")) {
            symbol = "CHF";
        } else if (currencyAbbrev == Money.valueOf("RUB")) {
            symbol = "py6";
        } else if (currencyAbbrev == Money.valueOf("MXN")) {
            symbol = "MEX$";
        } else if (currencyAbbrev == Money.valueOf("AED")) {
            symbol = "AED";
        } else if (currencyAbbrev == Money.valueOf("BDT")) {
            symbol = "\u09F3";
        }
        return symbol;
    }

    @Override
    public String getCurrencyCode(Enum<Money> currencyAbbrev) {
        String currencyCode = "";
        if (currencyAbbrev == Money.valueOf("AUD")) {
            currencyCode = "AUD";
        } else if (currencyAbbrev == Money.valueOf("BRL")) {
            currencyCode = "BRL";
        } else if (currencyAbbrev == Money.valueOf("CAD")) {
            currencyCode = "CAD";
        } else if (currencyAbbrev == Money.valueOf("CNY")) {
            currencyCode = "CNY";
        } else if (currencyAbbrev == Money.valueOf("EUR")) {
            currencyCode = "EUR";
        } else if (currencyAbbrev == Money.valueOf("GBP")) {
            currencyCode = "GBP";
        } else if (currencyAbbrev == Money.valueOf("JPY")) {
            currencyCode = "JPY";
        } else if (currencyAbbrev == Money.valueOf("INR")) {
            currencyCode = "INR";
        } else if (currencyAbbrev == Money.valueOf("CHF")) {
            currencyCode = "CHF";
        } else if (currencyAbbrev == Money.valueOf("RUB")) {
            currencyCode = "RUB";
        } else if (currencyAbbrev == Money.valueOf("MXN")) {
            currencyCode = "MXN";
        } else if (currencyAbbrev == Money.valueOf("AED")) {
            currencyCode = "AED";
        } else if (currencyAbbrev == Money.valueOf("BDT")) {
            currencyCode = "BDT";
        }
        return currencyCode;
    }
}
