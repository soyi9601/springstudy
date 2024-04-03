/**
 * 
 */
 
 // 전역 변수
var emailCheck = false;
var passwordCheck = false;
var passwordConfirm = false;
var nameCheck = false;
var mobileCheck = false;
var agreeCheck = false;


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
  .then(resData->{        //   {"enableEmail : true"}
    if(resData.enableEmail){
      fetch('인증코드전송요청', {})
      .then(response=>response.json())
      .then(resData=>{  // {"code": "123asd"}
        if(resData.code === 인증코드입력값)
      })
    }
  })  
  */
  
  let inpEmail = document.getElementById('inp-email');
  let regEmail = /^[A-Za-z0-9-_]{2,}@[A-Za-z0-9]+(\.[A-Za-z]{2,6}){1,2}$/;
  if(!regEmail.test(inpEmail.value)){
    alert('이메일 형식이 올바르지 않습니다.');
    emailCheck = false;
    return;
  }
  // feitch(주소, {옵션});
  fetch(fnGetContextPath() + '/user/checkEmail.do', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    // javascript 객체를 넣으면 JSON 으로 바뀐다.
    body: JSON.stringify({
      'email': inpEmail.value
    }) 
  })
  // 받아온 응답객체에서 JSON만 꺼내겠다.
  // .then( (response) => { return response.json(); } ) : 이 코드이지만 생략가능한 것은 생략함. 매개변수는 ()생략가능, 본문이 return 뿐이면 return 생략가능, 한줄만 나올 경우 {} 생략가능.
  .then(response => response.json())
  
  // return 값을 받는 메소드. JSON 이 늦게 넘어오더라도 기다렸다가 확실히 넘겨주겠다(promise) -> 그래야 비동기 작업이 순서대로 넘어올 수 있다.
  .then(resData => {
    // 비동기 작업은 순서가 상관없음, 그래서 순서를 지킬 수 있도록 promise 를 사용함 (fetch와 then 은 promise 가 내장객체로 되어있다.)
    if(resData.enableEmail) { // 중복 통과
      document.getElementById('msg-email').innerHTML = '';
      fetch(fnGetContextPath() + '/user/sendCode.do', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        // javascript 객체를 넣으면 JSON 으로 바뀐다.
        body: JSON.stringify({
          'email': inpEmail.value    // 이메일 받는 사람 -> Map 에 들어 있음
        })
      })
      .then(response => response.json())
      .then(resData => {   // resData = {"code": "123qwe"}
        let inpCode = document.getElementById('inp-code');
        let btnVerifyCode = document.getElementById('btn-verify-code');
        alert(inpEmail.value + '로 인증코드를 전송했습니다.');
        inpCode.disabled = false;
        btnVerifyCode.disabled = false;
        btnVerifyCode.addEventListener('click', (evt)=>{
          if(resData.code === inpCode.value) {
            alert('인증되었습니다.');
            emailCheck = true;
          } else {
            alert('인증되지 않았습니다.');
            emailCheck = false;
          }
        })
      })
    } else {  // 똑같은 이메일이 있어서 통과 실패
      document.getElementById('msg-email').innerHTML = '<p>이미 사용 중인 이메일 입니다.</p>';
      emailCheck = false;
      return;
    }
  })
}

const fnCheckName = () => {
  let inpName = document.getElementById('inp-name');
  let name = inpName.value;
  let totalByte = 0;
  for(let i = 0; i < name.length; i++) {
    // charCodeAt : 이름의 코드값 확인
    if(name.charCodeAt(i) > 127) {    // 코드값이 127(한글의 아스키 값 > 127) 초과이면 한 글자당 2바이트 처리한다.
      totalByte += 2;
    } else {
      totalByte++;
    }
  }
  nameCheck = (totalByte <= 100);
  let msgName = document.getElementById('msg-name');
  if(!nameCheck) {
    msgName.innerHTML = '이름은 100 바이트를 초과할 수 없습니다.';
  } else {
    msgName.innerHTML = '';
  }
}

const fnCheckMobile = () => {
  let inpMobile = document.getElementById('inp-mobile');
  let mobile = inpMobile.value;
  mobile = mobile.replaceAll(/[^0-9]/g, '');   // g : global
  mobileCheck = /^010[0-9]{8}$/.test(mobile);
  let msgMobile = document.getElementById('msg-mobile');
  if(mobileCheck) {
    msgMobile.innerHTML = '';
  } else {
    msgMobile.innerHTML = '휴대전화를 확인하세요';
  }
}

const fnCheckAgree = () => {
  let chkService = document.getElementById('chk-service');
  agreeCheck = chkService.checked;  
}

const fnSignup = () => {
  document.getElementById('frm-signup').addEventListener('submit', (evt) => {
    fnCheckAgree();
    if(!emailCheck) {
      alert('이메일을 확인하세요.');
      evt.preventDefault();
      return;
    } else if(!passwordCheck || !passwordConfirm) {
      alert('비밀번호를 확인하세요.');
      evt.preventDefault();
      return;
    } else if(!nameCheck) {
      alert('이름을 확인하세요.');
      evt.preventDefault();
      return;
    } else if(!mobileCheck) {
      alert('휴대전화를 확인하세요.');
      evt.preventDefault();
      return;
    } else if(!agreeCheck) {
      alert('서비스 약관에 동의해야 서비스를 이용할 수 있습니다.');
      evt.preventDefault();
      return;
    }
  })
}

const fnCheckPassword = () => {
  // 비밀번호 4 ~ 12자, 영문/숫자/특수문자 중 2개 이상 포함
  let inpPw = document.getElementById('inp-pw');
  let validCount = /[A-Za-z]/.test(inpPw.value)      // 영문 포함되어 있으면 true (JavaScript 에서 true 는 숫자 1과 같다.)
                 + /[0-9]/.test(inpPw.value)         // 숫자 포함되어 있으면 true 
                 + /[A-Za-z0-9]/.test(inpPw.value);  // 영문/숫자가 아니면 true
  
  let passwordLength = inpPw.value.length;
  passwordCheck = passwordLength >= 4
               && passwordLength <= 12
               && validCount >= 2;
  
  let msgPw = document.getElementById('msg-pw');
  if(passwordCheck) {
    msgPw.innerHTML = '사용 가능한 비밀번호입니다.';
  } else {
    msgPw.innerHTML = '비밀번호 4 ~ 12자, 영문/숫자/특수문자 중 2개 이상 포함'
  }

}

const fnConfirmPassword = () => {
  let inpPw = document.getElementById('inp-pw');
  let inpPw2 = document.getElementById('inp-pw2');
  passwordConfirm = (inpPw.value !== '')
                 && (inpPw.value === inpPw2.value);
  let msgPw2 = document.getElementById('msg-pw2');
  if(passwordConfirm) {
    msgPw2.innerHTML = '';
  } else {
    msgPw2.innerHTML = '비밀번호 입력을 확인하세요.';
  }
}



document.getElementById('btn-code').addEventListener('click', fnCheckEmail);
document.getElementById('inp-pw').addEventListener('keyup', fnCheckPassword);  // 키보드 입력하자마자 한글자한글자 -> keyup // 키보드 입력하고 포커스를 해제하면 -> blur
document.getElementById('inp-pw2').addEventListener('blur', fnConfirmPassword);
document.getElementById('inp-name').addEventListener('blur', fnCheckName);
document.getElementById('inp-mobile').addEventListener('blur', fnCheckMobile);

fnSignup();

/* 데이터의 흐름
 * POST 방식으로 JSON을 보낸다 : @ReqeustBody 로 받을 것. (받는 실제 도구는 MAP) -> 상호 보완해주는 도구가 jaxon.lib
 */ 