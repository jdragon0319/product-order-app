package kr.co._29cm.homework.order.dto;

import kr.co._29cm.homework.order.exception.InputOrderException;

import static kr.co._29cm.homework.order.exception.OrderExceptionMessages.INPUT_ORDER_REQUEST;

public record OrderProductRequest(
        Long productId,
        Integer quantity
) {
    public static OrderProductRequest of(Long productId, Integer quantity) {
        validate(productId, quantity);
        return new OrderProductRequest(productId, quantity);
    }

    private static void validate(Long productId, Integer quantity) {
        if (productId < 1 || quantity < 1) {
            throw new InputOrderException(INPUT_ORDER_REQUEST);
        }
    }

    public static OrderProductRequest endRequest() {
        return new OrderProductRequest(-1L, -1);
    }

    public boolean isEndOrder() {
        return productId.equals(-1L) && quantity.equals(-1);
    }
}
