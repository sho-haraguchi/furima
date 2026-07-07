package in.tech_camp.furima.dto;

import lombok.Data;

@Data // 商品一覧表示
public class ProductListDto {
  
  private Long id;
  private String img;
  private String name;
  private Long price;
  private int deliveryFee;
  private int soldout;

}
