
package com.crio.warmup.stock.exception;

public class StockQuoteServiceException extends Exception {

  public StockQuoteServiceException(String message) {
    super(message);
  }

  public StockQuoteServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
