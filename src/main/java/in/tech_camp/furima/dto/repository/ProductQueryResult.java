package in.tech_camp.furima.dto.repository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProductQueryResult {

  private Long id;
  private String img;
  private String name;
  private Long price;
  private int deliveryFee;
  private Long productId;

}
