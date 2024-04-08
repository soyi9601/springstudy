<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="${blog.blogNo}번 블로그" name="title"/>
</jsp:include>

  <h1 class="title">BLOG 상세화면</h1>  
    
    <div>
      <span>작성자</span>
      <span>${blog.user.email}</span>
    </div>
    
    <div>
      <span>제목</span>
      <span>${blog.title}</span>
    </div>
    
    <div>
      <span>내용</span>
      <span>${blog.contents}</span>
    </div> 
    
  <hr>
    
  <form id="frm-comment">
    <textarea id="contents" name="contents"></textarea>
    <input type="hidden" name="blogNo" value="${blog.blogNo}">
    <c:if test="${not empty sessionScope.user}">
      <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">
    </c:if>
    <button id="btn-comment-register"></button>    
  </form>
  
  <hr>
  
  <div id="comment-list"></div>
  
<script>

  const fnRegisterComment = () => {
	  $('#btn-comment-register').on('click', (evt) => {
		  if('${sessionScope.user}' === '') {
			  if(confirm('Sign In 이 필요한 기능입니다. Sign In 할까요?')) {
				  location.href = '${contextPath}/user/signin.page'
			  } else {
				  return;
			  }
		  } else {
			  $.ajax({
				  
			  })
		  }
	  })
  }
  
  fnRegisterComment();

</script>

<%@ include file="../layout/footer.jsp" %>


