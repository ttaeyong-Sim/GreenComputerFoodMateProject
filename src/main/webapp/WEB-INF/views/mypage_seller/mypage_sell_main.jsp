<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<link href="<c:url value="/resources/css/profile.css" />" rel="stylesheet">
<style>
		.myproduct{
			width: 100%;
			margin: 0 auto;
		}

		.table-custom {
		    border-top: 2px solid #000 !important;
	 	 }
		/* 헤더 배경색 */
	    .table-header {
		  background-color: #f8f9fa;
	      text-align: center;
	      font-weight: bold;
	    }
	    /* 내용 텍스트 정렬 */
	    .table tbody td {
	      vertical-align: middle;
	      text-align: center;
	    }
</style>

<meta charset="UTF-8">
<title>마이페이지</title>
</head>

<body>
	<div class="container mt-2">
		<div class="row">
			<div class="col-md-3 mt-3">
				<div class="mainbox">
					<div class="name_icon_btn">
						<img src="${contextPath}/resources/images/logo.png" alt="" />
						<p> 프로필수정 </p>
						<p> 그린축산 </p>
						<p> 팔로워 : n명 </p>
					</div>
				</div>
			</div>
			
			<div class="col-md-9 mt-2">
			<h5 class="mb-0 fw-bold">BEST3 상품</h5>
	  		<p class="mb-0">더보기
    			<div class="myproduct">
    			
					<table class="table table-hover table-custom">
						<thead class="table-header table-secondary">
							<tr>
								<td>상품번호</td>
								<td>상품명</td>
								<td>재고수</td>
								<td>대기중인 주문</td>
								
								<td>공개여부</td>
								<td>삭제신청</td>
							</tr>
						</thead>
						
				      	<tbody>
				      	
				      	<c:choose>
							<c:when test="${empty stockList}">
								<p>표시할 내용이 없습니다.</p>
							</c:when>
						
						<c:otherwise>
							<c:forEach var="product" items="${stockList}">
							
					      	<tr>				                
				                <td>${product.pdt_id}</td>
				                <td style="text-align: left">
				                    <img src="${contextPath}/resources/images/${product.img_path}" alt="pdt_img" class="img-fluid rounded" style="width: 50px; height: 50px; object-fit: cover;">
				                    ${product.name}
				                </td>
				                <td>
								    <input type="text" class="form-control form-control-sm" value="${product.stock}" min="0" style="width: 80px; display: inline-block;" />
								    <button class="btn btn-outline-secondary btn-sm btn-stockchange" data-pdt-id="${product.pdt_id}">변경</button>
								</td>
								<td>${product.ord_count}건</td>
				                <td>
				                ${product.status == 'Y' ? '판매 중' : '판매 중지됨'}
				                <button class="btn btn-outline-secondary btn-sm btn-changestat" data-pdt-id="${product.pdt_id}" data-status="${product.status}">변경</button>
				                </td>
				                <td>
				                <button class="btn btn-outline-secondary btn-sm btn-deletepdt" data-pdt-id="${product.pdt_id}">삭제</button>
				                </td>
				            </tr>
				            
				            </c:forEach>
						</c:otherwise>
						</c:choose>
				      	
						</tbody>
					</table>
					
				</div>
			</div>
			
		</div>
		
		
	  <div class="d-flex justify-content-between align-items-center mt-3 mb-2">
	  		<h5 class="mb-0 fw-bold">배송 대기중인 주문</h5>
	  		<p class="mb-0">더보기
      </div>
      <table class="table table-hover table-custom">
		<thead class="table-header table-secondary">
			<tr>
				<td>주문번호</td>
				<td>주문일자</td>
				<td>수령인</td>
				<td>배송주소</td>
				<td>상세주소</td>
				<td>배송시작</td>
				<td>주문취소</td>
			</tr>
		</thead>
      	<tbody>
      	
      	<c:forEach var="ord" items="${ordList}">
      	<tr>
          <td>${ord.ord_id}</td>
          <td>${ord.create_Date}</td>
          <td>${ord.to_name}</td>
          <td>${ord.addr}</td>
          <td>${ord.addr_detail}</td>
          <td>
          	<button class="btn btn-outline-secondary btn-sm">배송시작</button>
          </td>
          <td>
            <button class="btn btn-outline-secondary btn-sm">주문 취소</button>
          </td>
        </tr>
        	<c:forEach var="detail" items="${ord.orderDetails}">
		        <tr>
		            <td colspan="7">
		                <img src="${contextPath}/resources/images/${detail.img_path}">
		                ${detail.pdt_name} - ${detail.qty}
		            </td>
		        </tr>
		    </c:forEach>
        </c:forEach>
        
		</tbody>
	</table>

	</div>
	
</body>
</html>