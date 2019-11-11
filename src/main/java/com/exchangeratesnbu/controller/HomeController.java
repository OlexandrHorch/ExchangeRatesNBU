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

@Controller
public class HomeController {
    private CurrencyRequest currencyRequest = new CurrencyRequest();
    private ServiceForDate serviceForDate = new ServiceForDate();
    private Currency currency = new Currency();

    @GetMapping("/")
    public ModelAndView home(@RequestParam(required = false) CurrencyLiteralCode currencyLiteralCodeRequest,
                             @RequestParam(required = false) String currencyExchangeDateRequest,
                             @RequestParam(required = false) String currencyTotalInUAH,
                             @RequestParam(required = false) Boolean saveToTable) {
        ModelAndView result = new ModelAndView("home");

        // filling the object with input values.
        currency.setCurrentDate(serviceForDate.generateCurrentDate());

        if (currencyLiteralCodeRequest != null) {
            currency.setCurrencyLiteralCode(currencyLiteralCodeRequest);
        }
        if (currencyExchangeDateRequest != null) {
            currency.setCurrencyExchangeDate(currencyExchangeDateRequest);
        }
        if (currencyTotalInUAH != null) {
            currency.setCurrencyTotalInUAH(currencyTotalInUAH);
        }
        if (saveToTable != null) {
            currency.setSaveToTable(saveToTable);
        }

        result.addObject("currency", checkingInputData(currency, "ukr"));

        return result;
    }


    @GetMapping("/eng")
    public ModelAndView homeEng(@RequestParam(required = false) CurrencyLiteralCode currencyLiteralCodeRequest,
                                @RequestParam(required = false) String currencyExchangeDateRequest,
                                @RequestParam(required = false) String currencyTotalInUAH,
                                @RequestParam(required = false) Boolean saveToTable) {
        ModelAndView result = new ModelAndView("homeEng");

        // Filling the object with input values.
        currency.setCurrentDate(serviceForDate.generateCurrentDate());

        if (currencyLiteralCodeRequest != null) {
            currency.setCurrencyLiteralCode(currencyLiteralCodeRequest);
        }
        if (currencyExchangeDateRequest != null) {
            currency.setCurrencyExchangeDate(currencyExchangeDateRequest);
        }
        if (currencyTotalInUAH != null) {
            currency.setCurrencyTotalInUAH(currencyTotalInUAH);
        }
        if (saveToTable != null) {
            currency.setSaveToTable(saveToTable);
        }

        result.addObject("currency", checkingInputData(currency, "eng"));

        return result;
    }


    // Method for checking input data.
    private Currency checkingInputData(Currency currency, String language) {
        ServiceForExportToExcel serviceForExportToExcel = new ServiceForExportToExcel();

        if (currency.getCurrencyLiteralCode() != null && currency.getCurrencyExchangeDate() != null) {
            currency.setCurrencyExchangeDate(serviceForDate.checkingExchangeDate(currency.getCurrencyExchangeDate()));
            currency = currencyRequest.getExchangeRateNBUByCurrencyCodeAndData(currency);
        }

        if (currency.getCurrencyRate() != null && !currency.getCurrencyTotalInUAH().equals("")) {
            currency = currencyRequest.makeEquivalent(currency);
            if (currency.getSaveToTable()) {
                try {
                    serviceForExportToExcel.createExcelFile(currency, language);
                } catch (IOException e) {
                }
            }

        } else {
            currency.setCurrencyTotalInUAH("0");
            currency.setCurrencyEquivalentInCurrency(0.0);
        }
        return currency;
    }
}