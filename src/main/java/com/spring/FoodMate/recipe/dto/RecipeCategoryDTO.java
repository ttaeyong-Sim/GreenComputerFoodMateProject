package com.spring.FoodMate.recipe.dto;

import org.springframework.stereotype.Component;

@Component("recipeCategoryDTO")
public class RecipeCategoryDTO {
	
    private Integer category_id;  // 카테고리 설정을 안할수도있으므로 null 받을수있는 Integer형으로 함
    private String name;
    private Integer parent_id;

    // 기본 생성자
    public RecipeCategoryDTO() {}

    // 생성자 (categoryId, name, parentId를 전달받는 생성자)
    public RecipeCategoryDTO(Integer category_id, String name, Integer parent_id) {
        this.category_id = category_id;
        this.name = name;
        this.parent_id = parent_id;
    }

    public Integer getCategory_id() {		return category_id;	}
	public void setCategory_id(Integer category_id) {		this.category_id = category_id;	}
	public String getName() {		return name;	}
	public void setName(String name) {		this.name = name;	}
	public Integer getParent_id() {		return parent_id;	}
	public void setParent_id(Integer parent_id) {		this.parent_id = parent_id;	}
	
	public String toLogString() {
        return "RecipeCategoryVO [category_Id=" + category_id + ", name=" + name + ", parent_Id=" + parent_id + "]";
    }
}