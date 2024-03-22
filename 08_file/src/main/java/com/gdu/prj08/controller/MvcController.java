package com.gdu.prj08.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {

  
  @GetMapping(value= {"/", "/main.do"})
  public String welcome() {
    // System.out.println(boardDao.getBoardList());   
    return "index";
  }
  
  
  
}
