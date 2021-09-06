package com.beginwithsoftware.richorbrokecurrencyhistory;

import com.beginwithsoftware.richorbrokecurrencyhistory.controller.CurrencyController;
import com.beginwithsoftware.richorbrokecurrencyhistory.controller.GiphyController;
import com.beginwithsoftware.richorbrokecurrencyhistory.exchangedto.ExchangeResponse;
import com.beginwithsoftware.richorbrokecurrencyhistory.giphydto.GiphyResponse;
import com.beginwithsoftware.richorbrokecurrencyhistory.helperdto.DtoMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

@SpringBootApplication
@RestController
@EnableFeignClients
public class RichOrBrokeCurrencyHistoryApplication {

	@Autowired
	private CurrencyController currencyController;

	@Autowired
	private GiphyController giphyController;

	@Autowired
	private DtoMethods dtoMethods;

	@GetMapping("/getLatest")
	public ExchangeResponse getLatestExchangeResponse() {
		return currencyController.getLatest(currencyController.getCurrencyAppId());
	}

	@GetMapping("/getYesterday")
	public ExchangeResponse getYesterdayExchangeResponse() {
		return currencyController.getYesterday(currencyController.getYesterdayJsonName(),
												currencyController.getCurrencyAppId());
	}

	@GetMapping("/getGiphy")
	public GiphyResponse getGiphy() {
		return giphyController.getGiphy(giphyController.getGiphyAppId(), giphyController.getSearchQ(),
										giphyController.getGiphySearchLimit());
	}

	@GetMapping("/getRandomGiphy")
	public Object getRandomGiphy(@RequestParam(defaultValue = "rich") String searchParam)
			throws InvocationTargetException, IllegalAccessException {
		if (searchParam != null) {
			giphyController.setSearchQ(searchParam);
		}
		GiphyResponse giphyResponse = getGiphy();
		return dtoMethods.getRandomGiphyDtoObject(giphyResponse, giphyController.getGiphyDtoJsonPropertyValue());
	}

	@GetMapping("/compareCurrency")
	public Object compareCurrency() throws InvocationTargetException, IllegalAccessException {
		ExchangeResponse latestResponse = getLatestExchangeResponse();
		ExchangeResponse yesterdayResponse = getYesterdayExchangeResponse();
		boolean currencyRatio = currencyController.compareCurrencyRatio(latestResponse, yesterdayResponse);
		String giphySearchParam = "rich";
		if (!currencyRatio) {
			giphySearchParam = "broke";
		}
		return getRandomGiphy(giphySearchParam);
	}

	public static void main(String[] args) {
		SpringApplication.run(RichOrBrokeCurrencyHistoryApplication.class, args);
	}

}
