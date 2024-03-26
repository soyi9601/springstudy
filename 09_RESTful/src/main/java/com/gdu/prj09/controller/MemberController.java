package com.gdu.prj09.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gdu.prj09.service.MemberService;

import lombok.RequiredArgsConstructor;

/*
 * RESTful
 * 1. REpresentation State Transfer
 * 2. 요청 주소를 작성하는 한 방식이다.
 * 3. 요청 파라미터를 ? 뒤에 추가하는 Query String 방식을 사용하지 않는다.
 * 4. 요청 파라미터를 주소에 포함하는 Path Variable 방식을 사용하거나, 요청 본문에 포함하는 방식을 사용한다.
 * 5. 요청의 구분을 "주소 + 메소드" 조합으로 구성한다.
 * 6. CRUD 요청 예시
 * 
 *           |   URL                       | Method
 *   --------|-----------------------------|----------
 *   1) 목록 | /members                    | GET
 *           | /members/page/1             |
 *           | /members/page/1/display/20  |  
 *   2) 상세 | /members/1                  | GET
 *   3) 삽입 | /members                    | POST
 *   4) 수정 | /members                    | PUT
 *   5) 삭제 | /members/1                  | DELETE
 *           | /members/1,2,3,             | 
 *        
 */

@RequiredArgsConstructor
@Controller
public class MemberController {

  private final MemberService memberService;  // StringContainer 에서 가져올 수 있는지, 만들어져있어야 불러올 수 있음. <bean>태그에 있던지, @Bean, @Component 가 있어야함.
  
  @GetMapping("/admin/member.do")
  public void adminMember() {
    // 반환타입이 void 면 주소를 JSP 경로로 인식한다.
    // /admin/member.do ====> /WEB-INF/views/admin/member.jsp
  }
  
  @PostMapping(value="/members", produces="application/json")
  public ResponseEntity<Map<String, Object>> registerMember(@RequestBody Map<String, Object> map
                                                            , HttpServletResponse response) {    
    return memberService.registerMember(map, response);
  }
  
  @GetMapping(value="/members/page/{p}/display/{dp}", produces="application/json")
  // Optional : Null 일 수 있는 애들을 싸서 한번 불러봐서 확인해보는 것. value="p"가 필수는 아니다. 안올 수도 있다.
  public ResponseEntity<Map<String, Object>> getMembers(@PathVariable(value="p", required=false) Optional<String> optPage
                                                      , @PathVariable(value="dp", required=false) Optional<String> optDisplay) {
    int page = Integer.parseInt(optPage.orElse("1"));  // Null 이면 1을 꺼내서 쓴다. Null 이 아니면 꺼내서 해당 값을 쓰면 됨
    int display = Integer.parseInt(optDisplay.orElse("20"));
    return memberService.getMembers(page, display);
  }
  
  @GetMapping(value="/members/{memberNo}", produces="application/json")
  public ResponseEntity<Map<String, Object>> getMemberByNo(@PathVariable(value="memberNo", required=false) Optional<String> opt) {
    int memberNo = Integer.parseInt(opt.orElse("0"));
    return memberService.getMemberByNo(memberNo);
  }
  
}
