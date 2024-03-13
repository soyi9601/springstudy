package com.gdu.prj01.xml03;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyService {

  private MyDao myDao;
  
  public void add() {
    myDao.add();
    System.out.println("MyService add() 호출");
  }
  
}
