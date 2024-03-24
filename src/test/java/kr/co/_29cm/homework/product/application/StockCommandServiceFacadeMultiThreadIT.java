package kr.co._29cm.homework.product.application;

import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductRepository;
import kr.co._29cm.homework.product.dto.StockRequest;
import kr.co._29cm.homework.product.dto.StockRequests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.*;

import static kr.co._29cm.homework.product.exception.StockExceptionMessages.SOLD_OUT_STOCK;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class StockCommandServiceFacadeMultiThreadIT {

    @Autowired
    private StockServiceFacade stockServiceFacade;

    @Autowired
    @Qualifier("productRepository")
    private ProductRepository repository;

    @DisplayName("재고 감소 성공")
    @Test
    void deductSuccess() throws InterruptedException {
        repository.save(Product.of(1L, "멀티스레드상품1", 30000, 100));
        StockRequests requests = StockRequests.of(
                List.of(
                        StockRequest.of(1L, 1)
                )
        );

        int count = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 1; i <= count; i++) {
            executorService.submit(() -> {
                // 상품의 총 재고 100개, 1개씩 주문시도
                try {
                    stockServiceFacade.deductStocks(requests);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

        Product product = repository.findById(1L).get();
        assertThat(product.getQuantity()).isEqualTo(0);
    }

    @DisplayName("재고가 소진되고 재고감소를 시도하면 SoldOutException 예외 발생")
    @Test
    void soldOut() throws InterruptedException {
        repository.save(Product.of(2L, "멀티스레드상품2", 30000, 100));
        StockRequests requests = StockRequests.of(
                List.of(
                        StockRequest.of(2L, 1)
                )
        );

        assertThatExceptionOfType(ExecutionException.class)
                .isThrownBy(() -> {
                    int count = 130;
                    ExecutorService executorService = Executors.newFixedThreadPool(50);
                    CountDownLatch countDownLatch = new CountDownLatch(count);
                    for (int i = 1; i <= count; i++) {
                        try {
                            Future<?> submit = executorService.submit(() -> {
                                // 상품의 총 재고 100개, 1개씩 주문시도
                                try {
                                    stockServiceFacade.deductStocks(requests);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                } finally {
                                    countDownLatch.countDown();
                                }
                            });
                            Object o = submit.get();
                        } catch (ExecutionException e) {
                            System.out.println("TEST-- SOLDOUT");
                            System.out.println(e);
                            throw e;
                        }
                    }
                    countDownLatch.await();
                })
                .withMessageContaining(SOLD_OUT_STOCK);
    }

}
