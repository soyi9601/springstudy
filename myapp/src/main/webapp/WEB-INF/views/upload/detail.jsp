<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="${upload.uploadNo}번 블로그" name="title"/>
</jsp:include>

  <h1 class="title">Upload 상세화면</h1>  
    
    <div>
      <span>작성자</span>
      <span>${upload.user.email}</span>
    </div>
        
    <div>
      <span>제목</span>
      <span>${upload.title}</span>
    </div>
    
    <div>
      <span>내용</span>
      <span>${upload.contents}</span>
    </div> 
    
    <c:if test="${sessionScope.user.userNo == upload.user.userNo}">
      <div>
        <button type="button" class="btn-blog-modify">수정</button>
        <button type="button" class="btn-blog-remove">삭제</button>
      </div>
    </c:if>
    
    
  <hr>
 
  
<script>

  
</script>

<%@ include file="../layout/footer.jsp" %>


