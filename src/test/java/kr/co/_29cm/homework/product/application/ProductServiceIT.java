package kr.co._29cm.homework.product.application;

import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceIT {
    @Autowired
    private ProductCommandService productService;

    @Autowired
    @Qualifier("productRepository")
    private ProductRepository productRepository;

    @DisplayName("상품 데이터셋 초기화")
    @Test
    void init() throws IOException {
        productRepository.deleteAll();
        productService.init(new String[]{ResourceUtils.CLASSPATH_URL_PREFIX + "product/items.csv"});

        List<Product> result = productRepository.findAll();

        assertThat(result.size()).isEqualTo(19);
    }

}
