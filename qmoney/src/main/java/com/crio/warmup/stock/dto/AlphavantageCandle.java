

// TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
//  Implement the Candle interface in such a way that it matches the parameters returned
//  inside Json response from Alphavantage service.

  Reference - https:www.baeldung.com/jackson-ignore-properties-on-serialization
  Reference - https:www.baeldung.com/jackson-name-of-property
public class AlphavantageCandle implements Candle {
  @JsonProperty("1. open")
  private Double open;
  private Double close;
  private Double high;
  private Double low;
  private Date date;
}

