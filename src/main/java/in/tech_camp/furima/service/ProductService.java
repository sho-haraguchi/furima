package in.tech_camp.furima.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.tech_camp.furima.dto.ProductListDto;
import in.tech_camp.furima.entity.ProductEntity;
import in.tech_camp.furima.enums.DeliveryFeeType;
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
}