function requestService(url, param, callback, async) {
    /*
     * 스프링시큐리티 csrf 토큰 에러때문에  ajax 통신시 해더에 포함해줘야한다.
     */
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    if (async) {

        async = true;
    }

    $.ajax({
        url: "/" + url
        , data: encodeURI(param)
        , cache: false
        , async: async
        , type: 'POST'
        , statusCode: {
            301: function(resp){
                window.location.reload();
            },
            302: function(resp){
                window.location.reload();
            }
        }
        , beforeSend: function (xhr) {
            if(header != null) xhr.setRequestHeader(header, token);
        }

    })
        .done(callback)
        .fail(function (request,status,e) {
        console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+e);
    });
}


