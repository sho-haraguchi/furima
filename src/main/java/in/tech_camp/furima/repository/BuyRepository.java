package in.tech_camp.furima.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import in.tech_camp.furima.entity.BuyEntity;

@Mapper
public interface BuyRepository {
  @Insert("INSERT INTO buys (user_id, product_id) VALUES (#{userId}, #{productId})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(BuyEntity buy);
}