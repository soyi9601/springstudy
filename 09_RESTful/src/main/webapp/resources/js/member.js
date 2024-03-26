/**
 * 
 */

// jQuery 객체 선언
var members = $('#members');
var total = $('#total');
var paging = $('#paging');
var jqDisplay = $('#display');
var email = $('#email');
var mName = $('#name');
var zonecode = $('#zonecode');
var address = $('#address');
var detailAddress = $('#detailAddress');
var extraAddress = $('#extraAddress');
var btnInit = $('#btn-init');
var btnRegister = $('#btn-register');
var btnModify = $('#btn-modify');
var btnRemove = $('#btn-remove');
var btnSelectRemove = $('#btn-select-remove');


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


/* 두번째 script */

// 전역 변수
var page = 1;
var display = 20;

//함수 표현식 (함수 만들기)
const fnMemberList = ()=>{
  $.ajax({
    type: 'GET',
    url: getContextPath() + '/members/page/' + page + '/display/' + display,
    dataType: 'json',
    success: (resData)=>{   // resData = {"members" : [{}, {}, {}], "total" : 30}
      total.html('총 회원 ' + resData.total + '명');
      members.empty();
      $.each(resData.members, (i, member)=>{
        let str = '<tr>';
        str += '<td><input type="checkbox" class="chk_member" value="' + member.member.memberNo + '">' +'</td>';
        str += '<td>'+ member.member.email +'</td>';
        str += '<td>'+ member.member.name +'</td>';
        str += '<td>'+ member.member.gender +'</td>';
        str += '<td><button type="button" class="btn-detail" data-member-no="' + member.member.memberNo +'">조회</button></td>';
        str += '</tr>';
        members.append(str);
      })
      paging.html(resData.paging);
    },
    error: (jqXHR)=>{
      alert(jqXHR.statusText + '(' + jqXHR.status + ')');
    }
  })  
}

const fnChangeDisplay = ()=>{
  jqdisplay.val();
  fnMemberList();
}

const fnPaging = (p)=>{
  page = p;
  fnMemberList();
}

// 함수 호출 및 이벤트
fnMemberList();

