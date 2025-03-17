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
    <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
    <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css"/>
    <link href="<c:url value='/resources/css/slide.css' />" rel="stylesheet">
    <script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
    <meta charset="UTF-8">
    <title>관리자 페이지</title>
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
            background-color: #f4f4f4;  /* 상단 제목은 회색 */
            color: #333;
            font-weight: bold;
            border-top: 2px solid #ddd;
        }

        /* 표 본문 부분 스타일 */
        .report-list td {
            background-color: #ffffff;  /* 본문은 흰색 */
            border-top: 1px solid #ddd;
            color: #555;
        }        

        /* 마우스 오버 시 색상 변경 */
        .report-list tr:hover td {
            background-color: #f1f1f1;
        }

        /* 버튼 스타일 */
        .btn-detail {
            color: #007bff;
            text-decoration: none;
        }
        .btn-detail:hover {
            text-decoration: underline;
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
        
        /* 테이블 본문에 마지막 행 밑줄 추가 */
        .report-list tr:last-child td {
            border-bottom: 1px solid #ddd; /* 마지막 행에만 밑줄 추가 */
        }

        /* 이미지 크기 조정 */
        .recipe-image {
            width: 100px;
            height: 100px;
            object-fit: cover;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>레시피 관리</h2>

        <!-- 탭 UI -->
        <div class="tabs">
            <div class="tab-item active" data-tab="registered">등록된 레시피</div>
            <div class="tab-item" data-tab="unapproved">신고 처리 레시피</div>
        </div>
        <!-- 검색 기능 -->
        <div class="search-bar">
            <input type="text" id="searchInput" placeholder="검색어를 입력하세요" />
            <select id="searchFilter">
                <option value="title">레시피 제목</option>
                <option value="name">작성자</option>
            </select>
            <button onclick="searchRecipes()">검색</button>
        </div>


		<%-- 현재 페이지 정보 가져오기 (기본값: 1페이지) --%>
		<c:set var="AllcurrentPage" value="${param.page != null ? param.page : 1}" />
		<c:set var="AllitemsPerPage" value="6" />
		<c:set var="AllstartIndex" value="${(AllcurrentPage - 1) * AllitemsPerPage}" />
		<c:set var="AllendIndex" value="${AllcurrentPage * AllitemsPerPage}" />
			
		<%-- 전체 데이터 개수 구하기 --%>
		<c:set var="AlltotalItems" value="${fn:length(AllrecipeList)}" />
		<fmt:parseNumber var="AllparsedTotalPages" value="${(AlltotalItems + AllitemsPerPage - 1) / AllitemsPerPage}" integerOnly="true" />
		<c:set var="AlltotalPages" value="${AllparsedTotalPages}" />
        <!-- 탭 내용 -->
        <div class="tab-content">
            <!-- 등록된 레시피 -->
            <div id="registered" class="tab-pane active">
                <table class="report-list">
                    <thead>
                        <tr>
                            <th>이미지</th>
                            <th>레시피 제목</th>
                            <th>작성자</th>
                            <th>조회수</th>
                            <th>별점</th>
                            <th>등록일</th>
                        </tr>
                    </thead>
                    <tbody>
					<c:forEach var="allrecipe" items="${AllrecipeList}" varStatus="status">
			      		<c:if test="${status.index >= AllstartIndex && status.index < AllendIndex}">
	                        <tr>
	                            <td><img src="${contextPath}/resources/images/${allrecipe.mainimg_path}" alt="${allrecipe.name}이미지" class="recipe-image"></td>
	                            <td><a href="${contextPath}/recipe/recipe_Detail?rcp_id=${allrecipe.rcp_id}" class="btn-detail">${allrecipe.title}</a></td>
	                            <td>${allrecipe.name}</td>
	                            <td>${allrecipe.views}</td>
	                            <td>${allrecipe.rating}</td>
	                            <td>${allrecipe.create_date}</td>
	                        </tr>
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
		<c:set var="RecurrentPage" value="${param.page != null ? param.page : 1}" />
		<c:set var="ReitemsPerPage" value="6" />
		<c:set var="RestartIndex" value="${(RecurrentPage - 1) * ReitemsPerPage}" />
		<c:set var="ReendIndex" value="${RecurrentPage * ReitemsPerPage}" />
			
		<%-- 전체 데이터 개수 구하기 --%>
		<c:set var="RetotalItems" value="${fn:length(ReportedrecipeList)}" />
		<fmt:parseNumber var="ReparsedTotalPages" value="${(RetotalItems + ReitemsPerPage - 1) / ReitemsPerPage}" integerOnly="true" />
            <!-- 미처리 레시피 -->
            <div id="unapproved" class="tab-pane">
                <table class="report-list">
                    <thead>
                        <tr>
                            <th>이미지</th>
                            <th>레시피 제목</th>
                            <th>작성자</th>
                            <th>조회수</th>
                            <th>별점</th>
                            <th>등록일</th>
                            <th>처리</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="RPrecipe" items="${ReportedrecipeList}" varStatus="status">
			      		<c:if test="${status.index >= RestartIndex && status.index < ReendIndex}">
                        <tr>
                            <td><img src="${contextPath}/resources/images/${RPrecipe.mainimg_path}" alt="${RPrecipe.name}이미지" class="recipe-image"></td>
	                        <td><a href="${contextPath}/recipe/recipe_Detail?rcp_id=${RPrecipe.rcp_id}" class="btn-detail">${RPrecipe.title}</a></td>
	                        <td>${RPrecipe.name}</td>
	                        <td>${RPrecipe.views}</td>
	                        <td>${RPrecipe.rating}</td>
	                        <td>${RPrecipe.create_date}</td>
                            <td><button class="btn btn-primary">처리</button></td>
                        </tr>
                    	</c:if>
	                </c:forEach>
                    </tbody>
                </table>
				<!-- 페이지네이션 -->
				<div class="pagination">
				    <%-- 이전 페이지 버튼 --%>
				    <c:if test="${RecurrentPage > 1}">
				        <a href="?tab=unapproved&page=${RecurrentPage - 1}">이전</a>
				    </c:if>
				
				    <%-- 페이지 번호 표시 --%>
				    <c:forEach var="i" begin="1" end="${RetotalPages}">
				        <a href="?tab=unapproved&page=${i}" class="${i == RecurrentPage ? 'active' : ''}">${i}</a>
				    </c:forEach>
				
				    <%-- 다음 페이지 버튼 --%>
				    <c:if test="${RecurrentPage < RetotalPages}">
				        <a href="?tab=unapproved&page=${RecurrentPage + 1}">다음</a>
				    </c:if>
				</div>
            </div>
        </div>
    </div>

    <script>
        // 탭 클릭 시 해당 탭으로 이동
        $(document).ready(function() {
        var tab = "${tab}"; // 서버에서 받아온 tab 값
        var contextPath = "${pageContext.request.contextPath}";

        if (tab) {
            activateTab(tab);
        }

        $(".tab-item").on("click", function() {
            var tabId = $(this).data("tab");
            activateTab(tabId);
            history.pushState(null, null, "?tab=" + tabId);
        });

        function activateTab(tabName) {
            $(".tab-item").removeClass("active");
            $(".tab-pane").removeClass("active");
            $(".tab-item[data-tab='" + tabName + "']").addClass("active");
            $("#" + tabName).addClass("active");
        }
    });
        
        function searchRecipes() {
            var keyword = document.getElementById("searchInput").value;  // 검색어 입력 값
            var searchType = document.getElementById("searchFilter").value; // 검색 유형 선택 값
            var contextPath = "${pageContext.request.contextPath}";
            
            var urlParams = new URLSearchParams(window.location.search);
            var tab = urlParams.get("tab") || ""; // tab이 없으면 빈 값 설정

            var url = contextPath + "/admin/RecipeProductManage/adminRecipe?searchtype=" + encodeURIComponent(searchType) + "&keyword=" + encodeURIComponent(keyword) + "&tab=" + encodeURIComponent(tab);

            window.location.href = url; // 검색 요청 실행
        }
    </script>
</body>
</html>
