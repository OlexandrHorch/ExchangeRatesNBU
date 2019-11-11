package com.exchangeratesnbu.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "currencyNumeralCode")
    private int currencyNumeralCode;

    @Column(name = "currencyName")
    private String currencyName;

    @Column(name = "currencyRate")
    private Double currencyRate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "currencyLiteralCode")
    private CurrencyLiteralCode currencyLiteralCode;

    @Column(name = "currencyExchangeDate")
    private String currencyExchangeDate;

    @Column(name = "currencyTotalInUAH")
    private String currencyTotalInUAH;

    @Column(name = "currencyEquivalentInCurrency")
    private Double currencyEquivalentInCurrency;

    @Column(name = "saveToTable")
    private Boolean saveToTable;

    @Column(name = "currentDate")
    private String currentDate;
}