package com.exchangeratesnbu.serviсe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ServiceForDate {
    private GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    // Method for checking exchange date.
    public String checkingExchangeDate(String dateForComparison) {
        String currentDate = generateCurrentDate();
        long dateForComparisonLong = 0;
        long currentDateLong = 0;
        String resultDate;

        try {
            dateForComparisonLong = simpleDateFormat.parse(dateForComparison).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            currentDateLong = simpleDateFormat.parse(currentDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (dateForComparisonLong > currentDateLong) {
            resultDate = currentDate;
        } else {
            resultDate = dateForComparison;
        }

        return resultDate;
    }


    // Method for converting currency exchange date for query.
    public String convertingCurrencyExchangeDateForQuery(String currencyExchangeDateRequest) {
        String currencyExchangeDateForQuery = "";
        String[] dateParts = currencyExchangeDateRequest.split("-");

        for (String datePart : dateParts) {
            currencyExchangeDateForQuery = String.join("", currencyExchangeDateForQuery, datePart);
        }
        return currencyExchangeDateForQuery;
    }


    // Method for generation current date.
    public String generateCurrentDate() {
        String currentDate = simpleDateFormat.format(calendar.getTime());
        return currentDate;
    }


    // Method to transform date format "yyyy-MM-dd" to "yyyy.MM.dd"
    String transformDateFormat(String dateInFormatWithDash) {
        String dateInFormatWithDot;
        dateInFormatWithDot = dateInFormatWithDash.replace("-", ".");
        return dateInFormatWithDot;
    }
}