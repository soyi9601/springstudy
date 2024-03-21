<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
</head>
<body>

  <div>
    <form method="POST" id="frm-detail">
      <div>
        <label for="contact-no">번호</label>
        <input type="text" id="contact-no" name="contact-no" value="${contact.contactNo}" readonly />
      </div>
      <div>
        <label for="name">이름</label>
        <input type="text" id="name" name="name" value="${contact.name}" />
      </div>
      <div>
        <label for="mobile">연락처</label>
        <input type="text" id="mobile" name="mobile" value="${contact.mobile}" />
      </div>
      <div>
        <label for="email">이메일</label>
        <input type="text" id="email" name="email" value="${contact.email}" />
      </div>
      <div>
        <label for="address">주소</label>
        <input type="text" id="address" name="address" value="${contact.address}" />
      </div>
      <div>
        <button type="button" id="btn-modify">수정</button>
        <button type="button" id="btn-remove">삭제</button>
        <button type="button" id="btn-list">목록</button>
      </div>
    </form>
  </div>
  <script>
    const frmDetail = document.getElementById('frm-detail');
    const btnModify = document.getElementById('btn-modify');
    const btnRemove = document.getElementById('btn-remove');
    const btnList = document.getElementById('btn-list');
    
    btnModify.addEventListener('click', (evt)=>{
    	$(frmDetail).attr('action', '${contextPath}/contact/modify.do');   // frmDetail의 attr 속성을 작업해서 modify.do로 갈 수 있도록 해줌. -> controller 에서 modify.do 를 작업해준다.
    	$(frmDetail).submit();
    })
    
    btnRemove.addEventListener('click', (evt)=>{
    	if(confirm('연락처 삭제?')) {
        $(frmDetail).attr('action', '${contextPath}/contact/remove.do');
        $(frmDetail).submit();
        /* 현재 폼은 method가 POST 로 되어있는데 Controller 에서는 Getmapping 으로 되어있어서 메소드가 맞지 않는 오류 발생 -> remove를 2개 만들어줄 수 있음(controller에서)  */
      }
    })
    
    btnList.addEventListener('click', (evt)=>{
    	location.href = '${contextPath}/contact/list.do';
    })
  </script>
  
</body>
</html>