package com.gdu.myapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.myapp.service.BlogService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/blog")
@RequiredArgsConstructor
@Controller
public class BlogController {
  
  private final BlogService blogService;
  
  // 평소와 다른 형태의 List : 평소엔 blogService 를 호출해서 사용했음. -> 단순 페이지 이동(아무것도 가져가지않고)
  // ajax 을 이용하여 가져올 예정
  // 빈페이지로 감 -> ajax(javascript) 로 최초 목록을 뿌려준다.
  @GetMapping("/list.page")
  public String list() {
    return "blog/list";
  }
  
  @GetMapping("/write.page")
  public String writePage() {
    return "blog/write";
  }
  
  @PostMapping(value="/summernote/imageUpload.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> summernoteImageUpload(@RequestParam("image") MultipartFile multipartFile) {
    return blogService.summernoteImageUpload(multipartFile);
  }
  /*
   * @PostMapping(value="/summernote/imageUpload.do", produces="application/json")
   * // form에 Requestparam 하나 들어있다. // requestParam을 받을 때 multipartFile로 받아라!!! //
   * parameter 가 여러개면 RequestParam으로 안받고 request로 받을 건데 image 하나밖에 없어서
   * Requestparam으로 받는 것. public ResponseEntity<Map<String, Object>>
   * summernoteImageUpload(@RequestParam("image") MultipartFile multipartFile) {
   * return blogService.summernoteImageUpload(multipartFile); }
   */
  
  @PostMapping("/register.do")
  public String register(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("insertCount", blogService.registerBlog(request));
    return "redirect:/blog/list.page";
  }
  
  @GetMapping(value="/getBlogList.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> getBlogList(HttpServletRequest request) {
    return blogService.getBlogList(request);
  }
  
  @GetMapping("/detail.do")
  // 번호 한개 blogNo, Model 에 blog 내용을 저장을 해서 넘겨줘야함.
  public String detail(@RequestParam int blogNo, Model model) {
    model.addAttribute("blog", blogService.getBlogByNo(blogNo));
    return "/blog/detail";
  }
  
  @PostMapping(value="/registerComment.do", produces="application/json")
  public ResponseEntity<Map<String, Object>> registerComment(HttpServletRequest request) {
    return new ResponseEntity<Map<String,Object>>(Map.of("insertCount", blogService.registerComment(request))
                                                , HttpStatus.OK);
  }
  
  
  
}
