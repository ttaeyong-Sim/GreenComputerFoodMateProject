package com.spring.FoodMate.admin.controller;

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

import com.spring.FoodMate.admin.service.AdminService;
import com.spring.FoodMate.common.UtilMethod;
import com.spring.FoodMate.member.dto.BuyerDTO;


@Controller
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value="/admin/adminMain", method=RequestMethod.GET)
	private ModelAndView main(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		
		return mav;
	}
	
	@RequestMapping(value="/admin/AccountManage/*", method=RequestMethod.GET)
	private ModelAndView AdminAccountManage(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, @RequestParam(value="tab", required=false) String tab, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능
		return mav;
	}

	@RequestMapping(value="/admin/AccountManage/adminUsers", method=RequestMethod.GET)
	private ModelAndView AdminAccountAdminUsers(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, @RequestParam(value="tab", required=false) String tab, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		List<BuyerDTO> SleepingmemberList = adminService.getAdminBuyerInfo("sleeping");
		List<BuyerDTO> DeletingmemberList = adminService.getAdminBuyerInfo("deleting");
		List<BuyerDTO> ActivememberList = adminService.getAdminBuyerInfo("ACTIVE");
		List<BuyerDTO> AllmemberList = adminService.getAdminBuyerInfo("");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능
		mav.addObject("AllmemberList", AllmemberList);
		mav.addObject("ActivememberList", ActivememberList);
		mav.addObject("DeletingmemberList", DeletingmemberList);
		mav.addObject("SleepingmemberList", SleepingmemberList);
		return mav;
	}
	
	@RequestMapping(value="/admin/AdminManage/*", method=RequestMethod.GET)
	private ModelAndView AdminAdminManage(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, @RequestParam(value="tab", required=false) String tab, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능

		return mav;
	}
	
	@RequestMapping(value="/admin/InquiryManage/*", method=RequestMethod.GET)
	private ModelAndView AdminInquiryManage(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, @RequestParam(value="tab", required=false) String tab, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능

		return mav;
	}
	
	@RequestMapping(value="/admin/PaymentManage/*", method=RequestMethod.GET)
	private ModelAndView AdminPaymentManage(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, @RequestParam(value="tab", required=false) String tab, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능

		return mav;
	}
	
	@RequestMapping(value="/admin/RecipeProductManage/*", method=RequestMethod.GET)
	private ModelAndView AdminRecipeProductManage(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, @RequestParam(value="tab", required=false) String tab, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능

		return mav;
	}
	
	@RequestMapping(value="/admin/StatisticsManage/*", method=RequestMethod.GET)
	private ModelAndView AdminStatisticsManage(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, @RequestParam(value="tab", required=false) String tab, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능

		return mav;
	}


}
