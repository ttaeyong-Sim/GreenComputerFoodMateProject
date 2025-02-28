<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>주문 및 결제</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        body {
            background-color: #f9f9f9;
            font-family: 'Arial', sans-serif;
            color: #333;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 1200px;
            margin: 50px auto;
            padding: 40px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        h1, h2 {
            text-align: center;
            font-size: 2.5rem;
            color: #333;
            margin-bottom: 30px;
        }

        .section-title {
            font-size: 1.8rem;
            color: #f39c12;
            border-bottom: 2px solid #f39c12;
            padding-bottom: 5px;
            margin-bottom: 30px;
            text-align: left;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            font-size: 1.1rem;
            font-weight: bold;
            color: #333;
            display: block;
            margin-bottom: 10px;
        }

        input, select, textarea {
            width: 100%;
            padding: 12px;
            font-size: 1rem;
            border-radius: 6px;
            border: 1px solid #ddd;
            box-sizing: border-box;
        }

        input:focus, select:focus, textarea:focus {
            border-color: #f39c12;
            outline: none;
        }

        .order-detail, .delivery-info, .orderer-info {
            margin-bottom: 40px;
            background-color: #f9f9f9;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        }

        .order-detail table {
            width: 100%;
            border-collapse: collapse;
        }

        .order-detail th, .order-detail td {
            padding: 12px;
            text-align: left;
            border: 1px solid #ddd;
        }

        .order-detail th {
            background-color: #f1f1f1;
        }

        .order-detail img {
            width: 200px;
            height: 200px;
            object-fit: cover;
            border-radius: 8px;
        }

        .order-detail .product-info {
            padding-left: 20px;
        }

        .orderer-info input, .delivery-info input {
            margin-bottom: 10px;
        }

        .button-container {
            text-align: center;
            margin-top: 30px;
        }

        .submit-btn {
            width: 100%;
            background-color: #f39c12;
            color: white;
            padding: 15px;
            font-size: 1.1rem;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .submit-btn:hover {
            background-color: #e67e22;
        }
        
        /* 우편번호와 버튼을 가로로 배치 */
		.zipcode-container {
		    display: flex;
		    align-items: center;
		    gap: 10px;
		}
		
		.zipcode-input {
		    width:35%;
		    padding: 12px;
		    font-size: 1rem;
		    border-radius: 6px;
		    border: 1px solid #ddd;
		    box-sizing: border-box;
		}
		
		.zipcode-btn {
		    padding: 12px 20px;
		    background-color: #f39c12;
		    color: white;
		    border-radius: 6px;
		    border: none;
		    cursor: pointer;
		    font-size: 1rem;
		    transition: background-color 0.3s ease;
		}
		
		.zipcode-btn:hover {
		    background-color: #e67e22;
		}
        
        .address-input {
		    margin-top: 10px;
		    padding: 12px;
		    font-size: 1rem;
		    border-radius: 6px;
		    border: 1px solid #ddd;
		    box-sizing: border-box;
		}
        /* 결제 정보 박스 */
		.payment-info-container {
		    background-color: #f9f9f9;
		    padding: 20px;
		    border-radius: 8px;
		    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
		    margin-bottom: 30px;
		}
		
		/* 결제 정보 항목 */
		.payment-info {
		    display: flex;
		    flex-direction: column;
		    gap: 15px;
		}
		
		/* 결제 항목 */
		.payment-detail {
		    display: flex;
		    justify-content: space-between;
		    font-size: 1.1rem;
		    color: #333;
		}
		
		/* 최종 결제 금액 강조 */
		.payment-detail.total p {
		    font-weight: bold;
		    font-size: 1.2rem;
		    color: #f39c12; /* 강조 색상 */
		}
		
		/* 결제 정보 제목 */
		.payment-info-container .section-title {
		    font-size: 1.8rem;
		    color: #f39c12;
		    border-bottom: 2px solid #f39c12;
		    padding-bottom: 5px;
		    margin-bottom: 20px;
		    text-align: left;
		}
		
		/* 결제 항목 가격 스타일 */
		.payment-detail p.price, .payment-detail .total-price {
		    font-size: 1.2rem;
		    color: #333;
		}

        /* 결제 수단을 감싸는 박스 */
		.payment-methods-container {
		    background-color: #f9f9f9;
		    padding: 20px;
		    border-radius: 8px;
		    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
		    margin-bottom: 30px;
		}
		
		/* 결제 수단 항목 */
		.payment-methods {
		    display: flex;
		    gap: 20px;
		}
		
		/* 각 결제 수단 카드 */
		.payment-methods .method {
		    background-color: white;
		    padding: 20px;
		    border-radius: 8px;
		    border: 1px solid #ddd;
		    text-align: center;
		    width: 20%;
		    transition: box-shadow 0.3s ease;
		}
		
		/* 결제 수단 카드 hover 효과 */
		.payment-methods .method:hover {
		    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
		}
		
		/* 클릭 시 선택된 상태 */
		.payment-methods .method.selected {
		    background-color: #f39c12;  
		    border-color: #e67e22;  
		    color: white;             
		}
		
		/* 결제 수단 이미지 */
		.payment-methods .method img {
		    width: 50px;
		    height: 50px;
		    margin-bottom: 10px;
		}
		
		/* 결제 수단 텍스트 */
		.payment-methods .method p {
		    font-size: 1.2rem;
		    font-weight:800;
		    margin-top: 5px;
		    color: #333;
		}
		
		/* 결제 수단 제목 */
		.section-title {
		    font-size: 1.8rem;
		    color: #f39c12;
		    border-bottom: 2px solid #f39c12;
		    padding-bottom: 5px;
		    margin-bottom: 20px;
		    text-align: left;  /* 제목 왼쪽 정렬 */
		}
		        
        #font {
         	font-weight: 800;
        }
    </style>
</head>
<script src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
function execDaumPostcode() {
	  new daum.Postcode({
	    oncomplete: function(data) {
	      // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

	      // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
	      // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	      var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
	      var extraRoadAddr = ''; // 도로명 조합형 주소 변수

	      // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	      // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	      if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	        extraRoadAddr += data.bname;
	      }
	      // 건물명이 있고, 공동주택일 경우 추가한다.
	      if(data.buildingName !== '' && data.apartment === 'Y'){
	        extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	      }
	      // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	      if(extraRoadAddr !== ''){
	        extraRoadAddr = ' (' + extraRoadAddr + ')';
	      }
	      // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
	      if(fullRoadAddr !== ''){
	        fullRoadAddr += extraRoadAddr;
	      }

	      // 우편번호와 주소 정보를 해당 필드에 넣는다.
	      document.getElementById('postal_Code').value = data.zonecode; //5자리 새우편번호 사용
	      document.getElementById('addr').value = fullRoadAddr;
	     
	    }
	  }).open();
	}
	// 
	document.addEventListener("DOMContentLoaded", function() {
    let deliveryConfirm = document.getElementById("delivery-confirm");
    if (deliveryConfirm) { // 요소가 존재하는 경우에만 이벤트 추가
        deliveryConfirm.addEventListener("change", function() {
            let selectedOption = this.options[this.selectedIndex]; // 선택한 옵션
            let to_Name = document.getElementById("to_name");
            let to_Phone_Num = document.getElementById("to_phone_num");
            let zipcodeInput = document.getElementById("postal_Code");
            let roadAddressInput = document.getElementById("addr");
            let detailAddressInput = document.getElementById("addr_Detail");

            if (selectedOption.value === "custom") {
                // 직접 입력 선택 시 빈칸 유지하고 활성화
                to_Name.value = "";
                to_Phone_Num.value = "";
                zipcodeInput.value = "";
                roadAddressInput.value = "";
                detailAddressInput.value = "";
//                zipcodeInput.readOnly = false;
//                roadAddressInput.readOnly = false;
//                detailAddressInput.readOnly = false;
            } else {
                // 선택된 주소 자동 입력 
                to_Name.value = selectedOption.getAttribute("data-toname");
                to_Phone_Num.value = selectedOption.getAttribute("data-tophonenum");
                zipcodeInput.value = selectedOption.getAttribute("data-zipcode");
                roadAddressInput.value = selectedOption.getAttribute("data-road-address");
                detailAddressInput.value = selectedOption.getAttribute("data-detail-address");

//                zipcodeInput.readOnly = true;
//                roadAddressInput.readOnly = true;
//                detailAddressInput.readOnly = true;
            }
        });
    } else {
        console.error("Element with ID 'delivery-confirm' not found.");
    }
});
</script>
<body>
    <div class="container">
        <!-- 주문 및 결제 -->
        <h1 id="font">상품 주문 및 결제</h1>
        <br>
        <br>

        <!-- 주문 상세 내역 -->
        <div class="order-detail">
            <h2 class="section-title">주문 상세 내역</h2>
            <table>
                <thead>
                    <tr>
                        <th>상품 정보</th>
                        <th>수량</th>
                        <th>금액</th>
                        <th>포인트 차감</th>
                        <th>쿠폰 차감</th>
                        <th>배송비</th>
                    </tr>
                </thead>
                <tbody>
                <c:set var="totalPrice" value="0" />
                <c:forEach var="orderitems" items="${orderItems}">
                <c:set var="totalPrice" value="${totalPrice + (orderitems.price * orderitems.qty)}" />
                    <tr>
                        <td class="product-info">
                            <img src="${pageContext.request.contextPath}/resources/images/${orderitems.img_path}" alt="상품 이미지">
                            <div id="product-option"><strong>상품명</strong>: ${orderitems.pdt_name}</div>
                        </td>
                        <td>${orderitems.qty}</td>
                        <td><fmt:formatNumber value="${orderitems.price * orderitems.qty}" type="number" groupingUsed="true" /></td>
                        <td>0p</td>
                        <td>0원</td>
                        <td>기본 3000원</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- 배송지 정보 -->
        <div class="delivery-info">
            <h2 class="section-title">배송지 정보</h2>
            <div class="form-group">
                <label for="delivery-confirm">배송지 확인</label>
                <select id="delivery-confirm">
                    <option value="custom">직접 입력</option>
                        <c:forEach var="delivery" items="${deliveryList}">
        				<option value="${delivery.addr_Nickname}"
        						data-toname="${delivery.to_Name}" 
        						data-tophonenum="${delivery.to_Phone_Num}"
				                data-zipcode="${delivery.postal_Code}" 
				                data-road-address="${delivery.addr}" 
				                data-detail-address="${delivery.addr_Detail}">
				        ${delivery.addr_Nickname}
				        </option>
				    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="recipient-name">받으실 분</label>
                <input type="text" id="to_name" name="to_name" placeholder="받으실 분의 이름" >
            </div>

            <div class="form-group">
                <label for="phone">전화번호</label>
                <input type="text" id="to_phone_num" name="to_phone_num" placeholder="전화번호 입력" >
            </div>

			<div class="form-group">
			    <label for="address">받으실 곳</label>
			    <div class="zipcode-container">
			        <input type="text" id="postal_Code" name="postal_Code" placeholder="우편번호" class="zipcode-input">
			        <button type="button" class="zipcode-btn" onclick="execDaumPostcode()">우편번호 검색</button>
			    </div>
			    <input type="text" id="addr" name="addr" placeholder="도로명 주소를 입력하세요" class="address-input">
			    <input type="text" id="addr_Detail" name="addr_Detail" placeholder="상세주소를 입력하세요" class="address-input">
			</div>
			
            <div class="form-group">
                <label for="delivery-requests">배송 요청 사항</label>
                <textarea id="delivery-requests" placeholder="배송에 대한 요청 사항을 입력하세요"></textarea>
            </div>
        </div>

        <!-- 주문자 정보 -->
        <div class="orderer-info">
            <h2 class="section-title">주문자 정보</h2> 
            <div class="form-group">
                <label for="orderer-name">주문하시는 분</label>
                <input type="text" id="orderer-name" placeholder="주문자 이름" value="${buyerInfo.name}">
            </div>

            <div class="form-group">
                <label for="orderer-phone">전화번호</label>
                <input type="text" id="orderer-phone" placeholder="전화번호" value="${buyerInfo.phone_num}">
            </div>

            <div class="form-group">
                <label for="orderer-email">이메일</label>
                <input type="email" id="orderer-email" placeholder="이메일" value="${buyerInfo.email}">
            </div>
        </div>
        
        <!-- 결제 정보  -->
		<div class="payment-info-container">
		    <h2 class="section-title">결제 정보</h2>
		    <div class="payment-info">
		    
		        <div class="payment-detail">
		            <label>상품 합계 금액</label>
		            
		            <p class="price">₩ <fmt:formatNumber value="${totalPrice}" type="number" groupingUsed="true" /></p>
		        </div>
		       	<div class="payment-detail">
		            <label>포인트 할인</label>
		            <p class="price">-₩ 0</p>
		        </div>
		        <div class="payment-detail">
		            <label>쿠폰 할인</label>
		            <p class="price">-₩ 0</p>
		        </div>
		        <div class="payment-detail">
		            <label>배송비</label>
		            <p class="price">₩ 0</p>
		        </div>
		        <div class="payment-detail total">
		            <label>최종 결제 금액</label>
		            <p class="total-price">₩ <fmt:formatNumber value="${totalPrice}" type="number" groupingUsed="true" /></p>
		        </div>
		    </div>
		</div>

		 <!-- 결제 수단  -->
		<div class="payment-methods-container">
		    <h2 class="section-title">결제 수단</h2>
		    <div class="payment-methods" data-method="card">
		        <div class="method">
		            <img src="${pageContext.request.contextPath}/resources/images/credit_card.png" alt="신용카드">
		            <p>신용카드</p>
		        </div>
		        <div class="method" data-method="vbank">
		            <img src="${pageContext.request.contextPath}/resources/images/bank_transfer.png" alt="무통장입금">
		            <p>무통장입금</p>
		        </div>
		        <div class="method" data-method="trans">
		            <img src="${pageContext.request.contextPath}/resources/images/account_transfer.png" alt="계좌이체">
		            <p>계좌이체</p>
		        </div>
		    </div>
		</div>
		<script>
				// 결제 수단 클릭 시 선택된 상태 추가/제거
		document.querySelectorAll('.payment-methods .method').forEach(function(method) {
		    method.addEventListener('click', function() {
		        // 모든 결제 수단에서 'selected' 클래스 제거
		        document.querySelectorAll('.payment-methods .method').forEach(function(m) {
		            m.classList.remove('selected');
		        });
		        // 클릭한 결제 수단에 'selected' 클래스 추가
		        method.classList.add('selected');
		        
		        selectedPaymentMethod = method.getAttribute('data-method') || "card";
		    });
		});
		</script>

        <!-- 결제 버튼 -->
        <div class="button-container">
            <button type="submit" class="submit-btn" onclick="requestPay()">결제하기</button>
            <button type="submit" class="submit-btn" onclick="requestOrder2()">결제패스</button>
        </div>
        <%
    		String impUid = (String) request.getAttribute("impUid");
		%>
        <script>
        	var orderItemList = ${orderItemList != null ? orderItemList : '[]'};
        	
        	function requestOrder2() {
        		$.ajax({
                    type: "POST",
                    url: "${contextPath}/order/setOrderItems",
                    contentType: "application/json",
                    data: JSON.stringify(orderItemList),  // 기존 데이터를 전송
                    success: function(response) {
                        // 데이터 저장 후 주문 완료 페이지로 이동
                        window.location.replace("${contextPath}/order/order2");
                    },
                    error: function(xhr, status, error) {
                        alert("결제 데이터 저장 중 오류 발생: " + error);
                    }
                });
        	}
        	
		    function requestPay() {
		    	// 옵션 값 가져오기 (id를 사용하여 직접 선택)
		        let optionElements = document.querySelectorAll("#product-option"); // 모든 상품명 요소 가져오기
				let options = [];
				
				optionElements.forEach(element => {
				    let optionText = element.textContent.split(":")[1].trim(); // "상품명: 상품A"에서 "상품A"만 추출
				    options.push(optionText);
				});
				
				let productName = options[0]; // 첫 번째 상품명
				let optionslength = options.length -1;
				if (options.length > 1) {
				    productName += `외 ` + optionslength + '건'; // 여러 개일 경우 "상품A + 외 N건" 형식으로 변환
				}
				
				console.log(productName);
		        
		    	// 주문자 정보 가져오기
		        let ordererName = document.getElementById("orderer-name").value || "홍길동";
			    let ordererPhone = document.getElementById("orderer-phone").value || "000-0000-0000";
			    let ordererEmail = document.getElementById("orderer-email").value || "honggildong@naver.com";
			    let ordererAddress = document.getElementById("addr").value || "서울특별시 강남구";
			    let ordererPostCode = document.getElementById("postal_Code").value || "12345";
		        
		        var IMP = window.IMP;
		        IMP.init("<%= impUid %>"); //  포트원에서 발급받은 가맹점 식별 코드 입력 (ex: imp12345678)
		
		        IMP.request_pay({
		            pg: "html5_inicis", //  결제 PG사 설정 (이니시스: "html5_inicis", 카카오페이: "kakaopay")
		            pay_method: selectedPaymentMethod, //  결제 수단 (ex: "card", "trans", "vbank", "phone")
		            merchant_uid: "order_" + new Date().getTime(), //  주문번호 (고유번호)
		            name: productName,
		            amount: 1000, //  결제 금액 (숫자로 입력)
		            buyer_email: ordererEmail,
		            buyer_name: ordererName,
		            buyer_tel: ordererPhone,
		            buyer_addr: ordererAddress,
		            buyer_postcode: ordererPostCode
		        }, function (rsp) {
		            if (rsp.success) {
		            	$.ajax({
		                    type: "POST",
		                    url: "${contextPath}/order/setOrderItems",
		                    contentType: "application/json",
		                    data: JSON.stringify(orderItemList),  // 기존 데이터를 전송
		                    success: function(response) {
		                        // 데이터 저장 후 주문 완료 페이지로 이동
		                        window.location.replace("${contextPath}/order/order2");
		                    },
		                    error: function(xhr, status, error) {
		                        alert("결제 데이터 저장 중 오류 발생: " + error);
		                    }
		                });
		            	
		            	// 결제 성공 시 페이지이동
//		            	window.location.replace("${contextPath}/order/order2");
		            	// 결제 성공 시, 서버에 결제 검증 요청
//		            	$.ajax({
//		                    type: "POST",
//		                    url: "${contextPath}/order/verify/" + rsp.imp_uid
//		            	}).done(function(data) {
//		                    if(rsp.paid_amount === data.response.amount){
//		                        alert("결제 성공");
//		                     
//		                    } else {
//		                        alert("결제 실패");
//		                    }
//		                });
		            	
		            } else {
		                alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
		            }
		        });
		    }
	    </script>
    </div>
</body>
</html>
