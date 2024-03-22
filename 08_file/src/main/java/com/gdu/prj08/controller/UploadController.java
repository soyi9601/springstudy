package com.gdu.prj08.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.prj08.service.UploadService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UploadController {

  private final UploadService uploadService;
  
  @PostMapping("/upload1.do")
  public String upload1(MultipartHttpServletRequest multipartRequest
                        , RedirectAttributes redirectAttributes) {
    int insertCount = uploadService.upload1(multipartRequest);
    redirectAttributes.addFlashAttribute("insertCount", insertCount);
    return "redirect:/main.do";   // INSERT 후에는 redirect
  }
  
  @ResponseBody   // return 할 때 jsp가 아니라 데이터 자체를 반환할 것이다. 라고 알려줄 수 있는 annotation
  @PostMapping(value="/upload2.do", produces="application/json")
  public Map<String, Object> upload2(MultipartHttpServletRequest multipartRequest) {
    return uploadService.upload2(multipartRequest);
  }
  
  
//  @PostMapping(value="/upload2.do", produces="application/json")
//  public ResponseEntity<Map<String, Object>> upload2(MultipartHttpServletRequest multipartRequest) {
//    return new ResponseEntity(Map.of("success", 1), HttpStatus.OK);
//  }
  
}

// pom.xml - jackson(dependency에 넣은 것) : json <-> map (이렇게 작업하라라고 알려줄 수 있는건 produces="application/json" 이라고 기재해야 jackson 이 일을 할 수 있음)
// map 을 쓴건 json 을 쓰기 위해서 한거고
// responseEntity 는 비동기 객체를 위해 쓸 수 있는 것


