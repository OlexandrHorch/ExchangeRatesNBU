package com.exchangeratesnbu.controller;

import com.exchangeratesnbu.entity.Currency;
import com.exchangeratesnbu.entity.CurrencyLiteralCode;
import com.exchangeratesnbu.request.CurrencyRequest;
import com.exchangeratesnbu.serviсe.ServiceForDate;
import com.exchangeratesnbu.serviсe.ServiceForExportToExcel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class HomeController {
    private Currency currency;
    private CurrencyRequest currencyRequest = new CurrencyRequest();
    private ServiceForDate serviceForDate = new ServiceForDate();

    @GetMapping("/")
    public ModelAndView home(@RequestParam(required = false) ArrayList<CurrencyLiteralCode> currencyLiteralCodeRequest,
                             @RequestParam(required = false) String currencyExchangeDateRequest,
                             @RequestParam(required = false) String currencyTotalInUAH,
                             @RequestParam(required = false) Boolean saveToTable) {
        ModelAndView result = new ModelAndView("home");
        ArrayList<Currency> currencies = new ArrayList<>();

        // Filling the object with input values.
        try {
            for (int i = 0; i < currencyLiteralCodeRequest.size(); i++) {
                currency = new Currency();
                currency.setCurrentDate(serviceForDate.generateCurrentDate());
                currency.setCurrencyLiteralCode(currencyLiteralCodeRequest.get(i));

                if (currencyExchangeDateRequest != null) {
                    currency.setCurrencyExchangeDate(currencyExchangeDateRequest);
                }
                if (currencyTotalInUAH != null) {
                    currency.setCurrencyTotalInUAH(currencyTotalInUAH);
                }
                if (saveToTable != null) {
                    currency.setSaveToTable(saveToTable);
                }
                currencies.add(currency);
            }

        } catch (NullPointerException e) {
            System.out.println("Exception in HomeController \"/\"");
        }

        currencies = gettingInformationAboutCurrencies(currencies);
        exportToExcel(currencies, "ukr");
        result.addObject("currencies", currencies);

        return result;
    }


    @GetMapping("/eng")
    public ModelAndView homeEng(@RequestParam(required = false) ArrayList<CurrencyLiteralCode> currencyLiteralCodeRequest,
                                @RequestParam(required = false) String currencyExchangeDateRequest,
                                @RequestParam(required = false) String currencyTotalInUAH,
                                @RequestParam(required = false) Boolean saveToTable) {
        ModelAndView result = new ModelAndView("homeEng");
        ArrayList<Currency> currencies = new ArrayList<>();

        // Filling the object with input values.
        try {
            for (int i = 0; i < currencyLiteralCodeRequest.size(); i++) {
                currency = new Currency();
                currency.setCurrentDate(serviceForDate.generateCurrentDate());
                currency.setCurrencyLiteralCode(currencyLiteralCodeRequest.get(i));

                if (currencyExchangeDateRequest != null) {
                    currency.setCurrencyExchangeDate(currencyExchangeDateRequest);
                }
                if (currencyTotalInUAH != null) {
                    currency.setCurrencyTotalInUAH(currencyTotalInUAH);
                }
                if (saveToTable != null) {
                    currency.setSaveToTable(saveToTable);
                }
                currencies.add(currency);
            }

        } catch (NullPointerException e) {
            System.out.println("Exception in HomeController \"/eng\"");
        }

        currencies = gettingInformationAboutCurrencies(currencies);
        exportToExcel(currencies, "eng");
        result.addObject("currencies", currencies);

        return result;
    }


    // Method for getting information about currencies.
    private ArrayList<Currency> gettingInformationAboutCurrencies(ArrayList<Currency> currencies) {
        for (int i = 0; i < currencies.size(); i++) {
            Currency currency = currencies.get(i);

            // Get exchange rate from NBU.
            if (currency.getCurrencyLiteralCode() != null && currency.getCurrencyExchangeDate() != null) {
                currency.setCurrencyExchangeDate(serviceForDate.checkingExchangeDate(currency.getCurrencyExchangeDate()));
                currency = currencyRequest.getExchangeRateNBUByCurrencyCodeAndData(currency);
            }

            // Method for make total in UAH equivalent in currency.
            if (currency.getCurrencyRate() != null && !currency.getCurrencyTotalInUAH().equals("")) {
                currency = currencyRequest.makeEquivalent(currency);
            } else {
                currency.setCurrencyTotalInUAH("0");
                currency.setCurrencyEquivalentInCurrency(0.0);
            }

            currencies.set(i, currency);
        }

        return currencies;
    }


    // Method for export to Excel.
    private void exportToExcel(ArrayList<Currency> currencies, String language) {
        ServiceForExportToExcel serviceForExportToExcel = new ServiceForExportToExcel();

        if (currencies.size() > 0) {
            try {
                if (currencies.get(0).getSaveToTable()) {
                    serviceForExportToExcel.createExcelFile(currencies, language);
                }
            } catch (NullPointerException | IOException e) {
                System.out.println("Exception in \"exportToExcel\"");
            }
        }
    }
}