package com.beginwithsoftware.richorbrokecurrencyhistory.client;

import com.beginwithsoftware.richorbrokecurrencyhistory.exchangedto.ExchangeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "currency-history-client", url = "${openexchangerates.api-url}")
public interface CurrencyClient {

    @GetMapping(value = "/latest.json")
    public ExchangeResponse getLatest(@RequestParam String app_id);

    @GetMapping(value = "/historical/{yesterdayJson}")
    public ExchangeResponse getYesterday(@PathVariable String yesterdayJson, @RequestParam String app_id);

}
