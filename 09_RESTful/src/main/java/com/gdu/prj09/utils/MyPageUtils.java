package com.gdu.prj09.utils;

public class MyPageUtils {
  private int total;    // 멤버의개수 : db
  private int display;  // 파라미터로 처리 -> 전달이 안되면 기본 20개
  private int page;     // 파라미터로 처리 -> 전달이 안되면 1페이지
  private int begin;    
  private int end;      
  
  private int pagePerBlock = 10;
  private int totalPage;
  private int beginPage;
  private int endPage;
  
  public void setPaging(int total, int display, int page) {
    
    this.total = total;
    this.display = display;
    this.page = page;
    
    // begin 과 end 를 알아야 위의 3개 값(total, display, page)을 알 수 있음. + 계산해라
    // total, display, page 를 가지고 map -> List
    begin = (page - 1) * display + 1;
    end = begin + display - 1;
    /*
     * 1page 1 - 20
     * 2page 21 - 40
     */
    
    totalPage = (int)Math.ceil((double)total / display);
    // 몫을 구하는 것은 소수점으로 나올 수 있도록 double 로 캐스팅해주고 계산한 이후에는 int로 캐스팅 해준다
    /*
     * total  display  totalPage
     * 1000    20       1000 / 20 = 50.0 = 50
     */
    
    beginPage = ((page - 1) / pagePerBlock) * pagePerBlock +  1;
    endPage = Math.min(totalPage, beginPage + pagePerBlock - 1);
    
  }
  
  public String getAsyncPaging() {
    
    /*
     * function getList() { } -> ajax
     * function getPaging() { }
     * <a href="javascript:getPaging()"> 
     */    
    StringBuilder builder = new StringBuilder();
    return builder.toString();
    
  }
}
