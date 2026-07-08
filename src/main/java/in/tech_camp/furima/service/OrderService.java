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

    public ProductEntity findById(Long productId) {
      return productRepository.findById(productId)
                              .orElseThrow(() -> new IllegalArgumentException("Invalid product ID:" + productId));
    }

    public boolean isSoldout(Long productId) {
      return buyRepository.existsByProductId(productId) == null;
    }

    @Transactional
    public void createOrder(Long productId, Long userId, OrderDto orderDto) {
      ProductEntity product = findById(productId);

      // PayjpServiceを使って決済処理
      payjpService.charge(product.getPrice(), orderDto.getToken());

      // buysテーブルへ保存
      BuyEntity buy = new BuyEntity();
      buy.setUserId(userId);
      buy.setProductId(productId);
      BuyEntity savedBuy = buyRepository.insert(buy);

      // addressesテーブルへ保存
      AddressEntity address = new AddressEntity();
      address.setBuyId(savedBuy.getId());
      address.setPostNumber(orderDto.getPostNumber());
      address.setPrefecture(orderDto.getPrefecture());
      address.setCity(orderDto.getCity());
      address.setBlock(orderDto.getBlock());
      address.setBuilding(orderDto.getBuilding());
      address.setPhone(orderDto.getPhone());

      addressRepository.save(address);
    }
}