<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="${upload.uploadNo}번 블로그" name="title"/>
</jsp:include>

  <h1 class="title">Upload 상세화면</h1>  
    
    <div>
      <span>작성자</span>
      <span>${upload.user.email}</span>
    </div>
        
    <div>
      <span>제목</span>
      <span>${upload.title}</span>
    </div>
    
    <div>
      <span>내용</span>
      <span>${upload.contents}</span>
    </div> 
    
    <c:if test="${sessionScope.user.userNo == upload.user.userNo}">
      <div>
        <button type="button" class="btn-blog-modify">수정</button>
        <button type="button" class="btn-blog-remove">삭제</button>
      </div>
    </c:if>
  <hr>
  
  <!-- 첨부 목록  -->
  <h3>첨부 파일 다운로드</h3>
  <div>
    <c:if test="${empty attachList}">
      <div>첨부 없음</div>
    </c:if>
    <c:if test="${not empty attachList}">
      <c:forEach items="${attachList}" var="attach" >
        <div class="attach" data-attach-no="${attach.attachNo}">
          <c:if test="${attach.hasThumbnail == 1}">          
            <img src="${contextPath}${attach.uploadPath}/s_${attach.filesystemName}">
          </c:if>
          <c:if test="${attach.hasThumbnail == 0}">
            <img src="${contextPath}/resources/images/attach.png" width="96px">          
          </c:if>          
          <a>${attach.originalFilename}</a>
        </div>
      </c:forEach>
      <div>
        <a id="download-all" href="${contextPath}/upload/downloadAll.do?uploadNo=${upload.uploadNo}">모두 다운로드</a>
      </div>
    </c:if>
  </div>
  
<script>

  const fnDownload = () => {
	  $('.attach').on('click', (evt) => {
		  if(confirm('해당 첨부 파일을 다운로드 할까요?')) {
			  location.href = '${contextPath}/upload/download.do?attachNo=' + evt.currentTarget.dataset.attachNo;
		  }
	  })
  }
  
  const fnDownloadAll = () => {
	  document.getElementById('download-all').addEventListener('click', (evt) => {
		  if(!confirm('모두 다운로드 할까요?')) {
			  evt.preventDefault();
			  // return; : 존재 이유가 뒤의 다른 코드의 실행을 막기 위한 것인데, 뒤의 다른 코드가 없어서 있어도 그만, 없어도 그만임
		  }
	  })
  }
  
  fnDownload();
  fnDownloadAll();
  
</script>

<%@ include file="../layout/footer.jsp" %>


