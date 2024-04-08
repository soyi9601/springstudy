<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp" />

  <h1 class="title">BLOG 목록</h1>  
  
  <a href="${contextPath}/blog/write.page">블로그 작성</a>
  
  <table border="1">
    <thead>
      <tr>
        <td>순서</td>
        <td>작성자</td>
        <td>제목</td>
        <td>작성일</td>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="">
      
      </c:forEach>
    </tbody>
  </table>
  

<script>

  const fnInsertCount = () => {
	  let insertCount = '${insertCount}';
	  if(insertCount != '') {
		  if(insertCount === '1') {
			  alert('블로그가 등록되었습니다.');
		  } else {
			  alert('블로그가 등록 실패했습니다.');
		  }
	  }
  }
  
  fnInsertCount();
</script>

<%@ include file="../layout/footer.jsp" %>