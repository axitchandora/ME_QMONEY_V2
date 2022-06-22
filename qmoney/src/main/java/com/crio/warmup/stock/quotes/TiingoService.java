
package com.crio.warmup.stock.quotes;

import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

public class TiingoService implements StockQuotesService {

  private RestTemplate restTemplate;

  protected TiingoService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }


  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  // Implement getStockQuote method below that was also declared in the interface.
  @Override
  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException {
    String tiingoURL = buildURL(symbol, from, to);
    String responseString = restTemplate.getForObject(tiingoURL, String.class);
    TiingoCandle[] tiingoCandleArray =
        getObjectMapper().readValue(responseString, TiingoCandle[].class);
    return Arrays.stream(tiingoCandleArray).sorted(Comparator.comparing(Candle::getDate))
        .collect(Collectors.toList());
  }

  // Note:
  // 1. You can move the code from PortfolioManagerImpl#getStockQuote inside newly created method.
  // 2. Run the tests using command below and make sure it passes.
  // ./gradlew test --tests TiingoServiceTest


  // CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  // Write a method to create appropriate url to call the Tiingo API.
  protected String buildURL(String symbol, LocalDate startDate, LocalDate endDate) {

    String uriTemplate = "https://api.tiingo.com/tiingo/daily/" + symbol + "/prices?" + "startDate="
        + startDate + "&endDate=" + endDate + "&token=" + getToken();
    return uriTemplate;
  }

  protected String getToken() {
    String[] token =
        {"ba213dcac06ea722d054f3150e8988f3e398b015", "61ce1dff403ba4b2b40ba2db6d6e65cec1e6c187",
            "b197a6dd821010c89ef8f1de157f9f785a1de2d7", "8f01a5a63850b10acdd7c2af98189529a3bcd575",
            "1cc10465be8655349441703e609b2e6b103b1a0a", "4cde2b644448ec2379b2147260d78783cabeda33",
            "953e5d1702c35f1aabd7a475fd8d256e2274d731", "dbafe4de20a19ef23df3deda52d513e373206cb0"};
    Random random = new Random();
    return token[random.nextInt(token.length)];
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }

}
