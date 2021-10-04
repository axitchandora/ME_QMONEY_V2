
package com.crio.warmup.stock.portfolio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class PortfolioManagerFactoryTest {

  @Mock
  private RestTemplate restTemplate;

  @Test
  void getPortfolioManager() {
    Assertions.assertTrue(PortfolioManagerFactory.getPortfolioManager(restTemplate)
        instanceof PortfolioManagerImpl);
  }


}
