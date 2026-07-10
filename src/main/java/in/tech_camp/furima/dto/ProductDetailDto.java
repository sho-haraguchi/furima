package in.tech_camp.furima.dto;

import lombok.Data;

@Data // 商品詳細
public class ProductDetailDto {

  private Long id;
  private String name;
  private String img;
  private Long price;
  private String deliveryFee;
  private String description;
  private String nickname;
  private String category;
  private String condition;
  private String prefecture;
  private String untilDelivery;
  private boolean soldout;
  private Long userId;

}
