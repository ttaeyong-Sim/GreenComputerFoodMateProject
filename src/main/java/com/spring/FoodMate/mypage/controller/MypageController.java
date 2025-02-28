package com.spring.FoodMate.mypage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.FoodMate.common.SessionDTO;
import com.spring.FoodMate.common.UtilMethod;
import com.spring.FoodMate.member.dto.BuyerDTO;
import com.spring.FoodMate.mypage.dto.ProfileDTO;
import com.spring.FoodMate.mypage.service.DeliveryService;
import com.spring.FoodMate.mypage.service.MypageService;
import com.spring.FoodMate.mypage.service.ProfileService;
import com.spring.FoodMate.product.dto.ProductDTO;
import com.spring.FoodMate.product.service.ProductService;
import com.spring.FoodMate.recipe.service.RecipeService;


@Controller
public class MypageController {
	private static final Logger logger = LoggerFactory.getLogger(MypageController.class);
	@Autowired
	private MypageService mypageService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
    private RecipeService recipeService;
	
	@Autowired
    private ProfileService profileService;
	
	@Autowired
	private DeliveryService deliveryService;
	
	@RequestMapping(value="/mypage/mypageForm", method=RequestMethod.GET)
	private ModelAndView mypageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		
		BuyerDTO buyerInfo = (BuyerDTO) session.getAttribute("buyerInfo"); // 세션에서 buyerInfo 가져오기
		String byr_id = null;

		if (buyerInfo != null) {
		    byr_id = buyerInfo.getByr_id(); // byr_id 값 추출
		}
		
		ProfileDTO profileDTO = profileService.getBuyerProfile(byr_id);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("myrecipeList", recipeService.selectRecipeListByrID(byr_id)); //서비스에 selectRecipeList메소드있어야함
		mav.addObject("orderList", mypageService.getOrderById(byr_id));
		mav.addObject("profile", profileDTO);
		return mav;
	}
	
	@RequestMapping(value="/mypage/mateManage/myrecipeForm", method=RequestMethod.GET)
	private ModelAndView mateManagemyrecipeform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		
		BuyerDTO buyerInfo = (BuyerDTO) session.getAttribute("buyerInfo"); // 세션에서 buyerInfo 가져오기
		String byr_id = null;

		if (buyerInfo != null) {
		    byr_id = buyerInfo.getByr_id(); // byr_id 값 추출
		}
		
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("myrecipeList", recipeService.selectRecipeListByrID(byr_id)); //서비스에 selectRecipeList메소드있어야함
		return mav;
	}
	
	@RequestMapping(value="/mypage/mateManage/*Form", method=RequestMethod.GET)
	private ModelAndView mateManageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		return new ModelAndView();
	}
	
	@RequestMapping(value="/mypage/myInfoManage/memberEditForm", method=RequestMethod.GET)
	private ModelAndView myInfoManageMemberEditform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ModelAndView mav = new ModelAndView();
		mav.addObject("buyerInfo", (BuyerDTO) session.getAttribute("buyerInfo"));
		return mav;
	}
	
	@RequestMapping(value="/mypage/myInfoManage/profileEditForm", method=RequestMethod.GET)
	private ModelAndView profileEditform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		BuyerDTO buyerInfo = (BuyerDTO) session.getAttribute("buyerInfo"); // 세션에서 buyerInfo 가져오기
		String byr_id = null;

		if (buyerInfo != null) {
		    byr_id = buyerInfo.getByr_id(); // byr_id 값 추출
		}
		
		ProfileDTO profileDTO = profileService.getBuyerProfile(byr_id);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("profile", profileDTO);
		return mav;
	}
	
	@RequestMapping(value="/mypage/myInfoManage/deliveryManageForm", method=RequestMethod.GET)
	private ModelAndView deliveryManageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		BuyerDTO buyerInfo = (BuyerDTO) session.getAttribute("buyerInfo"); // 세션에서 buyerInfo 가져오기
		String byr_id = null;

		if (buyerInfo != null) {
		    byr_id = buyerInfo.getByr_id(); // byr_id 값 추출
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("deliveryList", deliveryService.getdeliveryList(byr_id));
		return mav;
	}
	
	@RequestMapping(value="/mypage/pointManage/*Form", method=RequestMethod.GET)
	private ModelAndView pointManageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		return new ModelAndView();
	}
	
	@RequestMapping(value="/mypage/ShoppingManage/*Form", method=RequestMethod.GET)
	private ModelAndView ShoppingManageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("action", action);	
		return new ModelAndView();
	}
	
	@RequestMapping(value="/mypage/ShoppingManage/orderlist", method=RequestMethod.GET)
	private ModelAndView orderlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		BuyerDTO memberVO = (BuyerDTO)session.getAttribute("buyerInfo");
		String id = memberVO.getByr_id();
		
//		List<OrderDTO> orderList = mypageService.getOrderById(id);
		
		// 오더리스트 만든 후 아무 작업도 없길래 임시조치로 위쪽 코드랑 비슷하게 넣어서 보냄
		mav.addObject("orderList", mypageService.getOrderById(id));
		return mav;
	}
	
	@RequestMapping(value="/mypage/customerManage/*Form", method=RequestMethod.GET)
	private ModelAndView customerManageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		return new ModelAndView();
	}
	
	// 여기부터 판매자 마이페이지
	
	@RequestMapping(value="/mypage_seller/mypage_sell_main", method=RequestMethod.GET)
	private ModelAndView seller_Mypage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", "FoodMate - 판매자 마이페이지");
		return mav;
	}
	
	@RequestMapping(value="/mypage_seller/mypage_sell_productlist", method=RequestMethod.GET)
	public ModelAndView seller_Mypage_productList(HttpServletRequest request, HttpSession session) throws Exception {
		    SessionDTO sellerInfo = (SessionDTO)session.getAttribute("sessionDTO");
		    List<ProductDTO> searchList = productService.ms_pdtList(sellerInfo.getUserId());
		    
		    ModelAndView mav = new ModelAndView();
            mav.addObject("title", "FoodMate - 내 상품 목록");
		    mav.addObject("list", searchList);
		    // Service 에 판매자 ID를 주고 해당하는 상품VO들의 List를 받아옴.
		    return mav;
	}
	
	@RequestMapping(value="/mypage_seller/mypage_sell_productamount", method=RequestMethod.GET)
	private ModelAndView seller_Mypage_productAmount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", "FoodMate - 상품 재고 관리");
		return mav;
	}

}
