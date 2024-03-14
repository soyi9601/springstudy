package com.gdu.prj03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

//  @Inject
//  private BoardDao boardDao;

  @GetMapping(value= {"/", "/main.do"})
  public String welcome() {
    // System.out.println(boardDao.getBoardList());    
    return "index";
  }
  
}
