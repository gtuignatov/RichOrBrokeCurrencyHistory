package com.beginwithsoftware.richorbrokecurrencyhistory.controller;

import com.beginwithsoftware.richorbrokecurrencyhistory.client.CurrencyClient;
import com.beginwithsoftware.richorbrokecurrencyhistory.exchangedto.ExchangeResponse;
import com.beginwithsoftware.richorbrokecurrencyhistory.helperdto.DtoMethods;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyClient currencyClient;

    @MockBean
    private DtoMethods dtoMethods;

    @MockBean
    private ExchangeResponse exchangeResponse;

    @Value("${openexchangerates.app-id}")
    private String currencyAppID;

    @Value("${openexchangerates.currency-foreign}")
    private String currencyForeign;

    @Value("${openexchangerates.currency-base}")
    private String currencyBase;

    @Test
    void getLatest_Json_HTTP_Request_Status_OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/latest.json").contentType("application/json")
                .param("app_id", currencyAppID))
                .andExpect(status().isOk());
    }

    @Test
    void getYesterday_Json_HTTP_Request_Status_OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/historical/2021-09-03.json")
                .contentType("application/json")
                .param("app_id", currencyAppID))
                .andExpect(status().isOk());
    }

    @Test
    void getYesterdayJsonName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String yesterdayDateJsonName = LocalDate.now().minusDays(1).format(formatter) + ".json";
        Assertions.assertEquals("2021-09-03.json", yesterdayDateJsonName);
    }

    @Test
    void compareCurrencyRatio_Mock_Check_Double_Values_Return()
            throws InvocationTargetException, IllegalAccessException {
        Assertions.assertEquals(0.0,
                dtoMethods.getCurrencyCourse(exchangeResponse, currencyForeign).doubleValue());
        Assertions.assertEquals(0.0,
                dtoMethods.getCurrencyCourse(exchangeResponse, currencyBase).doubleValue());
    }
}