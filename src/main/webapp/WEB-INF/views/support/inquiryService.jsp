<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>고객센터 Q&A</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 0; padding: 0; }
    .header { display: flex; justify-content: space-between; align-items: center; padding: 20px; background-color: #f8f8f8; }
    .logo { font-size: 24px; font-weight: bold; color: #f39c12; }
    .search-container { display: flex; }
    .search-bar { padding: 10px; width: 250px; border-radius: 5px; border: 1px solid #ccc; }
    .search-btn { padding: 10px 20px; background-color: #f39c12; border: none; border-radius: 5px; color: white; cursor: pointer; }
    .search-btn:hover { background-color: #e67e22; }
    .categories { display: flex; justify-content: space-around; margin-top: 20px; }
    .categories a { color: #f39c12; font-weight: bold; text-decoration: none; }
    .categories a:visited { color: #f39c12; }
    .categories a:hover { color: #e67e22; }
    .category { padding: 10px 20px; border: 1px solid #ccc; border-radius: 5px; cursor: pointer; }
    .category.active { border-color: #f39c12; color: #f39c12; }
    .qa-section { margin-top: 30px; padding: 20px; display: flex; flex-wrap: wrap; gap: 20px; }
    .qa-card { width: 100%; padding: 15px; border: 1px solid #ccc; border-radius: 8px; background-color: #fff; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); }
    .qa-header h3 { margin-bottom: 20px; font-size: 20px; font-weight: bold; }
    .qa-item { margin-bottom: 15px; }
    .qa-item strong { display: block; font-size: 16px; color: #333; }
    .qa-item p { margin: 5px 0 0; color: #555; }
    .contact-btn { width:120px; padding: 12px 25px; background-color: #f39c12; color: white; border: none; border-radius: 6px; font-size: 1.1rem; cursor: pointer; transition: background-color 0.3s; }
    .contact-btn a { text-decoration:none; color:#fff; }
    .contact-btn:hover { background-color: #e67e22; }
  </style>
</head>
<body>
  <div class="header">
    <div class="logo">❓ Q&A</div>
    <div class="search-container">
      <input type="text" placeholder="자주 묻는 질문 검색" class="search-bar" />
      <button class="search-btn">검색</button>
    </div>
  </div>

  <div class="categories">
    <div class="category"><a href="${contextPath}/support/inquiryLists">문의 내역</a></div>
    <div class="category"><a href="${contextPath}/support/faq">회원 문의</a></div>
    <div class="category"><a href="${contextPath}/support/inquiryProduct">상품 문의</a></div>
    <div class="category active"><a href="${contextPath}/support/inquiryService">서비스 문의</a></div>
  </div>

  <div class="qa-section">
  
    <div class="qa-card">
      <h3>서비스 문의</h3>
      <div class="qa-item">
        <strong>Q: 배송 기간은 얼마나 걸리나요?</strong>
        <p>A: 일반적으로 주문 후 2~3일 내에 배송됩니다. 지역에 따라 차이가 있을 수 있습니다.</p>
      </div>
      <div class="qa-item">
        <strong>Q: 배송 상태를 어디에서 확인할 수 있나요?</strong>
        <p>A: 마이페이지의 '주문 내역'에서 실시간 배송 상태를 확인할 수 있습니다.</p>
      </div>
      <div class="qa-item">
        <strong>Q: 고객센터 운영시간이 궁금해요.</strong>
        <p>A: 고객센터는 평일 오전 9시부터 오후 6시까지 운영됩니다.</p>
      </div>
    <div class="contact-btn"><a href="${contextPath}/support/inquiryLists">1:1 문의</a></div>
    </div>
  </div>


</body>
</html>