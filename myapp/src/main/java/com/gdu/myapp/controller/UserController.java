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

import com.gdu.myapp.dto.UserDto;
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
    
    // Sign In 페이지로 url 또는 referer 넘겨주기 (로그인 후 이동할 경로를 의미함)
    model.addAttribute("url", userService.getRedirectURLAfterSignin(request));
    
    // Sign In 페이지로 naverLoginURL 넘겨주기 (네이버 로그인 요청 주소를 의미함)
    model.addAttribute("naverLoginURL", userService.getNaverLoginURL(request));
    
    return "user/signin";
  }
  
  @PostMapping("/signin.do")
  public void signin(HttpServletRequest request, HttpServletResponse response) {
    userService.signin(request, response);
  }
  
  @GetMapping("/naver/getAccessToken.do")
  public String getAccessToken(HttpServletRequest request) {
    String accessToken = userService.getNaverLoginAccessToken(request);
    return "redirect:/user/naver/getProfile.do?accessToken=" + accessToken;
    // return null;
  }
  
  @GetMapping("/naver/getProfile.do")
  public String getProfile(HttpServletRequest request, Model model) {
    
    // 네이버로부터 받은 프로필 정보
    UserDto naverUser = userService.getNaverLoginProfile(request.getParameter("accessToken"));
    
    // 반환 경로
    String path = null;
    
    // 프로필이 DB에 있는지 확인 (있으면 Sign In, 없으면 Sign Up)
    if(userService.hasUser(naverUser)) {
      // Sign In
      userService.naverSignin(request, naverUser);
      path = "redirect:/main.page";
    } else {
      // Sign Up (네이버 가입 화면으로 이동) -> forward
      model.addAttribute("naverUser", naverUser);
      path = "user/naver_signup";
    }
    
    return path;
    
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
  
  @PostMapping("/signup.do")
  public void signup(HttpServletRequest request, HttpServletResponse response) {
    userService.signup(request, response);
  }
  
  @GetMapping("/leave.do")
  public void leave(HttpServletRequest request, HttpServletResponse response) {    
    userService.leave(request, response);
  }
  /*
  @GetMapping("/leave.do")
  public void leave(HttpSession session, HttpServletResponse response) {    
    UserDto user = (UserDto) session.getAttribute("user");    
  }
  @GetMapping("/leave.do")
  public void leave(@SessionAttribute(name="user"), HttpServletResponse response) {
    // 세션정보필요, HttpServletResponse response 응답을 던져줘야함  
  }
  */
  
  @GetMapping("/signout.do")
  public void signout(HttpServletRequest request, HttpServletResponse response) {
    userService.signout(request, response);
  }
  
  
}
