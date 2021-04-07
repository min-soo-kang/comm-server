<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
        <div class="system" id="system">

                <legend>System Information</legend>

                <table id="systemTable">
                    <tr>
                        <td><strong>System Id :</strong></td>
                        <td><input type="text" name="id" value="${ServerConfig.processId}" readonly></td>
                    </tr>
                    <tr>
                        <td><strong>Ip Address :</strong></td>
                        <td><input type="text" name="ip" value="${ServerConfig.processId}" readonly></td>
                    </tr>
                    <tr>
                        <td><strong>Tcp Port :</strong></td>
                        <td><input type="text" name="Tcp" value="${ServerConfig.processId}" readonly></td>
                    </tr>
                    <tr>
                        <td><strong>Udp Port :</strong></td>
                        <td><input type="text" name="Udp" value="${ServerConfig.processId}" readonly></td>
                    </tr>
                    <tr>
                        <td><strong>Boot Time :</strong></td>
                        <td><input type="text" name="time" value="${ServerConfig.processId}" readonly></td>
                    </tr>
                </table>

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
