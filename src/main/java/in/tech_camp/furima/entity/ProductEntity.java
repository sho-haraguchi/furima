package in.tech_camp.furima.entity;

import lombok.Data;

@Data
public class ProductEntity {
  
  private Long id;
  private Long userId;
  private String name;
  private String description;
  private int category;
  private int condition;
  private int deliveryFee;
  private int utilDelivery;
  private Long price;
  private String img;

}
