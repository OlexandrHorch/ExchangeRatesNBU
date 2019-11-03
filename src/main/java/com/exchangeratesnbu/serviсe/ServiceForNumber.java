package com.exchangeratesnbu.serviсe;

import com.exchangeratesnbu.entity.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Objects;

public class ServiceForNumber {

    // Method for rounding number
    public Double roundingNumber(Double number, int numberDecimalPlaces) {
        return new BigDecimal(number).setScale(numberDecimalPlaces, RoundingMode.HALF_UP).doubleValue();
    }


    // Method for checking and edition total in UAH
    public String checkAndEditTotalInUAH(Currency currency) {
        String input = currency.getCurrencyTotalInUAH();

        char[] inputInCharArray = input.toCharArray();

        // Recording in ArrayList numeric, coma and dot
        ArrayList<Character> numericInCharArray = new ArrayList<>();
        int coma = 0;
        for (int i = 0; i < inputInCharArray.length; i++) {
            if (Objects.equals(inputInCharArray[i], '0') ||
                    Objects.equals(inputInCharArray[i], '1') ||
                    Objects.equals(inputInCharArray[i], '2') ||
                    Objects.equals(inputInCharArray[i], '3') ||
                    Objects.equals(inputInCharArray[i], '4') ||
                    Objects.equals(inputInCharArray[i], '5') ||
                    Objects.equals(inputInCharArray[i], '6') ||
                    Objects.equals(inputInCharArray[i], '7') ||
                    Objects.equals(inputInCharArray[i], '8') ||
                    Objects.equals(inputInCharArray[i], '9')) {
                numericInCharArray.add(inputInCharArray[i]);
            } else if (Objects.equals(inputInCharArray[i], '.')) {
                numericInCharArray.add(inputInCharArray[i]);
                coma++;
            } else if (Objects.equals(inputInCharArray[i], ',')) {
                numericInCharArray.add('.');
                coma++;
            }
        }

        // formation of output data
        String output = "";
        for (int i = 0; i < numericInCharArray.size(); i++) {
            output += numericInCharArray.get(i);
        }
        if (coma > 1) {
            output = "0";
        }

        return output;
    }
}