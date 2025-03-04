<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매출 조회</title>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<link href="<c:url value='/resources/css/dashboard.css' />" rel="stylesheet">
<style>
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

        /* 통계 카드 스타일 */
        .stat-card {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
            padding: 15px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        .stat-card .info {
            flex-grow: 1;
        }
        .stat-card h5 {
            margin: 0;
            font-size: 18px;
            color: #007bff;
        }
        .stat-card p {
            font-size: 14px;
            color: #555;
        }

        /* 그래프 */
        .chart {
            margin-bottom: 20px;
        }

        /* 차트 크기 조정 */
        .chart canvas {
            width: 50% !important;
            height: 300px !important; /* 차트의 높이를 설정 */
        }

</style>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>

var contextPath = "${contextPath}";
//예시 차트 초기화 (Chart.js 사용 예)

document.addEventListener("DOMContentLoaded", function() {
    var contextPath = "${contextPath}";

    // 매출 차트 (Bar Chart)
    var salesCanvas = document.getElementById('salesChart');
    if (salesCanvas) {
        var salesCtx = salesCanvas.getContext('2d');
        var salesChart = new Chart(salesCtx, {
            type: 'bar',
            data: {
                labels: ['1월', '2월', '3월', '4월', '5월'],
                datasets: [{
                    label: '매출',
                    data: [1000000, 2000000, 1500000, 3000000, 2500000],
                    backgroundColor: '#36a2eb'
                }]
            }
        });
    }
});
</script>


</script>




</head>
<body>
<main>
<div class="container mt-1">
	<div class="d-flex align-items-center border-bottom pb-2 mb-3">
      		<h5 class="mb-0 fw-bold">매출 조회</h5>
    </div>
    <div id="sales" class="tab-pane">
                <div class="stat-card">
                    <div class="info">
                        <h5>오늘의 매출</h5>
                        <p>5,000,000원</p>
                    </div>
                    <div class="info">
                        <h5>이번 달 매출</h5>
                        <p>50,000,000원</p>
                    </div>
                    <div class="info">
                        <h5>전체 매출</h5>
                        <p>500,000,000원</p>
                    </div>
                </div>

                <!-- 그래프 (예시) -->
                <div class="chart">
                    <h4>월별 매출 추이</h4>
                    <canvas id="salesChart"></canvas> <!-- 차트 삽입 영역 -->
                </div>
            </div>

</div>
</main>
</body>
</html>