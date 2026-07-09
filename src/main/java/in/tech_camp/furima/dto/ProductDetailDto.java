package in.tech_camp.furima.dto;

import in.tech_camp.furima.enums.DeliveryFeeType;
import lombok.Data;

@Data // 商品詳細
public class ProductDetailDto {

  private String name;
  private String img;
  private Long price;
  private DeliveryFeeType deliveryFee;
  private String description;
  private String nickname;
  private String category;
  private String status;
  private String prefectures;
  private String untilDelivery;
  private boolean soldout;

}
