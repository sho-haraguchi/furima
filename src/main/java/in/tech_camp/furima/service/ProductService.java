package in.tech_camp.furima.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.furima.dto.ProductDetailDto;
import in.tech_camp.furima.dto.ProductListDto;
import in.tech_camp.furima.dto.repository.ProductDetailQueryResult;
import in.tech_camp.furima.entity.ProductEntity;
import in.tech_camp.furima.enums.Category;
import in.tech_camp.furima.enums.Condition;
import in.tech_camp.furima.enums.DeliveryFeeType;
import in.tech_camp.furima.enums.PrefectureType;
import in.tech_camp.furima.enums.UntilDelivery;
import in.tech_camp.furima.form.ProductEditForm;
import in.tech_camp.furima.form.ProductForm;
import in.tech_camp.furima.repository.ProductRepository;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  // 商品一覧表示機能
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

  // 商品出品機能
  @Transactional
  public void saveProduct(ProductForm form, Long userId) throws IOException {
    String imageName = null;
    MultipartFile imgFile = form.getImg();

    if (imgFile != null && !imgFile.isEmpty()) {
      String uuid = UUID.randomUUID().toString();
      imageName = uuid + "-" + imgFile.getOriginalFilename();

      Path uploadDir = Paths.get("uploads").toAbsolutePath();

      if (!Files.exists(uploadDir)) {
        Files.createDirectories(uploadDir);
      }

      Path imagePath = uploadDir.resolve(imageName);
      Files.copy(imgFile.getInputStream(), imagePath);
    }

    ProductEntity product = new ProductEntity();
    product.setImg(imageName);
    product.setUserId(userId);
    product.setName(form.getName());
    product.setDescription(form.getDescription());
    product.setCategory(form.getCategory());
    product.setCondition(form.getCondition());
    product.setDeliveryFee(form.getDeliveryFee());
    product.setPrefecture(form.getPrefecture());
    product.setUntilDelivery(form.getUntilDelivery());
    product.setPrice(form.getPrice());

    productRepository.insert(product);
  }

  // 商品詳細
  public ProductDetailDto selectByProductId(Long id) {

    ProductDetailQueryResult result = productRepository.selectByProductId(id);
    if (result == null)
      return null;

    ProductDetailDto dto = new ProductDetailDto();
    dto.setId(result.getId());
    dto.setImg(result.getImg());
    dto.setName(result.getName());
    dto.setDescription(result.getDescription());
    dto.setNickname(result.getNickname());
    dto.setPrice(result.getPrice());
    dto.setSoldout(result.isSoldout());
    dto.setDeliveryFee(DeliveryFeeType.fromCode(result.getDeliveryFee()).getLabel());
    dto.setCategory(Category.fromCode(result.getCategory()).getDisplayName());
    dto.setCondition(Condition.fromCode(result.getCondition()).getDisplayName());
    dto.setPrefecture(PrefectureType.fromCode(result.getPrefecture()).getLabel());
    dto.setUntilDelivery(UntilDelivery.fromCode(result.getUntilDelivery()).getDisplayName());
    dto.setUserId(result.getUserId());

    return dto;

  }

  // 商品削除
  @Transactional
  public int deleteByProductId(Long id, Long userId) {

    if (!productRepository.existsByIdANDUserId(id, userId)) {
      throw new RuntimeException("所有者ではありませんので削除できません");
    }

    int result = productRepository.deleteByProductId(id);

    if (result <= 0) {
      throw new RuntimeException("削除できませんでした");
    } else if (result >= 2) {
      throw new RuntimeException("予想された挙動とは異なったため削除できませんでした");
    }

    return result;

  }

  // 商品更新ページ
  @Transactional
  public ProductForm showEditProduct(ProductDetailDto dto,Long userId) {

    if (!productRepository.existsByIdANDUserId(dto.getId(), userId)) {
      throw new RuntimeException("所有者ではありませんので削除できません");
    }

    ProductForm form = new ProductForm();
    form.setName(dto.getName());
    form.setDescription(dto.getDescription());
    form.setCategory(Category.fromDisplayName(dto.getCategory()).getCode());
    form.setCondition(Condition.fromDisplayName(dto.getCondition()).getCode());
    form.setDeliveryFee(DeliveryFeeType.fromDisplayName(dto.getDeliveryFee()).getCode());
    form.setPrefecture(PrefectureType.fromDisplayName(dto.getPrefecture()).getCode());
    form.setUntilDelivery(UntilDelivery.fromDisplayName(dto.getUntilDelivery()).getCode());
    form.setPrice(dto.getPrice());

    return form;
  }

  // 商品更新
  @Transactional
  public int updateByProductId(Long id, ProductForm productForm, Long userId, String image) throws IOException {

    // dtoから受け取る -> dbのほうはint型なのでserviceでdtoをint型に変換 もしくは thymeleaf側で変換

    String imageName = null;
    MultipartFile imgFile = productForm.getImg();

    if (imgFile != null && !imgFile.isEmpty()) {
      String uuid = UUID.randomUUID().toString();
      imageName = uuid + "-" + imgFile.getOriginalFilename();

      Path uploadDir = Paths.get("uploads").toAbsolutePath();

      if (!Files.exists(uploadDir)) {
        Files.createDirectories(uploadDir);
      }

      Path imagePath = uploadDir.resolve(imageName);
      Files.copy(imgFile.getInputStream(), imagePath);
    }

    ProductEditForm product = new ProductEditForm();
    product.setId(id);
    if (imageName == null) {
      imageName = image;
    }
    product.setImg(imageName);
    product.setName(productForm.getName());
    product.setDescription(productForm.getDescription());
    product.setCategory(productForm.getCategory());
    product.setCondition(productForm.getCondition());
    product.setDeliveryFee(productForm.getDeliveryFee());
    product.setPrefecture(productForm.getPrefecture());
    product.setUntilDelivery(productForm.getUntilDelivery());
    product.setPrice(productForm.getPrice());

    int result = productRepository.updateByProductId(product);

    if (result <= 0) {
      throw new RuntimeException("編集できませんでした");
    } else if (result >= 2) {
      throw new RuntimeException("予想された挙動とは異なったため編集できませんでした");
    }

    return result;

  }
}
