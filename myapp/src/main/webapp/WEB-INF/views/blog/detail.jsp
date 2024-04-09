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
  
  <!-- 댓글 작성 창 -->
  <form id="frm-comment">
    <textarea id="contents" name="contents"></textarea>
    <input type="hidden" name="blogNo" value="${blog.blogNo}">
    <c:if test="${not empty sessionScope.user}">
      <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">
    </c:if>
    <button type="button" id="btn-comment-register">답글 작성</button>    
  </form>
  
  <hr>
  
  <!-- 댓글 목록 -->
  <div id="comment-list"></div>
  <div id="paging"></div>
  
<script>

  const fnCheckSignin = () => {
    if('${sessionScope.user}' === '') {
      if(confirm('Sign In 이 필요한 기능입니다. Sign In 할까요?')) {
        location.href = '${contextPath}/user/signin.page';
      }
    }
  }

  const fnRegisterComment = () => {
	  $('#btn-comment-register').on('click', (evt) => {
		  fnCheckSignin();
		  $.ajax({
			  // 요청 (삽입할 때는 고민하지 말고 POST)
			  type: 'POST',
			  url: '${contextPath}/blog/registerComment.do',
			  data: $('#frm-comment').serialize(),   // <form> 내부의 모든 입력을 파라미터 형식으로 보낼 때 사용, 입력 요소들은 name 속성을 가지고 있어야함. 
			  // 응답
			  dataType: 'json',
			  success: (resData) => {   // resData = {"insertCount": 1}
				  if(resData.insertCount === 1) {
					  alert('댓글이 등록되었습니다.');
					  $('#contents').val('');
					  fnCommentList();
					  // 댓글 목록 보여주는 함수 호출
				  } else {
					  alert('댓글 등록이 실패했습니다.');
				  }
			  },
			  error: (jqXHR) => {
				  alert(jqXHR.statusText + '(' + jqXHR.status + ')');
			  }
		  })
	  })
  }
  
  // 전역 변수
  var page = 1;
  
  const fnCommentList = () => {
	  $.ajax({
		  type: 'get',
		  url: '${contextPath}/blog/comment/list.do',
		  data: 'blogNo=${blog.blogNo}&page=' + page,
		  // 응답
		  dataType: 'json',
		  success: (resData) => {   // resData = {"commentList": [], "paging": "< 1 2 3 4 5 >"}
			  // 변수 저장
			  let commentList = $('#comment-list');
			  let paging = $('#paging');
			  // 초기화 (댓글 리스트 + 페이징)
			  commentList.empty();
			  paging.empty();
			  if(resData.commentList.length === 0) {
				  commentList.append('<div>첫 번째 댓글의 주인공이 되어보세요.</div>');
				  paging.empty();
				  return;
			  } 
			  $.each(resData.commentList, (i, comment) => {
				  let str = '';
				  // 댓글은 들여쓰기 (댓글 여는 div)
				  if(comment.depth === 0) {
					  str += '<div>';
				  } else {
					  str += '<div style="paggin-left: 32px;">';
				  }
				  // 댓글 내용 표시
				  str += '<span>';
				  str +=  comment.user.email;
				  str += '(' + moment(comment.createDt).format('YYYY.MM.DD.') + ')';
				  str += '</span>';
				  str += '<div>' + comment.contents + '</div>';
				  // 댓글 닫는 <div>
				  str += '</div>';
				  // 목록에 댓글 추가
				  commentList.append(str);
			  })
			  // 페이징표시
			  paging.append(resData.paging);
		  },
		  error: (jqXHR) => {
			  alert(jqXHR.statusText + '(' + jqXHR.status + ')');
		  }
	  })
  }
  
  const fnPaging = (p) => {
	  page = p;
	  fnCommentList();
  }
  
  $('#contents').on('click', fnCheckSignin);
  fnRegisterComment();
  fnCommentList();

</script>

<%@ include file="../layout/footer.jsp" %>


