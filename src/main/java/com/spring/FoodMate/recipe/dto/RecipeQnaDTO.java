package com.spring.FoodMate.recipe.dto;

import java.sql.Date;

public class RecipeQnaDTO {
	private int cmt_rcp_qna_id;  // 댓글 ID
	private int rcp_id;  // 레시피 ID
    private String byr_id;  // 작성자 ID
    private String comments;  // 댓글
    private int parent_id;
    private Date create_date;  // 작성일
    private Date update_date;  // 수정일
    
    //FOODMATE_buyer_PROFILE과 조인
	private String nickname;     // JOIN을 통해 가져온 NICKNAME
    private String img_path;      // JOIN을 통해 가져온 IMG_PATH
    
    
	public int getCmt_rcp_qna_id() {
		return cmt_rcp_qna_id;
	}
	public void setCmt_rcp_qna_id(int cmt_rcp_qna_id) {
		this.cmt_rcp_qna_id = cmt_rcp_qna_id;
	}
	public int getRcp_id() {
		return rcp_id;
	}
	public void setRcp_id(int rcp_id) {
		this.rcp_id = rcp_id;
	}
	public String getByr_id() {
		return byr_id;
	}
	public void setByr_id(String byr_id) {
		this.byr_id = byr_id;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
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
	
}
