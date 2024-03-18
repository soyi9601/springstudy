package com.gdu.prj05.service;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.gdu.prj05.dao.ContactDao;
import com.gdu.prj05.dto.ContactDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {
  
  private final ContactDao contactDao;

  @Override
  public void registerContact(HttpServletRequest request, HttpServletResponse response) {
    // 등록한 ContactDto 생성
    ContactDto contact = ContactDto.builder()
                            .name(request.getParameter("name"))
                            .mobile(request.getParameter("mobile"))
                            .email(request.getParameter("email"))
                            .address(request.getParameter("address"))
                          .build();
    // 등록
    int insertCount = contactDao.registerContact(contact);
    // 등록 결과에 따른 응답
    // 생성과정부터 예외가 있을 수 있음. ex) name 은 notnull 인데 입력하지 않으면 예외가 발생하므로 try 안에 넣고 예외처리를 다르게 해줘야함.
    response.setContentType("text/html; charset=UTF-8");
    try {
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(insertCount == 1) {
        out.println("alert('연락처가 등록되었습니다.')");
        out.println("location.href='" + request.getContextPath() + "/contact/list.do'");  // redirect 를 의미하는 코드
      } else {
        out.println("alert('연락처가 등록되지 않았습니다.')");
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
  public void modifyContact(HttpServletRequest request, HttpServletResponse response) {
    // 수정(성공->상세보기, 실패->뒤로가기)   controller 로 보내주는 게 아님.
    int contactNo = Integer.parseInt(request.getParameter("contact-no"));
    ContactDto contact = ContactDto.builder()
                            .contactNo(contactNo)
                            .name(request.getParameter("name"))
                            .mobile(request.getParameter("mobile"))
                            .email(request.getParameter("email"))
                            .address(request.getParameter("address"))
                          .build();
        
    int updateCount = contactDao.modifyContact(contact);
    response.setContentType("text/html; charset=UTF-8");
    try {
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(updateCount == 1) {
        out.println("alert('수정 완료')");
        out.println("location.href='" + request.getContextPath() + "/contact/detail.do?contactNo=" + contactNo + "'");
      } else {
        out.println("alert('수정 실패')");
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
  public void removeContact(HttpServletRequest request, HttpServletResponse response) {
    // 삭제(성공->목록보기, 실패->뒤로가기)
    int contactNo = Integer.parseInt(request.getParameter("contact-no"));
    int deleteCount = contactDao.removeContact(contactNo);
    response.setContentType("text/html; charset=UTF-8");
    try {
      PrintWriter out = response.getWriter();
      out.println("<script>");
      if(deleteCount == 1) {
        out.println("alert('삭제 완료')");
        out.println("location.href='" + request.getContextPath() + "/contact/list.do'");
      } else {
        out.println("alert('삭제 실패')");
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
  public List<ContactDto> getContactList() {
    return contactDao.getContactList();
  }

  @Override
  public ContactDto getContactByNo(int contactNo) {
    return contactDao.getContactByNo(contactNo); // controller로 바로 보내주기
  }

}
