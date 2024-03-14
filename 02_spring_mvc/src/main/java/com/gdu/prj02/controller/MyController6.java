package com.gdu.prj02.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@SessionAttributes(names="user")  // Model 에 user 가 저장되면 session 에 같은 값을 저장한다.
@Controller
public class MyController6 {

  @GetMapping("/user/login1.do")
  public String login1(HttpServletRequest request) {
    
    // HttpSession 구하기
    HttpSession session = request.getSession();
    
    // session 에 저장할 객체
    UserDto user = new UserDto(1, "min@naver.com");
    
    // session 에 객체 저장하기
    session.setAttribute("user", user);
    
    // 메인 페이지로 이동    
    return "redirect:/main.do";
    // return "index"   둘 중 권장은 redirect 방법
    // return "redirect:" + request.getParameter("redirectURL"); 로그인 후 열려있던 현재 페이지로 돌아오고 싶을 때
    
  }
  
  @GetMapping("user/logout1.do")
  public String logout1(HttpServletRequest request) {
    
    // HttpSession 구하기
    HttpSession session = request.getSession();
    
    // session 의 모든 정보 지우기
    session.invalidate();
    
    // 메인 페이지로 이동
    return "redirect:/main.do";
    
  }
  
  @GetMapping("/user/login2.do")
  public String login2(Model model) {
    
    // model 에 저장할 객체
    UserDto user = new UserDto(1, "min@naver.com");
    
    // model 에 객체 저장하기 (@sessionAttributes 에 의해서 session 에도 저장된다.)
    model.addAttribute("user", user);
    
    return "redirect:/main.do";
    
  }
  
  @GetMapping("/user/logout2.do")
  public String logout2(SessionStatus sessionStatus) {
    
    // session attribute 삭제를 위해 세션 완료 처리
    sessionStatus.setComplete();
    
    // 메인 페이지로 이동
    return "redirect:/main.do";
    
  }
  
  // @GetMapping("/user/mypage.do")
  public String mypage1(HttpSession session, Model model) {
    
    // session 에 저장된 user 정보
    UserDto user =  (UserDto) session.getAttribute("user");   // Attribute 의 타입은 object 이므로 userDto 타입으로 캐스팅해줘야함.
    
    // model 에 user 정보 저장
    model.addAttribute("user", user);
    
    // user/mypage.jsp 로 forward
    return "user/mypage";
    
  }
  
  @GetMapping("/user/mypage.do")
  public String mypage2(@SessionAttribute(name="user") UserDto user) {  // session attribute 중 user 를 UserDto user 에 저장하시오.
    
    // user/mypage.jsp 로 forward
    return "user/mypage";
    
  }
  
//  @GetMapping("/user/mypage.do")
//  public String mypage2(@SessionAttribute(name = "user") UserDto user) {  // session attribute 중 user 를 UserDto user 에 저장하시오.
//    
//    // user/mypage.jsp 로 forward
//    return "user/mypage";
//    
//  }
  
}
