
package com.crio.warmup.stock.dto;

import java.time.LocalDate;

public class PortfolioTrade {

  public PortfolioTrade() {  }

  public static enum TradeType {
    BUY,
    SELL
  }

  private String symbol;
  private int quantity;
  private TradeType tradeType;
  private LocalDate purchaseDate;

  public PortfolioTrade(String symbol, int quantity, LocalDate purchaseDate) {
    this.symbol = symbol;
    this.quantity = quantity;
    this.purchaseDate = purchaseDate;
    this.tradeType = TradeType.BUY;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public void setTradeType(TradeType tradeType) {
    this.tradeType = tradeType;
  }

  public void setPurchaseDate(LocalDate purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  //solution
  public String getSymbol() {
    return symbol;
  }

  public int getQuantity() {
    return quantity;
  }

  public LocalDate getPurchaseDate() {
    return purchaseDate;
  }

  public TradeType getTradeType() {
    return tradeType;
  }
  //solution

}
