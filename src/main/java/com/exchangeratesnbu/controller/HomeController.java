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

    @GetMapping("/")
    public ModelAndView index(@RequestParam(required = false) CurrencyLiteralCode currencyLiteralCodeRequest,
                              @RequestParam(required = false) String currencyExchangeDateRequest,
                              @RequestParam(required = false) String currencyTotalInUAH) {
        ModelAndView result = new ModelAndView("home");
        Currency currency = new Currency();

        // filling the object with input values
        if (currencyLiteralCodeRequest != null) {
            currency.setCurrencyLiteralCode(currencyLiteralCodeRequest);
        }
        if (currencyExchangeDateRequest != null) {
            currency.setCurrencyExchangeDate(currencyExchangeDateRequest);
        }
        if (currencyTotalInUAH != null) {
            currency.setCurrencyTotalInUAH(currencyTotalInUAH);
        }

        result.addObject("currency", checkingInputData(currency));

        return result;
    }


    // Method for checking input data
    private Currency checkingInputData(Currency currency) {
        ServiceForExportToExcel serviceForExportToExcel = new ServiceForExportToExcel();
        if (currency.getCurrencyLiteralCode() != null && currency.getCurrencyExchangeDate() != null) {
            currency.setCurrencyExchangeDate(serviceForDate.checkingExchangeDate(currency.getCurrencyExchangeDate()));
            currency = currencyRequest.getExchangeRateNBUByCurrencyCodeAndData(currency);
        }

        if (currency.getCurrencyRate() != null && !currency.getCurrencyTotalInUAH().equals("")) {
            currency = currencyRequest.makeEquivalent(currency);

            try {
                serviceForExportToExcel.createExcelFile(currency);
            } catch (IOException e) {
                System.out.println("Excel File не створено!");
            }

        } else {
            currency.setCurrencyTotalInUAH("0");
            currency.setCurrencyEquivalentInCurrency(0.0);
        }
        return currency;
    }
}