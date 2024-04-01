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
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>


<style>
  @import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css');
  @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&display=swap');
  body {
    font-family: "Roboto", sans-serif;
    font-weight: 400;
  }

</style>

</head>
<body>

  <h1>Sign Up</h1>
  
  <form method="POST"
        action="${contextPath}/user/signup.do"
        id="frm-signup">
    
    <div class="mb-3">
      <label for="email">아이디</label>
      <input type="text" name="email" id="email" placeholder="aaa@aaa.aaa" />
      <button type="button" id="btn-code"  class="btn btn-primary">인증코드 받기</button>
      <div id="msg-email"></div>
    </div>
    
    <div class="mb-3">
      <input type="text" id="code" placeholder="인증코드입력" disabled /> <!-- 이 값은 DB 에 저장해서 컨트롤러로 보낼 필요가 없기 때문에 name 을 붙일 필요 없음 -->
      <button type="button"  id="btn-verify-code"  class="btn btn-primary">인증하기</button>
    </div>
    
  </form>

<script>

const fnGetContextPath = ()=>{
  const host = location.host;  /* localhost:8080 */
  const url = location.href;   /* http://localhost:8080/mvc/getDate.do */
  const begin = url.indexOf(host) + host.length;
  const end = url.indexOf('/', begin + 1);
  return url.substring(begin, end);
}

const fnCheckEmail = ()=>{
	
	/*
	promise 가 꼭!! 꼭 !! 필요할 때는 : ajax 호출이 연속(2번 이상)으로 이루어질 때
	new Promise((resolve, reject) => {
		  $.ajax({
		    url: '이메일중복체크요청'
		  })
		  .done(resData=>{
		    if(resData.enableEmail){
		      resolve();
		    } else {
		      reject();
		    }
		  })
		})
		.then(()=>{
		  $.ajax({
		    url: '인증코드전송요청'
		  })
		  .done(resData=>{
		    if(resData.code === 인증코드입력값)
		  })
		})
		.catch(()=>{
		  
		})
	*/
	
	/*
	fetch('이메일중복체크요청', {})
  .then(response=>response.json())
  .then(resData->{
    if(resData.enableEmail){
      fetch('인증코드전송요청', {})
      .then(response=>response.json())
      .then(resData=>{  // {"code": "123asd"}
        if(resData.code === 인증코드입력값)
      })
    }
  })	
	*/
	
	let email = document.getElementById('email');
	let regEmail = /^[A-Za-z0-9-_]{2,}@[A-Za-z0-9]+(\.[A-Za-z]{2,6}){1,2}$/;
	if(!regEmail.test(email.value)){
		alert('이메일 형식이 올바르지 않습니다.');
		return;
	}
	// fetch(주소, {옵션});
	fetch(fnGetContextPath() + '/user/checkEmail.do', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		// javascript 객체를 넣으면 JSON 으로 바뀐다.
		body: JSON.stringify({
			'email': email.value
		}) 
	})
	// 받아온 응답객체에서 JSON만 꺼내겠다.
	// .then( (response) => { return response.json(); } ) : 이 코드이지만 생략가능한 것은 생략함. 매개변수는 ()생략가능, 본문이 return 뿐이면 return 생략가능, 한줄만 나올 경우 {} 생략가능.
	.then(response => response.json())
	
	// return 값을 받는 메소드. JSON 이 늦게 넘어오더라도 기다렸다가 확실히 넘겨주겠다(promise) -> 그래야 비동기 작업이 순서대로 넘어올 수 있다.
	.then(resData => {
		// 비동기 작업은 순서가 상관없음, 그래서 순서를 지킬 수 있도록 promise 를 사용함 (fetch와 then 은 promise 가 내장객체로 되어있다.)
		if(resData.enableEmail) { // 중복 통과
			fetch(fnGetContextPath() + '/user/sendCode.do', {
				method: 'POST',
		    headers: {
		      'Content-Type': 'application/json'
		    },
		    // javascript 객체를 넣으면 JSON 으로 바뀐다.
		    body: JSON.stringify({
		      'email': email.value
		    })
			});
		} else {  // 똑같은 이메일이 있어서 통과 실패
			document.getElementById('msg-email').innerHTML = '<p>이미 사용 중인 이메일 입니다.</p>';
			return;
		}
	})      
	
}

document.getElementById('btn-code').addEventListener('click', fnCheckEmail);

/* 데이터의 흐름
 * POST 방식으로 JSON을 보낸다 : @ReqeustBody 로 받을 것. (받는 실제 도구는 MAP) -> 상호 보완해주는 도구가 jaxon.lib
 */ 

</script>

</body>
</html>





