package kr.co._29cm.homework.order.dto;

public record OrderProductResponse(
        Long productId,
        String productName,
        Integer productPrice,
        Integer quantity
) {
    public static OrderProductResponse of(Long id, String name, Integer price, Integer quantity) {
        return new OrderProductResponse(id, name, price, quantity);
    }
}
