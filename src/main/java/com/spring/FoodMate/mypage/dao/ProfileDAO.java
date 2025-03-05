package com.spring.FoodMate.mypage.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.FoodMate.mypage.dto.ProfileDTO;
import com.spring.FoodMate.mypage.dto.SellerProfileDTO;


@Repository("profileDAO")
public class ProfileDAO {
	@Autowired
	private SqlSession sqlSession;	
	
	public void insertNewBuyerProfile(ProfileDTO _profileDTO) throws DataAccessException{
		sqlSession.insert("mapper.profile.insertNewBuyerProfile", _profileDTO);
	}
	
	public void insertNewSellerProfile(SellerProfileDTO _profileDTO) throws DataAccessException{
		sqlSession.insert("mapper.profile.insertNewSellerProfile", _profileDTO);
	}
	
	public ProfileDTO getBuyerProfile(String slr_id) throws DataAccessException{
		return sqlSession.selectOne("mapper.profile.selectProfileByID", slr_id);
	}
	
	public SellerProfileDTO getSellerProfile(String id) throws DataAccessException{
		return sqlSession.selectOne("mapper.profile.selectSellerProfileByID", id);
	}
	
	public int updateUserProfile(ProfileDTO _profileDTO) throws DataAccessException{
		return sqlSession.update("mapper.profile.updateUserProfile", _profileDTO);
	}
	
	public int updateSellerProfile(SellerProfileDTO _profileDTO) throws DataAccessException{
		return sqlSession.update("mapper.profile.updateSellerProfile", _profileDTO);
	}

}
