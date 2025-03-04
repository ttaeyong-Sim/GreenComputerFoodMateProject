package com.spring.FoodMate.mypage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.FoodMate.mypage.dao.ProfileDAO;
import com.spring.FoodMate.mypage.dto.ProfileDTO;
import com.spring.FoodMate.mypage.dto.SellerProfileDTO;

@Service("profileService")
//@Transactional(propagation=Propagation.REQUIRED)
public class ProfileService {
	@Autowired
	private ProfileDAO profileDAO;
	
	public void addNewBuyerProfile(String id) throws Exception{		
		ProfileDTO _profileDTO = new ProfileDTO(id);
		_profileDTO.setNickname("MateMember");
		_profileDTO.setImg_path("profile/defaultimg.jpg");
		_profileDTO.setBIO("");
		profileDAO.insertNewBuyerProfile(_profileDTO);
	}
	
	public void addNewSellerProfile(String id) throws Exception{		
		SellerProfileDTO _profileDTO = new SellerProfileDTO(id);
		_profileDTO.setNickname("MateStore1");
		_profileDTO.setImg_path("profile/defaultimg.jpg");
		_profileDTO.setStore_address("");
		profileDAO.insertNewSellerProfile(_profileDTO);
	}
	
	public ProfileDTO getBuyerProfile(String id) throws Exception{
		return profileDAO.getBuyerProfile(id);
	}
	
	public SellerProfileDTO getSellerProfile(String id) throws Exception{
		return profileDAO.getSellerProfile(id);
	}
	
	public boolean updateUserProfile(ProfileDTO _profileDTO) throws Exception{	
		if(profileDAO.updateUserProfile(_profileDTO) == 1) {
			return true;
		}
		return false;
	}
	
	public boolean updateSellerProfile(SellerProfileDTO _profileDTO) throws Exception{	
		if(profileDAO.updateSellerProfile(_profileDTO) == 1) {
			return true;
		}
		return false;
	}
}
