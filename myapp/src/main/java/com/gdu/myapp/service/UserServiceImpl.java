package com.gdu.myapp.service;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gdu.myapp.dto.UserDto;
import com.gdu.myapp.mapper.UserMapper;
import com.gdu.myapp.utils.MyJavaMailUtils;
import com.gdu.myapp.utils.MySecurityUtils;


@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;
  private final MyJavaMailUtils myJavaMailUtils;
  
  

  // @Autowired :: 생략 가능하지만 명시할 수 있음.
  public UserServiceImpl(UserMapper userMapper, MyJavaMailUtils myJavaMailUtils) {
    super();
    this.userMapper = userMapper;
    this.myJavaMailUtils = myJavaMailUtils;
  }
    
  /*
   * 위의 생성자를 만들던가
   * @RequiredArgsConstructor ::  annotation 을 쓰던지
   * 둘 중 하나만 쓸 수있음.
   * 그러나 lombok 의 사용이 불가할 수 있기 때문에 생성자를 만들어야할 수도 있다.
  */

  @Override
  public void signin(HttpServletRequest request, HttpServletResponse response) {    
    try {
      
      // 입력한 아이디
      String email = request.getParameter("email");
      
      // 입력한 비밀번호 + SHA256 방식의 암호화
      String pw = MySecurityUtils.getSha256(request.getParameter("pw"));    // 사용자가 입력한 패스워드가 암호화 돼서 나올 것. 
      
      // 접속 IP (접속 기록을 남길 때)
      String ip = request.getRemoteAddr();    // IP 저장! *********
      
      // DB로 보낼 정보 (email/pw -> USER_T, email/IP -> ACCESS_HISTORY_T) *우리는 이것을 Map 에 담기로 함.
      Map<String, Object> params = Map.of("email", email
                                        , "pw", pw
                                        , "ip", ip);
      
      // email/pw 가 일치하는 회원 정보 가져오기
      UserDto user = userMapper.getUserByMap(params);
      
      // 일치하는 회원 있음 (Sign In 성공)
      if(user != null) {
        // 접속 기록 ACCESS_HISTORY_T 에 남기기
        userMapper.insertAccessHistory(params); 
        // ****** 로그인의 기본 원리는 session 이라는 저장소(데이터바인딩 영역)에 정보를 올려주는 것. "user" 이런 정보는 모든 팀원들이 공유하고 있어야 할 정보
        // (브라우저 닫기 전까지 정보가 유지되는 공간, 기본 30분 정보 유지)
        request.getSession().setAttribute("user", user);
        // Sign In 후 페이지 이동
        response.sendRedirect(request.getParameter("url"));
      
      // 일치하는 회원 없음 (Sign In 실패)
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
  public ResponseEntity<Map<String, Object>> checkEmail(Map<String, Object> params) {
    // Email 을 쓸 수 있다 vs 없다
                          // 정상적인 사용자가 없어야 이메일을 사용 가능
    boolean enableEmail = userMapper.getUserByMap(params) == null
                       && userMapper.getLeaveUserByMap(params) == null;
    return new ResponseEntity<>(Map.of("enableEmail", enableEmail)
                              , HttpStatus.OK);
  }
  
  @Override
  public ResponseEntity<Map<String, Object>> sendCode(Map<String, Object> params) {
    // Map 에 받는 사람 이메일 정보 들어있음
    
    // 인증코드 생성 (만드는 방법은 MySecurityUtils 안에 있음)
    String code = MySecurityUtils.getRandomString(6, false, true);
    
    // 메일 보내기
    myJavaMailUtils.sendMail((String)params.get("email")
                           , "myapp 인증요청"
                           , "<div>인증코드는 <strong>" + code + " </storng> 입니다.</div>");
    
    // 인증코드 입력화면으로 보내주는 값
    return new ResponseEntity<>(Map.of("code", code)
                              , HttpStatus.OK);
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
