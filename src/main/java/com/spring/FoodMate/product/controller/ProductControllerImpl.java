package com.spring.FoodMate.product.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.FoodMate.common.SessionDTO;
import com.spring.FoodMate.common.UtilMethod;
import com.spring.FoodMate.common.exception.UnhandledException;
import com.spring.FoodMate.product.dto.CategoryDTO;
import com.spring.FoodMate.product.dto.ProductDTO;
import com.spring.FoodMate.product.dto.ProductQnaDTO;
import com.spring.FoodMate.product.dto.ProductRatingDTO;
import com.spring.FoodMate.product.exception.ProductException;
import com.spring.FoodMate.product.service.ProductService;
import com.spring.FoodMate.recipe.dto.RecipeDTO;
import com.spring.FoodMate.recipe.exception.RecipeException;
import com.spring.FoodMate.recipe.service.RecipeService;

@Controller
public class ProductControllerImpl implements ProductController {
	@Autowired
	private ProductService productService;// 상품 문의 수정
	@Autowired
	private RecipeService recipeService;
	
	// nav 의 "식료품" 눌렀을 때나 식재료 검색했을 때 
	@Override
	@RequestMapping(value="/product/pdtlist", method=RequestMethod.GET)
	public ModelAndView pdtList(
		@RequestParam(value = "slr_id", required = false, defaultValue = "") String slr_id,	
		@RequestParam(value = "category_id", required = false, defaultValue = "") Integer category_id,
	    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
	    @RequestParam(value = "sort", required = false, defaultValue = "") String sort,
	    HttpServletRequest request) throws Exception {
	    List<ProductDTO> searchList = productService.searchList(slr_id, category_id, keyword, sort);
	    
	    ModelAndView mav = new ModelAndView();
        mav.setViewName("common/layout");
        mav.addObject("title", "FoodMate-상품 검색창");
        mav.addObject("showNavbar", true);
        mav.addObject("body", "/WEB-INF/views" + UtilMethod.getViewName(request) + ".jsp");
	    mav.addObject("list", searchList);
	    mav.addObject("keyword", keyword);
	    // Service 에 keyword(검색어)를 주고 해당하는 상품VO들의 List를 받아옴.
	    // 검색어 없을땐 전체 상품리스트 갖고옴.
	    return mav;
	}
	
		@Override
	@RequestMapping(value="/mypage_seller/ms_pdtlist", method=RequestMethod.GET)
	public ModelAndView msPdtList(HttpServletRequest request, HttpSession session) throws Exception {
		    SessionDTO sellerInfo = (SessionDTO)session.getAttribute("sessionDTO");
		    List<ProductDTO> searchList = productService.ms_pdtList(sellerInfo.getUserId());
		    
		    ModelAndView mav = new ModelAndView();
            mav.setViewName("common/layout");
            mav.addObject("title", "FoodMate-상품 검색창");
            mav.addObject("showNavbar", true);
            mav.addObject("body", "/WEB-INF/views" + UtilMethod.getViewName(request) + ".jsp");
		    mav.addObject("list", searchList);
		    // Service 에 판매자 ID를 주고 해당하는 상품VO들의 List를 받아옴.
		    return mav;
	}
	
	@Override
	@RequestMapping(value="/product/pdtdetail", method=RequestMethod.GET)
	public ModelAndView pdtDetail(
			@RequestParam(value = "pdt_id", required = true) int pdt_id,
			HttpServletRequest request, HttpServletResponse response) throws Exception {		
		ProductDTO product = productService.select1PdtByPdtId(pdt_id); // pdt_id로 pdt 전체행 받아옴
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("common/layout");
        mav.addObject("title", "제품 상세정보");
        mav.addObject("showNavbar", true);
        mav.addObject("body", "/WEB-INF/views" + UtilMethod.getViewName(request) + ".jsp");
        
		if(product.getCategory_id() != null) {
			List<CategoryDTO> categoryStep = productService.categoryStep(product.getCategory_id());
			mav.addObject("category", categoryStep);
		}
		// pdt의 카테고리id 받아서 최상위 부모카테고리까지 싹 받아옴
		mav.addObject("pdt", product);
		return mav;
	}
	
	@Override
	@RequestMapping(value="/product/pdtaddform", method=RequestMethod.GET)
	public ModelAndView pdtAddForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		List<CategoryDTO> categories = productService.getGrandCategoryList();
		
		mav.setViewName("common/layout");
		mav.addObject("title", "상품 등록");
        mav.addObject("showNavbar", true);
        mav.addObject("body", "/WEB-INF/views" + UtilMethod.getViewName(request) + ".jsp");
        mav.addObject("categories", categories);
		return mav;
	}
	
	@Override
	@RequestMapping(value="/product/pdtadd", method=RequestMethod.POST)
	public void pdtAdd(@ModelAttribute ProductDTO newPdt,
	        HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
			    SessionDTO sessionDTO = (SessionDTO)session.getAttribute("sessionDTO");
	    String slr_id = sessionDTO.getUserId();
	    String imagePath = UtilMethod.savePdtImage(request, newPdt.getPdt_img()); // 유틸메서드로 일단 경로에 이미지 저장해
	    
	    newPdt.setSlr_id(slr_id);
	    newPdt.setImg_path(imagePath);
	    
	    productService.insertNewProduct(newPdt);
	    
	    PrintWriter out = null;
    	// 상품 추가 성공 시 JavaScript로 알림 및 페이지 이동 처리
        response.setContentType("text/html; charset=UTF-8");
        out = response.getWriter();
        out.println("<script type='text/javascript'>");
        out.println("alert('상품이 추가되었습니다. 상품 관리 페이지로 이동합니다.');");
        out.println("window.location.href='" + request.getContextPath() + "/mypage_seller/mypage_sell_productlist';");
        out.println("</script>");
	}
	
	@Override
	@RequestMapping(value="/product/pdteditform", method=RequestMethod.GET)
	public ModelAndView pdtEditForm(@RequestParam(value = "pdt_id", required = false, defaultValue = "0") int pdt_id,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {		
		if(pdt_id<=0) {
			throw new ProductException(201);
		}
		
		ProductDTO needEdit = productService.select1PdtByPdtId(pdt_id);
		// 받은 pdt_id로 상품 테이블 뒤져서 있는지 확인하고 (없으면 Service에서 오류 던질거임)
		SessionDTO sellerInfo = (SessionDTO)session.getAttribute("sessionDTO");
		// 등록한놈이랑 다른놈이 수정하려고하면
		if( !sellerInfo.getUserId().equals( needEdit.getSlr_id() ) ) {
			throw new ProductException(203);
		}
		
		List<CategoryDTO> categories = productService.getGrandCategoryList();
		
		ModelAndView mav = new ModelAndView();
        mav.setViewName("common/layout");
        mav.addObject("title", needEdit.getName()+" 상품정보 수정");
        mav.addObject("showNavbar", true);
        mav.addObject("body", "/WEB-INF/views" + UtilMethod.getViewName(request) + ".jsp");
		mav.addObject("categories", categories); // 카테고리 대분류 띄워주기
		mav.addObject("ProductDTO", needEdit);
		return mav;
	}
	
	@Override
	@RequestMapping(value="/product/pdtedit", method=RequestMethod.POST)
	public void pdtEdit(@ModelAttribute ProductDTO editPdt,
	        HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		
		int oldPdt_id = editPdt.getPdt_id();
		
		// 만약 사진첨부를 안했으면 다시 이미지 경로 갖고와서 넣어
		if(editPdt.getPdt_img() == null || editPdt.getPdt_img().isEmpty()) {
			editPdt.setImg_path(productService.select1PdtByPdtId(oldPdt_id).getImg_path());
		} else {
		// 사진첨부 했으면 사진 넣어, 나중에 옛날사진 삭제하는 기능 넣어
			String imagePath = UtilMethod.savePdtImage(request, editPdt.getPdt_img()); // 유틸메서드로 일단 경로에 이미지 저장해
			editPdt.setImg_path(imagePath);
		}
		
		// 만약 카테고리id가 널로 들어왔으면 예전꺼 넣어
		if(editPdt.getCategory_id() == null) {
			editPdt.setCategory_id(productService.select1PdtByPdtId(oldPdt_id).getCategory_id());
		}
		
	    productService.updateProduct(editPdt);
	    // 이거 일단 boolean 반환하긴 하는데 어카노. 애초에 true 아니면 여기로 못넘어오는디.
	    
	    PrintWriter out = null;
    	// 상품 추가 성공 시 JavaScript로 알림 및 페이지 이동 처리
        response.setContentType("text/html; charset=UTF-8");
        out = response.getWriter();
        out.println("<script type='text/javascript'>");
        out.println("alert('상품 정보를 수정하였습니다. 상품 관리 페이지로 이동합니다.');");
        out.println("window.location.href='" + request.getContextPath() + "/mypage_seller/mypage_sell_productlist';");
        out.println("</script>");
	}

	@Override
	@RequestMapping(value="/product/pdtdelete", method=RequestMethod.POST)
	public String pdtDelete(@RequestParam(value = "pdt_id", required = true, defaultValue = "0") int pdt_id,
			HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
		// 리디렉션 하고 flashAttribue
		if(pdt_id<=0) {
			throw new ProductException(201);
		}
		
		ProductDTO needDelete = productService.select1PdtByPdtId(pdt_id);
		// 받은 pdt_id로 상품 테이블 뒤져서 있는지 확인하고
		SessionDTO sellerInfo = (SessionDTO)session.getAttribute("sessionDTO");
		// 등록한놈이랑 다른놈이 삭제하려고하면
		if( !sellerInfo.getUserId().equals( needDelete.getSlr_id() ) ) {
			throw new ProductException(203);
		}
		
		productService.deleteProduct(pdt_id);		
        redirectAttributes.addFlashAttribute("message", "상품이 성공적으로 삭제되었습니다.");
        return "redirect:/mypage_seller/ms_pdtlist";
	}
	
	@Override
	@RequestMapping(value="/product/getSubCategories/{category_id}", method=RequestMethod.GET)
	@ResponseBody
	public List<CategoryDTO> getSubCategories(@PathVariable("category_id") int category_id) throws Exception {
	    // 데이터베이스에서 category_id에 해당하는 자식 카테고리 가져오기
	    List<CategoryDTO> subCategories = productService.getChildCategoryList(category_id);
	    return subCategories;
	}
	
	@Override
	@RequestMapping(value="/product/startcompare", method=RequestMethod.GET)
	public ModelAndView startcompare(@RequestParam(value = "rcp_id") int rcp_id, HttpServletRequest request) throws Exception {
		RecipeDTO recipe = recipeService.recipe(rcp_id); 
		if(recipe == null) {
			throw new RecipeException(301); // 301은 검색한 레시피가 DB에 존재하지않는다는뜻임
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("common/layout_noBootStrap");
		mav.addObject("showNavbar", true);
		mav.addObject("title", "재료 비교");
		mav.addObject("body", "/WEB-INF/views" + UtilMethod.getViewName(request) + ".jsp");
		Map<String, Object> um = recipeService.selectRecipeDetail(rcp_id);
		mav.addObject("recipe", um.get("recipe"));
		mav.addObject("ingredients", um.get("ingredients"));
		mav.addObject("steps", um.get("steps"));
		return mav;
	}
	
	@Override
	@RequestMapping(value="/product/categorycompare", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> categorycompare(@RequestParam("category_id") Integer category_id,
			@RequestParam(value = "sort", required = false, defaultValue = "") String sort) {
		try {
			List<ProductDTO> products = productService.searchList(null, category_id, null, sort);
		    Map<String, Object> response = new HashMap<>();
		    response.put("products", products);
		    return response;
        } catch (ProductException e) {
            throw e;
        } catch (Exception e) {
            throw new UnhandledException("상품 비교하려다 오류발생", e);
        }
	}
    // 상품 후기 리스트 조회
    @RequestMapping(value="/product/rating/list/{pdt_id}", method=RequestMethod.GET)
    public String getProductRatingList(@PathVariable("pdt_id") int pdt_id, HttpServletRequest request) throws Exception {
        List<ProductRatingDTO> ratingList = productService.getProductRatings(pdt_id);
        request.setAttribute("ratingList", ratingList);
        return "/product/productRatingList";
    }

    // 상품 후기 작성 폼
    @RequestMapping(value="/product/rating/add/{pdt_id}", method=RequestMethod.GET)
    public String showAddRatingForm(@PathVariable("pdt_id") int pdt_id, HttpServletRequest request) {
        request.setAttribute("pdt_id", pdt_id);
        return "/product/productRatingForm";
    }

    // 상품 후기 작성 처리
    @RequestMapping(value="/product/rating/add", method=RequestMethod.POST)
    public void addProductRating(@ModelAttribute ProductRatingDTO productRating, HttpServletResponse response, HttpSession session) throws Exception {
        String userId = (String) session.getAttribute("userId");
        productRating.setUserId(userId);

        productService.insertProductRating(productRating);

        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=UTF-8");
        out.println("<script type='text/javascript'>");
        out.println("alert('상품 후기가 등록되었습니다.');");
        out.println("window.location.href='/product/rating/list/" + productRating.getPdt_id() + "';");
        out.println("</script>");
    }

    // 상품 후기 수정 폼
    @RequestMapping(value="/product/rating/edit/{rating_id}", method=RequestMethod.GET)
    public String showEditRatingForm(@PathVariable("rating_id") int rating_id, HttpServletRequest request) throws Exception {
        ProductRatingDTO rating = productService.getProductRatingById(rating_id);
        request.setAttribute("rating", rating);
        return "/product/productRatingEditForm";
    }

    // 상품 후기 수정 처리
    @RequestMapping(value="/product/rating/edit", method=RequestMethod.POST)
    public void editProductRating(@ModelAttribute ProductRatingDTO productRating, HttpServletResponse response) throws Exception {
        productService.updateProductRating(productRating);

        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=UTF-8");
        out.println("<script type='text/javascript'>");
        out.println("alert('상품 후기가 수정되었습니다.');");
        out.println("window.location.href='/product/rating/list/" + productRating.getPdt_id() + "';");
        out.println("</script>");
    }

    // 상품 후기 삭제
    @RequestMapping(value="/product/rating/delete/{rating_id}", method=RequestMethod.GET)
    public void deleteProductRating(@PathVariable("rating_id") int rating_id, HttpServletResponse response) throws Exception {
        productService.deleteProductRating(rating_id);

        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=UTF-8");
        out.println("<script type='text/javascript'>");
        out.println("alert('상품 후기가 삭제되었습니다.');");
        out.println("window.location.href='/product/rating/list';");
        out.println("</script>");
    }
    
    // 상품 문의 리스트 조회 (상품에 대한 모든 문의)
    @RequestMapping(value = "/product/qna/list/{pdt_id}", method = RequestMethod.GET)
    public ModelAndView getProductQnaList(@PathVariable("pdt_id") int pdt_id, HttpServletRequest request) throws Exception {
        List<ProductQnaDTO> qnaList = productService.getProductQna(pdt_id);  // ProductService로 호출
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/layout");
        mav.addObject("title", "상품 문의 리스트");
        mav.addObject("showNavbar", true);
        mav.addObject("body", "/WEB-INF/views" + UtilMethod.getViewName(request) + ".jsp");
        mav.addObject("qnaList", qnaList);  // 상품 문의 목록을 JSP로 전달
        return mav;
    }

    @RequestMapping(value = "/product/qna/add/{pdt_id}", method = RequestMethod.GET)
    public ModelAndView showAddQnaForm(@PathVariable("pdt_id") int pdt_id, HttpServletRequest request) { 
        ModelAndView mav = new ModelAndView();
        try {
            mav.setViewName("common/layout");
            mav.addObject("title", "상품 문의 작성");
            mav.addObject("showNavbar", true);
            mav.addObject("body", "/WEB-INF/views" + UtilMethod.getViewName(request) + ".jsp");
            mav.addObject("pdt_id", pdt_id); // 상품 ID 전달
        } catch (Exception e) {
            // 예외 처리 로직 (로그 출력, 사용자에게 에러 메시지 등)
            mav.addObject("error", "오류가 발생했습니다.");
        }
        return mav;
    }
    // 상품 문의 작성 처리
    @RequestMapping(value = "/product/qna/add", method = RequestMethod.POST)
    public void addProductQna(@ModelAttribute ProductQnaDTO productQna, HttpServletResponse response, HttpSession session) throws Exception {
        String userId = (String) session.getAttribute("userId");
        productQna.setUser_id(userId);  // 상품 문의 작성자의 ID를 설정

        productService.insertProductQna(productQna);  // ProductService로 호출하여 상품 문의 등록

        // 상품 문의 등록 후 페이지 리디렉션 (알림 처리)
        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=UTF-8");
        out.println("<script type='text/javascript'>");
        out.println("alert('상품 문의가 등록되었습니다.');");
        out.println("window.location.href='/product/qna/list/" + productQna.getPdt_id() + "';");
        out.println("</script>");
    }

    // 상품 문의 수정 폼
    @RequestMapping(value = "/product/qna/edit/{qna_id}", method = RequestMethod.GET)
    public ModelAndView showEditQnaForm(@PathVariable("qna_id") int qna_id, HttpServletRequest request) throws Exception {
        ProductQnaDTO qna = productService.getProductQnaById(qna_id);  // ProductService로 호출
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/layout");
        mav.addObject("title", "상품 문의 수정");
        mav.addObject("showNavbar", true);
        mav.addObject("body", "/WEB-INF/views" + UtilMethod.getViewName(request) + ".jsp");
        mav.addObject("qna", qna); // 수정할 상품 문의 정보 전달
        return mav;
    }

    // 상품 문의 수정 처리
    @RequestMapping(value = "/product/qna/edit", method = RequestMethod.POST)
    public void editProductQna(@ModelAttribute ProductQnaDTO productQna, HttpServletResponse response) throws Exception {
        productService.updateProductQna(productQna);  // ProductService로 호출하여 상품 문의 수정
        // 수정 후 알림 처리
        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=UTF-8");
        out.println("<script type='text/javascript'>");
        out.println("alert('상품 문의가 수정되었습니다.');");
        out.println("window.location.href='/product/qna/list/" + productQna.getPdt_id() + "';");
        out.println("</script>");
    }

    // 상품 문의 삭제
    @RequestMapping(value = "/product/qna/delete/{qna_id}", method = RequestMethod.GET)
    public void deleteProductQna(@PathVariable("qna_id") int qna_id, HttpServletResponse response) throws Exception {
        productService.deleteProductQna(qna_id);  // ProductService로 호출하여 상품 문의 삭제
        // 삭제 후 알림 처리
        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=UTF-8");
        out.println("<script type='text/javascript'>");
        out.println("alert('상품 문의가 삭제되었습니다.');");
        out.println("window.location.href='/product/qna/list';");
        out.println("</script>");
    }
}