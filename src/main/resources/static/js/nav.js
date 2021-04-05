var underline = document.querySelector('#underline');
var item = document.querySelectorAll('nav a');
function clickNav(id) {
    var nav_id = document.getElementById("nav_"+id);
    var conent_id = document.getElementById(id+"Table");
    select(nav_id);
    selectContent(id);
}
function select(id){
    underline.style.left = id.offsetLeft+"px";
    underline.style.width = id.offsetWidth+"px";
}



function selectContent(id) {
    $("#system").css('display','none');
    $("#controller").css('display','none');
    $("#log").css('display','none');

    $("#"+id).css('display','block');

    updateContent(id);

}
var data;

function updateContent(id) {


    if(id==='controller'){
        requestService("getControllerInfo","",ControllerList,true);


    }
    else if(id ==='log'){
        logList();
    }



}

function ControllerList(result) {
    console.log(result);
    var tbody="";
    for(key in result){
        tbody += '<tr>';
        tbody += '<td>'+result[key].id+'</td>';
        tbody += '<td>'+result[key].rse_ID+'</td>';
        tbody += '<td>'+result[key].istl_LCTN_NM+'</td>';
        tbody += '<td>'+result[key].ip_ADDR+'</td>';
        tbody += '<td>'+result[key].netState+'</td>';
        tbody += '<td>2021-01-01-00:00:00</td>';
        tbody += '<td>2021-01-01-00:00:00</td>';
        tbody += '<td>'+1+'</td>';
        tbody += '<td><a href="javascript:disConn()">연결해제</a> <a href="javascript:dsrcReset()">제어기리셋</a> <a href="javascript:countReset()">접속횟수초기화</a></td>';
        tbody += '</tr>';
    }
    //console.log(tbody);
    $("#controllerTbody").empty();
    $("#controllerTbody").append(tbody);

}

/*
item.forEach(link =>{
    link.addEventListener('click', (event)=>{
        console.log(event.target);
        select(event.target);

    })
});*/
