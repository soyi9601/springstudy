<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- include libraries(jquery, bootstrap) -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<!-- <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script> -->

<!-- include summernote css/js -->
<%-- <link rel="stylesheet" href="${contextPath}/resources/summernote-0.8.18-dist/summernote.min.css">
<script src="${contextPath}/resources/summernote-0.8.18-dist/summernote.min.js"></script>
<script src="${contextPath}/resources/summernote-0.8.18-dist/lang/summernote-ko-KR.min.js"></script> --%>

<style>
  @import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css');
  @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&display=swap');
  body {
    font-family: "Roboto", sans-serif;
    font-weight: 400;
  }
  a {
    color: #000;
    text-decoration: none;
    margin-right: 30px;
    font-size: 20px;
  }
  a:hover {
    color: #09cfb5;
    border-bottom: 1px solid #09cfb5;
  }
  a i {
    padding-right: 10px;
  }

</style>

</head>
<body>

    <!-- Sign In 안 된 경우 -->
    <c:if test="${sessionScope.user == null}">
      <a href="${contextPath}/user/signin.page"><i class="fa-solid fa-arrow-right-to-bracket"></i>Sign In</a>
      <a href="${contextPath}/user/signup.page"><i class="fa-solid fa-user-plus"></i>Sign Up</a>
    </c:if>
    
    <!-- Sign In 된 경우 -->
    <c:if test="${sessionScope.user != null}">
      ${sessionScope.user.name}님 반갑
    </c:if>
</body>
</html>