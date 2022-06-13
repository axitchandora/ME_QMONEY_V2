
package com.crio.warmup.stock.dto;

import java.time.LocalDate;

public interface Candle {

  Double getOpen();

  Double getClose();

  Double getHigh();

  Double getLow();

  LocalDate getDate();
}
