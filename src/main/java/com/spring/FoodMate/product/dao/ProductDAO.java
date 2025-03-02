package com.spring.FoodMate.product.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.FoodMate.common.exception.DBException;
import com.spring.FoodMate.product.dto.CategoryDTO;
import com.spring.FoodMate.product.dto.ProductDTO;
import com.spring.FoodMate.product.dto.ProductQnaDTO;
import com.spring.FoodMate.product.dto.ProductRatingDTO;

@Repository("productDAO")
public class ProductDAO {
	@Autowired
	private SqlSession sqlSession;
	
	public List<ProductDTO> pdtSearchList(String keyword) {
		try {
			List<ProductDTO> searchList = sqlSession.selectList("mapper.product.searchlist", keyword);
			return searchList;
		} catch (DataAccessException e) {
			throw new DBException("ProductDAO.pdtSerachList 에러! 검색어 : '" + keyword + "'", e);
		}	
	}
	
	public List<ProductDTO> pdtAllList() {
		try {
			List<ProductDTO> searchList = sqlSession.selectList("mapper.product.alllist");
			return searchList;
		} catch (DataAccessException e) {
			throw new DBException("ProductDAO.pdtAllList 에러!", e);
		}
	}
	
	// 판매자 당사자가 자기 상품 다 볼때 쓰는거. status 구분 안함.
	public List<ProductDTO> pdtListBySlrId(String slr_id) {
		try {
			List<ProductDTO> searchList = sqlSession.selectList("mapper.product.listBySlrSelf", slr_id);
			return searchList;
		} catch (DataAccessException e) {
			throw new DBException("ProductDAO.pdtListBySlrId 에러! 판매자 id : '" + slr_id + "'", e);
		}
	}
	
	// 판매자 id, 카테고리 id, 검색어로 검색하는거
	public List<ProductDTO> searchList(String slr_id, Integer category_id, String keyword, String sort) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("slr_id", slr_id);
        paramMap.put("category_id", category_id);
        paramMap.put("keyword", keyword);
        paramMap.put("sort", sort);
        try {
            return sqlSession.selectList("mapper.product.searchlist2", paramMap);
        } catch (DataAccessException e) {
        	throw new DBException("ProductDAO.searchList 에러! params: " + paramMap, e);
		}
    }
	
	public ProductDTO select1Pdt(int pdt_id) {
		try {
			ProductDTO searchDTO = sqlSession.selectOne("mapper.product.getPdt", pdt_id);
			return searchDTO;
		} catch (DataAccessException e) {
			throw new DBException("ProductDAO.select1Pdt 에러! pdt_id = '" + pdt_id + "'", e);
		}
	}
	
	public List<CategoryDTO> getGrandCategoryList() {
		try {
			List<CategoryDTO> list = sqlSession.selectList("mapper.product.getGrandCategory");
			return list;
		} catch (DataAccessException e) {
			throw new DBException("ProductDAO.getGrandCategoryList 에러!", e);
		}
	}
	
	public List<CategoryDTO> getChildCategoryList(int parent_id) {
		try {
			List<CategoryDTO> list = sqlSession.selectList("mapper.product.getChildCategory", parent_id);
			return list;
		} catch (DataAccessException e) {
			throw new DBException("ProductDAO.getChildCategoryList 에러! parent_id = '" + parent_id + "'", e);
		}
	}
	
	public List<CategoryDTO> getCategoryStep(int category_id) {
		try {
			List<CategoryDTO> list = sqlSession.selectList("mapper.product.getCategoryStep", category_id);
			return list;
		} catch (DataAccessException e) {
			throw new DBException("ProductDAO.getCategoryStep 에러! category_id = '" + category_id + "'", e);
		}
	}
	
	public String getNameById(int pdt_id) {
		try {
			String pdt_name = sqlSession.selectOne("mapper.product.getNameById", pdt_id);
			return pdt_name;
		} catch (DataAccessException e) {
			throw new DBException("ProductDAO.getNameById 에러! pdt_id = '" + pdt_id + "'", e);
		}
	}
	
	public int insertNewProduct(ProductDTO newPdt) {
		try {
			int result = sqlSession.insert("mapper.product.newPdt", newPdt);
			return result;
		} catch (DataAccessException e) {
			throw new DBException("ProductDAO.insertNewProduct 에러!" + newPdt.toLogString(), e);
		}
	}
	
	public int updateProduct(ProductDTO editPdt) {
		try {
			int result = sqlSession.update("mapper.product.editPdt", editPdt);
			return result;
		} catch (DataAccessException e) {
			throw new DBException("ProductDAO.updateProduct 에러!" + editPdt.toLogString(), e);
		}
	}
	
	public int deleteProduct(int pdt_id) {
		try {
			int result = sqlSession.delete("mapper.product.deletePdt", pdt_id);
			return result;
		} catch (DataAccessException e) {
			throw new DBException("ProductDAO.deleteProduct 에러! pdt_id = " + pdt_id, e);
		}
	}
	
	/*
	 * // 평점 추가 public int insertProductRating(ProductRatingDTO rating) { try { int
	 * result = sqlSession.insert("mapper.product.insertProductRating", rating);
	 * return result; } catch (DataAccessException e) { throw new
	 * DBException("ProductDAO.insertProductRating 에러!" + rating.toLogString(), e);
	 * } }
	 * 
	 * // 특정 상품의 평점 조회 public List<ProductRatingDTO> getProductRatings(int pdt_id) {
	 * try { List<ProductRatingDTO> ratings =
	 * sqlSession.selectList("mapper.product.getProductRatings", pdt_id); return
	 * ratings; } catch (DataAccessException e) { throw new
	 * DBException("ProductDAO.getProductRatings 에러! pdt_id = " + pdt_id, e); } }
	 * 
	 * // 평점 삭제 public int deleteProductRating(int rating_id) { try { int result =
	 * sqlSession.delete("mapper.product.deleteProductRating", rating_id); return
	 * result; } catch (DataAccessException e) { throw new
	 * DBException("ProductDAO.deleteProductRating 에러! rating_id = " + rating_id,
	 * e); } }
	 * 
	 * // 상품 문의 추가 public int insertProductQna(ProductQnaDTO qna) { try { int result
	 * = sqlSession.insert("mapper.product.insertProductQna", qna); return result; }
	 * catch (DataAccessException e) { throw new
	 * DBException("ProductDAO.insertProductQna 에러!" + qna.toLogString(), e); } }
	 * 
	 * // 특정 상품의 문의 조회 public List<ProductQnaDTO> getProductQna(int pdt_id) { try {
	 * List<ProductQnaDTO> qnas =
	 * sqlSession.selectList("mapper.product.getProductQna", pdt_id); return qnas; }
	 * catch (DataAccessException e) { throw new
	 * DBException("ProductDAO.getProductQna 에러! pdt_id = " + pdt_id, e); } }
	 * 
	 * // 상품 문의 삭제 public int deleteProductQna(int qna_id) { try { int result =
	 * sqlSession.delete("mapper.product.deleteProductQna", qna_id); return result;
	 * } catch (DataAccessException e) { throw new
	 * DBException("ProductDAO.deleteProductQna 에러! qna_id = " + qna_id, e); } }
	 */
}