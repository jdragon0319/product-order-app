package kr.co._29cm.homework.product.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product {
    @Id
    private Long id;
    private String name;
    private Integer price;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Stock stock;

    public static Product of(String productInfo) {
        String[] splitInputProductInfo = productInfo.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        return Product.builder()
                .id(Long.valueOf(splitInputProductInfo[0]))
                .name(splitInputProductInfo[1])
                .price(Integer.parseInt(splitInputProductInfo[2]))
                .stock(
                        Stock.of(
                                Long.valueOf(splitInputProductInfo[0]),
                                Integer.parseInt(splitInputProductInfo[3])
                        )
                )
                .build();
    }

    public static Product of(Long id, String name, int price, int quantity) {
        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .stock(Stock.builder()
                        .id(id)
                        .quantity(quantity)
                        .build())
                .build();
    }

    public void checkStock(Integer inputQuantity) {
        stock.checkStocks(inputQuantity);
    }

    public Integer getQuantity() {
        return stock.getQuantity();
    }

    @Override
    public String toString() {
        return id + "   " + name + "   " + price + "   " + stock.getQuantity();
    }

}
