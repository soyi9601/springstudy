package com.gdu.myapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.myapp.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    super();
    this.userService = userService;
  }
  
  @GetMapping("/signin.page")
  // 이전페이지의 주소값 알아내기 Request 안에 있는 header 값 "referer" 
  // index.jsp 에서 "referer" 값은 null
  public String signinPage(HttpServletRequest request
                         , Model model) {
    
    // Sign In 페이지 이전의 주소가 저장되어 있는 Request Header 의 referer
    // 블로그를 보고 있다가 로그인을 하면 전에 보고 있던 블로그 화면으로 넘어가야함. 그것을 알아내기 위한 값이 referer
    String referer = request.getHeader("referer");
    
    // referer 로 돌아가면 안되는 예외 상황 (아이디/비밀번호 찾기, 가입 화면 등)
    String[] excludeUrls = {"/findId.page", "findPw.page", "/signup.page"};
    
    // Sign In 이후 이동할 url
    // 초기 값을 referer 로 잡으면 main으로 보내고 싶을 때(예외상황) referer을 main으로 덮어씌울 수 있음
    String url = referer;
    if(referer != null) {
      for(String excludeUrl : excludeUrls) {
        if(referer.contains(excludeUrl)) {
          url = request.getContextPath() + "/main.page";
          break;
        } 
      }
      // referer 값이 없을 때
    } else {
      url = request.getContextPath() + "/main.page";
    }
    
    // Sign In 페이지로 url 또는 referer 넘겨주기
    model.addAttribute("url", url);
    
    return "user/signin";
  }
  
  @PostMapping("/signin.do")
  public void signin(HttpServletRequest request, HttpServletResponse response) {
    userService.signin(request, response);
  }
  
  @GetMapping("/signup.page")
  public String signupPage() {
    return "user/signup";
  }
  
  // 비동기처리에서 응답은 JSON 처리 1개
  @PostMapping(value="/checkEmail.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> checkEmail(@RequestBody Map<String, Object> params) {
    return userService.checkEmail(params);
    // service 에서 넘긴 return 은 controller 로 오고 바로 화면으로 넘어간다.
  }
  
  @PostMapping(value="/sendCode.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> sendCode(@RequestBody Map<String, Object> params) {
    return userService.sendCode(params);
  }
  
 
  
}
