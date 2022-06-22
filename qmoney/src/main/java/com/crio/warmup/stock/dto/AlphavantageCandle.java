package com.crio.warmup.stock.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
// TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
// Implement the Candle interface in such a way that it matches the parameters returned
// inside Json response from Alphavantage service.

// Reference - https:www.baeldung.com/jackson-ignore-properties-on-serialization
// Reference - https:www.baeldung.com/jackson-name-of-property
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlphavantageCandle implements Candle {

  @JsonProperty("1. open")
  private Double open;
  @JsonProperty("2. high")
  private Double high;
  @JsonProperty("3. low")
  private Double low;
  @JsonProperty("4. close")
  private Double close;
  @JsonIgnore
  private LocalDate date;

  public Double getOpen() {
    return open;
  }

  public Double getClose() {
    return close;
  }

  public Double getHigh() {
    return high;
  }

  public Double getLow() {
    return low;
  }

  public LocalDate getDate() {
    return date;
  }
    
  public void setDate(LocalDate date) {
    this.date = date;
  }

  public void setOpen(Double open) {
    this.open = open;
  }

  public void setHigh(Double high) {
    this.high = high;
  }

  public void setLow(Double low) {
    this.low = low;
  }

  public void setClose(Double close) {
    this.close = close;
  }
}

