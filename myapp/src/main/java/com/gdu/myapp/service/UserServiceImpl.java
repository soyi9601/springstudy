package com.gdu.myapp.service;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.myapp.dto.UserDto;
import com.gdu.myapp.mapper.UserMapper;
import com.gdu.myapp.utils.MySecurityUtils;

import lombok.RequiredArgsConstructor;


@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;
  
  // @Autowired :: 생략 가능하지만 명시할 수 있음.
  public UserServiceImpl(UserMapper userMapper) {
    super();
    this.userMapper = userMapper;
  }
  
  /*
   * public UserServiceImpl(@Autowired UserMapper userMapper) {
   *   super();
   *   this.userMapper = userMapper;
   * }  -- UserMapper userMapper :: 매개변수로 사용
   */
  
  /*
   * 위의 생성자를 만들던가
   * @RequiredArgsConstructor ::  annotation 을 쓰던지
   * 둘 중 하나만 쓸 수있음.
   * 그러나 lombok 의 사용이 불가할 수 있기 때문에 생성자를 만들어야할 수도 있다.
  */

  @Override
  public void signin(HttpServletRequest request, HttpServletResponse response) {
    
    try {
      
      String email = request.getParameter("email");
      String pw = MySecurityUtils.getSha256(request.getParameter("pw"));    // 사용자가 입력한 패스워드가 암호화 돼서 나올 것. 
      
      // 우리는 이것을 Map 에 담기로 함
      Map<String, Object> params = Map.of("email", email
          , "pw", pw);
      
      UserDto user = userMapper.getUserByMap(params);
      
      if(user != null) {
        // 로그인의 기본 원리는 session 이라는 저장소(데이터바인딩 영역)에 정보를 올려주는 것.
        request.getSession().setAttribute("user", user);
        response.sendRedirect(request.getParameter("url"));
      } else {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('일치하는 회원 정보가 없습니다.')");
        out.println("location.href='"+ request.getContextPath() +"/main.page'");
        out.println("</script>");
        out.flush();
        out.close();
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }

    
  }

  @Override
  public void signout(HttpServletRequest request, HttpServletResponse response) {
    // TODO Auto-generated method stub

  }

  @Override
  public void signup(HttpServletRequest request, HttpServletResponse response) {
    // TODO Auto-generated method stub

  }

  @Override
  public void leave(HttpServletRequest request, HttpServletResponse response) {
    // TODO Auto-generated method stub

  }

}
