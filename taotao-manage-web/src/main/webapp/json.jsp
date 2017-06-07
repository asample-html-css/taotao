<%--
  Created by IntelliJ IDEA.
  User: yangdongan
  Date: 2017/6/7 0007
  Time: 上午 10:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String callback = request.getParameter("callback");
    if (callback==null || "".equals(callback)){
        out.print("{\"abc\":123}");
    }else{
        out.print(callback+"({\"abc\":123})");
    }
%>