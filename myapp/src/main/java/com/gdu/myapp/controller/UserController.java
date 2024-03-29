package com.gdu.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
  public String signinPage(HttpServletRequest request
                         , Model model) {
    
    // Sign In 페이지 이전의 주소가 저장되어 있는 Request Header 의 referer
    // 블로그를 보고 있다가 로그인을 하면 전에 보고 있던 블로그 화면으로 넘어가야함. 그것을 알아내기 위한 값이 referer
    String referer = request.getHeader("referer");
    
    // referer 로 돌아가면 안되는 예외 상황 (아이디/비밀번호 찾기, 가입 화면 등)
    String[] excludeUrls = {};
    
    // Sign In 이후 이동할 url
    // 초기 값을 referer 로 잡으면 main 이면 덮어씌울 수 있음
    String url = referer;
    if(referer != null) {
      for(String excludeUrl : excludeUrls) {
        if(referer.contains(excludeUrl)) {
          url = request.getContextPath() + "/main.page";
          break;
        } 
      }
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
}
