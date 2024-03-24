package kr.co._29cm.homework.product.dto;

public record StockRequest(
        Long stockId,
        Integer quantity
) {
    public static StockRequest of(Long productId, Integer quantity) {
        return new StockRequest(productId, quantity);
    }

}
