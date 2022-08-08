<%--
  Created by IntelliJ IDEA.
  User: 11601
  Date: 2022/3/23
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>"/>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>

    <script>
        $(function () {
            $('#customerName').typeahead({
                source:['京东商城']
            })
        });
    </script>
    <title>Title</title>

</head>
<body>
<input type="text" id="customerName">
</body>
</html>
