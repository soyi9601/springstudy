package com.gdu.prj02.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.prj02.dto.BlogDto;

@Controller
public class MyController4 {

  @RequestMapping("/blog/list.do")
  // value="/blog/list.do", method=RequestMethod.GET  -- value, method 생략가능
  public String list(Model model) {
    
    // DB 에서 select 한 결과
    List<BlogDto> blogList = Arrays.asList(
        new BlogDto(1, "제목1"),
        new BlogDto(2, "제목2"),
        new BlogDto(3, "제목3")
        );
    
    model.addAttribute("blogList", blogList);
    
    // 기본 이동 방식은 forward 방식이다.
    // model 은 forward 할 때 데이터를 저장해주는 역할
    return "blog/list";
  }
  
  // location.href 로 이동하는 방식은 GET = a 태그 이동과 같은 방식 -> 경로만 적어주면 됨
  @RequestMapping("/blog/detail.do")
  public String detail(@RequestParam(value="blogNo"
                                   , required=false
                                   , defaultValue="0") int blogNo, Model model) {   // forward 할 때 model 사용!
    
    // DB 에서 가져온 데이터
    BlogDto blog = BlogDto.builder()
                        .blogNo(blogNo)
                        .title("제목" + blogNo)
                      .build();
    
    // JSP 로 전달할 데이터
    model.addAttribute("blog", blog);
    
    // blog/detail.jsp 로 이동
    return "blog/detail";
    
  }
  
  // @RequestMapping(value="/blog/add.do", method=RequestMethod.POST)
  public String add(BlogDto blog) {   // 커맨드 객체의 Model 저장 방식 : 클래스타입을 camelCase 로 변경해서 저장한다. (BlogDto -> blogDto로 변경해서 저장)
    
    // 파라미터 이름(detail.jsp의 input name)과 필드의 이름(BlogDto.java -> blogNo, title)이 동일하면 setter 를 이용해서 불러올 수 있음
    
    // blog/addResult.jsp 로 forward
    return "blog/addResult";
    
  }
  
  @RequestMapping(value="/blog/add.do", method=RequestMethod.POST)
  public String add2(@ModelAttribute("blog")BlogDto blog) {    // @ModelAttribute : 커맨드 객체가 model 저장되는
    
    return "blog/addResult";
    
  }
  
}
