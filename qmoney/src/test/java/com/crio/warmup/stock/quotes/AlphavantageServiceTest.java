
package com.crio.warmup.stock.quotes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;

import com.crio.warmup.stock.dto.AlphavantageDailyResponse;
import com.crio.warmup.stock.dto.Candle;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.client.RestTemplate;

class AlphavantageServiceTest {

  @Mock
  private RestTemplate restTemplate;

  @Spy
  @InjectMocks
  private AlphavantageService alphavantageService;

  private String aaplQuotes = "{\"Meta Data\": {\"1. Information\": \"Daily Time Series with Splits"
      + " and Dividend Events\",\"2. Symbol\": \"AAPL\",\"3. Last Refreshed\": \"2019-12-23\",\"4."
      + " Output Size\": \"Full size\",\"5. Time Zone\": \"US/Eastern\"},\"Time Series (Daily)\": "
      + "{\"2019-12-12\": {\"1. open\": \"267.7800\",\"2. high\": \"272.5599\",\"3. low\": "
      + "\"267.3210\",\"4. close\": \"271.4600\",\"5. adjusted close\": \"271.4600\",\"6. volume\":"
      + " \"34437042\",\"7. dividend amount\": \"0.0000\",\"8. split coefficient\": \"1.0000\"},"
      + "\"2019-12-11\": {\"1. open\": \"268.8100\",\"2. high\": \"271.1000\",\"3. low\": "
      + "\"268.5000\",\"4. close\": \"270.7700\",\"5. "
      + "adjusted close\": \"270.7700\",\"6. volume\": "
      + "\"19723391\",\"7. dividend amount\": \"0.0000\",\"8. split coefficient\": \"1.0000\"},"
      + "\"2019-01-04\": {\"1. open\": \"144.5300\",\"2. high\": \"148.5499\",\"3. low\": "
      + "\"143.8000\",\"4. close\": \"148.2600\",\"5. "
      + "adjusted close\": \"146.0586\",\"6. volume\": "
      + "\"58607070\",\"7. dividend amount\": \"0.0000\",\"8. split coefficient\": \"1.0000\"},"
      + "\"2019-01-03\": {\"1. open\": \"143.9800\",\"2. high\": \"145.7200\",\"3. low\": "
      + "\"142.0000\",\"4. close\": \"142.1900\",\"5. adjusted close\": \"140.0787\",\"6. volume\":"
      + " \"91312195\",\"7. dividend amount\": \"0.0000\",\"8. split coefficient\": \"1.0000\"},"
      + "\"2019-01-02\": {\"1. open\": \"154.8900\",\"2. high\": \"158.8500\",\"3. low\": "
      + "\"154.2300\",\"4. close\": \"157.9200\",\"5. adjusted close\": \"155.5752\",\"6. volume\":"
      + " \"37039737\",\"7. dividend amount\": \"0.0000\",\"8. split coefficient\": \"1.0000\"},"
      + "\"2018-12-31\": {\"1. open\": \"158.5300\",\"2. high\": \"159.3600\",\"3. low\": "
      + "\"156.4800\",\"4. close\": \"157.7400\",\"5. adjusted close\": \"155.3979\",\"6. volume\":"
      + " \"35003466\",\"7. dividend amount\": \"0.0000\",\"8. split coefficient\": \"1.0000\"}}}";



  @Test
  @MockitoSettings(strictness = Strictness.LENIENT)
  void getStockQuoteSingle() throws Exception {
    Mockito.doReturn(aaplQuotes).when(restTemplate).getForObject(anyString(), eq(String.class));
    Mockito.doReturn(getParsedResponse(aaplQuotes)).when(restTemplate).getForObject(anyString(),
            eq(AlphavantageDailyResponse.class));

    List<Candle> candles = alphavantageService
        .getStockQuote("AAPL",
            LocalDate.parse("2019-01-01"), LocalDate.parse("2019-01-04"));

    assertEquals(candles.get(0).getOpen(), 154.89, 0.1);
    assertEquals(candles.get(2).getClose(), 148.26, 0.1);
    assertEquals(candles.get(2).getDate(), LocalDate.parse("2019-01-04"));

    ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
    Mockito.verify(restTemplate, atLeast(0)).getForObject(urlCaptor.capture(), any());

    ArgumentCaptor<String> urlCaptorWithMap = ArgumentCaptor.forClass(String.class);
    Mockito.verify(restTemplate, atLeast(0))
        .getForObject(urlCaptorWithMap.capture(), any(), anyMap());

    ArgumentCaptor<URI> urlCaptorAsUri = ArgumentCaptor.forClass(URI.class);
    Mockito.verify(restTemplate, atLeast(0))
        .getForObject(urlCaptorAsUri.capture(), any(Class.class));

    List<String> propertyKeyValues = urlCaptor.getAllValues();
    List<String> propertyKeyValues2 = urlCaptorWithMap.getAllValues();
    List<URI> propertyKeyValues3 = urlCaptorAsUri.getAllValues();

    assertTrue(!propertyKeyValues.isEmpty() || !propertyKeyValues2.isEmpty() || !propertyKeyValues3
        .isEmpty());
  }


  private AlphavantageDailyResponse getParsedResponse(String quotes) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    try {
      return objectMapper.readValue(quotes, AlphavantageDailyResponse.class);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }


}

