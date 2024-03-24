package kr.co._29cm.homework.product.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import kr.co._29cm.homework.product.exception.SoldOutException;
import lombok.*;

import static kr.co._29cm.homework.product.exception.StockExceptionMessages.SOLD_OUT_STOCK;

@Getter
@Entity
@Builder
@Table(name = "stocks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Stock {
    @Id
    private Long productId;
    private Integer quantity;

    @Version
    private Long version;

    public static Stock of(Long id, Integer quantity) {
        return Stock.builder()
                .productId(id)
                .quantity(quantity)
                .build();
    }

    public void checkStocks(Integer inputQuantity) {
        if (this.quantity < inputQuantity) {
            throw new SoldOutException(SOLD_OUT_STOCK);
        }
    }

    public void deduct(Integer quantity) {
        this.quantity -= quantity;
    }

    public void plus(Integer quantity) {
        this.quantity += quantity;
    }
}
