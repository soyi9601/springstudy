package com.gdu.myapp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.myapp.dto.BlogDto;

public interface BlogService {
  ResponseEntity<Map<String, Object>> summernoteImageUpload(MultipartFile multipartFile);
  int registerBlog(HttpServletRequest request);
  // 목록과 페이지 2개를 가져가기 때문에 Map 으로 묶어서 가야함. 목록만 가져간다면 List 작성
  // 페이징 최초 - total, sort, page 등 3개를 가져옴. 1개 이상의 데이터를 넘겨줌
  ResponseEntity<Map<String, Object>> getBlogList(HttpServletRequest request); 
  BlogDto getBlogByNo(int blogNo);
  int registerComment(HttpServletRequest request);
}

// 선택지 : Request, RequestParam, 커맨드 객체
// requestParam 은 제외 : title도 넣ㅇ러야되고 넣을게 너무 많음.