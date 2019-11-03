package com.exchangeratesnbu.serviсe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ServiceForDate {
    // Method for checking exchange date
    public String checkingExchangeDate(String dateForComparison) {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = simpleDateFormat.format(calendar.getTime());

        long dateForComparisonLong = 0;
        try {
            dateForComparisonLong = simpleDateFormat.parse(dateForComparison).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long currentDateLong = 0;
        try {
            currentDateLong = simpleDateFormat.parse(currentDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (dateForComparisonLong > currentDateLong) {
            dateForComparison = currentDate;
        }

        return dateForComparison;
    }


    // Method for converting currency exchange date for query
    public String convertingCurrencyExchangeDateForQuery(String currencyExchangeDateRequest) {
        String currencyExchangeDateForQuery = "";
        String[] dateParts = currencyExchangeDateRequest.split("-");

        for (String datePart : dateParts) {
            currencyExchangeDateForQuery = String.join("", currencyExchangeDateForQuery, datePart);
        }
        return currencyExchangeDateForQuery;
    }
}