<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${ServerConfig.processName}</title>
    <link href="/css/main.css" rel="stylesheet" />
</head>
<body>

<div class="container">
    <div class ="title" >${ServerConfig.processName}</div>
    <hr>
    <div class="content">
        <form id="loginForm" name="loginForm" action="/login" method="post">

            <table style="width: 50%;">
                <tr>
                    <td style="text-align: right"><label for="username">USER ID : </label></td>
                    <td style="text-align: left"><input type="text" id="username" name="username"></td>
                </tr>
                <tr>
                    <td style="text-align: right"><label for="password" >PASSWORD : </label></td>
                    <td style="text-align: left"><input type="password" id="password" name="password"></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center"><input type="submit" style="margin: 0 auto;" value="LOG IN"></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<script src="/js/jquery-2.2.4.min.js"></script>
<script src="/js/login.js"></script>

</body>
</html>