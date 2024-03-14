<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%-- contextPath는 java 변수인데 javascript 에서 쓸 수 있다.. --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>블로그 목록</title>
<style>
  .blog {
    width: 200px;
    cursor: pointer;
    background: yellow;
  }
</style>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
</head>
<body>

  <c:forEach items="${blogList}" var="blog" varStatus="vs">
    <div class="blog">
      <span>${vs.index}</span>
      <span class="blog-no">${blog.blogNo}</span>
      <span>${blog.title}</span>
    </div>
  </c:forEach>
  
  <script type="text/javascript">
  
    // this는 javascript 의 객체
    $('.blog').on('click', function(evt){
    	let blogNo = $(this).find('.blog-no').text();    // click 한 this(div)요소의 하위 .blog-no 를 찾아라
    	location.href = '${contextPath}/blog/detail.do?blogNo=' + blogNo;    // /blog/detail.do <- 요청! forward 해서 상세보기로 넘겨달라
    });
  
  </script>

</body>
</html>