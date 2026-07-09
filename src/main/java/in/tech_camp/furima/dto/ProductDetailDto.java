package in.tech_camp.furima.dto;

import in.tech_camp.furima.enums.Category;
import in.tech_camp.furima.enums.Condition;
import in.tech_camp.furima.enums.DeliveryFeeType;
import in.tech_camp.furima.enums.PrefectureType;
import in.tech_camp.furima.enums.UntilDelivery;
import lombok.Data;

@Data // 商品詳細
public class ProductDetailDto {

  private String name;
  private String img;
  private Long price;
  private DeliveryFeeType deliveryFee;
  private String description;
  private String nickname;
  private Category category;
  private Condition status;
  private PrefectureType prefectures;
  private UntilDelivery untilDelivery;
  private boolean soldout;

}
