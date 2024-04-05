package com.gdu.myapp.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface BbsService {
  int registerBbs(HttpServletRequest request);
  
  // 반환 안하고 실어만 주기.
  void loadBbsList(HttpServletRequest request, Model model);          // 목록 조회
  int registerReply(HttpServletRequest request);                      // 답글 등록
  int removeBbs(int bbsNo);                                           // 삭제
  void loadBbsSearchList(HttpServletRequest request, Model model);    // 검색
}
