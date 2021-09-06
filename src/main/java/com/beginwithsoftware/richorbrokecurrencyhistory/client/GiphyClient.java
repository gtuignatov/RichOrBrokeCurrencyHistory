package com.beginwithsoftware.richorbrokecurrencyhistory.client;

import com.beginwithsoftware.richorbrokecurrencyhistory.giphydto.GiphyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "giphy-client", url = "${giphy.api-url}")
public interface GiphyClient {

    @GetMapping(value = "/search")
    public GiphyResponse getGiphyResponse(@RequestParam String api_key,
                                          @RequestParam String q,
                                          @RequestParam Integer limit);

}
