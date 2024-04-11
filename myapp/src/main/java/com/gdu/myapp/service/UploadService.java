package com.gdu.myapp.service;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface UploadService {
  // 되었다 안되었다를 true/false 로 받아오기
  public boolean registerUpload(MultipartHttpServletRequest multipartRequest);
  public void loadUploaddList(Model model);
}
