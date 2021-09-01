<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="<c:url value="/assets/js/jquery-3.5.0.js"/>"></script>
<body>
<h2>Hello World!</h2>
<form id="modifyForm">
    <input type="button" value="测试新增" id="addBtn">
    <input type="button" value="测试查询" id="queryBtn">
    <input type="button" value="测试事务" id="tranBtn">
<div id="resultSpan"></div>
</form>
</body>
<script>
    $("#addBtn").click(function () {
        $.ajax({
            url: '<c:url value="/sys/sys-log/save"/>',
            dataType: 'json',
            type: 'post',
            data: $("#modifyForm").serialize(),
            beforeSend: function () {
                //请求前
            },
            success: function (result) {
                var s = JSON.stringify(result);
                $("#resultSpan").text(s);
            },
            complete: function () {
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                //请求失败时
                $("#resultSpan").text("系统后台异常，请与管理员联系");
            }
        })
    })
    $("#tranBtn").click(function () {
        $.ajax({
            url: '<c:url value="/sys/sys-log/replaceUpdate"/>',
            dataType: 'json',
            type: 'post',
            data: $("#modifyForm").serialize(),
            beforeSend: function () {
                //请求前
            },
            success: function (result) {
                var s = JSON.stringify(result);
                $("#resultSpan").text(s);
            },
            complete: function () {
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                //请求失败时
                $("#resultSpan").text("系统后台异常，请与管理员联系");
            }
        })
    })
    $("#queryBtn").click(function () {
        $.ajax({
            url: '<c:url value="/sys/sys-log/query"/>',
            dataType: 'json',
            type: 'post',
            data: $("#modifyForm").serialize(),
            beforeSend: function () {
                //请求前
            },
            success: function (result) {
                var s = JSON.stringify(result);
                $("#resultSpan").text("查询结果："+s);
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
