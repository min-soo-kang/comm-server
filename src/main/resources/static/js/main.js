



function init(){
    initFileList(_fileList,1);
    //console.log(fileListTbody);
    tbodyAppend("logTbody",fileListTbody);
}

/*
    서버시간 요청 함수
 */
setInterval(function () {
    /*var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");*/

    var nowTime = new Date();
    $("#serverTime").text(moment(new Date()).format('YYYY-MM-DD HH:mm:ss'));
   /* $.ajax({
        url:"/getServerDate",
        type:"POST",
        /!*beforeSend: function(xhr){
            xhr.setRequestHeader(header,token);
        },*!/
        success:function (result) {

        },
        error:function (e) {
        }
    }).done*/
},1000);

/*
    LOG 함수
 */

var test;
var _fileViewTimeout=null;
function fileView(name,path){
    $("#fileTbody").empty();
    var endPoint = 0;
    function requestLog(){
        console.log("requestLog");
        console.log("endPoint: "+endPoint);

        $.ajax({
            url:"/getFileView",
            type:"POST",
            data:"fileName="+name+"&filePath="+path+"&preEndPoint="+endPoint,
            success:function (data) {

                test=data;
                endPoint = data.endPoint;
                if(data.log.length < 1) return;

                var tbody="<tr><td>"+data.log+"</td></tr>";

                $("#FilesForm").css("display","none");
                $("#FileForm").css("display","block");

                //$("#fileTbody").empty();
                tbodyAppend("fileTbody",tbody);
            },
            error:function (request,status,e) {
                console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+e);
            }
        }).done
        if(_fileViewTimeout == null ){

            _fileViewTimeout=setInterval(requestLog, 1000);
        }
    }
    requestLog();



}
function fileDownload(name,path) {
    location.href="/getFileDownload?fileName="+name+"&filePath="+path;
}
function fileDelete(name,path) {
    if (!confirm(name+"을 삭제합니다.")) {
        return;
    }else{
        $.ajax({
            url:"/getFileDelete",
            type:"POST",
            data:"fileName="+name+"&filePath="+path,
            success:function (data) {
                alert("삭제 했습니다.");
                $("#logTbody").empty();
                fileListTbody ="<tr><td>logs</td></tr>";

                console.log(data);
                //fileList = data;
                initFileList(data,1);
                tbodyAppend("logTbody",fileListTbody);

            },
            error:function (request,status,e) {
                alert("삭제를 실패했습니다.");
                console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+e);
            }
        }).done
    }


}
function logList() {
    $("#FilesForm").css("display","block");
    $("#FileForm").css("display","none");
    if(_fileViewTimeout != null) {clearInterval(_fileViewTimeout);_fileViewTimeout=null;}

}
var dirMaxIndex =3;
function initFileList(fileList,i){
    var root =i;
    var fileIndex=1;
    fileList.forEach(function (el,index) {
        if(el.type=="dir"){
            fileListTbody+="<tr>";
            for(var ii =1;ii<=root;ii++){
                //console.log(ii+" "+ root);
                fileListTbody +=(ii==root)?"<td style='text-align: right'>  ↳</td>":"<td></td>";
            }
            fileListTbody +="<td>"+el.fileName+"</td>";
            fileListTbody+="</tr>";
            if(el.fileInfos.length >0 ){
                i++;
                initFileList(el.fileInfos,i);
            }
        }
        if(el.type=="log"){
            fileListTbody+="<tr>";
            for(var ii =1;ii<=root;ii++) {
                fileListTbody += (root == ii)?"<td class='fileIndex'>"+(fileIndex)+"</td>":"<td></td>";
                fileIndex++;
            }
            fileListTbody +="<td colspan="+(dirMaxIndex-root+1)+">"+el.fileName+" ("+el.fileSize+" Byte)</td>";
            fileListTbody += "<td><a href=javascript:fileView('"+el.fileName+"','"+el.filePath+"');>VIew</a></td>"
            fileListTbody += "<td><a href=javascript:fileDownload('"+el.fileName+"','"+el.filePath+"');>Download</a></td>"
            fileListTbody += "<td><a href=javascript:fileDelete('"+el.fileName+"','"+el.filePath+"');>Delete</a></td>"
            fileListTbody+="</tr>";
        }


    });
    //console.log(fileListTbody);


}

/*
    CONTROLLER 명령 함수

 */

//연결해제
function disConn(id) {

}
//제어기 리셋
function dsrcReset(id) {

}
//접속횟수 초기화
function countReset(id) {

}
/*
    Tbody 데이터 넣기
 */
function tbodyAppend(tagId,tbodyData) {
    $("#"+tagId).append(tbodyData);
}