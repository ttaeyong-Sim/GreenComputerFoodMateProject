package com.spring.FoodMate.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.FoodMate.member.dto.BuyerDTO;
import com.spring.FoodMate.member.dto.SellerDTO;

@Repository("adminDAO")
public class AdminDAO {
	@Autowired
	private SqlSession sqlSession;	
	
    public List<BuyerDTO> getAdminBuyerInfo(String status) throws DataAccessException {
    	Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("status", status);
        return sqlSession.selectList("mapper.member.selectAdminBuyerInfo", paramMap);
    }
}
