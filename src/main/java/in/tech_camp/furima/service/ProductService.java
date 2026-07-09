package in.tech_camp.furima.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import in.tech_camp.furima.dto.ProductDetailDto;
import in.tech_camp.furima.dto.ProductListDto;
import in.tech_camp.furima.enums.DeliveryFeeType;
import in.tech_camp.furima.repository.ProductRepository;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  // 一覧表示
  public List<ProductListDto> allProduct() {

    List<ProductListDto> products = productRepository.findAll()
        .stream().map(product -> {
          ProductListDto dto = new ProductListDto();
          dto.setId(product.getId());
          dto.setImg(product.getImg());
          dto.setName(product.getName());
          dto.setPrice(product.getPrice());
          dto.setSoldout(product.getProductId() != null);
          dto.setDeliveryFee(DeliveryFeeType.fromCode(product.getDeliveryFee()).getLabel());
          return dto;
        }).collect(Collectors.toList());

    return products;

  }

  // 詳細表示
  public ProductDetailDto selectByProductId(Long id) {

    ProductDetailDto dto = productRepository.selectByProductId(id);

    dto.setDeliveryFee(DeliveryFeeType.fromCode(dto.getDeliveryFee().getCode()));

    return dto;

  }

  // 商品削除
  public int deleteByProductId(Long id) {

    int result = productRepository.deleteByProductId(id);

    if (result <= 0) {
      throw new RuntimeException("削除できませんでした");
    } else if (result >= 2) {
      throw new RuntimeException("予想された挙動とは異なったため削除できませんでした");
    }

    return result;

  }

  // 商品更新
  public int updateByProductId(Long id) {

    int result = productRepository.updateByProductId(id);

    if (result <= 0) {
      throw new RuntimeException("削除できませんでした");
    } else if (result >= 2) {
      throw new RuntimeException("予想された挙動とは異なったため削除できませんでした");
    }

    return result;

  }

}
