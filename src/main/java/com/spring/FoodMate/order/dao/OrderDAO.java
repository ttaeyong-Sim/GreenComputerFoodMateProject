package com.spring.FoodMate.order.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.FoodMate.cart.dto.CartDTO;
import com.spring.FoodMate.order.dto.OrderAddressDTO;
import com.spring.FoodMate.order.dto.OrderDTO;
import com.spring.FoodMate.order.dto.OrderDTOoutput;
import com.spring.FoodMate.order.dto.OrderDetailDTO;
import com.spring.FoodMate.order.dto.OrderDetailDTOoutput;
import com.spring.FoodMate.order.dto.OrderPaymentDTO;
import com.spring.FoodMate.product.dto.ProductDTO;

@Repository("orderDAO")
public class OrderDAO {
	@Autowired
	private SqlSession sqlSession;	
	
    public List<CartDTO> getCartItemsByIds(List<Integer> cartIdList) throws DataAccessException {
        return sqlSession.selectList("mapper.order.getCartItemsByIds", cartIdList);
    }
    
    public void setOrderList(OrderDTO order) throws DataAccessException {
    	sqlSession.insert("mapper.order.insertOrderList", order);
    }
    
    public int getOrdId(Map<String, Object> order) throws DataAccessException {
    	return sqlSession.selectOne("mapper.order.selectOrdId",order);
    }
    
    public void insertOrderDetail(OrderDetailDTO orderdetail) throws DataAccessException {
    	sqlSession.insert("mapper.order.insertOrderDetail", orderdetail);
    }
    
    public void insertOrderAddress(OrderAddressDTO orderAddress) throws DataAccessException {
    	sqlSession.insert("mapper.order.insertOrderAddress", orderAddress);
    }
    
    public void insertOrderPayment(OrderPaymentDTO orderPayment) throws DataAccessException {
    	sqlSession.insert("mapper.order.insertOrderPayment", orderPayment);
    }
    
    // 여기서부터 마이페이지에 쓸거. 딴데도 쓸거면 어디에썼는지 적기
    // 출력 전용임
    public List<OrderDTOoutput> findOrdersByBuyer(String byr_id) throws DataAccessException {
        return sqlSession.selectList("mapper.order.findOrdersByBuyer", byr_id);
    }
    
    public List<OrderDTOoutput> findReturnExchangeListByByrId(String byr_id) throws DataAccessException {
        return sqlSession.selectList("mapper.order.findReturnExchangeListByByrId", byr_id);
    }
    
    public List<OrderDTOoutput> findRefundListByByrId(String byr_id) throws DataAccessException {
        return sqlSession.selectList("mapper.order.findRefundListByByrId", byr_id);
    }
    
    public List<OrderDTOoutput> findCancelOrdersByBuyer(String byr_id) throws DataAccessException {
        return sqlSession.selectList("mapper.order.findCancelOrdersByBuyer", byr_id);
    }
    
    public List<OrderDetailDTOoutput> findOrderDetailsByBuyer(String byr_id) throws DataAccessException {
    	return sqlSession.selectList("mapper.order.findOrderDetailsByBuyer", byr_id);
    }

    public List<OrderDTOoutput> findOrdersBySeller(String slr_id, int ord_stat) throws DataAccessException {
    	Map<String, Object> params = new HashMap<>();
        params.put("slr_id", slr_id);
        params.put("ord_stat", ord_stat);
        return sqlSession.selectList("mapper.order.findOrdersBySeller", params);
    }
    
    // 판매자 마이페이지 메인화면 배송대기 주문들, 주문상세는 따로 넣어줘야함
    public List<OrderDTOoutput> mypageMainOrdList(String slr_id) throws DataAccessException {
        return sqlSession.selectList("mapper.order.mypageMainOrdList", slr_id);
    }

    public List<OrderDetailDTOoutput> findOrderDetailsByOrderId(int ord_id) throws DataAccessException {
        return sqlSession.selectList("mapper.order.findOrderDetailsByOrderId", ord_id);
    }
    
    public OrderDTOoutput findOrderByOrderId(int ord_id) throws DataAccessException {
    	return sqlSession.selectOne("mapper.order.findOrderByOrderId", ord_id);
    }
    
    public OrderAddressDTO getOrderAddressByOrdId(int ord_id) throws DataAccessException {
    	return sqlSession.selectOne("mapper.order.findAddressByOrderId", ord_id);
    }
    
    public void updateWaybill(int ord_id, String del_code, String waybill_num) throws DataAccessException {
        Map<String, Object> params = new HashMap<>();
        params.put("ord_id", ord_id);
        params.put("del_code", del_code);
        params.put("waybill_num", waybill_num);
        sqlSession.update("mapper.order.updateWaybill", params);
    }
    
    public List<ProductDTO> getOrderedProductsByBuyerId(String buyerId) throws DataAccessException {
        return sqlSession.selectList("mapper.order.getOrderedProductsByBuyerId", buyerId);
    }
    
    public int isOwnOrder(String userId, int ord_id, String del_code, String waybill_num) throws DataAccessException {
    	Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("ord_id", ord_id);
        params.put("del_Code", del_code);
        params.put("waybill_Num", waybill_num);
    	return sqlSession.selectOne("mapper.order.isOwnOrder", params);
    }
    
    public int updateOrderStatus(int ord_id, String del_code, String waybill_num, int ord_stat) throws DataAccessException {
    	Map<String, Object> params = new HashMap<>();
    	params.put("ord_id", ord_id);
        params.put("del_Code", del_code);
        params.put("waybill_Num", waybill_num);
        params.put("ord_stat", ord_stat);
    	return sqlSession.update("mapper.order.updateOrderStatus", params);
    }
    
    public int updateOrderStatusByOrdCode(String slr_id, String ord_code, int ord_stat) throws DataAccessException {
    	Map<String, Object> params = new HashMap<>();
        params.put("slr_id", slr_id);
        params.put("ord_code", ord_code);
        params.put("ord_stat", ord_stat);
    	return sqlSession.update("mapper.order.updateOrderStatusByOrdCode", params);
    }
    
    // pdt_id랑 qty를 주면 상품테이블의 pdt_id로 검색해서 qty 만큼 빼는 메서드
    // 반환값이 1이면 재고차감이 된거고 0이면 안된거(주요 예외원인은 재고부족)
    public int reducePdtStock(int pdt_id, int qty) throws DataAccessException {
    	Map<String, Object> params = new HashMap<>();
        params.put("pdt_id", pdt_id);
        params.put("qty", qty);
        return sqlSession.update("mapper.order.reducePdtStock", params);
    }
}