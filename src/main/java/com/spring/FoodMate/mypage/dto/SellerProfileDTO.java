package com.spring.FoodMate.mypage.dto;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("SellerProfileDTO")
public class SellerProfileDTO {
	private String slr_id;
	private String nickname;
	private String img_path;
	private String store_address; // 주소
	private MultipartFile profileImageInput; // 프로필 이미지
	
	// 생성자 (byr_id를 전달받는 생성자)
    public SellerProfileDTO() {
    }
	
    // 생성자 (byr_id를 전달받는 생성자)
    public SellerProfileDTO(String slr_id) {
        this.slr_id = slr_id;
    }

	public String getSlr_id() {
		return slr_id;
	}

	public void setSlr_id(String slr_id) {
		this.slr_id = slr_id;
	}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getStore_address() {
		return store_address;
	}

	public void setStore_address(String store_address) {
		this.store_address = store_address;
	}

	public MultipartFile getProfileImageInput() {
		return profileImageInput;
	}

	public void setProfileImageInput(MultipartFile profileImageInput) {
		this.profileImageInput = profileImageInput;
	}
	
	
}
