package kr.co._29cm.homework.product.application;

import kr.co._29cm.homework.product.dto.StockRequests;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Component
public class StockServiceHandler {

    private final StockCommandService stockCommandService;

    public StockServiceHandler(StockCommandService stockCommandService) {
        this.stockCommandService = stockCommandService;
    }

    public void deductStocks(StockRequests requests) throws InterruptedException {
        while (true) {
            try {
                stockCommandService.deductStocks(requests);
                break;
            } catch (ObjectOptimisticLockingFailureException e) {
                Thread.sleep(100);
            }
        }
    }

    public void plusStocks(StockRequests requests) throws InterruptedException {
        while (true) {
            try {
                stockCommandService.plusStocks(requests);
                break;
            } catch (ObjectOptimisticLockingFailureException e) {
                Thread.sleep(100);
            }
        }
    }
}
