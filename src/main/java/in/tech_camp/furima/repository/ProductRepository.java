package in.tech_camp.furima.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.furima.dto.repository.ProductDetailQueryResult;
import in.tech_camp.furima.dto.repository.ProductQueryResult;

@Mapper
public interface ProductRepository {

  // 商品一覧表示
  @Select("""
      SELECT p.id,p.img,p.name,p.price,p.delivery_fee,b.product_id FROM products p
      LEFT JOIN buys b
      ON p.id = b.product_id
      ORDER BY p.id DESC
      """)
  List<ProductQueryResult> findAll();

  // 商品詳細
  @Select("""
      SELECT 
        p.id,p.img,p.name,p.price,p.delivery_fee,p.description,
        u.nickname,p.category,p.status,p.prefectures,p.untilDelivery,p.soldout
      FROM products p
      LEFT JOIN users u
      ON p.userId = u.id
      WHERE p.id = #{id}
      """)
  List<ProductDetailQueryResult> selectByProductId(Long id);

}
