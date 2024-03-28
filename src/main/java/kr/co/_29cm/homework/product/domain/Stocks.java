package kr.co._29cm.homework.product.domain;


import jakarta.persistence.EntityNotFoundException;
import kr.co._29cm.homework.product.dto.StockRequest;
import kr.co._29cm.homework.product.dto.StockRequests;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static kr.co._29cm.homework.product.exception.StockExceptionMessages.EMPTY_STOCK;
import static kr.co._29cm.homework.product.exception.StockExceptionMessages.NOT_MATCH_STOCK_SIZE;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stocks {
    private List<Stock> stockList;

    public static Stocks of(List<Stock> stockList) {
        if (stockList.isEmpty()) throw new EntityNotFoundException(EMPTY_STOCK);
        return new Stocks(stockList);
    }

    public void deductStocks(StockRequests requests) {
        validate(requests.stockRequestList());
        for (Stock stock : stockList) {
            StockRequest request = requests.getStockRequest(stock.getId());
            stock.checkStocks(request.quantity());
            stock.deduct(request.quantity());
        }
    }

    public void validate(List<StockRequest> stockRequestList) {
        if (stockList.size() != stockRequestList.size()) {
            throw new IllegalStateException(NOT_MATCH_STOCK_SIZE);
        }
    }

    public void plusStocks(StockRequests requests) {
        for (Stock stock : stockList) {
            StockRequest request = requests.getStockRequest(stock.getId());
            stock.plus(request.quantity());
        }
    }
}
