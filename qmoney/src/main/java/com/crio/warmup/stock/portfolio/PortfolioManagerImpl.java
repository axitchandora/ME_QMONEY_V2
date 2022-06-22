
package com.crio.warmup.stock.portfolio;

import static java.time.temporal.ChronoUnit.DAYS;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.PortfolioTrade;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.crio.warmup.stock.quotes.StockQuotesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

public class PortfolioManagerImpl implements PortfolioManager {

  private RestTemplate restTemplate;
  private StockQuotesService stockQuotesService;
  

  protected PortfolioManagerImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }
  
  public PortfolioManagerImpl(StockQuotesService stockQuotesService) {
    this.stockQuotesService = stockQuotesService;
  }
  
  private Comparator<AnnualizedReturn> getComparator() {
    return Comparator.comparing(AnnualizedReturn::getAnnualizedReturn).reversed();
  }

  //CHECKSTYLE:OFF

  private String getToken() {
    return "3a9bb71bc48d9077662424550e62c72a7b1ce2cd";
  }

  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException {
        String url = buildUri(symbol,from,to);
        String response = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Candle[] obj = objectMapper.readValue(response, TiingoCandle[].class);
        if(obj==null) return new ArrayList<>();
        else return Arrays.asList(obj);
  }

  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
    return "https://api.tiingo.com/tiingo/daily/" + symbol + "/prices?startDate=" + startDate
    + "&endDate=" + endDate + "&token=" + getToken();
  }
  private Double getOpeningPriceOnStartDate(List<Candle> candles) {
    return candles.get(0).getOpen();
  }

  private Double getClosingPriceOnEndDate(List<Candle> candles) {
    return candles.get(candles.size() - 1).getClose();
  }

  private AnnualizedReturn calculateReturns(LocalDate endDate, PortfolioTrade trade, Double buyPrice,
      Double sellPrice) {
      Double totalReturns = (sellPrice - buyPrice) / buyPrice;
      long DaysBetween = ChronoUnit.DAYS.between(trade.getPurchaseDate(), endDate);
      Double v = (double) (DaysBetween / 365.24);
      Double annualizedReturn = Math.pow((1+totalReturns),(1/v))-1;
      return new AnnualizedReturn(trade.getSymbol(), annualizedReturn, totalReturns);
  }
  
  @Override
  public List<AnnualizedReturn> calculateAnnualizedReturn(List<PortfolioTrade> portfolioTrades, LocalDate endDate) throws JsonProcessingException{
    List<Candle> obj = null;
    String moduleToRun = null;
    List<AnnualizedReturn> put = new ArrayList<>();
    for (PortfolioTrade trade : portfolioTrades) {

        obj = stockQuotesService.getStockQuote(trade.getSymbol(), trade.getPurchaseDate(), endDate);

      Double buyPrice = getOpeningPriceOnStartDate(obj);
      Double sellPrice = getClosingPriceOnEndDate(obj);
      AnnualizedReturn anr = calculateReturns(endDate, trade, buyPrice, sellPrice);
      put.add(anr);
    }
    List<AnnualizedReturn> res = put.stream().sorted((e1,e2)->{
      if(e1.getAnnualizedReturn()==e2.getAnnualizedReturn()) return 0;
      else if(e2.getAnnualizedReturn()>e1.getAnnualizedReturn()) return 1;
      else return -1;
    }).collect(Collectors.toList());
    return res;
  }


 


}
