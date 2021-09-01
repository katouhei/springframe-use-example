<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="<c:url value="/assets/js/jquery-3.5.0.js"/>"></script>
<body>
<h2>Hello World!</h2>
<form id="loginForm">
    账号：<input type="text" name="account" value="account">
    密码：<input type="password" name="passwd" value="passwd">
    <input type="button" value="登录" id="loginBtn">
    <div id="resultSpan"></div>
</form>
</body>
<script>

    $("#loginBtn").click(function () {
        $.ajax({
            url: '<c:url value="/login"/>',
            dataType: 'json',
            type: 'post',
            data: $("#loginForm").serialize(),
            beforeSend: function () {
                //请求前
            },
            success: function (result) {
                var s = JSON.stringify(result);
                $("#resultSpan").text(s);
                location.href = '<c:url value="/index"/>';
            },
            complete: function () {
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                //请求失败时
                $("#resultSpan").text("系统后台异常，请与管理员联系");
            }
        })
    })
</script>
</html>
