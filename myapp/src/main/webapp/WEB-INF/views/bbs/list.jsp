<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp" />

  <h1 class="title">BBS</h1>
  
  <a href="${contextPath}/bbs/write.page">작성하러 가기</a>
  
  <script>
    const fnInsertCount = () => {
    	let insertCount = '${insertCount}';
    	if(insertCount != '') {
    		if(insertCOunt === '1') {
    			alert('BBS 원글이 등록되었습니다.');
    		} else {
    			alert('BBS 원글이 등록되지 않았습니다.');
    		}
    	}
    }
    
    fnInsertCount();
  </script>

<%@ include file="../layout/footer.jsp" %>