package com.spring.FoodMate.recipe.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.spring.FoodMate.product.dto.CategoryDTO;
import com.spring.FoodMate.recipe.dto.RecipeCategoryDTO;
import com.spring.FoodMate.recipe.dto.RecipeDTO;
import com.spring.FoodMate.recipe.dto.RecipeIngredientDTO;
import com.spring.FoodMate.recipe.dto.RecipeQnaDTO;
import com.spring.FoodMate.recipe.dto.RecipeRatingDTO;
import com.spring.FoodMate.recipe.dto.RecipeStepDTO;

public interface RecipeDAO {

    // 레시피 등록
    void insertRecipe(RecipeDTO recipe) throws Exception;

    // 레시피 ID 반환 (마지막으로 삽입된 레시피 ID)
    int selectLastInsertedRecipeId() throws Exception;

    // 재료 삽입
    void insertRecipeIngredients(RecipeIngredientDTO ingredient) throws Exception;

    // 단계 삽입
    void insertRecipeSteps(RecipeStepDTO step) throws Exception;

    // 레시피 목록 조회
    List<RecipeDTO> selectRecipeList() throws Exception;
    
    //레시피 대분류 조회
    public List<RecipeDTO> selectRecipeByParent(int parent_id);
    
    //레시피 중분류 조회
    public List<RecipeDTO> selectRecipeByChild(int categoryId);
    
    //레시피 목록 평점순으로 조회
    public List<RecipeDTO> selectRecipeListByRating() throws Exception;
    
    // 레시피 아이디 목록 조회
    List<RecipeDTO> selectRecipeListByrID(String byr_Id) throws Exception;
    
    // 레시피 상세 조회
    RecipeDTO selectRecipeDetail(int rcp_Id) throws Exception;
    List<RecipeIngredientDTO> selectIngredientDetail(int rcp_Id) throws DataAccessException;
    List<RecipeStepDTO> selectStepDetail(int rcp_Id) throws DataAccessException;
    
    List<RecipeCategoryDTO> getGrandCategoryList();
    // 중분류 카테고리 조회 (페이지 출력용)
    public List<RecipeCategoryDTO> selectChildCategoryList();
	
	List<RecipeCategoryDTO> getChildCategoryList(int parent_id); 
	
	List<RecipeCategoryDTO> getCategoryStep(int category_id);
	
	List<CategoryDTO> select_all_IngrdCategory();
	
	List<CategoryDTO> select_Child_IngrdCategory(int parentIngrdCategoryId);
	
	//레시피 후기 작성
	public void addRecipeRating(RecipeRatingDTO ratingDTO) throws Exception;
		
	//후기 작성 시 중복 작성 확인 (별점 테러 방지)
	public int alreadyExistRating(int rcpId, String byrId) throws Exception;
	
	//레시피 후기 출력
	public List<RecipeRatingDTO> getRatingsByRecipeId(int rcp_id) throws Exception;
	
	//레시피 후기 수정
	public void updateRecipeRating(RecipeRatingDTO ratingDTO);

	public List<RecipeRatingDTO> selectRecipeReviewByrIDList(String byr_id) throws Exception;
	
	//레시피 후기 삭제
	public void deleteRecipeRating(RecipeRatingDTO ratingDTO);
	
	//레시피 질문 저장
	public void addRecipeQna(RecipeQnaDTO qnaDTO) throws Exception;
	
	//레시피 질문만 조회
	public List<RecipeQnaDTO> getQnasByRecipeId(int rcp_id) throws Exception;
	
	//레시피 답변만 조회
	public List<RecipeQnaDTO> getAnswersByRecipeId(int rcp_id) throws Exception;
	
	//레시피 답변 작성
	public void addRecipeQnaAnswer(RecipeQnaDTO answerDTO) throws Exception;
	
	// 레시피 목록 검색
    public List<RecipeDTO> searchRecipeList(String keyword) throws Exception;
    
    // 레시피 조회수
    public void updateRecipeViews(int rcp_id) throws Exception;
    
    	//레시피 질문,답변 수정
	public void updateRecipeQna(RecipeQnaDTO qnaDTO);
	
	//레시피 질문,답변 삭제
	public void deleteRecipeQna(RecipeQnaDTO qnaDTO);
	
}