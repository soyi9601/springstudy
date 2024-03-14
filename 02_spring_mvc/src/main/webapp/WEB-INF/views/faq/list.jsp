<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>faq 목록</title>
<script>
  function fnAddResult() {
	// '' 문자열로 인식 시켜주지 않으면 list.do 페이지 직접 열 때 console 오류가 뜬다. let addResult = ; 로 나오면서 값이 없다고 인식.
	// 값이 들어오지 않아도 빈 문자열로 인식해서 오류 뜨지 않게 '문자열' 로 작업
	  let addResult = '${addResult}'; 
	  if(addResult !== '' && addResult === '1'){
		  alert('정상적으로 등록되었습니다.');
	  }
  }
  fnAddResult();
</script>
</head>
<body>

</body>
</html>