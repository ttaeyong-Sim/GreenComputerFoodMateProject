<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    request.setCharacterEncoding("UTF-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html lang="ko">
<head>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <meta charset="UTF-8">
    <title>판매자 관리</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .container {
            margin: 20px;
        }

        /* 탭 스타일 */
        .tabs {
            display: flex;
            cursor: pointer;
            margin-bottom: 20px;
        }
        .tab-item {
            padding: 10px 20px;
            background-color: #f4f4f4;
            border-radius: 5px;
            margin-right: 10px;
            transition: background-color 0.3s;
        }
        .tab-item:hover {
            background-color: #007bff;
            color: #fff;
        }
        .tab-item.active {
            background-color: #007bff;
            color: #fff;
        }
        
         /* 검색창 스타일 */
        .search-bar {
            display: flex;
            margin-bottom: 20px;
        }
        .search-bar input {
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            flex: 1;
        }
        .search-bar select {
            padding: 8px 12px;
            margin-left: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .search-bar button {
            padding: 8px 16px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            margin-left: 10px;
            cursor: pointer;
        }
        .search-bar button:hover {
            background-color: #0056b3;
        }

        /* 표 스타일 */
        .report-list {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .report-list th,
        .report-list td {
            padding: 12px 20px;
            text-align: left;
        }

        /* 표 제목(헤더) 부분 스타일 */
        .report-list th {
            background-color: #f4f4f4;
            color: #333;
            font-weight: bold;
            border-top: 2px solid #ddd;
        }

        /* 표 본문 부분 스타일 */
        .report-list td {
            background-color: #ffffff;
            border-top: 1px solid #ddd;
            color: #555;
        }

        /* 마우스 오버 시 색상 변경 */
        .report-list tr:hover td {
            background-color: #f1f1f1;
        }

        /* 페이지네이션 */
        .pagination {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
        .pagination a {
            padding: 8px 16px;
            margin: 0 5px;
            background-color: #f4f4f4;
            border-radius: 5px;
            color: #007bff;
            text-decoration: none;
        }
        .pagination a.active {
            background-color: #007bff;
            color: white;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>판매자 관리</h2>

        <!-- 탭 UI -->
        <div class="tabs">
            <div class="tab-item active" data-tab="active">활동 중인 판매자</div>
            <div class="tab-item" data-tab="inactive">휴면 상태 판매자</div>
        </div>
        
        <!-- 검색 기능 -->
        <div class="search-bar">
            <input type="text" id="searchInput" placeholder="검색어를 입력하세요" />
            <select id="searchFilter">
            	<option value="name">판매자 이름</option>
                <option value="email">이메일</option>
            </select>
            <button onclick="searchMember()">검색</button>
        </div>

        <%-- 현재 페이지 정보 가져오기 (기본값: 1페이지) --%>
        <c:set var="AllcurrentPage" value="${param.page != null ? param.page : 1}" />
		<c:set var="AllitemsPerPage" value="6" />
		<c:set var="AllstartIndex" value="${(AllcurrentPage - 1) * AllitemsPerPage}" />
		<c:set var="AllendIndex" value="${AllcurrentPage * AllitemsPerPage}" />
			
		<%-- 전체 데이터 개수 구하기 --%>
		<c:set var="AlltotalItems" value="${fn:length(AllmemberList)}" />
		<fmt:parseNumber var="AllparsedTotalPages" value="${(AlltotalItems + AllitemsPerPage - 1) / AllitemsPerPage}" integerOnly="true" />
		<c:set var="AlltotalPages" value="${AllparsedTotalPages}" />
        <!-- 탭 내용 -->
        <div class="tab-content">
            <!-- 활동 중인 판매자 -->
            <div id="active" class="tab-pane active">
                <table class="report-list">
                    <thead>
                        <tr>
                            <th>프로필 이미지</th>
                            <th>판매자 이름</th>
                            <th>판매 상품 수</th>
                            <th>달 매출</th>
                            <th>가입일</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="allmember" items="${AllmemberList}" varStatus="status">
			      		<c:if test="${status.index >= AllstartIndex && status.index < AllendIndex}">
		                        <tr>
		                            <td><img src="${contextPath}/resources/images/${allmember.img_path}" alt="${allmember.name}" width="100" height="100" style="object-fit: cover; border-radius: 8px;"></td>
		                            <td><a href="${contextPath}/admin/AccountManage/adminSellerDetail.do">${allmember.name}</a></td>
		                            <td>${allmember.product_count}개</td>
		                            <td><fmt:formatNumber value="${allmember.monthly_sales}" type="number" groupingUsed="true" />원</td>
		                            <td>${allmember.join_date}</td>
		                        </tr>
                        <!-- 추가 활동 중인 판매자들 -->
                        </c:if>
	                </c:forEach>
                    </tbody>
                </table>
                <!-- 페이지네이션 -->
				<div class="pagination">
				    <%-- 이전 페이지 버튼 --%>
				    <c:if test="${AllcurrentPage > 1}">
				        <a href="?tab=all&page=${AllcurrentPage - 1}">이전</a>
				    </c:if>
				
				    <%-- 페이지 번호 표시 --%>
				    <c:forEach var="i" begin="1" end="${AlltotalPages}">
				        <a href="?tab=all&page=${i}" class="${i == AllcurrentPage ? 'active' : ''}">${i}</a>
				    </c:forEach>
				
				    <%-- 다음 페이지 버튼 --%>
				    <c:if test="${AllcurrentPage < AlltotalPages}">
				        <a href="?tab=all&page=${AllcurrentPage + 1}">다음</a>
				    </c:if>
				</div>
            </div>

			<%-- 현재 페이지 정보 가져오기 (기본값: 1페이지) --%>
			<c:set var="INACTcurrentPage" value="${param.page != null ? param.page : 1}" />
			<c:set var="INACTitemsPerPage" value="6" />
			<c:set var="INACTstartIndex" value="${(INACTcurrentPage - 1) * INACTitemsPerPage}" />
			<c:set var="INACTendIndex" value="${INACTcurrentPage * INACTitemsPerPage}" />
			
			<%-- 전체 데이터 개수 구하기 --%>
			<c:set var="INACTtotalItems" value="${fn:length(SleepingmemberList)}" />
			<fmt:parseNumber var="INACTparsedTotalPages" value="${(INACTtotalItems + INACTitemsPerPage - 1) / INACTitemsPerPage}" integerOnly="true" />
			<c:set var="INACTtotalPages" value="${INACTparsedTotalPages}" />
            <!-- 휴면 상태 판매자 -->
            <div id="inactive" class="tab-pane">
                <table class="report-list">
                    <thead>
                        <tr>
                            <th>이미지</th>
                            <th>판매자 이름</th>
                            <th>판매 상품 수</th>
                            <th>달 매출</th>
                            <th>가입일</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="inactmember" items="${SleepingmemberList}" varStatus="status">
			      		<c:if test="${status.index >= INACTstartIndex && status.index < INACTendIndex}">
		                        <tr>
		                            <td><img src="${contextPath}/resources/images/${inactmember.img_path}" alt="${inactmember.name}" width="100" height="100" style="object-fit: cover; border-radius: 8px;"></td>
		                            <td><a href="${contextPath}/admin/AccountManage/adminSellerDetail.do">${inactmember.name}</a></td>
		                            <td>${inactmember.product_count}개</td>
		                            <td><fmt:formatNumber value="${inactmember.monthly_sales}" type="number" groupingUsed="true" />원</td>
		                            <td>${inactmember.join_date}</td>
		                        </tr>
                        <!-- 추가 휴면 상태 판매자들 -->
                        </c:if>
	                </c:forEach>
                    </tbody>
                </table>
                <!-- 페이지네이션 -->
				<div class="pagination">
				    <%-- 이전 페이지 버튼 --%>
				    <c:if test="${INACTcurrentPage > 1}">
				        <a href="?tab=inactive&page=${INACTcurrentPage - 1}">이전</a>
				    </c:if>
				
				    <%-- 페이지 번호 표시 --%>
				    <c:forEach var="i" begin="1" end="${INACTtotalPages}">
				        <a href="?tab=inactive&page=${i}" class="${i == INACTcurrentPage ? 'active' : ''}">${i}</a>
				    </c:forEach>
				
				    <%-- 다음 페이지 버튼 --%>
				    <c:if test="${INACTcurrentPage < INACTtotalPages}">
				        <a href="?tab=inactive&page=${INACTcurrentPage + 1}">다음</a>
				    </c:if>
				</div>
            </div>
        </div>
    </div>

    <script>
        // 탭 클릭 시 해당 탭으로 이동
        $(".tab-item").on("click", function() {
            var tabId = $(this).data("tab");
            $(".tab-item").removeClass("active");
            $(this).addClass("active");
            $(".tab-pane").removeClass("active");
            $("#" + tabId).addClass("active");
        });
        
        function searchMember() {
            var keyword = document.getElementById("searchInput").value;  // 검색어 입력 값
            var searchType = document.getElementById("searchFilter").value; // 검색 유형 선택 값
            var contextPath = "${pageContext.request.contextPath}";
            
            var urlParams = new URLSearchParams(window.location.search);
            var tab = urlParams.get("tab") || ""; // tab이 없으면 빈 값 설정

            var url = contextPath + "/admin/AccountManage/adminSellers?searchtype=" + encodeURIComponent(searchType) + "&keyword=" + encodeURIComponent(keyword) + "&tab=" + encodeURIComponent(tab);

            window.location.href = url; // 검색 요청 실행
        }
    </script>
</body>
</html>
