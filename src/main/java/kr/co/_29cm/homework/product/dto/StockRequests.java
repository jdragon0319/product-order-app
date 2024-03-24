package kr.co._29cm.homework.product.dto;



import kr.co._29cm.homework.product.exception.NotFoundStockRequestException;

import java.util.List;

import static kr.co._29cm.homework.product.exception.StockExceptionMessages.NOT_FOUND_STOCK;


public record StockRequests(
        List<StockRequest> stockRequestList
) {
    public static StockRequests of(List<StockRequest> stockRequestList) {
        return new StockRequests(stockRequestList);
    }

    public List<Long> createStockIdList() {
        return stockRequestList.stream()
                .map(StockRequest::stockId)
                .toList();
    }

    public StockRequest getStockRequest(Long stockId) {
        for (StockRequest request : stockRequestList) {
            if (request.stockId().equals(stockId)) {
                return request;
            }
        }
        throw new NotFoundStockRequestException(NOT_FOUND_STOCK);
    }
}
