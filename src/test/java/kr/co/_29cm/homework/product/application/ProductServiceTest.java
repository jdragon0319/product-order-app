package kr.co._29cm.homework.product.application;

import jakarta.persistence.EntityNotFoundException;
import kr.co._29cm.homework.order.dto.OrderProductRequest;
import kr.co._29cm.homework.order.dto.OrderProductResponse;
import kr.co._29cm.homework.product.domain.FakeProductRepository;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.exception.SoldOutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ProductServiceTest {

    private final FakeProductRepository repository = new FakeProductRepository();
    private final ProductCommandService productCommandService = new ProductCommandService(repository);
    private final ProductQueryService productQueryService = new ProductQueryService(repository);

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @DisplayName("상품 데이터셋 초기화")
    @Test
    void init() throws IOException {
        productCommandService.init(new String[]{ResourceUtils.CLASSPATH_URL_PREFIX + "product/items.csv"});

        List<Product> result = repository.findAll();

        assertThat(result.size()).isEqualTo(19);
    }

    @DisplayName("등록된 상품이 없으면 예외 발생")
    @Test
    void getProductInfoEntityNotFound() {
        OrderProductRequest req = OrderProductRequest.of(1L, 1);

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> productQueryService.getOrderProductInfo(req));
    }

    @DisplayName("재고보다 많은 수량을 주문하면 예외 발생")
    @Test
    void getProductInfoSoldOut() {
        repository.save(Product.of(1L, "테스트상품1", 10000, 10));
        OrderProductRequest req = OrderProductRequest.of(1L, 11);

        assertThatExceptionOfType(SoldOutException.class)
                .isThrownBy(() -> productQueryService.getOrderProductInfo((req)));
    }

    @DisplayName("상품조회 성공")
    @Test
    void getProductInfoSuccess() {
        repository.save(Product.of(1L, "테스트상품1", 10000, 10));
        OrderProductRequest req = OrderProductRequest.of(1L, 3);

        OrderProductResponse result = productQueryService.getOrderProductInfo((req));

        assertThat(result.productName()).isEqualTo("테스트상품1");
        assertThat(result.productPrice()).isEqualTo(10000);
        assertThat(result.quantity()).isEqualTo(3);
    }
}
