package com.gdu.prj09.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.gdu.prj09.dto.MemberDto;

// 하나의 서비스는 여러개의 dao 를 불러올 수 있음
// 목록, 상세, 등록, 수정, 삭제(2개)

// 싱글페이지 처리이기 때문에 service 가 반환하는 값은 다 1개로 통일할 것. -> ResponseEntity
// @ResponseBody를 품고 있는 ResponseEntity
// ResponseBody 는 wrapper 같은 것. 실제로 입력해야할 데이터는 <T>안에 넣어줘야함

// 파라미터 받아올 수 있는 3가지 방법 : HttpservletRequest, @RequestParam, MyPageUtils 
// prj09/members/page/1/display/20 형태의 주소 체계로 바꿔서 작업해볼 예정으로 위의 파라미터를 받아올 수 있는 3가지 방법이 아닌 다른 방식으로 작업예정.

// 경로에 포함된 데이터 : @PathVariable

public interface MemberService {

  ResponseEntity<Map<String, Object>> getMembers(int page, int display);
  ResponseEntity<MemberDto> getMemberByNo(int memberNo);
  ResponseEntity<Map<String, Object>> registerMember(Map<String, Object> map, HttpServletResponse response);   // 중복체크를 위해서 HttpServletResponse를 같이 파라미터로 넘겨주는 것임
  ResponseEntity<Map<String, Object>> modifyMember(MemberDto member);
  ResponseEntity<Map<String, Object>> removeMember(int memberNo);   // 주소체계 : members/1 을 불러와서 지울 수 있음(삭제, 상세보기)
  ResponseEntity<Map<String, Object>> removeMembers(String memberNoList);
}
