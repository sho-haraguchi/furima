package in.tech_camp.furima.dto.repository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProductQueryResult {

  private final Long id;
  private final String img;
  private final String name;
  private final Long price;
  private final int deliveryFee;
  private final Long productId;

}
