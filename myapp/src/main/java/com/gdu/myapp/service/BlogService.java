package com.gdu.myapp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface BlogService {
  ResponseEntity<Map<String, Object>> summernoteImageUpload(MultipartFile multipartFile, String contextPath);
  int registerBlog(HttpServletRequest request);
  // 선택지 : Request, RequestParam, 커맨드 객체
  // requestParam 은 제외 : title도 넣ㅇ러야되고 넣을게 너무 많음.
}
