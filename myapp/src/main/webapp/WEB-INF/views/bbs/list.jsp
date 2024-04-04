<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp" />

  <h1 class="title">BBS</h1>
  
  <a href="${contextPath}/bbs/write.page">작성하러 가기</a>
  
  <table border="1">
    <thead>
      <tr>
        <td>순서</td>
        <td>작성자</td>
        <td>내용</td>
        <td>작성일자</td>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${bbsList}" var="bbs" varStatus="vs">
        <tr class="bbs">
          <td>${beginNo - vs.index}</td>
          <c:if test="${bbs.state == 1}">
            <td>${bbs.user.email}</td>
            <td>
              <!-- 들여쓰기 -->
              <c:forEach begin="1" end="${bbs.depth}" step="1">&nbsp;&nbsp;</c:forEach>
              <c:if test="${bbs.depth != 0}">[Re]</c:if>
              ${bbs.contents}
              <c:if test="${sessionScope.user.email != bbs.user.email}"><button type="button" class="btn-reply">답글</button></c:if>
              <!-- <c:if test="${sessionScope.user.userNo != bbs.user.userNo}"><button type="button" class="btn-reply">답글</button></c:if> -->              
              <c:if test="${sessionScope.user.email == bbs.user.email}"><button type="button" class="btn-remove">삭제</button></c:if>
            </td>
            <td>
              <fmt:formatDate value="${bbs.createDt}" pattern="yyyy.MM.dd. HH:mm:ss" />
            </td>
          </c:if>
          <c:if test="${bbs.state == 0}">
            <td colspan="3">삭제된 게시글입니다.</td>
          </c:if>
        </tr>
        <tr class="write blind">
          <td colspan="4">
            <form method="POST"
                  action="${contextPath}/bbs/registerReply.do">
              <div>
                <span>답글작성자</span>
                <span>${sessionScope.user.email}</span>
              </div>
              
              <div>
                <textarea id="contents" name="contents" placeholder="답글을 입력하세요 입력하세요"></textarea>
              </div>
              
              <div>
                <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">
                <!-- 원글이 bbs 인 것. bbs(원글)의 depth, bbs의 groupNo -->
                <input type="hidden" name="depth" value="${bbs.depth}">
                <input type="hidden" name="groupNo" value="${bbs.groupNo}">
                <input type="hidden" name="groupOrder" value="${bbs.groupOrder}">   
                <button type="submit">작성완료</button>
              </div>
            </form>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  <div>${paging}</div>
  
  <script>
  
  const fnBlind = () => {
	  $('.btn-reply').on('click', (evt) => {
		  let write = $(evt.target).closest('.bbs').next();
		  if(write.hasClass('blind')) {
			  $('.write').addClass('blind');   // 모든 답글 작성 화면 닫은 뒤
			  write.removeClass('blind');      // 클릭한 답글 작성 화면만 열기
		  } else {
			  write.addClass('blind');         // 답글 작성 화면이 열려있었다면 닫겠다.
		  }
	  })
  }
  
  const fnInsertBbsCount = () => {
  	let insertBbsCount = '${insertBbsCount}';
  	if(insertBbsCount != '') {
  		if(insertBbsCount === '1') {
  			alert('BBS 원글이 등록되었습니다.');
  		} else {
  			alert('BBS 원글이 등록되지 않았습니다.');
  		}
  	}
  }
  
  const fnInsertReplyCount = () => {
      let insertReplyCount = '${insertReplyCount}';
      if(insertReplyCount != '') {
        if(insertReplyCount === '1') {
          alert('BBS 답글이 등록되었습니다.');
        } else {
          alert('BBS 답글이 등록되지 않았습니다.');
        }
      }
    }
  
  fnBlind();
  fnInsertBbsCount();
  fnInsertReplyCount();
  </script>

<%@ include file="../layout/footer.jsp" %>