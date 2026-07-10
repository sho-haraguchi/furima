package in.tech_camp.furima.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import in.tech_camp.furima.entity.AddressEntity;

@Mapper
public interface AddressRepository {
  @Insert("INSERT INTO addresses (buy_id, post_number, prefecture, city, block, building, phone) VALUES (#{buyId}, #{postNumber}, #{prefecture}, #{city}, #{block}, #{building}, #{phone})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void save(AddressEntity address);
}
