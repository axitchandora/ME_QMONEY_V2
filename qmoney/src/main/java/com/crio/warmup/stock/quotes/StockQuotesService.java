package com.crio.warmup.stock.quotes;

import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDate;
import java.util.List;

public interface StockQuotesService {


  // TODO: CRIO_TASK_MODULE_EXCEPTIONS
  //  Change the method signature to throw StockQuoteServiceException

  //CHECKSTYLE:OFF
  List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException
      , StockQuoteServiceException
  ;
  //CHECKSTYLE:ON

}
