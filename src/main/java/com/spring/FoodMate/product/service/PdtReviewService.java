package com.spring.FoodMate.product.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.FoodMate.common.exception.DBException;
import com.spring.FoodMate.common.exception.UnauthorizedException;
import com.spring.FoodMate.product.dao.PdtReviewDAO;
import com.spring.FoodMate.product.dto.ProductRatingDTO;
import com.spring.FoodMate.product.exception.ProductException;

@Service("pdtReviewService")
public class PdtReviewService {
	@Autowired
	private PdtReviewDAO pdtReviewDAO;
	
	// 상품 후기 등록 및 수
	public boolean addPdtReviewProcess(String userId, ProductRatingDTO review) throws Exception {
	    try {
	        // 먼저 이사람이 상품 구매확정했는지부터 확인해야함
	    	if(pdtReviewDAO.isByrConfirmPdt(userId, review.getPdt_id()) < 1) {
	    		throw new UnauthorizedException(107);
	    	}
	    	review.setByr_id(userId);
	    	
	    	// 그리고 이사람이 그 상품에 대한 후기 썼었는지도 확인해야함
	    	ProductRatingDTO isReviewed = isReviewed(userId, review.getPdt_id());
	    	
	    	if(isReviewed != null) { // 이미 작성한 리뷰있으면
	    		review.setCmt_pdt_rating_id(isReviewed.getCmt_pdt_rating_id()); // 시퀀스 전달해
	    		return pdtReviewDAO.updateProductRating(review) > 0;
	    	} else {
	    		return pdtReviewDAO.insertProductRating(review) > 0;
	    	}
	    } catch (DBException e) {
	        System.out.println("상품 후기 등록 실패: " + review.toLogString());  // 실패 로그
	        throw new ProductException("ProductService에서 DB예외 전달.", e);
	    } catch (Exception e) {
	        System.out.println("상품 후기 등록 에러: " + review.toLogString());  // 실패 로그
	        throw new ProductException("ProductService.insertProductRating 에러!", e);
	    }
	}
	
	// 상품 후기 썼었는지 확인
	public ProductRatingDTO isReviewed(String byr_id, int pdt_id) throws Exception {
		ProductRatingDTO review = pdtReviewDAO.isReviewed(byr_id, pdt_id);
		return review;
	}
	
	public boolean deleteReview(int reviewId) throws Exception {
		return pdtReviewDAO.deleteProductRating(reviewId) > 0;
	}
	
	// 상품 후기 댓글들 불러와서 html 태그로 준비해주는 메서드
		public String getPdtReviews(int pdt_id) throws Exception {
		    List<ProductRatingDTO> reviews = pdtReviewDAO.getPdtReviews(pdt_id);  // DAO에서 상품에 대한 후기 리스트 가져오기
		    
		    StringBuilder descriptionHtml = new StringBuilder();
		    descriptionHtml.append("<h3>상품 후기</h3>");
		    // 후기들 반복하여 HTML로 작성
		    for (ProductRatingDTO review : reviews) {
		        descriptionHtml.append("<div class='review'>")
		                       .append("<div class='review-header'>")
		                       .append("<span class='review-author'>").append(review.getByr_id()).append("</span>")
		                       .append("<span class='review-date'>").append(new SimpleDateFormat("yyyy-MM-dd").format(review.getCreate_date())).append("</span>")
		                       .append("</div>")  // 리뷰 헤더 끝
		                       
		                       .append("<div class='review-rating'>")
		                       .append("<span class='stars'>")
		                       .append("★".repeat(Math.max(0, review.getRating()))).append("</span>")  // 별점 표시
		                       .append("</div>")  // 리뷰 평점 끝
		                       
		                       .append("<div class='review-comments'>")
		                       .append(review.getComments())  // 댓글 내용
		                       .append("</div>")  // 리뷰 댓글 끝
		                       
		                       .append("</div>");  // 전체 리뷰 div 끝
		    }
		    
		    return descriptionHtml.toString();  // HTML로 구성된 상품 후기 태그들 반환
		}
	
//	// 상품 후기 조회
//		public List<ProductRatingDTO> getProductRatings(int pdt_id) {
//		    try {
//		        System.out.println("상품 후기 조회 시작: pdt_id = " + pdt_id);  // 로그 추가
//		        List<ProductRatingDTO> ratings = productDAO.getProductRatings(pdt_id);
//		        System.out.println("상품 후기 조회 성공: " + ratings.size() + "개 후기");  // 성공 로그
//		        return ratings;
//		    } catch (DBException e) {
//		        System.out.println("상품 후기 조회 실패: pdt_id = " + pdt_id);  // 실패 로그
//		        throw new ProductException("ProductService에서 DB예외 전달.", e);
//		    } catch (Exception e) {
//		        System.out.println("상품 후기 조회 에러: pdt_id = " + pdt_id);  // 실패 로그
//		        throw new ProductException("ProductService.getProductRatings 에러! pdt_id = '" + pdt_id + "'", e);
//		    }
//		}
//
//		// 상품 후기 ID로 조회하는 메소드
//		public ProductRatingDTO getProductRatingById(int rating_id) {
//		    try {
//		        System.out.println("상품 후기 조회 시작: rating_id = " + rating_id);  // 로그 추가
//		        ProductRatingDTO productRating = productDAO.getProductRatingById(rating_id);
//		        System.out.println("상품 후기 조회 성공: " + productRating);  // 성공 로그
//		        return productRating;
//		    } catch (Exception e) {
//		        System.out.println("상품 후기 조회 실패: rating_id = " + rating_id);  // 실패 로그
//		        throw new ProductException("ProductService.getProductRatingById 에러!", e);
//		    }
//		}
//
//		// 상품 후기 삭제
//		public boolean deleteProductRating(int cmt_pdt_rating_id) {
//		    try {
//		        System.out.println("상품 후기 삭제 시작: cmt_pdt_rating_id = " + cmt_pdt_rating_id);  // 로그 추가
//		        int result = productDAO.deleteProductRating(cmt_pdt_rating_id);
//		        if (result <= 0) {
//		            throw new ProductException("ProductService.deleteProductRating 에러! cmt_pdt_rating_id = " + cmt_pdt_rating_id, 207);
//		        }
//		        System.out.println("상품 후기 삭제 성공: " + result);  // 성공 로그
//		        return result > 0;
//		    } catch (DBException e) {
//		        System.out.println("상품 후기 삭제 실패: cmt_pdt_rating_id = " + cmt_pdt_rating_id);  // 실패 로그
//		        throw new ProductException("ProductService에서 DB예외 전달.", e);
//		    } catch (Exception e) {
//		        System.out.println("상품 후기 삭제 에러: cmt_pdt_rating_id = " + cmt_pdt_rating_id);  // 실패 로그
//		        throw new ProductException("ProductService.deleteProductRating 에러!", e);
//		    }
//		}
//
//		// 상품 후기 수정
//		public boolean updateProductRating(ProductRatingDTO productRatingDTO) {
//		    try {
//		        System.out.println("상품 후기 수정 시작: " + productRatingDTO.toLogString());  // 로그 추가
//		        int result = productDAO.updateProductRating(productRatingDTO);
//		        if (result <= 0) {
//		            throw new ProductException("ProductService.updateProductRating 에러!" + productRatingDTO.toLogString(), 208);
//		        }
//		        System.out.println("상품 후기 수정 성공: " + result);  // 성공 로그
//		        return result > 0;
//		    } catch (DBException e) {
//		        System.out.println("상품 후기 수정 실패: " + productRatingDTO.toLogString());  // 실패 로그
//		        throw new ProductException("ProductService에서 DB예외 전달.", e);
//		    } catch (Exception e) {
//		        System.out.println("상품 후기 수정 에러: " + productRatingDTO.toLogString());  // 실패 로그
//		        throw new ProductException("ProductService.updateProductRating 에러!", e);
//		    }
//		}
//
//		// 상품 문의 등록
//		public boolean insertProductQna(ProductQnaDTO newProductQna) {
//		    try {
//		        System.out.println("상품 문의 등록 시작: " + newProductQna.toLogString());  // 로그 추가
//		        int result = productDAO.insertProductQna(newProductQna);
//		        if (result <= 0) {
//		            throw new ProductException("ProductService.insertProductQna 에러!" + newProductQna.toLogString(), 209);
//		        }
//		        System.out.println("상품 문의 등록 성공: " + result);  // 성공 로그
//		        return result > 0;
//		    } catch (DBException e) {
//		        System.out.println("상품 문의 등록 실패: " + newProductQna.toLogString());  // 실패 로그
//		        throw new ProductException("ProductService에서 DB예외 전달.", e);
//		    } catch (Exception e) {
//		        System.out.println("상품 문의 등록 에러: " + newProductQna.toLogString());  // 실패 로그
//		        throw new ProductException("ProductService.insertProductQna 에러!", e);
//		    }
//		}
//
//		// 상품 문의 조회
//		public List<ProductQnaDTO> getProductQna(int pdt_id) {
//		    try {
//		        System.out.println("상품 문의 조회 시작: pdt_id = " + pdt_id);  // 로그 추가
//		        List<ProductQnaDTO> qnaList = productDAO.getProductQna(pdt_id);
//		        System.out.println("상품 문의 조회 성공: " + qnaList.size() + "개 문의");  // 성공 로그
//		        return qnaList;
//		    } catch (DBException e) {
//		        System.out.println("상품 문의 조회 실패: pdt_id = " + pdt_id);  // 실패 로그
//		        throw new ProductException("ProductService에서 DB예외 전달.", e);
//		    } catch (Exception e) {
//		        System.out.println("상품 문의 조회 에러: pdt_id = " + pdt_id);  // 실패 로그
//		        throw new ProductException("ProductService.getProductQna 에러! pdt_id = '" + pdt_id + "'", e);
//		    }
//		}
//
//		// 상품 문의 ID로 조회
//		public ProductQnaDTO getProductQnaById(int qna_id) {
//		    try {
//		        System.out.println("상품 문의 조회 시작: qna_id = " + qna_id);  // 로그 추가
//		        ProductQnaDTO productQna = productDAO.getProductQnaById(qna_id);
//		        System.out.println("상품 문의 조회 성공: " + productQna);  // 성공 로그
//		        return productQna;
//		    } catch (DBException e) {
//		        System.out.println("상품 문의 조회 실패: qna_id = " + qna_id);  // 실패 로그
//		        throw new ProductException("ProductService에서 DB예외 전달.", e);
//		    } catch (Exception e) {
//		        System.out.println("상품 문의 조회 에러: qna_id = " + qna_id);  // 실패 로그
//		        throw new ProductException("ProductService.getProductQnaById 에러! qna_id = '" + qna_id + "'", e);
//		    }
//		}
//
//		// 상품 문의 수정
//		public boolean updateProductQna(ProductQnaDTO productQnaDTO) {
//		    try {
//		        System.out.println("상품 문의 수정 시작: " + productQnaDTO.toLogString());  // 로그 추가
//		        int result = productDAO.updateProductQna(productQnaDTO);
//		        if (result <= 0) {
//		            throw new ProductException("ProductService.updateProductQna 에러!" + productQnaDTO.toLogString(), 211);
//		        }
//		        System.out.println("상품 문의 수정 성공: " + result);  // 성공 로그
//		        return result > 0;
//		    } catch (DBException e) {
//		        System.out.println("상품 문의 수정 실패: " + productQnaDTO.toLogString());  // 실패 로그
//		        throw new ProductException("ProductService에서 DB예외 전달.", e);
//		    } catch (Exception e) {
//		        System.out.println("상품 문의 수정 에러: " + productQnaDTO.toLogString());  // 실패 로그
//		        throw new ProductException("ProductService.updateProductQna 에러!", e);
//		    }
//		}
//
//		// 상품 문의 삭제
//		public boolean deleteProductQna(int cmt_pdt_qna_id) {
//		    try {
//		        System.out.println("상품 문의 삭제 시작: cmt_pdt_qna_id = " + cmt_pdt_qna_id);  // 로그 추가
//		        int result = productDAO.deleteProductQna(cmt_pdt_qna_id);
//		        if (result <= 0) {
//		            throw new ProductException("ProductService.deleteProductQna 에러! cmt_pdt_qna_id = " + cmt_pdt_qna_id, 210);
//		        }
//		        System.out.println("상품 문의 삭제 성공: " + result);  // 성공 로그
//		        return result > 0;
//		    } catch (DBException e) {
//		        System.out.println("상품 문의 삭제 실패: cmt_pdt_qna_id = " + cmt_pdt_qna_id);  // 실패 로그
//		        throw new ProductException("ProductService에서 DB예외 전달.", e);
//		    } catch (Exception e) {
//		        System.out.println("상품 문의 삭제 에러: cmt_pdt_qna_id = " + cmt_pdt_qna_id);  // 실패 로그
//		        throw new ProductException("ProductService.deleteProductQna 에러!", e);
//		    }
//		}
	

}
