package kr.co._29cm.homework.order.dto;

public record OrderProductSaveResponse(
        String productName,
        Integer price,
        Integer quantity
) {
    public static OrderProductSaveResponse of(String productName, Integer price, Integer quantity) {
        return new OrderProductSaveResponse(productName, price, quantity);
    }
}
