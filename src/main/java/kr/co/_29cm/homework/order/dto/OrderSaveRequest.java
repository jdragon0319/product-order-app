package kr.co._29cm.homework.order.dto;

import java.util.List;

public record OrderSaveRequest(
        List<OrderProductResponse> orderProductResponseList
) {
    public static OrderSaveRequest of(List<OrderProductResponse> responseList) {
        return new OrderSaveRequest(responseList);
    }

    public boolean hasNotOrderProduct() {
        return orderProductResponseList.isEmpty();
    }
}
