package com.gdu.myapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.myapp.dto.UploadDto;
import com.gdu.myapp.service.UploadService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/upload")
@RequiredArgsConstructor
@Controller
public class UploadController {

  private final UploadService uploadService;
  
  @GetMapping("/list.do")
  public String list(HttpServletRequest request, Model model) {
    model.addAttribute("request", request);
    uploadService.loadUploaddList(model);
    return "upload/list";
  }
  
  @GetMapping("/write.page")
  public String write() {
    return "upload/write";
  }
  
  @PostMapping("/register.do")
  public String register(MultipartHttpServletRequest multipartRequest
                       , RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("inserted", uploadService.registerUpload(multipartRequest));
    return "redirect:/upload/list.do";
  }
  
  /*
   @GetMapping("/detail.do") public String detail(@RequestParam int uploadNo,
   Model model) { model.addAttribute("upload",
   uploadService.getUploadByNo(uploadNo)); return "/upload/detail"; }
   */
  
  @GetMapping("/detail.do")
  public String detail(@RequestParam(value="uploadNo", required=false, defaultValue="0") int uploadNo
                     , Model model) {
    uploadService.loadUploadByNo(uploadNo, model);
    return "upload/detail";
  }
  
  @GetMapping("/download.do")
  public ResponseEntity<Resource> download(HttpServletRequest request) {
    return uploadService.download(request);
  }
  
  @GetMapping("/downloadAll.do")
  public ResponseEntity<Resource> downloadAll(HttpServletRequest request) {
    return uploadService.downloadAll(request);
  }
  
  @PostMapping("/edit.do")
  public String edit(@RequestParam int uploadNo, Model model) {
    model.addAttribute("upload", uploadService.getUploadByNo(uploadNo));
    return "upload/edit";
  }
  
  // 커맨드 객체로 받은 것(edit 안에 있는 title, contents, userNo 3가지 받ㅇ옴)
  @PostMapping("/modify.do")
  public String modify(UploadDto upload, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("updateCount", uploadService.modifyUpload(upload));
    return "redirect:/upload/detail.do?uploadNo=" + upload.getUploadNo();
    // ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★ 받아오는 정보가 확실히 다 있는지 확인하고 코드 짜기! ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
    // uploadNo 가 edit 에서 불러온게 없기 때문에 uploadNo hidden 값으로 불러와줌
    // ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★ 받아오는 정보가 확실히 다 있는지 확인하고 코드 짜기! ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
    // return "redirect:/upload/list.do"
  }
  
}
