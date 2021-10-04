package com.crio.warmup.stock.quotes;

import com.crio.warmup.stock.dto.Candle;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDate;
import java.util.List;

public interface StockQuotesService {

  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  The implementation of this functions will be doing following tasks:
  //    1. Build the appropriate url to communicate with the third-party service.
  //       The url should consider startDate and endDate if it is supported by the provider.
  //    2. Communicate with the third-party with the url from step#1.
  //    3. Map the response and convert the same to List<Candle>.
  //    4. If the provider does not support startDate and endDate, then the implementation
  //       should also filter the dates based on startDate and endDate.
  //    5. Return a sorted List<Candle> sorted ascending based on Candle#getDate


  //CHECKSTYLE:OFF
  List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException
  ;
  //CHECKSTYLE:ON

}
