package com.gdu.prj07.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {

//  @Inject
//  private BoardDao boardDao;

  @GetMapping(value= {"/", "/main.do"})
  public String welcome() {
    // System.out.println(boardDao.getBoardList());    
    return "index";
  }
  
  
  
}
