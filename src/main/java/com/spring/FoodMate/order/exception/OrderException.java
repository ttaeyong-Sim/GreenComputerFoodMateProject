package com.spring.FoodMate.order.exception;

public class OrderException extends RuntimeException {
	private static final long serialVersionUID = 55L;
	
	private int errorCode;
	// 101 : ord_id로 FoodMate_Order 테이블 검색해봤는데 암것도 없었음
	// 102 : 주문한대로 order2로 넘기려다 트랜잭션 에러 발생함
	// 103 : 재고 이상으로 주문하려고 하면 발생함
	
	public int getErrorCode() {
		return errorCode;
	}
	
    public OrderException(int errorCode) {
        this.errorCode = errorCode;
    }

    public OrderException(String message, Throwable cause) {
        super(message, cause);
    }
}