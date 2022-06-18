
package com.crio.warmup.stock;

import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

class ModuleThreeRefactorTest {

  private String googlQuotes = "[{\"date\":\"2019-01-02T00:00:00.000Z\",\"close\":1054.68,"
          + "\"high\":1060.79,\"low\":1025.28,\"open\":1027.2,\"volume\":1593395,\"adjClose\":1054.68,"
          + "\"adjHigh\":1060.79,\"adjLow\":1025.28,\""
          + "adjOpen\":1027.2,\"adjVolume\":1593395,\"divCash\""
          + ":0.0,\"splitFactor\":1.0},{\"date\":\""
          + "2019-01-03T00:00:00.000Z\",\"close\":1025.47,\"high\""
          + ":1066.26,\"low\":1022.37,\"open\":1050.67,\"volume\":2097957,\"adjClose\":1025.47,"
          + "\"adjHigh\":1066.26,\"adjLow\":1022.37,\"adjOpen\":1050.67,\"adjVolume\":2097957,"
          + "\"divCash\":0.0,\"splitFactor\":1.0},{\"date\":\"2019-12-12T00:00:00.000Z\","
          + "\"close\":1348.49,\"high\":1080.0,\"low\":1036.86,\"open\":1042.56,\"volume\":2301428,"
          + "\"adjClose\":1078.07,\"adjHigh\":1080.0,\"adjLow\":1036.86,\"adjOpen\":1042.56,\"adjVolume"
          + "\":2301428,\"divCash\":0.0,\"splitFactor\":1.0}]";

  private List<Candle> getCandles(String responseText) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    return Arrays.asList(mapper.readValue(responseText, TiingoCandle[].class));
  }

  @Test
  public void getOpeningPriceOnStartDate() throws JsonProcessingException {
    Double price = PortfolioManagerApplication.getOpeningPriceOnStartDate(getCandles(googlQuotes));
    Assertions.assertEquals(1027.2, price, 0.1);
  }

  @Test
  public void getClosingPriceOnEndDate() throws JsonProcessingException {
    Double price = PortfolioManagerApplication.getClosingPriceOnEndDate(getCandles(googlQuotes));
    Assertions.assertEquals(1348.49, price, 0.1);
  }

  @Test
  public void fetchCandles() throws JsonProcessingException {
    PortfolioTrade trade = new PortfolioTrade();
    trade.setPurchaseDate(LocalDate.parse("2020-01-01"));
    trade.setSymbol("AAPL");
    List<Candle> candleList = PortfolioManagerApplication.fetchCandles(trade, LocalDate.parse("2020-01-05"),
            PortfolioManagerApplication.getToken());
    Assertions.assertEquals(296.24, candleList.get(0).getOpen(), 0.1);
    Assertions.assertEquals(297.15, candleList.get(candleList.size()-1).getOpen(), 0.1);
  }

}
