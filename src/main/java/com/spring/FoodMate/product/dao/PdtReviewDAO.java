package com.spring.FoodMate.product.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.FoodMate.common.exception.DBException;
import com.spring.FoodMate.product.dto.ProductQnaDTO;
import com.spring.FoodMate.product.dto.ProductRatingDTO;

@Repository("pdtReviewDAO")
public class PdtReviewDAO {
	@Autowired
	private SqlSession sqlSession;
	
	// 상품 후기 등록
	public int insertProductRating(ProductRatingDTO newProductRating) {
	    try {
	        System.out.println("상품 후기 등록 시작: " + newProductRating.toLogString());  // 로그 추가
	        int result = sqlSession.insert("mapper.product.insertProductRating", newProductRating);
	        System.out.println("상품 후기 등록 성공: " + result);  // 성공 로그
	        return result;
	    } catch (DataAccessException e) {
	        System.out.println("상품 후기 등록 실패: " + newProductRating.toLogString());  // 실패 로그
	        throw new DBException("ProductDAO.insertProductRating 에러!" + newProductRating.toLogString(), e);
	    }
	}
	
	// 상품 후기 수정
	public int updateProductRating(ProductRatingDTO productRatingDTO) {
	    try {
	        System.out.println("상품 후기 수정 시작: " + productRatingDTO.toLogString());  // 로그 추가
	        int result = sqlSession.update("mapper.product.updateProductRating", productRatingDTO);
	        System.out.println("상품 후기 수정 성공: " + result);  // 성공 로그
	        return result;
	    } catch (DataAccessException e) {
	        System.out.println("상품 후기 수정 실패: " + productRatingDTO.toLogString());  // 실패 로그
	        throw new DBException("ProductDAO.updateProductRating 에러!" + productRatingDTO.toLogString(), e);
	    }
	}
	
	// 상품 후기 삭제
	public int deleteProductRating(int cmt_pdt_rating_id) {
	    try {
	        System.out.println("상품 후기 삭제 시작: cmt_pdt_rating_id = " + cmt_pdt_rating_id);  // 로그 추가
	        int result = sqlSession.delete("mapper.product.deleteProductRating", cmt_pdt_rating_id);
	        System.out.println("상품 후기 삭제 성공: " + result);  // 성공 로그
	        return result;
	    } catch (DataAccessException e) {
	        System.out.println("상품 후기 삭제 실패: cmt_pdt_rating_id = " + cmt_pdt_rating_id);  // 실패 로그
	        throw new DBException("ProductDAO.deleteProductRating 에러! cmt_pdt_rating_id = '" + cmt_pdt_rating_id + "'", e);
	    }
	}
	
	// 이 구매자가 이 상품 샀음? 안샀으면 0, 샀으면 산횟수만큼 숫자출력
	public int isByrConfirmPdt(String byr_id, int pdt_id) throws DataAccessException {
		ProductRatingDTO param = new ProductRatingDTO();
		param.setByr_id(byr_id);
		param.setPdt_id(pdt_id);
		return sqlSession.selectOne("mapper.product.isByrConfirmPdt", param);
	}
	
	// 이 구매자가 상품 리뷰 썼었음?
	public ProductRatingDTO isReviewed(String byr_id, int pdt_id) throws DataAccessException {
		ProductRatingDTO param = new ProductRatingDTO();
		param.setByr_id(byr_id);
		param.setPdt_id(pdt_id);
		return sqlSession.selectOne("mapper.product.isReviewed", param);
	}
	
	// 상품 후기 조회 (상품 ID에 해당하는 후기들)
	public List<ProductRatingDTO> getPdtReviews(int pdt_id) {
	    try {
	        System.out.println("상품 후기 조회 시작: pdt_id = " + pdt_id);  // 로그 추가
	        List<ProductRatingDTO> ratingsList = sqlSession.selectList("mapper.product.getProductRatings", pdt_id);
	        System.out.println("상품 후기 조회 성공: " + ratingsList.size() + "개 후기");  // 성공 로그
	        return ratingsList;
	    } catch (DataAccessException e) {
	        System.out.println("상품 후기 조회 실패: pdt_id = " + pdt_id);  // 실패 로그
	        throw new DBException("ProductDAO.getProductRatings 에러! pdt_id = '" + pdt_id + "'", e);
	    }
	}

	// 상품ID 넣으면 평점 평균 반환, 후기하나도없으면 null반환
	public Double getAvgRatingByPdtId(int pdt_id) throws DataAccessException {
		return sqlSession.selectOne("mapper.product.selectAvgRatingByProductId", pdt_id);
	}
	
	
	
	
	
	
	// made by 류세환, 사용X중

	// 상품 후기 ID로 조회하는 메소드
	public ProductRatingDTO getProductRatingById(int rating_id) {
	    try {
	        System.out.println("상품 후기 조회 시작: rating_id = " + rating_id);  // 로그 추가
	        ProductRatingDTO productRating = sqlSession.selectOne("mapper.product.getProductRatingById", rating_id);
	        System.out.println("상품 후기 조회 성공: " + productRating);  // 성공 로그
	        return productRating;
	    } catch (DataAccessException e) {
	        System.out.println("상품 후기 조회 실패: rating_id = " + rating_id);  // 실패 로그
	        throw new DBException("ProductDAO.getProductRatingById 에러! rating_id = " + rating_id, e);
	    }
	}

	// 상품 문의 등록
	public int insertProductQna(ProductQnaDTO newProductQna) {
	    try {
	        System.out.println("상품 문의 등록 시작: " + newProductQna.toLogString());  // 로그 추가
	        int result = sqlSession.insert("mapper.product.insertProductQna", newProductQna);
	        System.out.println("상품 문의 등록 성공: " + result);  // 성공 로그
	        return result;
	    } catch (DataAccessException e) {
	        System.out.println("상품 문의 등록 실패: " + newProductQna.toLogString());  // 실패 로그
	        throw new DBException("ProductDAO.insertProductQna 에러!" + newProductQna.toLogString(), e);
	    }
	}

	// 상품 문의 조회 (상품 ID에 해당하는 문의들)
	public List<ProductQnaDTO> getProductQna(int pdt_id) {
	    try {
	        System.out.println("상품 문의 조회 시작: pdt_id = " + pdt_id);  // 로그 추가
	        List<ProductQnaDTO> qnaList = sqlSession.selectList("mapper.product.getProductQna", pdt_id);
	        System.out.println("상품 문의 조회 성공: " + qnaList.size() + "개 문의");  // 성공 로그
	        return qnaList;
	    } catch (DataAccessException e) {
	        System.out.println("상품 문의 조회 실패: pdt_id = " + pdt_id);  // 실패 로그
	        throw new DBException("ProductDAO.getProductQna 에러! pdt_id = '" + pdt_id + "'", e);
	    }
	}

	// 상품 문의 ID로 단일 문의 조회
	public ProductQnaDTO getProductQnaById(int qna_id) {
	    try {
	        System.out.println("상품 문의 조회 시작: qna_id = " + qna_id);  // 로그 추가
	        ProductQnaDTO productQna = sqlSession.selectOne("mapper.product.getProductQnaById", qna_id);
	        System.out.println("상품 문의 조회 성공: " + productQna);  // 성공 로그
	        return productQna;
	    } catch (DataAccessException e) {
	        System.out.println("상품 문의 조회 실패: qna_id = " + qna_id);  // 실패 로그
	        throw new DBException("ProductDAO.getProductQnaById 에러! qna_id = '" + qna_id + "'", e);
	    }
	}

	// 상품 문의 삭제
	public int deleteProductQna(int cmt_pdt_qna_id) {
	    try {
	        System.out.println("상품 문의 삭제 시작: cmt_pdt_qna_id = " + cmt_pdt_qna_id);  // 로그 추가
	        int result = sqlSession.delete("mapper.product.deleteProductQna", cmt_pdt_qna_id);
	        System.out.println("상품 문의 삭제 성공: " + result);  // 성공 로그
	        return result;
	    } catch (DataAccessException e) {
	        System.out.println("상품 문의 삭제 실패: cmt_pdt_qna_id = " + cmt_pdt_qna_id);  // 실패 로그
	        throw new DBException("ProductDAO.deleteProductQna 에러! cmt_pdt_qna_id = '" + cmt_pdt_qna_id + "'", e);
	    }
	}

	// 상품 문의 수정
	public int updateProductQna(ProductQnaDTO productQnaDTO) {
	    try {
	        System.out.println("상품 문의 수정 시작: " + productQnaDTO.toLogString());  // 로그 추가
	        int result = sqlSession.update("mapper.product.updateProductQna", productQnaDTO);
	        System.out.println("상품 문의 수정 성공: " + result);  // 성공 로그
	        return result;
	    } catch (DataAccessException e) {
	        System.out.println("상품 문의 수정 실패: " + productQnaDTO.toLogString());  // 실패 로그
	        throw new DBException("ProductDAO.updateProductQna 에러!" + productQnaDTO.toLogString(), e);
	    }
	}
    
}