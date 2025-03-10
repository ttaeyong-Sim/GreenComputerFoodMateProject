package com.spring.FoodMate.recipe.service;

import java.util.List;

import java.util.Map;

import com.spring.FoodMate.product.dto.CategoryDTO;
import com.spring.FoodMate.recipe.dto.RecipeCategoryDTO;
import com.spring.FoodMate.recipe.dto.RecipeDTO;
import com.spring.FoodMate.recipe.dto.RecipeIngredientDTO;
import com.spring.FoodMate.recipe.dto.RecipeQnaDTO;
import com.spring.FoodMate.recipe.dto.RecipeRatingDTO;
import com.spring.FoodMate.recipe.dto.RecipeStepDTO;

public interface RecipeService {

    // 레시피 등록 후 레시피 ID 반환
    int addRecipe(RecipeDTO recipe) throws Exception;

    // 재료 삽입
    void insertRecipeIngredients(List<RecipeIngredientDTO> ingredients) throws Exception;

    // 단계 삽입
    void insertRecipeSteps(List<RecipeStepDTO> steps) throws Exception;

    // 레시피 목록 조회
    List<RecipeDTO> selectRecipeList() throws Exception;
    
	//레시피 대분류 조회
    public List<RecipeDTO> selectRecipeByParent(int parent_id);
    
    //레시피 중분류 조회
    public List<RecipeDTO> selectRecipeByChild(int categoryId);
	
    //레시피 목록 평점순으로 조회
    public List<RecipeDTO> selectRecipeListByRating() throws Exception;
    
    // 레시피 목록 아이디 조회
    List<RecipeDTO> selectRecipeListByrID(String byr_id) throws Exception;
    
    // 레시피 하나 조회(순서랑 재료는 안들어있음)
    RecipeDTO recipe(int rcp_id) throws Exception;
    
    // 레시피 하나에 대한 재료만 조회
    List<RecipeIngredientDTO> recipeIngrds(int rcp_id) throws Exception;
    
    // 레시피 하나에 대한 순서만 조회
    List<RecipeStepDTO> recipeSteps(int rcp_id) throws Exception;
    
    //레시피 상세 조회에 필요한 모든 데이터 쌈싸주기
    List<RecipeIngredientDTO> getRecipeIngrd(int rcp_id);
    Map<String, Object> selectRecipeDetail(int rcp_id) throws Exception;
    
    List<RecipeCategoryDTO> getGrandCategoryList();
    // 중분류 카테고리 조회 (페이지 출력용)
    public List<RecipeCategoryDTO> selectChildCategoryList();
	
	List<RecipeCategoryDTO> getChildCategoryList(int category_id);
	
	List<RecipeCategoryDTO> categoryStep(int category_id);
	
	
	

    List<CategoryDTO> select_all_IngrdCategory();
    List<CategoryDTO> select_Child_IngrdCategory(int ingrd_category_id);
    
    public void addRecipeRating(RecipeRatingDTO ratingDTO) throws Exception;
    
    //후기 작성 시 중복 작성 확인 (별점 테러 방지)
    public int alreadyExistRating(int rcp_id, String byr_id) throws Exception;
    public List<RecipeRatingDTO> getRatingsByRecipeId(int rcp_id) throws Exception;
    
    public void updateRecipeRating(RecipeRatingDTO ratingDTO);
    
    public List<RecipeRatingDTO> selectRecipeReviewByrIDList(String byr_id) throws Exception;
    //후기 삭제
    public void deleteRecipeRating(RecipeRatingDTO ratingDTO);
    
    //질문 저장
    public void addRecipeQna(RecipeQnaDTO qnaDTO) throws Exception;
    
    //질문만 조회
    public List<RecipeQnaDTO> getQnasByRecipeId(int rcp_id) throws Exception;
    
    //답변만 조회
    public List<RecipeQnaDTO> getAnswersByRecipeId(int rcp_id) throws Exception;
   
    //답변 작성
    public void addRecipeQnaAnswer(RecipeQnaDTO answerDTO) throws Exception;
    
 // 레시피 검색
    public List<RecipeDTO> searchRecipeList(String keyword) throws Exception;
    
    // 레시피 조회수
    public void setViewsCount(int rcp_id) throws Exception;
    
        //레시피 질문,답변 수정
    public void updateRecipeQna(RecipeQnaDTO qnaDTO);
    
    //레시피 질문,답변 삭제
    public void deleteRecipeQna(RecipeQnaDTO qnaDTO);
    
    
}