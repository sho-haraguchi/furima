package in.tech_camp.furima.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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

}
