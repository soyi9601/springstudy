package com.gdu.myapp.utils;

import java.util.Properties;

import javax.mail.Session;

public class MyJavaMailUtils {

  public void sendMail(String to, String subject, String contents) {
    
    // 이메일을 보내는 호스트의 정보 : 구글
    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", 587);
    props.put("mail.smtp.auth", true);
    props.put("mail.smtp.starttls.enable", true);
    
    // javax.mail.Session 객체 생성 : 이메일을 보내는 사용자의 정보 (개인 정보)
    Session session = Session.getInstance(props, null);
    
    
    
    
  }
  
}
