package in.tech_camp.furima.repository;

import java.util.List;
import java.util.Optional;

import in.tech_camp.furima.entity.ProductEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.furima.dto.repository.ProductQueryResult;

@Mapper
public interface ProductRepository {
  // 商品投稿
  @Insert("INSERT INTO products (user_id, name, description, category, condition, delivery_fee, prefecture, until_delivery, price, img) VALUES (#{userId}, #{name}, #{description}, #{category}, #{condition}, #{deliveryFee}, #{prefecture}, #{untilDelivery}, #{price}, #{img})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(ProductEntity product);

  // 商品一覧表示
  @Select("""
      SELECT p.id,p.img,p.name,p.price,p.delivery_fee,b.product_id FROM products p
      LEFT JOIN buys b
      ON p.id = b.product_id
      ORDER BY p.id DESC
      """)
  List<ProductQueryResult> findAll();

  // 1件取得
  @Select("SELECT * FROM products WHERE id = #{id}")
  Optional<ProductEntity> findById(Long id);
}
