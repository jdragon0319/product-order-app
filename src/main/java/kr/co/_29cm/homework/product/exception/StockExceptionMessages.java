package kr.co._29cm.homework.product.exception;

public class StockExceptionMessages {
    public static final String EMPTY_STOCK = "등록된 재고가 없습니다.";
    public static final String NOT_FOUND_STOCK = "등록된 재고가 없습니다. %d";
    public static final String NOT_MATCH_STOCK_SIZE = "조회한 재고와 주문 상품의 숫자가 안맞습니다.";
    public static final String SOLD_OUT_STOCK = "SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.";
}
