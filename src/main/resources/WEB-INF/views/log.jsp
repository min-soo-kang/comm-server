<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>${ServerConfig.processName}</title>
    <link  href="/css/main.css" rel="stylesheet" />
    <link href="/css/jquery.treegrid.css" rel="stylesheet">
</head>
<body>


<div class="container">
    <jsp:include page="header.jsp"/>

    <div class="content">
        <div class="log" id="log">

                <legend>Log Files</legend>
                <table class="table table-bordered tree-basic"  id="logFiles">
                    <tbody id="logTbody">

                    </tbody>
                </table>


            <fieldset id="FileForm">
                <legend name="fileName">log</legend>
                <h3><a href="javascript:logList()">로그 목록</a></h3>
                <div id="logScroll" style="width: 98%;height: 660px;overflow: auto;margin:5px auto;">
                    <table   >
                        <tbody id="fileTbody">

                        </tbody>
                    </table>
                </div>
            </fieldset>
        </div>
    </div>
</div>


</div>

<script src="/js/jquery-2.2.4.min.js"></script>
<script src="/js/ajax.js"></script>

<script type="text/javascript" src="/js/main.js"></script>
<script type="text/javascript" src="/js/login.js"></script>
<script type="text/javascript" src="/js/moment.min.js"></script>



<script src="/js/jquery.treegrid.min.js"></script>
<script type="text/javascript">
    var fileListTbody="";

    var fileList = ${fileList};
    $(document).ready(function() {



    function init(){
        initFileListTest(fileList);
        //console.log(fileListTbody);
        tbodyAppend("logTbody",fileListTbody);
        $('.tree-basic').treegrid();
    }
    init();
    });
</script>

<script type="text/javascript">

</script>
</body>
</html>
