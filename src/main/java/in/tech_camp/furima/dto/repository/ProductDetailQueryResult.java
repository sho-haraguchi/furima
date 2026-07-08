package in.tech_camp.furima.dto.repository;

import lombok.Data;

@Data
public class ProductDetailQueryResult {

  private String name;
  private String img;
  private Long price;
  private int deliveryFee;
  private String description;
  private String nickname;
  private String category;
  private String status;
  private String prefectures;
  private String untilDelivery;
  private boolean soldout;

}
