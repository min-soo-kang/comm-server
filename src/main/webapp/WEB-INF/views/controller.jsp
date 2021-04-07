<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>${ServerConfig.processName}</title>
    <link  href="/css/main.css" rel="stylesheet" />
</head>
<body>


<div class="container">
    <jsp:include page="header.jsp"/>

    <div class="content">

        <div class="controller" id="controller">

                <legend>Controller Information</legend>
                <div style="width: 98%;height: 660px;overflow: auto;margin:5px auto;">
                    <table id="controllerTable">
                        <thead>
                        <tr>
                            <th>관리번호</th>
                            <th>제어기 ID</th>
                            <th>제어기 명칭</th>
                            <th>IP Address</th>
                            <th>상태</th>
                            <th>마지막 연결 시간</th>
                            <th>마지막 해제 시간</th>
                            <th>접속횟수</th>
                            <th>명령</th>
                        </tr>
                        </thead>
                        <tbody id="controllerTbody">
                            <c:forEach var="entry" items="${list}" varStatus="status">
                            <tr>
                                <td>${entry.value.ID}</td>
                                <td>${entry.value.RSE_ID}</td>
                                <td>${entry.value.ISTL_LCTN_NM}</td>
                                <td>${entry.value.IP_ADDR}</td>
                                <td>${entry.value.netState}</td>
                                <td>2021-01-01-00:00:00</td>
                                <td>2021-01-01-00:00:00</td>
                                <td>1</td>
                                <td><a href=javascript:disConn('${entry.value.RSE_ID}')>연결해제</a> <a href=javascript:dsrcReset('${entry.value.RSE_ID}')>제어기리셋</a> <a href=javascript:countReset('${entry.value.RSE_ID}')>접속횟수초기화</a></td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="ControllerButtons">
                    <button type="button" onclick="allDisConn()">전체 해제</button>
                    <button type="button" onclick="allConnCountReset()">전체 접속횟수 초기화</button>
                </div>

        </div>

    </div>

</div>


</div>
<script src="/js/jquery-2.2.4.min.js"></script>
<script src="/js/ajax.js"></script>
<script type="text/javascript" src="/js/main.js"></script>
<script type="text/javascript" src="/js/login.js"></script>
<script type="text/javascript" src="/js/moment.min.js"></script>
</body>
</html>
