package com.beginwithsoftware.richorbrokecurrencyhistory.controller;

import com.beginwithsoftware.richorbrokecurrencyhistory.client.GiphyClient;
import com.beginwithsoftware.richorbrokecurrencyhistory.giphydto.GiphyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GiphyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GiphyClient giphyClient;

    @Value("${giphy.app-id}")
    private String giphyAppId;

    @Value("${giphy.search-limit}")
    private Integer giphySearchLimit;

    @Test
    void getGiphy_Rich_Json_HTTP_Request_OK() throws Exception {
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("api_key", giphyAppId);
        paramsMap.add("q", "rich");
        paramsMap.add("limit", Integer.toString(giphySearchLimit));
        mockMvc.perform(MockMvcRequestBuilders.get("/search").contentType("application/json")
                .params(paramsMap))
                .andExpect(status().isOk());
    }

    @Test
    void getGiphy_Broke_Json_HTTP_Request_OK() throws Exception {
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("api_key", giphyAppId);
        paramsMap.add("q", "broke");
        paramsMap.add("limit", Integer.toString(giphySearchLimit));
        mockMvc.perform(MockMvcRequestBuilders.get("/search").contentType("application/json")
                .params(paramsMap))
                .andExpect(status().isOk());
    }
}