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

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>

const contextPath = '${contextPath}';

// 재고 변경
$(document).on("click", ".btn-stockchange", function() {
    let pdtId = $(this).data("pdt-id"); // 상품 ID 가져오기
    let stockInput = $(this).prev("input"); // 해당 버튼 앞에 있는 입력 필드 찾기
    let newStock = stockInput.val(); // 입력된 재고 값 가져오기

    // 입력값 검증
    if (newStock < 0) {
        alert("재고는 0 이상이어야 합니다.");
        stockInput.val(stockInput.data("original-stock")); // 원래 값으로 되돌리기
        return;
    }

    if (isNaN(newStock)) {
        alert("올바른 숫자를 입력하세요.");
        stockInput.val(stockInput.data("original-stock")); // 원래 값으로 되돌리기
        return;
    }

    $.ajax({
        type: "POST",
        url: contextPath + "/product/updateStock", // 서버에서 처리할 URL
        data: { pdt_id: pdtId, stock: newStock }, // 상품 ID와 변경된 재고 값 전송
        success: function(response) {
            if (response.success) {
                alert("재고가 변경되었습니다.");
                location.reload(); // 변경된 정보 새로고침
            } else {
                alert(response.message || "재고 변경에 실패했습니다.");
            }
        },
        error: function() {
            alert("오류가 발생했습니다. 다시 시도해주세요.");
        }
    });
});

// 상품 판매상태 변경
$(document).on('click', '.btn-changestat', function() {
    var pdtId = $(this).data('pdt-id');  // pdt_id 가져오기
    var currentStatus = $(this).data('status');  // 현재 상태 확인

    // AJAX 요청
    $.ajax({
        type: "POST",
        url: contextPath + "/product/changeStat",  // 요청 주소
        data: {
            pdt_id: pdtId,
            status: currentStatus  // 상태 값 (Y 또는 N)
        },
        success: function(response) {
            if (response.success) {
                // 상태 변경이 성공하면 새로고침
            	var newStatusText = (currentStatus === 'Y') ? '판매 중지됨' : '판매 중';
                alert("판매 상태가 변경되었습니다.");
                location.reload();
            } else {
                alert("상태 변경에 실패했습니다.");
            }
        },
        error: function() {
            alert("오류가 발생했습니다. 다시 시도해주세요.");
        }
    });
});

// 상품 삭제 요청 ajax
$(document).on("click", ".btn-deletepdt", function () {
    if (!confirm("상품을 삭제하시겠습니까? 이 결정은 되돌릴 수 없습니다.")) {
        return;
    }

    var pdtId = $(this).data("pdt-id");
    var contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2)); // contextPath 자동 설정

    $.ajax({
        type: "POST",
        url: contextPath + "/product/pdtdeleteAjax",
        contentType: "application/json",
        data: JSON.stringify({ pdt_id: pdtId }),
        success: function (response) {
            if (response.success) {
                alert("상품이 성공적으로 삭제되었습니다.");
                location.reload(); // 페이지 새로고침
            } else {
                alert("상품 삭제에 실패했습니다. 다시 시도해주세요.");
            }
        },
        error: function () {
            alert("서버와의 통신 중 오류가 발생했습니다.");
        }
    });
});

// 운송장번호 입력버튼 누르면 모달띄우는친구
$(document).on("click", ".waybill-button", function() {
    let ordId = $(this).data("ord-id"); // 버튼에서 ord_id 가져오기
    $("#ord_id").val(ordId); // 모달 내부에 설정
    $("#waybillModal").modal("show"); // 모달 열기
});

// 운송장번호저장누르면 ajax요청하는친구
$(document).on("click", "#saveWaybill", function() {
    let ordId = $("#ord_id").val();
    let delCode = $("#del_code").val();
    let waybillNum = $("#waybill_num").val();

    if (!waybillNum.trim()) {
        alert("운송장번호를 입력하세요.");
        return;
    }

    $.ajax({
        url: contextPath + "/order/updateWaybill",
        type: "POST",
        data: {
            ord_id: ordId,
            del_code: delCode,
            waybill_num: waybillNum
        },
        success: function(response) {
            if (response.success) {
                alert("운송장 정보가 저장되었습니다.");
                $("#waybillModal").modal("hide"); // 모달 닫기
                location.reload(); // 페이지 새로고침
            } else {
                alert("저장 실패: " + response.message);
            }
        },
        error: function() {
            alert("오류 발생. 다시 시도해주세요.");
        }
    });
});

</script>


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
								<td>상품 삭제</td>
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
          	<button class="btn btn-outline-secondary btn-sm waybill-button" data-ord-id="${ord.ord_id}">운송장번호입력</button>
          </td>
        </tr>
        	<c:forEach var="detail" items="${ord.orderDetails}">
	        <tr>
	        	<td></td>
	        	<td>
                <img src="${contextPath}/resources/images/${detail.img_path}" style="width: 50px">
                ${detail.pdt_name}, ${detail.qty}ea
                </td>
	        </tr>
	        </c:forEach>
	    </c:forEach>
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
	
	<!-- 운송장번호 입력하는 모달 창 -->
<div id="waybillModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">운송장번호 입력</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <input type="hidden" id="ord_id">
                <div class="form-group">
                    <label for="del_code">택배사</label>
                    <select id="del_code" class="form-control">
                        <option value="kr.cjlogistics">CJ대한통운</option>
                        <option value="kr.lotte">롯데택배</option>
                        <option value="kr.hanjin">한진택배</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="waybill_num">운송장번호</label>
                    <input type="text" id="waybill_num" class="form-control">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="saveWaybill">저장</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>
	
</body>
</html>