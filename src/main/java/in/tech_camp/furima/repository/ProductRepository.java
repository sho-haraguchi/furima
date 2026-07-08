package in.tech_camp.furima.repository;

import in.tech_camp.furima.entity.ProductEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductRepository {
  @Insert("INSERT INTO products (user_id, name, description, category, condition, delivery_fee, prefecture, until_delivery, price, img) VALUES (#{userId}, #{name}, #{description}, #{category}, #{condition}, #{deliveryFee}, #{prefecture}, #{untilDelivery}, #{price}, #{img})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(ProductEntity product);

  @Select("SELECT * FROM products WHERE id = #{id}")
  ProductEntity findById(Long id);
}