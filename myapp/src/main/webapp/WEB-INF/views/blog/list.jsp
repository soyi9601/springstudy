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
				  let str = '<div class="blog" data-user-no="' + blog.user.userNo + '"data-blog-no="' + blog.blogNo + '">';
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
	  // window.addEventListener('scroll', (evt) => {})
	  
	  // 타이머 id (동작한 타이머의 동작 취소를 위한 변수)
	  // 목록 가져오기를 타이머에 넣은 건 타이머는 취소하기 쉬움. 한번 동작하고나면 타이머가 동작을 취소하도록 짠 것.
	  var timerId; // undifined, boolean 의 의미로는 false
	  $(window).on('scroll', (evt) => {
		  
		  if(timerId) {   // timerId 가 undefined 이면 false, 아니면 true
			                // timerId 가 undefined 이면 setTimeout() 함수가 동작한 적 없음
			  
			  clearTimeout(timerId);  // setTimeout() 함수 동작을 취소함 -> 목록을 가져오지 않는다. 
		  }
		  
		  // 500밀리초 (0.5초) 후에 () => {} 동작하는 setTimeout 함수
		  timerId = setTimeout(() => {		  
  		  let scrollTop = $(window).scrollTop();
  		  let windowHeight = $(window).height();
  		  let documentHeight = $(document).height();
  		  
  		  if( (scrollTop + windowHeight + 50) >= documentHeight ) {   // 스크롤바와 바닥 사이 크기가 50px 이하인 경우
  			  if(page > totalPage) {
  				  return;
  			  }
  			  page++;
  			  fnGetBlogList();			  
  		  }
		  }, 500);		  		  
		  // scrollTop + windowHeight = documentHeight : 같을 때 scroll 이 바닥에 닿았다라고 생각.
		  // scrollTop + windowHeight + @ = documentHeight : 바닥이 @만큼 있을 때 바닥에 닿았다라고 생각할 수 있음.
		  
	  })	  
	}
  
  const fnBlogDetail = () => {
	  
	  // $('.blog').on('click', (evt) => {alert('동작 확인');})  :: 함수로 생성해준 .blog 는 이 방법으로 부르면 동작하지 않는다.
	  $(document).on('click', '.blog', (evt) => {
		  // <div class="blog"> 중 클릭 이벤트가 발생한 <div> : 이벤트 대상
		  // alert(evt.target.dataset.blogNo);
		  // jQuery : alert($(evt.target).data('blogNo'));
		  /* 
		    아래의 링크는 조회수는 늘려주지 않고 상세보기만 보이는 링크
		    location.href = '${contextPath}/blog/detail.do?blogNo=' + evt.target.dataset.blogNo;
		  */
		  if('${sessionScope.user.userNo}' === evt.target.dataset.userNo) {
			  location.href = '${contextPath}/blog/detail.do?blogNo=' + evt.target.dataset.blogNo;
		  } else {
			  location.href = '${contextPath}/blog/updateHit.do?blogNo=' + evt.target.dataset.blogNo;
		  }
			  
		  
		})
		  
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
  fnScrollHandler();
  fnBlogDetail();
  fnInsertCount();
</script>

<%@ include file="../layout/footer.jsp" %>