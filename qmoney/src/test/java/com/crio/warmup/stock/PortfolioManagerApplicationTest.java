
package com.crio.warmup.stock;

import com.crio.warmup.stock.dto.PortfolioTrade;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PortfolioManagerApplicationTest {


  // TODO: WARNING!!!
  //  DO NOT MODIFY ANY FILES IN THE TESTS/ ASSESSMENTS UNLESS ASKED TO.
  //  These files are replaced from stock contents while executing the assessments.
  //  Any modifications in this file may result in Assessment failure!


  @Test
  void mainReadFile() throws Exception {
    //given
    String filename = "trades.json";
    List<String> expected = Arrays.asList(new String[]{"AAPL", "MSFT", "GOOGL"});

    //when
    List<String> results = PortfolioManagerApplication
        .mainReadFile(new String[]{filename});

    //then
    Assertions.assertEquals(expected, results);
  }


  @Test
  void mainReadQuotes() throws Exception {
    //given
    String filename = "trades.json";
    List<String> expected = Arrays.asList(new String[]{"MSFT", "AAPL", "GOOGL"});

    //when
    List<String> actual = PortfolioManagerApplication
        .mainReadQuotes(new String[]{filename, "2019-12-12"});

    //then
    Assertions.assertEquals(expected, actual);
  }



  @Test
  public void testDebugValues() {
    List<String> responses = PortfolioManagerApplication.debugOutputs();
    Assertions.assertTrue(responses.get(0).contains("trades.json"));
  }

}

