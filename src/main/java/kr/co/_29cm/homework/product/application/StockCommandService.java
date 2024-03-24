package kr.co._29cm.homework.product.application;


import kr.co._29cm.homework.product.domain.StockRepository;
import kr.co._29cm.homework.product.domain.Stocks;
import kr.co._29cm.homework.product.dto.StockRequests;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockCommandService {

    private final StockRepository stockRepository;

    public StockCommandService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deductStocks(StockRequests requests) {
        Stocks stocks = Stocks.of(stockRepository.findAllByIdIn(requests.createStockIdList()));
        stocks.deductStocks(requests);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void plusStocks(StockRequests requests) {
        Stocks stocks = Stocks.of(stockRepository.findAllByIdIn(requests.createStockIdList()));
        stocks.plusStocks(requests);
    }
}
