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
	
	// 판매자 본인이 재고관리페이지에서 볼때 쓰는거
	public List<ProductDTO> pdtListForStockBySlrId(String slr_id) {
		try {
			List<ProductDTO> searchList = sqlSession.selectList("mapper.product.listForStockBySlrSelf", slr_id);
			return searchList;
		} catch (DataAccessException e) {
			throw new DBException("ProductDAO.pdtListBySlrId 에러! 판매자 id : '" + slr_id + "'", e);
		}
	}
	
	//판매자 본인 마이페이지 best3상품용
	public List<ProductDTO> myBest3(String slr_id) {
		try {
			List<ProductDTO> searchList = sqlSession.selectList("mapper.product.myBest3", slr_id);
			return searchList;
		} catch (DataAccessException e) {
			throw new DBException("ProductDAO.myBest3 에러! 판매자 id : '" + slr_id + "'", e);
		}
	}

	// 판매자가 상품 상태 변경하는 메서드
	public int updateStatus(int pdt_id, String newStatus) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pdt_id", pdt_id);
		params.put("newStatus", newStatus);
		return sqlSession.update("mapper.product.changeStatus", params);
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
	
	public int getNewPdtId() throws DataAccessException {
		return sqlSession.selectOne("mapper.product.getLastInsertedPdtId");
	}
	
	public int insertProductDescImg(int pdt_id, String imgPath) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pdt_id", pdt_id);
		params.put("img_path", imgPath);
		return sqlSession.insert("mapper.product.newPdtDescImg", params);
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
	
	// 재고 변경 sql로 연결
	public int updateStock(int pdt_id, int stock) throws DataAccessException {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("pdt_id", pdt_id);
		params.put("stock", stock);
		return sqlSession.update("mapper.product.updateStock", params);
	}
	
	// 상품 상세설명 이미지 경로만 리스트로 가져오기
	public List<String> getDescImgPaths(int pdt_id) throws Exception {
	    return sqlSession.selectList("mapper.product.getDescImgPaths", pdt_id);
	}
}