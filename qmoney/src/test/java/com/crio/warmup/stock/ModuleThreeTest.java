
package com.crio.warmup.stock;

import com.crio.warmup.stock.dto.AnnualizedReturn;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ModuleThreeTest {

  @Test
  void mainCalculateReturns() throws Exception {
    //given
    String filename = "assessments/trades.json";

    //when
    List<AnnualizedReturn> result = PortfolioManagerApplication
        .mainCalculateSingleReturn(new String[]{filename, "2019-12-12"});

    //then
    List<String> symbols = result.stream().map(AnnualizedReturn::getSymbol)
        .collect(Collectors.toList());
    Assertions.assertEquals(0.556, result.get(0).getAnnualizedReturn(), 0.01);
    Assertions.assertEquals(0.044, result.get(1).getAnnualizedReturn(), 0.01);
    Assertions.assertEquals(0.025, result.get(2).getAnnualizedReturn(), 0.01);
    Assertions.assertEquals(Arrays.asList(new String[]{"MSFT", "CSCO", "CTS"}), symbols);
  }

  @Test
  void mainCalculateReturnsEdgeCase() throws Exception {
    //given
    String filename = "assessments/empty.json";

    //when
    List<AnnualizedReturn> result = PortfolioManagerApplication
        .mainCalculateSingleReturn(new String[]{filename, "2019-12-12"});

    Assertions.assertTrue(result.isEmpty());
  }

  @Test
  void mainCalculateReturnsVaryingDateRanges() throws Exception {
    //given
    String filename = "assessments/trades_invalid_dates.json";
    //when
    List<AnnualizedReturn> result = PortfolioManagerApplication
        .mainCalculateSingleReturn(new String[]{filename, "2019-12-12"});

    //then
    List<String> symbols = result.stream().map(AnnualizedReturn::getSymbol)
        .collect(Collectors.toList());
    Assertions.assertEquals(0.36, result.get(0).getAnnualizedReturn(), 0.01);
    Assertions.assertEquals(0.15, result.get(1).getAnnualizedReturn(), 0.01);
    Assertions.assertEquals(0.02, result.get(2).getAnnualizedReturn(), 0.01);
    Assertions.assertEquals(Arrays.asList(new String[]{"MSFT", "CSCO", "CTS"}), symbols);

  }


  @Test
  void mainCalculateReturnsInvalidStocks() throws Exception {
    //given
    String filename = "assessments/trades_invalid_stock.json";
    //when
    Assertions.assertThrows(RuntimeException.class, () -> PortfolioManagerApplication
        .mainCalculateSingleReturn(new String[]{filename, "2019-12-12"}));

  }

  @Test
  void mainCalculateReturnsOldTrades() throws Exception {
    //given
    String filename = "assessments/trades_old.json";

    //when
    List<AnnualizedReturn> result = PortfolioManagerApplication
        .mainCalculateSingleReturn(new String[]{filename, "2019-12-20"});

    //then
    List<String> symbols = result.stream().map(AnnualizedReturn::getSymbol)
        .collect(Collectors.toList());
    Assertions.assertEquals(0.141, result.get(0).getAnnualizedReturn(), 0.01);
    Assertions.assertEquals(0.091, result.get(1).getAnnualizedReturn(), 0.01);
    Assertions.assertEquals(0.056, result.get(2).getAnnualizedReturn(), 0.01);
    Assertions.assertEquals(Arrays.asList(new String[]{"ABBV", "CTS", "MMM"}), symbols);
  }

}
