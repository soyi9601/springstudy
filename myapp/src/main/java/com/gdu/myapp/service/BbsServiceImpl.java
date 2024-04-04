package com.gdu.myapp.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.myapp.dto.BbsDto;
import com.gdu.myapp.dto.UserDto;
import com.gdu.myapp.mapper.BbsMapper;
import com.gdu.myapp.utils.MyPageUtils;
import com.gdu.myapp.utils.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BbsServiceImpl implements BbsService {

  private final BbsMapper bbsMapper;
  private final MyPageUtils myPageUtils;
  
  @Override
  public int registerBbs(HttpServletRequest request) {
    
    // 사용자가 입력한 contents
    String Contents = MySecurityUtils.getPreventXss(request.getParameter("contents"));
    
    // 뷰에서 전달된 uiserNO
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    
    // UserDto 객체 생성 (userNo 저장)
    UserDto user = new UserDto();
    user.setUserNo(userNo);
    
    // DB 에 저장할 BbsDto 객체
    BbsDto bbs = BbsDto.builder()
                    .contents(Contents)
                    .user(user)
                  .build();
    
    return bbsMapper.insertBbs(bbs);
  }

  @Override
  public void loadBbsList(HttpServletRequest request, Model model) {
    // TODO Auto-generated method stub

  }

  @Override
  public int registerReply(HttpServletRequest request) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int removeBbs(int bbsNo) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void loadBbsSearchList(HttpServletRequest request, Model model) {
    // TODO Auto-generated method stub

  }

}
