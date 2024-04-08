<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp" />

  <h1 class="title">BLOG 목록</h1>  
  
  <a href="${contextPath}/blog/write.page">블로그 작성</a>
  
  <div id="blog-list"></div>
  

<script>

  // 아래 2개의 함수에서 page 를 공유해줘야한다. -> 전역변수 필요
  var page = 1;
  var totalPage = 0;

  // 초기 페이지를 보여주는 함수 
  const fnGetBlogList = () => {
	  
	  // page 에 해당하는 목록 요청
	  $.ajax({
		  // 요청
		  type: 'GET',
		  url: '${contextPath}/blog/getBlogList.do',
		  data: 'page=' + page,
		  // 응답
		  dataType: 'json',
		  success: (resData) => {   // resData = {"blogList": [], "totalPage": 10}
			  totalPage = resData.totalPage; // 가져와서 갱신해준다
			  $.each(resData.blogList, (i, blog) => {
				  let str = '<div class="blog" data-blog-no="' + blog.blogNo + '">';
				  str += '<span>' + blog.title + '</span>';
				  str += '<span>' + blog.user.email + '</span>';
				  str += '<span>' + blog.hit + '</span>';
				  str += '<span>' + moment(blog.createDt).format('YYYY.MM.DD') + '</span>';
				  str += '</div>';
				  $('#blog-list').append(str);
			  })
		  },
		  error: (jqXHR)=>{
			  alert(jqXHR.statusText + '(' + jqXHR.status + ')');
		  }
	  });
	  
  }
  
  // 페이지를 바꿔주는 함수
  const fnScrollHandler = () => {
	  
	  // 스크롤이 바닥에 닿으면 page 증가(최대 totalPage 까지) 후 새로운 목록 요청
	  
  }
  
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
  
  fnGetBlogList();
  fnInsertCount();
</script>

<%@ include file="../layout/footer.jsp" %>