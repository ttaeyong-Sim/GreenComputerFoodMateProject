package com.spring.FoodMate.order.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.FoodMate.cart.dto.CartDTO;

@Component("orderrequestDTO")
public class OrderRequestDTO {
	@JsonProperty("cartIds")
    private List<Integer> cartIds; // 상품 ID 리스트
	private int directqty; // 바로구매 전용
	
    private String merchantUid;
    private String payMethod;
    private String pgId;
    private int used_point;
    private OrderAddressDTO orderAddress;

    public OrderRequestDTO() {}

    public List<Integer> getCartItems() { return cartIds; }
    public void setCartItems(List<Integer> cartIds) { this.cartIds = cartIds; }

    public String getMerchantUid() { return merchantUid; }
    public void setMerchantUid(String merchantUid) { this.merchantUid = merchantUid; }

    public String getPayMethod() { return payMethod; }
    public void setPayMethod(String payMethod) { this.payMethod = payMethod; }

    public String getPgId() { return pgId; }
    public void setPgId(String pgId) { this.pgId = pgId; }

    public OrderAddressDTO getOrderAddress() { return orderAddress; }
    public void setOrderAddress(OrderAddressDTO orderAddress) { this.orderAddress = orderAddress; }

	public int getUsed_point() {
		return used_point;
	}

	public void setUsed_point(int used_point) {
		this.used_point = used_point;
	}

	public int getDirectqty() {
		return directqty;
	}

	public void setDirectqty(int directqty) {
		this.directqty = directqty;
	}
	
	
	
	
}
