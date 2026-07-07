package in.tech_camp.furima.dto;

import lombok.Data;

@Data // 商品詳細
public class ProductDetailDto {

  private String name;
  private String img;
  private Long price;
  private int deliveryFee;
  private String nickname;
  private String category;
  private String status;
  private String prefectures;
  private String untilDelivery;
  private int soldout;

}
