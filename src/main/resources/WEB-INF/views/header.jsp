<div class ="title">${ServerConfig.processName}</div>

<div class ="time" id="serverTime" >${ServerTime}</div>
<hr>
<div class="nav">
    <nav>
        <a id="nav_system" href="/index">System</a>
        <a id="nav_controller" href="/controller">Controller</a>
        <a id="nav_log" href="/log">Log</a>
        <form name="loginForm" method="post" action="/logout">
            <a href="javascript:submitForm();">Logout</a>
        </form>

    </nav>
</div>
<script type="text/javascript">
    var _sever_time = '${ServerTime}';
</script>