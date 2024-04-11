<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp" />

  <h1 class="title">Upload 목록</h1>  
  
  <a href="${contextPath}/upload/write.page">업로드 작성</a>
  
  

<script>

  
</script>

<%@ include file="../layout/footer.jsp" %>