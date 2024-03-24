package kr.co._29cm.homework.product.domain;

import kr.co._29cm.homework.product.exception.SoldOutException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ProductTest {


    @DisplayName("상품명에 컴마,쌍따옴표가 있는 상품 생성")
    @Test
    void containsComma() {
        Product product = Product.of("779049,\"[리퍼브/키친마켓] Fabrik Pottery Cup, Saucer (단품)\",10000,64");

        assertThat(product.getName()).isEqualTo("\"[리퍼브/키친마켓] Fabrik Pottery Cup, Saucer (단품)\"");
    }

    @DisplayName("재고가 부족하면 예외발생")
    @Test
    void checkStockSoldOutException() {
        Product product = Product.of(1L, "테스트상품1", 10000, 10);

        assertThatExceptionOfType(SoldOutException.class)
                .isThrownBy(() -> product.checkStock(15));
    }

}
