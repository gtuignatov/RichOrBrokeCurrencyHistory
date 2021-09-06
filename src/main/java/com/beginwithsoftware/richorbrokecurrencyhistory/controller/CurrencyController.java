package com.beginwithsoftware.richorbrokecurrencyhistory.controller;

import com.beginwithsoftware.richorbrokecurrencyhistory.client.CurrencyClient;
import com.beginwithsoftware.richorbrokecurrencyhistory.exchangedto.ExchangeResponse;
import com.beginwithsoftware.richorbrokecurrencyhistory.helperdto.DtoMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@EnableAutoConfiguration
@RequestMapping("/")
public class CurrencyController {

    private final CurrencyClient currencyClient;
    private final DtoMethods dtoMethods;

    @Value("${openexchangerates.app-id}")
    private String currencyAppID;

    @Value("${openexchangerates.currency-foreign}")
    private String currencyForeign;

    @Value("${openexchangerates.currency-base}")
    private String currencyBase;

    public String getCurrencyForeign() {
        return currencyForeign;
    }

    public String getCurrencyBase() {
        return currencyBase;
    }

    @Autowired
    public CurrencyController(CurrencyClient currencyClient, DtoMethods dtoMethods) {
        this.currencyClient = currencyClient;
        this.dtoMethods = dtoMethods;
    }

    @GetMapping(value = "/latest.json")
    public ExchangeResponse getLatest(@RequestParam String app_id) {
        return currencyClient.getLatest(currencyAppID);
    };

    @GetMapping(value = "/historical/{yesterdayJson}")
    public ExchangeResponse getYesterday(@PathVariable String yesterdayJson, @RequestParam String app_id) {
        return currencyClient.getYesterday(getYesterdayJsonName(), currencyAppID);
    };

    public String getYesterdayJsonName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.now().minusDays(1).format(formatter) + ".json";
    }

    public String getCurrencyAppId() {
        return currencyAppID;
    }

    public boolean compareCurrencyRatio(ExchangeResponse latestResponse, ExchangeResponse yesterdayResponse)
            throws InvocationTargetException, IllegalAccessException {
        boolean currencyRatio = false;
        Double latestForeignCourse = dtoMethods.getCurrencyCourse(latestResponse, currencyForeign);
        Double yesterdayForeignCourse = dtoMethods.getCurrencyCourse(yesterdayResponse, currencyForeign);
        Double latestBaseCourse = dtoMethods.getCurrencyCourse(latestResponse, currencyBase);
        Double yesterdayBaseCourse = dtoMethods.getCurrencyCourse(yesterdayResponse, currencyBase);
        double foreignRatio = latestForeignCourse / yesterdayForeignCourse;
        double baseRatio = latestBaseCourse / yesterdayBaseCourse;
        if (foreignRatio > baseRatio) {
            currencyRatio = true;
        }
        return currencyRatio;
    }

}
