package com.gdu.myapp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.gdu.myapp.dto.UserDto;

public interface UserService {
  
  // 가입 및 탈퇴
  ResponseEntity<Map<String, Object>> checkEmail(Map<String, Object> params);   // 체크 이메일
  ResponseEntity<Map<String, Object>> sendCode(Map<String, Object> params);   // 이메일 인증번호 날리기
  void signup(HttpServletRequest request, HttpServletResponse response);    // 가입
  void leave(HttpServletRequest request, HttpServletResponse response);     // 탈퇴
  
  // 로그인 및 로그아웃
  String getRedirectURLAfterSignin(HttpServletRequest request);
  void signin(HttpServletRequest request, HttpServletResponse response);    // 로그인 + 응답도 로직으로 만들겠다
  void signout(HttpServletRequest request, HttpServletResponse response);   // 로그아웃
  
  // 네이버 로그인
  String getNaverLoginURL(HttpServletRequest request);
  String getNaverLoginAccessToken(HttpServletRequest request);    // 네이버 로그인 토큰 받아오기
  UserDto getNaverLoginProfile(String accessToken);
  boolean hasUser(UserDto user);
  void naverSignin(HttpServletRequest request, UserDto naverUser);
  
}
