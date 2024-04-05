package com.gdu.myapp.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
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
    
    /*
     * 구글 앱 비밀번호 설정 방법
     * 1. 구글에 로그인한다.
     * 2. [계정] - [보안]
     * 3. [Google에 로그인하는 방법] - [2단계 인증]을 사용 설정한다.
     * 4. 검색란에 "앱 비밀번호"를 검색한다.
     * 5. 앱 이름을 "myapp"으로 작성하고 [만들기] 버튼을 클릭한다.
     * 6. 16자리 비밀번호가 나타나면 복사해서 사용한다. (비밀번호 사이 공백은 모두 제거한다.)
     */
    
    // Map 에 받는 사람 이메일 정보 들어있음
    
    // 인증코드 생성 (만드는 방법은 MySecurityUtils 안에 있음)
    String code = MySecurityUtils.getRandomString(6, true, true);
    System.out.println("인증코드 : " + code);
    
    // 메일 보내기
    myJavaMailUtils.sendMail((String)params.get("email")
        , "myapp 인증요청"
        , "<div>인증코드는 <strong>" + code + " </strong> 입니다.</div>");
    
    // 인증코드 입력화면으로 보내주는 값
    return new ResponseEntity<>(Map.of("code", code)
        , HttpStatus.OK);
  }
  
  @Override
  public void signup(HttpServletRequest request, HttpServletResponse response) {
    
    // 전달된 파라미터
    String email = request.getParameter("email");
    String pw = MySecurityUtils.getSha256(request.getParameter("pw"));          // pw : 암호화 작업
    String name = MySecurityUtils.getPreventXss(request.getParameter("name"));  // 이름 : 크로스 사이트 스크립팅
    String mobile = request.getParameter("mobile");
    String gender = request.getParameter("gender");
    String event = request.getParameter("event");
    
    String ip = request.getRemoteAddr();
    
    // Mapper 로 보낼 UserDto 객체 생성
    UserDto user = UserDto.builder()
                       .email(email)
                       .pw(pw)
                       .name(name)
                       .mobile(mobile)
                       .gender(gender)
                       .eventAgree(event == null ? 0 : 1)
                     .build();
    
    // 회원 가입
    int insertCount = userMapper.insertUser(user);    // userDto 의 타입의 user 전달!
    
    // 응답 만들기 (성공하면 sign in 처리하고 /main.do 이동, 실패하면 뒤로 가기)
    
    try {
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(insertCount == 1) {
        // 기록을 위한 Map 에 저장
        Map<String, Object> params = Map.of("email", email, "pw", pw, "ip", ip
                                       , "userAgent", request.getHeader("User-Agent")
                                       , "sessionId", request.getSession().getId());
        
        // 세션에 user 저장하기
        request.getSession().setAttribute("user", userMapper.getUserByMap(params));
        userMapper.insertAccessHistory(params);
        
        out.println("location.href='" + request.getContextPath() + "/main.page'");
      } else {
        out.println("history.back()");
      }
      out.println("</script>");
      out.flush();
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
  
  @Override
  public void leave(HttpServletRequest request, HttpServletResponse response) {

    try {      
      // 세션에 저장된 user 값 확인
      HttpSession session = request.getSession();
      UserDto user = (UserDto) session.getAttribute("user");
      
      // 세션 만료로 user 정보가 세션에 없을 수 있음
      if(user == null) {
        response.sendRedirect(request.getContextPath() + "/main.page");
      }
      
      // 탈퇴 처리
      int deleteCount = userMapper.deleteUser(user.getUserNo());    // userNo 또는 user 통째로 넘겨서 뺼 수 있음.
      
      // 탈퇴 이후 응답 만들기    
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(deleteCount == 1) {
        out.println("alert('탈퇴 완료했습니다.');");
        out.println("location.href='" + request.getContextPath() + "/main.page';");
        // session 초기화 (모든 값을 유효하지 않게 만든다.)
        session.invalidate();  // SessionStatus 객체의 setComplete() 메소드 호출
      } else {
        out.println("alert('탈퇴 실패했습니다.');");
        out.println("history.back();");
      }
      out.println("</script>");
      out.flush();
      out.close();
      } catch (Exception e) {
      e.printStackTrace();
    }
       
  }
  
  @Override
  public String getRedirectURLAfterSignin(HttpServletRequest request) {
    
    // Sign In 페이지 이전의 주소가 저장되어 있는 Request Header 의 referer
    // 블로그를 보고 있다가 로그인을 하면 전에 보고 있던 블로그 화면으로 넘어가야함. 그것을 알아내기 위한 값이 referer
    String referer = request.getHeader("referer");
    
    // referer 로 돌아가면 안되는 예외 상황 (아이디/비밀번호 찾기, 가입 화면 등)
    String[] excludeUrls = {"/findId.page", "findPw.page", "/signup.page"};
    
    // Sign In 이후 이동할 url
    // 초기 값을 referer 로 잡으면 main으로 보내고 싶을 때(예외상황) referer을 main으로 덮어씌울 수 있음
    String url = referer;
    if(referer != null) {
      for(String excludeUrl : excludeUrls) {
        if(referer.contains(excludeUrl)) {
          url = request.getContextPath() + "/main.page";
          break;
        } 
      }
      // referer 값이 없을 때
    } else {
      url = request.getContextPath() + "/main.page";
    }
    
    return url;
  }
  
  @Override
  public void signin(HttpServletRequest request, HttpServletResponse response) {    
    try {
      
      // 입력한 아이디
      String email = request.getParameter("email");
      
      // 입력한 비밀번호 + SHA256 방식의 암호화
      String pw = MySecurityUtils.getSha256(request.getParameter("pw"));    // 사용자가 입력한 패스워드가 암호화 돼서 나올 것. 
      
      // 접속 IP (접속 기록을 남길 때)
      String ip = request.getRemoteAddr();    // IP 저장! *********
      
      // 접속 수단 (요청 헤더의 USer-Agent 값)
      String userAgent = request.getHeader("User-Agent");
      
      // DB로 보낼 정보 (email/pw -> USER_T, email/IP -> ACCESS_HISTORY_T) *우리는 이것을 Map 에 담기로 함.
      Map<String, Object> params = Map.of("email", email
                                        , "pw", pw
                                        , "ip", ip
                                        , "userAgent", userAgent
                                        , "sessionId", request.getSession().getId());
      
      // email/pw 가 일치하는 회원 정보 가져오기
      UserDto user = userMapper.getUserByMap(params);
      
      // 일치하는 회원 있음 (Sign In 성공)
      if(user != null) {
        // 접속 기록 ACCESS_HISTORY_T 에 남기기
        userMapper.insertAccessHistory(params); 
        
        // ****** 로그인의 기본 원리는 session 이라는 저장소(데이터바인딩 영역)에 정보를 올려주는 것. "user" 이런 정보는 모든 팀원들이 공유하고 있어야 할 정보
        // 회원정보를 세션에 저장(브라우저 닫기 전까지 정보가 유지되는 공간, 기본 30분 정보 유지)
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        // session.setMaxInactiveInterval(10); // 세션 유지 시간 10초 설정
        
        
        // Sign In 후 페이지 이동
        response.sendRedirect(request.getParameter("url"));
      
      // 일치하는 회원 없음 (Sign In 실패)
      } else {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('일치하는 회원 정보가 없습니다.')");
        out.println("location.href='" + request.getContextPath() + "/main.page'");
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
    
      try {
      
      // Sign Out 기록 남기기
      HttpSession session = request.getSession();
      String sessionId = session.getId(); 
      userMapper.updateAccessHistory(sessionId);
      
      // 세션에 저장된 모든 정보 초기화
      session.invalidate();
      
      // 메인 페이지로 이동
      response.sendRedirect(request.getContextPath() + "/main.page");
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  @Override
  public String getNaverLoginURL(HttpServletRequest request) {
    
    /************************ 네이버 로그인 1 ****************************/
    String redirectUri = "http://localhost:8080" + request.getContextPath() + "/user/naver/getAccessToken.do";
    String state = new BigInteger(130, new SecureRandom()).toString();  // 네이버 개발자센터 뒤져보면 code 나와있음
    
    StringBuilder builder = new StringBuilder();
    builder.append("https://nid.naver.com/oauth2.0/authorize");
    builder.append("?response_type=code");
    builder.append("&client_id=Kxpn76LYwVERM6YVO8Dj");
    builder.append("&redirect_uri=" + redirectUri);
    builder.append("&state=" + state);
    
    return builder.toString();
  }
  
  @Override
  public String getNaverLoginAccessToken(HttpServletRequest request) {
    
    /************************ 네이버 로그인 2 ****************************/
    // 네이버로부터 Access Token 을 발급 받아 반환하는 메소드
    // 네이버 로그인 1단계에서 전달한 redirect_uri 에서 동작하는 서비스
    // code 와 state 파라미터를 받아서 Access Token 을 발급 받을 때 사용
    
    String code = request.getParameter("code");
    String state = request.getParameter("state");
    
    String spec = "https://nid.naver.com/oauth2.0/token";
    String grantType = "authorization_code";
    String clientId = "Kxpn76LYwVERM6YVO8Dj" ;
    String clientSecret = "usZ8ilRnKm";
    
    StringBuilder builder = new StringBuilder();
    builder.append(spec);
    builder.append("?grant_type=" + grantType);
    builder.append("&client_id=" + clientId);
    builder.append("&client_secret=" + clientSecret);
    builder.append("&code=" + code);
    builder.append("&state=" + state);
    
    // try 밖으로 뺀 이유는 try 밖에서 닫는것 까지 해야하기 때문에
    HttpURLConnection con = null;
    JSONObject obj = null;
    
    try {
      
      URL url = new URL(builder.toString());
      con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");    // 반드시 대문자로 작성해야한다
      
      // 응답
      BufferedReader reader = null;
      int responseCode = con.getResponseCode();   // 정상일 때 200 나옴
      if(responseCode == HttpURLConnection.HTTP_OK) {
        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));    // byte밖에 지원을 안하니까 문자로 받아올 stream을 사용해야한다.
      } else {
        reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
      }
      
      // 응답 데이터 받기
      String line = null;
      StringBuilder responseBody = new StringBuilder();
      while((line = reader.readLine()) != null) {   // null 값이 아니라면 한 줄씩 읽는다.
        responseBody.append(line);
      }
      
      // 응답 데이터를 JSON 객체로 변환하기
      obj = new JSONObject(responseBody.toString());
      
      // 응답 스트림 닫기
      reader.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    con.disconnect();
    
    return obj.getString("access_token");
  }
  
  @Override
  public UserDto getNaverLoginProfile(String accessToken) {

    /************************ 네이버 로그인 3 ****************************/
    // 네이버로부터 프로필 정보(이메일, [이름, 성별, 휴대전화번호]을 발급 받아 반환하는
    
    String spec = "https://openapi.naver.com/v1/nid/me";
    
    HttpURLConnection con = null;
    UserDto user = null;
    
    try {
      
      // 요청
      URL url = new URL(spec);
      con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      
      // 요청 헤더
      con.setRequestProperty("Authorization", "Bearer " + accessToken);
      
      // 응답
      BufferedReader reader = null;
      int responseCode = con.getResponseCode();   // 정상일 때 200 나옴
      if(responseCode == HttpURLConnection.HTTP_OK) {
        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));    // byte밖에 지원을 안하니까 문자로 받아올 stream을 사용해야한다.
      } else {
        reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
      }
      
      // 응답 데이터 받기
      String line = null;
      StringBuilder responseBody = new StringBuilder();
      while((line = reader.readLine()) != null) {   // null 값이 아니라면 한 줄씩 읽는다.
        responseBody.append(line);
      }
      
      // 응답 데이터를 JSON 객체로 변환하기
      JSONObject obj = new JSONObject(responseBody.toString());
      JSONObject response = obj.getJSONObject("response");
      user = UserDto.builder()
                  .email(response.getString("email"))
                  .gender(response.has("gender") ? response.getString("gender") : null)
                  .name(response.has("name") ? response.getString("name") : null)
                  .mobile(response.has("mobile") ? response.getString("mobile") : null)
                .build();
      /*
      if(response.has("name"))user.setName(response.getString("name"));
      if(response.has("gender"))user.setGender(response.getString("gender"));
      if(response.has("mobile"))user.setMobile(response.getString("mobile"));
       */
      
      // 응답 스트림 닫기
      reader.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    con.disconnect();
    
    return user;
  }
  
  @Override
  public boolean hasUser(UserDto user) {
    return userMapper.getLeaveUserByMap(Map.of("email", user.getEmail())) != null;
  }

  @Override
  public void naverSignin(HttpServletRequest request, UserDto naverUser) {
    
    Map<String, Object> map = Map.of("email", naverUser.getEmail()
                                   , "ip", request.getRemoteAddr());
    
    UserDto user = userMapper.getUserByMap(map);
    request.getSession().setAttribute("user", user);
    userMapper.insertAccessHistory(map);
  }
  
}
