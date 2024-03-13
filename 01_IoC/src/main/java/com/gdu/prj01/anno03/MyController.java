package com.gdu.prj01.anno03;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyController {

  private MyService myService;
  
  public void add() {
    myService.add();
    System.out.println("MyController add() 호출");
  }
  
}
