package com.gdu.prj05.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.prj05.service.ContactService;

import lombok.RequiredArgsConstructor;

@RequestMapping(value="/contact")     // /contact 으로 시작하는 모든 요청은 requestmapping
@RequiredArgsConstructor
@Controller
public class ContactController {

  private final ContactService contactService;
  
  @GetMapping(value="/list.do")
  public String list(Model model)   {
    model.addAttribute("contactList", contactService.getContactList());
    return "contact/list";
  }
  
  @GetMapping(value="/detail.do")
  // contactNo 가 전달되지 않았을 때를 대비해서 requestparam 작성/ required=true 를 작성하면 값이 넘어오지 않았을 때 오류가 발생.
  // required=false 를 주고 default 값을 작성해준다.
  public String detail(@RequestParam(value="contact-no", required=false, defaultValue="0") int contactNo
                      , Model model) {
    model.addAttribute("contact", contactService.getContactByNo(contactNo));
    return "contact/detail";
  }
  
  @GetMapping(value="/write.do")
  public String write() {
    return "contact/write";
  }
  
  @PostMapping(value="/register.do")
  public void register(HttpServletRequest request, HttpServletResponse response) {
    contactService.registerContact(request, response);
  }
  
  @GetMapping(value="/remove.do")
  public void remove(HttpServletRequest request, HttpServletResponse response) {
    contactService.removeContact(request, response);
  }
  
  @PostMapping(value="/remove.do")
  public void remove2(HttpServletRequest request, HttpServletResponse response) {
    contactService.removeContact(request, response);
  }
  
  @PostMapping(value="/modify.do")
  public void modify(HttpServletRequest request, HttpServletResponse response) {
    contactService.modifyContact(request, response);
  }
  
  
}
