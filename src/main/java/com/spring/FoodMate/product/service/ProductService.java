package com.spring.FoodMate.product.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.spring.FoodMate.common.SessionDTO;
import com.spring.FoodMate.common.UtilMethod;
import com.spring.FoodMate.common.exception.DBException;
import com.spring.FoodMate.product.dao.ProductDAO;
import com.spring.FoodMate.product.dto.CategoryDTO;
import com.spring.FoodMate.product.dto.ProductDTO;
import com.spring.FoodMate.product.dto.ProductQnaDTO;
import com.spring.FoodMate.product.dto.ProductRatingDTO;
import com.spring.FoodMate.product.exception.ProductException;

@Service("productService")
public class ProductService {
	@Autowired
	private ProductDAO productDAO;
	
	//기존에 쓰던 전체상품+검색어로 상품조회
	public List<ProductDTO> pdtList(String keyword) {
		try {
			if(keyword.equals("")) {
				return productDAO.pdtAllList();
			} else {
				return productDAO.pdtSearchList(keyword);
			}
		} catch (DBException e) {
			throw new ProductException("ProductService에서 DB예외 전달.", e);
		} catch (Exception e) {
			throw new ProductException("ProductService.pdtList 에러! keyword = '" + keyword + "'", e);
		}
	}
	
	// 앞으로 쓸 전체상품 & 판매자별 & 카테고리별 & 검색어별 상품목록조회
	public List<ProductDTO> searchList(String slr_id, Integer category_id, String keyword, String sort) {
		try {
			return productDAO.searchList(slr_id, category_id, keyword, sort);
		} catch (DBException e) {
			throw new ProductException("ProductService에서 DB예외 전달.", e);
		} catch (Exception e) {
			throw new ProductException("ProductService.searchList 에러!", e);
		}
	}
	
	public List<ProductDTO> ms_pdtList(String slr_id) {
		try {
			return productDAO.pdtListBySlrId(slr_id);
		} catch (DBException e) {
			throw new ProductException("ProductService에서 DB예외 전달.", e);
		} catch (Exception e) {
			throw new ProductException("ProductService.ms_pdtList 에러! slr_id = '" + slr_id + "'", e);
		}
	}
	
	// 상품 재고및 상태변경 페이지에 필요한 데이터 제공 메서드
	public List<ProductDTO> mypageseller_pdtListForStock(String slr_id) {
		try {
			return productDAO.pdtListForStockBySlrId(slr_id);
		} catch (DBException e) {
			throw new ProductException("ProductService에서 DB예외 전달.", e);
		} catch (Exception e) {
			throw new ProductException("ProductService.ms_pdtList 에러! slr_id = '" + slr_id + "'", e);
		}
	}
	
	// 상품 상태 변경 메서드
	public boolean changeStatus(int pdt_id, String currentStatus) {
        String newStatus = (currentStatus.equals("Y")) ? "N" : "Y";  // 상태 반전

        try {
            // 상품 상태 변경
            return productDAO.updateStatus(pdt_id, newStatus) > 0;
        } catch (Exception e) {
            throw new ProductException("음...", e);
        }
    }
	
	public ProductDTO select1PdtByPdtId(int pdt_id) {
		try {
			ProductDTO pdt = productDAO.select1Pdt(pdt_id);
			if(pdt != null) {
				return pdt;
			} else {
				throw new ProductException("ProductService.select1PdtByPdtId 에러!", 201);
			}
		} catch (DBException e) {
			throw new ProductException("ProductService에서 DB예외 전달.", e);
		}
	}
	
	public List<CategoryDTO> getGrandCategoryList() {
		try {
			return productDAO.getGrandCategoryList();
		} catch (DBException e) {
			throw new ProductException("ProductService에서 DB예외 전달.", e);
		} catch (Exception e) {
			throw new ProductException("ProductService.getGrandCategoryList 에러!", e);
		}
	}
	
	public List<CategoryDTO> getChildCategoryList(int category_id) {
		try {
			return productDAO.getChildCategoryList(category_id);
		} catch (DBException e) {
			throw new ProductException("ProductService에서 DB예외 전달.", e);
		} catch (Exception e) {
			throw new ProductException("ProductService.getChildCategoryList 에러! category_id = '" + category_id + "'", e);
		}
	}
	
	public List<CategoryDTO> categoryStep(int category_id) {
		try {
			return productDAO.getCategoryStep(category_id);
		} catch (DBException e) {
			throw new ProductException("ProductService에서 DB예외 전달.", e);
		} catch (Exception e) {
			throw new ProductException("ProductService.categoryStep 에러! category_id = '" + category_id + "'", e);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public boolean insertNewProduct(HttpServletRequest request, ProductDTO newPdt) throws Exception {
		try {
			HttpSession session = request.getSession();
			SessionDTO userInfo = (SessionDTO)session.getAttribute("sessionDTO");
			String slr_id = userInfo.getUserId();
			newPdt.setSlr_id(slr_id);
			// 세션에서 id꺼내와서 ProductDTO newPdt에 저장
			
			String imagePath = UtilMethod.savePdtImage(newPdt.getPdt_img(), 1); // 유틸메서드로 일단 경로에 이미지 저장해
		    newPdt.setImg_path(imagePath);
		    // newPdt에서 멀티파트파일형 이미지파일 꺼내와서 서버에 저장하고 저장경로 받아와서 newPdt에 저장
			
			int result = productDAO.insertNewProduct(newPdt);
			// 받아온 내용 DTO에 insert
			
			if(result <= 0) {
				throw new ProductException("ProductService.insertNewProduct 에러!" + newPdt.toLogString(), 202);
				// 오류나면 로깅하면서 에러띄움
			}
			
			boolean result2 = insertProductDescImgs(newPdt);
			
			boolean result3 = (result > 0) || result2;
			
			return result3;
		} catch (DBException e) {
			throw new ProductException("ProductService에서 DB예외 전달.", e);
		}
	}
	
	public boolean insertProductDescImgs(ProductDTO newPdt) throws Exception {
	    List<MultipartFile> descimgs = newPdt.getPdt_descimg();
	    int pdt_id = productDAO.getNewPdtId(); //방금만들어진 상품아이디 갖고오기

	    // 향상된 for문을 사용하여 각 MultipartFile에 대해 처리
	    for (MultipartFile file : descimgs) {
	        if (!file.isEmpty()) { // 꺼내온 파일이 비어있지않으면
	        	String imgPath = UtilMethod.savePdtImage(file, 2);
	            int result = productDAO.insertProductDescImg(pdt_id, imgPath);
	            if (result < 1) {
	            	System.out.println("상품설명이미지넣는중오류");
	            	throw new ProductException(206);
	            }
	        }
	    }
	    return true;
	}
	
	public boolean updateProduct(ProductDTO editPdt) {
		try {
			int result = productDAO.updateProduct(editPdt);
			if(result <= 0) {
				throw new ProductException("ProductService.updateProduct 에러!" + editPdt.toLogString(), 204);
			}
			return result > 0;
		} catch (DBException e) {
			throw new ProductException("ProductService에서 DB예외 전달.", e);
		}
	}
	
	public boolean deleteProduct(int pdt_id) {
		try {
			int result = productDAO.deleteProduct(pdt_id);
			if(result <= 0) {
				throw new ProductException("ProductService.deleteProduct 에러! pdt_id = " + pdt_id, 205);
			}
			return result > 0;
		} catch (DBException e) {
			throw new ProductException("ProductService에서 DB예외 전달.", e);
		}
	}
	
	// 재고 수 업데이트하는 메서드
	public int updateStock(int pdt_id, int stock) throws Exception {
		return productDAO.updateStock(pdt_id, stock);
	}
	
	// 상품 후기 등록
	public boolean insertProductRating(ProductRatingDTO newProductRating) {
	    try {
	        System.out.println("상품 후기 등록 시작: " + newProductRating.toLogString());  // 로그 추가
	        int result = productDAO.insertProductRating(newProductRating);
	        if (result <= 0) {
	            throw new ProductException("ProductService.insertProductRating 에러!" + newProductRating.toLogString(), 206);
	        }
	        System.out.println("상품 후기 등록 성공: " + result);  // 성공 로그
	        return result > 0;
	    } catch (DBException e) {
	        System.out.println("상품 후기 등록 실패: " + newProductRating.toLogString());  // 실패 로그
	        throw new ProductException("ProductService에서 DB예외 전달.", e);
	    } catch (Exception e) {
	        System.out.println("상품 후기 등록 에러: " + newProductRating.toLogString());  // 실패 로그
	        throw new ProductException("ProductService.insertProductRating 에러!", e);
	    }
	}

	// 상품 후기 조회
	public List<ProductRatingDTO> getProductRatings(int pdt_id) {
	    try {
	        System.out.println("상품 후기 조회 시작: pdt_id = " + pdt_id);  // 로그 추가
	        List<ProductRatingDTO> ratings = productDAO.getProductRatings(pdt_id);
	        System.out.println("상품 후기 조회 성공: " + ratings.size() + "개 후기");  // 성공 로그
	        return ratings;
	    } catch (DBException e) {
	        System.out.println("상품 후기 조회 실패: pdt_id = " + pdt_id);  // 실패 로그
	        throw new ProductException("ProductService에서 DB예외 전달.", e);
	    } catch (Exception e) {
	        System.out.println("상품 후기 조회 에러: pdt_id = " + pdt_id);  // 실패 로그
	        throw new ProductException("ProductService.getProductRatings 에러! pdt_id = '" + pdt_id + "'", e);
	    }
	}

	// 상품 후기 ID로 조회하는 메소드
	public ProductRatingDTO getProductRatingById(int rating_id) {
	    try {
	        System.out.println("상품 후기 조회 시작: rating_id = " + rating_id);  // 로그 추가
	        ProductRatingDTO productRating = productDAO.getProductRatingById(rating_id);
	        System.out.println("상품 후기 조회 성공: " + productRating);  // 성공 로그
	        return productRating;
	    } catch (Exception e) {
	        System.out.println("상품 후기 조회 실패: rating_id = " + rating_id);  // 실패 로그
	        throw new ProductException("ProductService.getProductRatingById 에러!", e);
	    }
	}

	// 상품 후기 삭제
	public boolean deleteProductRating(int cmt_pdt_rating_id) {
	    try {
	        System.out.println("상품 후기 삭제 시작: cmt_pdt_rating_id = " + cmt_pdt_rating_id);  // 로그 추가
	        int result = productDAO.deleteProductRating(cmt_pdt_rating_id);
	        if (result <= 0) {
	            throw new ProductException("ProductService.deleteProductRating 에러! cmt_pdt_rating_id = " + cmt_pdt_rating_id, 207);
	        }
	        System.out.println("상품 후기 삭제 성공: " + result);  // 성공 로그
	        return result > 0;
	    } catch (DBException e) {
	        System.out.println("상품 후기 삭제 실패: cmt_pdt_rating_id = " + cmt_pdt_rating_id);  // 실패 로그
	        throw new ProductException("ProductService에서 DB예외 전달.", e);
	    } catch (Exception e) {
	        System.out.println("상품 후기 삭제 에러: cmt_pdt_rating_id = " + cmt_pdt_rating_id);  // 실패 로그
	        throw new ProductException("ProductService.deleteProductRating 에러!", e);
	    }
	}

	// 상품 후기 수정
	public boolean updateProductRating(ProductRatingDTO productRatingDTO) {
	    try {
	        System.out.println("상품 후기 수정 시작: " + productRatingDTO.toLogString());  // 로그 추가
	        int result = productDAO.updateProductRating(productRatingDTO);
	        if (result <= 0) {
	            throw new ProductException("ProductService.updateProductRating 에러!" + productRatingDTO.toLogString(), 208);
	        }
	        System.out.println("상품 후기 수정 성공: " + result);  // 성공 로그
	        return result > 0;
	    } catch (DBException e) {
	        System.out.println("상품 후기 수정 실패: " + productRatingDTO.toLogString());  // 실패 로그
	        throw new ProductException("ProductService에서 DB예외 전달.", e);
	    } catch (Exception e) {
	        System.out.println("상품 후기 수정 에러: " + productRatingDTO.toLogString());  // 실패 로그
	        throw new ProductException("ProductService.updateProductRating 에러!", e);
	    }
	}

	// 상품 문의 등록
	public boolean insertProductQna(ProductQnaDTO newProductQna) {
	    try {
	        System.out.println("상품 문의 등록 시작: " + newProductQna.toLogString());  // 로그 추가
	        int result = productDAO.insertProductQna(newProductQna);
	        if (result <= 0) {
	            throw new ProductException("ProductService.insertProductQna 에러!" + newProductQna.toLogString(), 209);
	        }
	        System.out.println("상품 문의 등록 성공: " + result);  // 성공 로그
	        return result > 0;
	    } catch (DBException e) {
	        System.out.println("상품 문의 등록 실패: " + newProductQna.toLogString());  // 실패 로그
	        throw new ProductException("ProductService에서 DB예외 전달.", e);
	    } catch (Exception e) {
	        System.out.println("상품 문의 등록 에러: " + newProductQna.toLogString());  // 실패 로그
	        throw new ProductException("ProductService.insertProductQna 에러!", e);
	    }
	}

	// 상품 문의 조회
	public List<ProductQnaDTO> getProductQna(int pdt_id) {
	    try {
	        System.out.println("상품 문의 조회 시작: pdt_id = " + pdt_id);  // 로그 추가
	        List<ProductQnaDTO> qnaList = productDAO.getProductQna(pdt_id);
	        System.out.println("상품 문의 조회 성공: " + qnaList.size() + "개 문의");  // 성공 로그
	        return qnaList;
	    } catch (DBException e) {
	        System.out.println("상품 문의 조회 실패: pdt_id = " + pdt_id);  // 실패 로그
	        throw new ProductException("ProductService에서 DB예외 전달.", e);
	    } catch (Exception e) {
	        System.out.println("상품 문의 조회 에러: pdt_id = " + pdt_id);  // 실패 로그
	        throw new ProductException("ProductService.getProductQna 에러! pdt_id = '" + pdt_id + "'", e);
	    }
	}

	// 상품 문의 ID로 조회
	public ProductQnaDTO getProductQnaById(int qna_id) {
	    try {
	        System.out.println("상품 문의 조회 시작: qna_id = " + qna_id);  // 로그 추가
	        ProductQnaDTO productQna = productDAO.getProductQnaById(qna_id);
	        System.out.println("상품 문의 조회 성공: " + productQna);  // 성공 로그
	        return productQna;
	    } catch (DBException e) {
	        System.out.println("상품 문의 조회 실패: qna_id = " + qna_id);  // 실패 로그
	        throw new ProductException("ProductService에서 DB예외 전달.", e);
	    } catch (Exception e) {
	        System.out.println("상품 문의 조회 에러: qna_id = " + qna_id);  // 실패 로그
	        throw new ProductException("ProductService.getProductQnaById 에러! qna_id = '" + qna_id + "'", e);
	    }
	}

	// 상품 문의 수정
	public boolean updateProductQna(ProductQnaDTO productQnaDTO) {
	    try {
	        System.out.println("상품 문의 수정 시작: " + productQnaDTO.toLogString());  // 로그 추가
	        int result = productDAO.updateProductQna(productQnaDTO);
	        if (result <= 0) {
	            throw new ProductException("ProductService.updateProductQna 에러!" + productQnaDTO.toLogString(), 211);
	        }
	        System.out.println("상품 문의 수정 성공: " + result);  // 성공 로그
	        return result > 0;
	    } catch (DBException e) {
	        System.out.println("상품 문의 수정 실패: " + productQnaDTO.toLogString());  // 실패 로그
	        throw new ProductException("ProductService에서 DB예외 전달.", e);
	    } catch (Exception e) {
	        System.out.println("상품 문의 수정 에러: " + productQnaDTO.toLogString());  // 실패 로그
	        throw new ProductException("ProductService.updateProductQna 에러!", e);
	    }
	}

	// 상품 문의 삭제
	public boolean deleteProductQna(int cmt_pdt_qna_id) {
	    try {
	        System.out.println("상품 문의 삭제 시작: cmt_pdt_qna_id = " + cmt_pdt_qna_id);  // 로그 추가
	        int result = productDAO.deleteProductQna(cmt_pdt_qna_id);
	        if (result <= 0) {
	            throw new ProductException("ProductService.deleteProductQna 에러! cmt_pdt_qna_id = " + cmt_pdt_qna_id, 210);
	        }
	        System.out.println("상품 문의 삭제 성공: " + result);  // 성공 로그
	        return result > 0;
	    } catch (DBException e) {
	        System.out.println("상품 문의 삭제 실패: cmt_pdt_qna_id = " + cmt_pdt_qna_id);  // 실패 로그
	        throw new ProductException("ProductService에서 DB예외 전달.", e);
	    } catch (Exception e) {
	        System.out.println("상품 문의 삭제 에러: cmt_pdt_qna_id = " + cmt_pdt_qna_id);  // 실패 로그
	        throw new ProductException("ProductService.deleteProductQna 에러!", e);
	    }
	}


}