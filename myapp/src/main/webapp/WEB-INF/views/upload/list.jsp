<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp" />

  <h1 class="title">Upload 목록</h1>  
  
  <a href="${contextPath}/upload/write.page">업로드 작성</a>
  
  <div>
    <div>
      <input type="radio" name="sort" value="DESC" id="descending" checked>
      <label for="descending">내림차순</label>
      <input type="radio" name="sort" value="ASC"  id="ascending">
      <label for="ascending">오름차순</label>
    </div>
    <div>    
      <select id="display" name="display">
        <option>10</option>
        <option>20</option>
        <option>50</option>
      </select>
    </div>
    <table class="table align-middle">
      <thead>
        <tr>
          <td>순번</td>   <!-- uploadNo 최악 -->
          <td>제목</td>
          <td>작성자</td>
          <td>첨부개수</td>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${uploadList}" var="upload" varStatus="vs">
          <tr>
            <td>${beginNo - vs.index}</td>
            <td><a href="${contextPath}/upload/detail.do?uploadNo=${upload.uploadNo}">${upload.title}</a></td>
            <td>${upload.user.email}</td>
            <td>${upload.attachCount}</td>
          </tr>
        </c:forEach>
      </tbody>
      <tfoot>
        <tr>
          <td colspan="4">${paging}</td>
        </tr>
      
    </table>
  </div>
  
<script>
  
  const fnChangeDisplay = () => {
	  document.getElementById('display').value = '${display}';
	  document.getElementById('display').addEventListener('change', (evt) => {
		  location.href = '${contextPath}/upload/list.do?page=1&sort=${sort}&display=' + evt.target.value;
	  })
	};
  
  const fnSort = () => {
	  $(':radio[value=${sort}]').prop('checked', true);
	  $(':radio').on('click', (evt) => {
		  location.href = '${contextPath}/upload/list.do?page=1&sort=' + evt.target.value + '&display=${display}';
	  })
  };
  
  const fnUploadInserted = () => {
	  const inserted = '${inserted}';
	  if(inserted !== '') {
		  if(inserted === 'true') {
			  alert('업로드 되었습니다.');
		  } else {
			  alert('업로드 실패했습니다');
		  }
	  }
  }
    
  fnChangeDisplay();
  fnSort();
  fnUploadInserted();
  
</script>

<%@ include file="../layout/footer.jsp" %>