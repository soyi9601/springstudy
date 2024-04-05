package com.gdu.myapp.service;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.myapp.mapper.BlogMapper;
import com.gdu.myapp.utils.MyFileUtils;
import com.gdu.myapp.utils.MyPageUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {

  private final BlogMapper blogMapper;
  private final MyPageUtils mypageUtils;
  private final MyFileUtils myFileUtils;
  
  @Override
  public ResponseEntity<Map<String, Object>> summernoteImageUpload(MultipartFile multipartFile, String contextPath) {
    
    // 이미지 저장할 경로 생성
    String uploadPath = myFileUtils.getUploadPath();
    File dir = new File(uploadPath);
    if(!dir.exists()) {
      dir.mkdirs();
    }
    
    // 이미지 저장할 이름 생성
    String fileSystemName = myFileUtils.getFilesystemName(multipartFile.getOriginalFilename());    
    
    // 이미지 저장
    File file = new File(dir, fileSystemName);
    try {
      multipartFile.transferTo(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    // 이미지가 저장된 경로를 Map 으로 반환
    // Generic 타입은 new에서는 Entity 안의 Map 을 생략할 수 있다.
    return new ResponseEntity<>(Map.of("src", contextPath + uploadPath + "/" + fileSystemName)
                                     , HttpStatus.OK);
    
    
  }

  @Override
  public int registerBlog(HttpServletRequest request) {
    // TODO Auto-generated method stub
    return 0;
  }

}
