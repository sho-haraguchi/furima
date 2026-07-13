package in.tech_camp.furima.service;

import in.tech_camp.furima.dto.OrderDto;
import in.tech_camp.furima.entity.AddressEntity;
import in.tech_camp.furima.entity.BuyEntity;
import in.tech_camp.furima.entity.ProductEntity;
import in.tech_camp.furima.repository.AddressRepository;
import in.tech_camp.furima.repository.BuyRepository;
import in.tech_camp.furima.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final BuyRepository buyRepository;
    private final AddressRepository addressRepository;
    private final PayjpService payjpService;

    // 無い商品にアクセスしようとしたとき
    public ProductEntity findById(Long productId) {
      return productRepository.findById(productId)
                              .orElseThrow(() -> new IllegalArgumentException("Invalid product ID:" + productId));
    }

    // ログイン状態の場合でも、自身が出品していない売却済み商品の商品購入ページへ遷移しようとすると、トップページに遷移すること。
    public boolean isSoldout(Long productId) {
      return buyRepository.existsByProductId(productId) != null;
    }

    // 商品購入処理
    @Transactional
    public void createOrder(Long productId, Long userId, OrderDto orderDto) {
      ProductEntity product = findById(productId);

      // PayjpServiceを使って決済処理
      payjpService.charge(product.getPrice(), orderDto.getToken());

      // buysテーブルへ保存
      BuyEntity buy = new BuyEntity();
      buy.setUserId(userId);
      buy.setProductId(productId);
      buyRepository.insert(buy);

      // addressesテーブルへ保存
      AddressEntity address = new AddressEntity();
      address.setBuyId(buy.getId());
      address.setPostNumber(orderDto.getPostNumber());
      address.setPrefecture(orderDto.getPrefecture());
      address.setCity(orderDto.getCity());
      address.setBlock(orderDto.getBlock());
      address.setBuilding(orderDto.getBuilding());
      address.setPhone(orderDto.getPhone());
      addressRepository.save(address);
    }
}