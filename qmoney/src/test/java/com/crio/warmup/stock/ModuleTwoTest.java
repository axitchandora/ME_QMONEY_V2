
package com.crio.warmup.stock;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ModuleTwoTest {

  @Test
  void mainReadQuotes() throws Exception {
    //given
    String filename = "assessments/trades.json";
    List<String> expected = Arrays.asList(new String[]{"CTS", "CSCO", "MSFT"});

    //when
    List<String> actual = PortfolioManagerApplication
        .mainReadQuotes(new String[]{filename, "2019-12-12"});

    //then
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void mainReadQuotesEdgeCase() throws Exception {
    //given
    String filename = "assessments/empty.json";
    List<String> expected = Arrays.asList(new String[]{});

    //when
    List<String> actual = PortfolioManagerApplication
        .mainReadQuotes(new String[]{filename, "2019-12-12"});

    //then
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void mainReadQuotesInvalidDates() throws Exception {
    //given
    String filename = "assessments/trades_invalid_dates.json";
    //when
    Assertions.assertThrows(RuntimeException.class, () -> PortfolioManagerApplication
        .mainReadQuotes(new String[]{filename, "2017-12-12"}));

  }


  @Test
  void mainReadQuotesInvalidStocks() throws Exception {
    //given
    String filename = "assessments/trades_invalid_stock.json";
    //when
    Assertions.assertThrows(RuntimeException.class, () -> PortfolioManagerApplication
        .mainReadQuotes(new String[]{filename, "2017-12-12"}));

  }

  @Test
  void mainReadQuotesOldTrades() throws Exception {
    //given
    String filename = "assessments/trades_old.json";
    List<String> expected = Arrays.asList(new String[]{"CTS", "ABBV", "MMM"});

    //when
    List<String> actual = PortfolioManagerApplication
        .mainReadQuotes(new String[]{filename, "2019-12-12"});

    //then
    Assertions.assertEquals(expected, actual);
  }

}
