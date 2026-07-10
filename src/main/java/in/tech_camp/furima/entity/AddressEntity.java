package in.tech_camp.furima.entity;

import lombok.Data;

@Data
public class AddressEntity {
  private Long id;
  private Long buyId;
  private String postNumber;
  private Integer prefecture;
  private String city;
  private String block;
  private String building;
  private String phone;
}