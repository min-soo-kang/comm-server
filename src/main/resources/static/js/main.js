





/*
    서버시간 요청 함수
 */

var _time_id=null;
/*requestService("getServerDate","",getServerTimeCallback,true);
function getServerTimeCallback(result, statusText, xhr){
    _sever_time =result.serverDate;

}*/
if(_time_id == null)_time_id = setInterval(severTimeInterval,1000);
function severTimeInterval(){
    $("#serverTime").text(moment(_sever_time).add("1","s").format("YYYY-MM-DD HH:mm:ss"));
    _sever_time = moment(_sever_time).add("1","s").format("YYYY-MM-DD HH:mm:ss");
}


/*
    LOG 함수
 */

var test;
var _fileViewTimeout=null;
function fileView(name,path){
    $("#fileTbody").empty();
    var endPoint = 0;
    function requestLog(){


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
                $("#logScroll").scrollTop($('#logScroll')[0].scrollHeight);
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
                fileList = data;
                fileListTbody="";

                initFileListTest(data);
                tbodyAppend("logTbody",fileListTbody);
                $('.tree-basic').treegrid();

            },
            error:function (request,status,e) {
                alert("삭제를 실패했습니다.");
                console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+e);
            }
        }).done
    }


}
function initFileListTest(fileList) {
    fileList.forEach(function (el,index) {
        if(el.type=="dir"){

            if(el.fileName==="logs"){
                fileListTbody += "<tr class='treegrid-"+el.id+" expanded'>";
            }else {
                fileListTbody += "<tr class='treegrid-"+el.id+" expanded treegrid-parent-"+el.parent+"'>";
            }

            fileListTbody += "<td>"+el.fileName+"</td>"
            fileListTbody += "</tr>"
        }else{
            fileListTbody += "<tr class='treegrid-"+el.id+" treegrid-parent-"+el.parent+"'>";
            fileListTbody +="<td>"+el.fileName+" ("+el.fileSize+" Byte)</td>";
            fileListTbody += '<td><a href=javascript:fileView(\''+el.fileName+'\',\''+el.filePath+'\')>VIew</a></td>'
            fileListTbody += "<td><a href=javascript:fileDownload('"+el.fileName+"','"+el.filePath+"')>Download</a></td>"
            fileListTbody += "<td><a href=javascript:fileDelete('"+el.fileName+"','"+el.filePath+"')>Delete</a></td>"
            fileListTbody += "</tr>"
        }

        if(el.fileInfos.length > 0) initFileListTest(el.fileInfos);
    });

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
    alert(id+"연결해제");

}
//제어기 리셋
function dsrcReset(id) {
    alert(id+"제어기 리셋");
}
//접속횟수 초기화
function countReset(id) {
    alert(id+"접속횟수 초기화");
}
//전체 해제
function allDisConn() {
    alert("전체연결해제");
}
//전체 접속횟수 초기화
function allConnCountReset() {
    alert("전체 접속횟수 초기화");
}

/*
    Tbody 데이터 넣기
 */
function tbodyAppend(tagId,tbodyData) {
    $("#"+tagId).append(tbodyData);
}