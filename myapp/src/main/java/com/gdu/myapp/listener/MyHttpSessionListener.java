package com.gdu.myapp.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gdu.myapp.mapper.UserMapper;

public class MyHttpSessionListener implements HttpSessionListener {

  // session 이 만료 시 자동으로 동작
  @Override
  public void sessionDestroyed(HttpSessionEvent se) {
    
    // HttpSession
    HttpSession session = se.getSession();
    
    // ApplicationContext
    ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
    // 데이터바인딩 영역
    // pageContext(현재 페이지에서만 저장한다.), request(한번의 응답에서만 - Model)
    // session(브라우저 열고 닫을 때까지), ServletContext(서비스 시작해서 끝날 때까지 - application)
    
    // session_id
    String sessionId = session.getId();
    
    // getBean()
    // Service 를 추천하나, Mapper 도 가능
    UserMapper userMapper = ctx.getBean("userMapper", UserMapper.class);
    
    // updateAccessHistory() 동작
    userMapper.updateAccessHistory(sessionId);
    
    // 확인 메세지
    System.out.println(sessionId + "세션 정보가 소멸되었습니다.");
    
  }
  
}
