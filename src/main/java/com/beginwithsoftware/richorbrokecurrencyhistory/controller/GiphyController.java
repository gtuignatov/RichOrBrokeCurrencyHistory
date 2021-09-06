package com.beginwithsoftware.richorbrokecurrencyhistory.controller;

import com.beginwithsoftware.richorbrokecurrencyhistory.client.GiphyClient;
import com.beginwithsoftware.richorbrokecurrencyhistory.giphydto.GiphyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/")
public class GiphyController {

    private final GiphyClient giphyClient;

    @Value("${giphy.app-id}")
    private String giphyAppId;

    @Value("${giphy.search-limit}")
    private Integer giphySearchLimit;

    @Value("${giphy.dto-json-property-value}")
    private String giphyDtoJsonPropertyValue;

    public String getGiphyDtoJsonPropertyValue() {
        return giphyDtoJsonPropertyValue;
    }

    private String searchQ = "rich";

    public String getSearchQ() {
        return searchQ;
    }

    public void setSearchQ(String searchQ) {
        this.searchQ = searchQ;
    }

    @Autowired
    public GiphyController(GiphyClient giphyClient) {
        this.giphyClient = giphyClient;
    }

    public String getGiphyAppId() {
        return giphyAppId;
    }

    public Integer getGiphySearchLimit() {
        return giphySearchLimit;
    }

    @GetMapping("/search")
    public GiphyResponse getGiphy(@RequestParam String api_key, @RequestParam String q,
                                  @RequestParam Integer limit) {
        return giphyClient.getGiphyResponse(api_key, q, limit);
    }


}
