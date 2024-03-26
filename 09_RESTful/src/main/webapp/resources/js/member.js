/**
 * 
 */

// jQuery 객체 선언
var email = $('#email');
var mName = $('#name');
// var gender = $('input[type=radio][name=gender]')
var zonecode = $('#zonecode');
var address = $('#address');
var detailAddress = $('#detailAddress');
var extraAddress = $('#extraAddress');
var btnInit = $('#btn-init');
var btnRegister = $('#btn-register');
var btnModify = $('#btn-modify');
var btnRemove = $('#btn-remove');

// 함수 표현식 (함수 만들기) -> 입력창 초기화
const fnInit = ()=>{
  email.val('');
  mName.val('');
  $('#none').prop('checked', true);
  // $('#none').attr('checked', 'checked');
  zonecode.val('');
  address.val('');
  detailAddress.val('');
  extraAddress.val('');
}

const getContextPath = ()=>{
  const host = location.host; /* localhost:8080 */
  const url = location.href   /* http://localhost:8080/mvc/getDate.do */
  const begin = url.indexOf(host) + host.length;
  const end = url.indexOf('/', begin + 1);
  return url.substring(begin, end);
}
 
const fnRegisterMember = ()=>{
  $.ajax({
    // 요청
    type: 'POST',
    url: getContextPath() + '/members',
    contentType: 'application/json',  // 보내는 데이터의 타입
    data: JSON.stringify({    // 서버로 데이터를 보낼 때 : JSON.stringify(JS 객체) : 문자열 형식의 JSON 데이터
      'email': email.val(),
      'name': mName.val(),
      'gender': $(':radio:checked').val(),
      'zonecode': zonecode.val(),
      'address': address.val(),
      'detailAddress': detailAddress.val(),
      'extraAddress': extraAddress.val()
    }),  
    // 응답
    dataType: 'json'  // 응답 데이터 타입
  }).done(resData=>{  // resData = {"insertCount": 2} 
    if(resData.insertCount === 2) {
      alert('정상적으로 등록');
      fnInit();
    }
  }).fail(jqXHR=>{    // jqXHR = {"insertCount": 0}  
    alert(jqXHR.responseText);
  })
}

// 함수 호출 및 이벤트
fnInit();
btnInit.on('click', fnInit);
btnRegister.on('click', fnRegisterMember);


