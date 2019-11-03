package com.exchangeratesnbu.request;

import com.exchangeratesnbu.entity.Currency;
import com.exchangeratesnbu.serviсe.ServiceForConnection;
import com.exchangeratesnbu.serviсe.ServiceForDate;
import com.exchangeratesnbu.serviсe.ServiceForNumber;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class CurrencyRequest extends Component {
    private Currency currency = new Currency();
    private ServiceForDate serviceForDate = new ServiceForDate();
    private HttpURLConnection connection;
    private ServiceForConnection serviceForConnection = new ServiceForConnection();
    private ServiceForNumber serviceForNumber = new ServiceForNumber();


    public Currency getExchangeRateNBUByCurrencyCodeAndData(Currency currencyIn) {
        currency = currencyIn;

        String query = "https://bank.gov.ua/NBUStatService/v1/statdirectory/"
                + "exchange?valcode=" + currency.getCurrencyLiteralCode()
                + "&date=" + serviceForDate.convertingCurrencyExchangeDateForQuery(currency.getCurrencyExchangeDate())
                + "&json";

        try {
            connection = serviceForConnection.makeConnection(null, query, "GET");
            readResponse(connection);
        } catch (Throwable cause) {
            cause.printStackTrace();
            System.out.println("Проблема зі з'єднанням");
        } finally {
            serviceForConnection.disconnectConnection(connection);
        }
        return currency;
    }


    private void readResponse(HttpURLConnection connection) throws IOException {
        String line;

        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            // read input stream ("in")
            String linePlus = "";
            while (( line = in.readLine() ) != null) {
                linePlus = linePlus + line;
            }

            // transform Json to object
            parseFromJson(linePlus);

        } else {
            System.out.println("Інформація про курс валюти не знайдена.");
        }
    }


    private void parseFromJson(String json) {
        if (json.equals("[]")) {
            currency.setCurrencyNumeralCode(0);
            currency.setCurrencyName("__________");
            currency.setCurrencyRate(0.0);
        } else {
            currency.setCurrencyNumeralCode(Integer.parseInt(json.substring(json.indexOf("r030") + 6, json.indexOf("r030") + 9)));
            currency.setCurrencyName(json.substring(json.indexOf("txt") + 6, json.indexOf("\",\"rate\"")));
            currency.setCurrencyRate(Double.parseDouble(json.substring(json.indexOf("rate") + 6, json.indexOf(",\"cc\""))));
        }
    }


    public Currency makeEquivalent(Currency currency) {
        String totalInUAH = serviceForNumber.checkAndEditTotalInUAH(currency);
        currency.setCurrencyTotalInUAH(totalInUAH);
        if (currency.getCurrencyRate() > 0) {
            currency.setCurrencyEquivalentInCurrency(serviceForNumber.roundingNumber(
                    Double.valueOf(totalInUAH) / currency.getCurrencyRate(),
                    2));
        } else {
            currency.setCurrencyEquivalentInCurrency(0.0);
        }

        return currency;
    }
}