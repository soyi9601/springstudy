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
      ${sessionScope.user.name}님 반갑습니다.
      <a href="${contextPath}/user/leave.do">회원탈퇴</a>
    </c:if>
    
    
    <!-- 
    * 동기처리 : 순서를 지켜야하는 *
    이메일 중복체크  -> 요청 (비동기 요청)
    가능한 이메일    <- 응답
    해당이메일로전송 -> 요청 (비동기 요청)
    보낸코드         <- 응답
    일치여부 : 허용 여부 판단
    
    비동기 요청 2개는 순서와 상관없이 요청을 하게 되는데 
    요청 - 응답 요청 - 응답 순서를 지켜야 하기 때문에 fetch/then 을 사용한 것 (promise 내장 객체 : 순서에 맞게 기다리라고 해주는 것)
     -->
     
     <!--     
     스크립트 코드 넘어올 수 없음 === 양아취놈들이 스크립트 코드 넘기면서 DB 호출 할 때 마다 오류날 수 있음
     name="email"   -> 스크립트 코드 넘어올 수 없음 : 정규식 처리
     name="pw"      -> 스크립트 코드 넘어올 수 없음 : 글자수 제한
     name="name"    -> 스크립트 코드 넘어올 수 있음 : XXS 처리해줘야함
     name="mobile"  -> 스크립트 코드 넘어올 수 없음 : 숫자만 넘어올 수 있음
     name="gender"  -> 스크립트 코드 넘어올 수 없음 : value 가 넘어옴 woman/man/none
     name="event"   -> 스크립트 코드 넘어올 수 없음 : checkbox (radio 처럼 value 가능) // value를 안달아줄 때는 'on' 또는 null (request.getParameter("event") == null)
     -->
     
     
     <!-- 
     08_File > index.jsp 확인
     new FormData().append() 로 POST 방식을 보낼 수 있음
     HttpServvletRequest 로 받을 수 있음
      -->
    
    
    
    
    
    
    
</body>
</html>