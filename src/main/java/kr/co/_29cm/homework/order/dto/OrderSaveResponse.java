package kr.co._29cm.homework.order.dto;

import lombok.Builder;

import java.util.List;

import static kr.co._29cm.homework.order.domain.FeeConstants.DELIVERY_FEE;
import static kr.co._29cm.homework.order.domain.FeeConstants.LIMIT_CHARGED_DELIVERY_FEE;

public record OrderSaveResponse(
        Long orderId,
        List<OrderProductSaveResponse> orderProductSaveResponseList
) {
    @Builder
    public OrderSaveResponse {
    }

    public static OrderSaveResponse of(Long orderId, List<OrderProductSaveResponse> orderProductSaveResponseList) {
        return new OrderSaveResponse(orderId, orderProductSaveResponseList);
    }

    public String getOrderInfo() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < orderProductSaveResponseList.size(); i++) {
            OrderProductSaveResponse orderProductSaveResponse = orderProductSaveResponseList.get(i);
            builder.append(orderProductSaveResponse.productName())
                    .append(" - ")
                    .append(orderProductSaveResponse.quantity())
                    .append("개")
                    .append("\n");
        }
        return builder.substring(0, builder.length() - 1);
    }

    public Integer calculateProductPrice() {
        return orderProductSaveResponseList.stream()
                .map(response -> response.price() * response.quantity())
                .reduce(Integer::sum)
                .get();
    }

    public Integer calculateTotalPrice() {
        Integer sumProductPrice = calculateProductPrice();
        if (sumProductPrice < LIMIT_CHARGED_DELIVERY_FEE) {
            return sumProductPrice + DELIVERY_FEE;
        }
        return sumProductPrice;
    }

    public Integer calculateShippingCharge() {
        if (calculateProductPrice() < LIMIT_CHARGED_DELIVERY_FEE)
            return DELIVERY_FEE;
        return 0;
    }
}
