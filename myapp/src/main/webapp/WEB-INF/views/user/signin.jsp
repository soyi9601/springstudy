<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="Sign In" name="title" />
</jsp:include>

  <h1 class="title">Sign In</h1>
  
  <div>
    <form method="POST"
          action="${contextPath}/user/signin.do">
      <div>
        <label for="email">아이디</label>
        <input type="text" id="email" name="email" placeholder="example@naver.com">
      </div>
      <div>
        <label for="pw">비밀번호</label>
        <input type="password" id="pw" name="pw" placeholder="●●●●">
      </div>
      <!-- email 이랑 pw 는 DB 까지 전달되어야함 -->
      <div>
        <input type="hidden" name="url" value="${url}">  <!-- Model 에 저장 되어있는 건 일회용 저장소 -->
        <button type="submit">Sign In</button>
      </div>
      <div>
        <a href="${naverLoginURL}">
          <img style="width:100px;" src="../resources/n_image/btnG_naver.png">
        </a>      
      </div>
    </form>
  </div>

<%@ include file="../layout/footer.jsp" %>