
package com.crio.warmup.stock.quotes;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

class StockQuoteServiceFactoryTest {

  @Test
  void getServiceTiingo() {
    assertTrue(StockQuoteServiceFactory.INSTANCE.getService("tiingo", new RestTemplate())
        instanceof TiingoService);
  }

  @Test
  void getServiceTiingoUpperCase() {
    assertTrue(StockQuoteServiceFactory.INSTANCE.getService("Tiingo", new RestTemplate())
        instanceof TiingoService);
  }

  @Test
  void getServiceAlphavantage() {
    assertTrue(StockQuoteServiceFactory.INSTANCE.getService("alphavantage", new RestTemplate())
        instanceof AlphavantageService);
  }

  @Test
  void getServiceDefault() {
    assertTrue(StockQuoteServiceFactory.INSTANCE.getService("", new RestTemplate())
        instanceof AlphavantageService);
  }
}
