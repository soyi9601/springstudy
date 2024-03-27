/**
 * 
 */

 
// 전역 변수 (vxxxx)
var vPage = 1;
var vDisplay = 20;
 
// jQuery 객체 선언 (jqxxxx)
var jqMembers = $('#members');
var jqTotal = $('#total');
var jqPaging = $('#paging');
var jqDisplay = $('#display');
var jqEmail = $('#email');
var jqName = $('#name');
var jqZonecode = $('#zonecode');
var jqAddress = $('#address');
var jqDetailAddress = $('#detailAddress');
var jqExtraAddress = $('#extraAddress');
var jqBtnInit = $('#btn-init');
var jqBtnRegister = $('#btn-register');
var jqBtnModify = $('#btn-modify');
var jqBtnRemove = $('#btn-remove');
var jqBtnSelectRemove = $('#btn-select-remove');


// 함수 표현식 (함수 만들기) -> 입력창 초기화
const fnInit = ()=>{
  jqEmail.val('');
  jqName.val('');
  $('#none').prop('checked', true);
  // $('#none').attr('checked', 'checked');
  jqZonecode.val('');
  jqAddress.val('');
  jqDetailAddress.val('');
  jqExtraAddress.val('');
}

const fnGetContextPath = ()=>{
  const host = location.host; // localhost:8080 /
  const url = location.href   // http://localhost:8080/mvc/getDate.do /
  const begin = url.indexOf(host) + host.length;
  const end = url.indexOf('/', begin + 1);
  return url.substring(begin, end);
}

// 등록
const fnRegisterMember = ()=>{
  $.ajax({
    // 요청
    type: 'POST',
    url: fnGetContextPath() + '/members',
    contentType: 'application/json',  // 보내는 데이터의 타입
    data: JSON.stringify({    // 서버로 데이터를 보낼 때 : JSON.stringify(JS 객체) : 문자열 형식의 JSON 데이터
      'email': jqEmail.val(),
      'name': jqName.val(),
      'gender': $(':radio:checked').val(),
      'zonecode': jqZonecode.val(),
      'address': jqAddress.val(),
      'detailAddress': jqDetailAddress.val(),
      'extraAddress': jqExtraAddress.val()
    }),  
    // 응답
    dataType: 'json'  // 응답 데이터 타입
  }).done(resData=>{  // resData = {"insertCount": 2} 
    if(resData.insertCount === 2) {
      alert('정상적으로 등록');
      fnInit();
      fnGetMemberList();
    }
  }).fail(jqXHR=>{    // jqXHR = {"insertCount": 0}  
    alert(jqXHR.responseText);
  })
}

// 함수 호출 및 이벤트
fnInit();
jqBtnInit.on('click', fnInit);
jqBtnRegister.on('click', fnRegisterMember);

//함수 표현식 (함수 만들기)
const fnMemberList = ()=>{
  $.ajax({
    type: 'GET',
    url: fnGetContextPath() + '/members/page/' + vPage + '/display/' + vDisplay,
    dataType: 'json',
    success: (resData)=>{   // resData = {"members" : [{}, {}, {}], "total" : 30}
      jqTotal.html('총 회원 ' + resData.total + '명');
      jqMembers.empty();
      $.each(resData.members, (i, member)=>{
        let str = '<tr>';
        str += '<td><input type="checkbox" class="chk_member" value="' + member.member.memberNo + '">' +'</td>';
        str += '<td>'+ member.member.email +'</td>';
        str += '<td>'+ member.member.name +'</td>';
        str += '<td>'+ member.member.gender +'</td>';
        str += '<td><button type="button" class="btn-detail" data-member-no="' + member.member.memberNo +'">조회</button></td>';
        str += '</tr>';
        jqMembers.append(str);
      })
      jqPaging.html(resData.paging);
    },
    error: (jqXHR)=>{
      alert(jqXHR.statusText + '(' + jqXHR.status + ')');
    }
  })  
}

// 함수 표햔식 (함수 만들기)
  const getMemberByNo = (evt)=>{
    $.ajax({
      type: 'GET',
      url: fnGetContextPath() + '/members/' + evt.target.dataset.memberNo,
      dataType: 'json'
    }).done(resData=>{   
      /* resData = {
        "addressList": [
          {
            "addressNo": 1,
            "zonecode": "1234",
            "address": "ddd",
            "detailAddress": "ddd",
            "extraAddress": "(ddd)"
          },
          ...
        ],
        "member": {
          "memberNo":1,
          "email": eee,
          "name": "nnn",
          "gender": "man"
        }
    } */
      fnInit();
      if(resData.member !== null) {
        jqEmail.val(resData.member.email);
        jqName.val(resData.member.name);
        $(':radio[value=' + resData.member.gender + ']').prop('checked', true);       
      }
      if(resData.address.length() !== 0) {
        jqZonecode.val(resData.addressList[0].zonecode);
        jqAddress.val(resData.addressList[0].address);
        jqDetailAddress.val(resData.addressList[0].detailAddress);
        jqExtraAddress.val(resData.addressList[0].extraAddress);        
      }
    }).fail(jqXHR=>{
      alert(jqXHR.statusText + '(' + jqXHR.status + ')');
    })
  }
  
  // 함수 호출 및 이벤트
  $(document).on('click', '.btn-detail', (evt)=>{
    getMemberByNo(evt);
  })

const fnChangeDisplay = ()=>{
  vDisplay = jqDisplay.val();
  fnMemberList();
}

const fnPaging = (p)=>{
  vPage = p;
  fnMemberList();
}

// 함수 호출 및 이벤트
fnInit();
jqBtnInit.on('click', fnInit);
jqBtnRegister.on('click', fnRegisterMember);
fnMemberList();
jqDisplay.on('change', fnChangeDisplay);
