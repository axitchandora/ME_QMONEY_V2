
package com.crio.warmup.stock.portfolio;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;


/*
This class is supposed to be used by assessments only.
 */
@ExtendWith(MockitoExtension.class)
class PortfolioManagerTest {


  @Mock
  private RestTemplate restTemplate;

  @Spy
  @InjectMocks
  private PortfolioManagerImpl portfolioManager;

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

  private String aaplQuotes = "[{\"date\":\"2019-01-02T00:00:00.000Z\",\"close\":157.92,\"high\":"
      + "158.85,\"low\":154.23,\"open\":154.89,\"volume\":37039737,\"adjClose\":155.575184502,"
      + "\"adjHigh\":156.4913757481,\"adjLow\":151.9399740739,\"adjOpen\":152.590174313,\"adjVolume"
      + "\":37039737,\"divCash\":0.0,\"splitFactor\":1.0},{\"date\":\"2019-01-03T00:00:00.000Z\","
      + "\"close\":142.19,\"high\":145.72,\"low\":142.0,\"open\":143.98,\"volume\":91312195,"
      + "\"adjClose\":140.0787454682,\"adjHigh\":143.5563315959,\"adjLow\":139.8915666115,\"adjOpen"
      + "\":141.842167329,\"adjVolume\":91312195,\"divCash\":0.0,\"splitFactor\":1.0},{\"date\":"
      + "\"2019-12-12T00:00:00.000Z\",\"close\":271.46,\"high\":148.5499,\"low\":143.8,\"open"
      + "\":144.53,\"volume\":58607070,\"adjClose\":146.0586173649,\"adjHigh\":146.3442128942,"
      + "\"adjLow\":141.6648399911,\"adjOpen\":142.3840008617,\"adjVolume\":58607070,\"divCash"
      + "\":0.0,\"splitFactor\":1.0}]";

  private String msftQuotes = "[{\"date\":\"2019-01-02T00:00:00.000Z\",\"close\":101.12,\"high\""
      + ":101.75,\"low\":98.94,\"open\":99.55,\"volume\":35329345,\"adjClose\":99.6386555235,"
      + "\"adjHigh\":100.2594264193,\"adjLow\":97.490591154,\"adjOpen\":98.0916550372,\"adjVolume"
      + "\":35329345,\"divCash\":0.0,\"splitFactor\":1.0},{\"date\":\"2019-01-03T00:00:00.000Z\","
      + "\"close\":97.4,\"high\":100.185,\"low\":97.2,\"open\":100.1,\"volume\":42578410,\"adjClose"
      + "\":95.9731511866,\"adjHigh\":98.7173526861,\"adjLow\":95.7760810609,\"adjOpen"
      + "\":98.6335978827,\"adjVolume\":42578410,\"divCash\":0.0,\"splitFactor\":1.0},{\"date\":"
      + "\"2019-12-12T00:00:00.000Z\",\"close\":153.24,\"high\":102.51,\"low\":98.93,\"open"
      + "\":99.72,\"volume\":44060620,\"adjClose\":100.4367895323,\"adjHigh\":101.0082928967,"
      + "\"adjLow\":97.4807376477,\"adjOpen\":98.259164644,\"adjVolume\":44060620,\"divCash"
      + "\":0.0,\"splitFactor\":1.0}]";

  @Test
  public void calculateExtrapolatedAnnualizedReturn()
      throws Exception {
    //given
    String moduleToRun = null;
    moduleToRun = "REFACTOR";


    if (moduleToRun.equals("REFACTOR")) {
      Mockito.doReturn(getCandles(aaplQuotes))
          .when(portfolioManager).getStockQuote(eq("AAPL"), any(), any());
      Mockito.doReturn(getCandles(msftQuotes))
          .when(portfolioManager).getStockQuote(eq("MSFT"), any(), any());
      Mockito.doReturn(getCandles(googlQuotes))
          .when(portfolioManager).getStockQuote(eq("GOOGL"), any(), any());
    }
    PortfolioTrade trade1 = new PortfolioTrade("AAPL", 50, LocalDate.parse("2019-01-02"));
    PortfolioTrade trade2 = new PortfolioTrade("GOOGL", 100, LocalDate.parse("2019-01-02"));
    PortfolioTrade trade3 = new PortfolioTrade("MSFT", 20, LocalDate.parse("2019-01-02"));
    List<PortfolioTrade> portfolioTrades = Arrays
        .asList(new PortfolioTrade[]{trade1, trade2, trade3});


    //when
    List<AnnualizedReturn> annualizedReturns = portfolioManager
        .calculateAnnualizedReturn(portfolioTrades, LocalDate.parse("2019-12-12"));

    //then
    List<String> symbols = annualizedReturns.stream().map(AnnualizedReturn::getSymbol)
        .collect(Collectors.toList());
    Assertions.assertEquals(0.814, annualizedReturns.get(0).getAnnualizedReturn(), 0.01);
    Assertions.assertEquals(0.584, annualizedReturns.get(1).getAnnualizedReturn(), 0.01);
    Assertions.assertEquals(0.33, annualizedReturns.get(2).getAnnualizedReturn(),0.01);
    Assertions.assertEquals(Arrays.asList(new String[]{"AAPL", "MSFT", "GOOGL"}), symbols);

  }


  private List<TiingoCandle> getCandles(String responseText) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    return Arrays.asList(mapper.readValue(responseText, TiingoCandle[].class));
  }



}

