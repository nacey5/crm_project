<%--
  Created by IntelliJ IDEA.
  User: 11601
  Date: 2022/3/9
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script type="text/javascript">
    document.location.href = "settings/qx/user/toLogin.do";
</script>
</body>
</html>
