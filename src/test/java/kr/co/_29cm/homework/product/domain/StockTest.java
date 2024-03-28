package kr.co._29cm.homework.product.domain;


import kr.co._29cm.homework.product.exception.SoldOutException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class StockTest {

    @DisplayName("입력한 주문수량이 재고보다 많으면 예외반환")
    @Test
    void soldOut() {
        Stock stock = Stock.of(1L, 100);

        assertThatExceptionOfType(SoldOutException.class)
                .isThrownBy(() -> stock.checkStocks(103));
    }

    @DisplayName("재고 감소")
    @Test
    void deduct() {
        Stock stock = Stock.of(1L, 100);

        stock.deduct(33);

        assertThat(stock.getQuantity()).isEqualTo(67);
    }

    @DisplayName("재고 증가")
    @Test
    void plus() {
        Stock stock = Stock.of(1L, 100);

        stock.plus(33);

        assertThat(stock.getQuantity()).isEqualTo(133);
    }

}
