package com.gdu.myapp.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myapp.dto.UploadDto;

public interface UploadService {
  // 되었다 안되었다를 true/false 로 받아오기
  boolean registerUpload(MultipartHttpServletRequest multipartRequest);
  void loadUploaddList(Model model);
  // UploadDto getUploadByNo(int uploadNo);
  void loadUploadByNo(int uploadNo, Model model);
  // 파일 자체를 반환할 때는 resource 를 쓰면 편하다. 페이지 안바꾸는 요청이기 때문에 responseEntity
  ResponseEntity<Resource> download(HttpServletRequest request);
  ResponseEntity<Resource> downloadAll(HttpServletRequest request);
  void removeTempFiles();
  UploadDto getUploadByNo(int uploadNo);
  int modifyUpload(UploadDto upload);
}
