package com.gdu.myapp.service;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myapp.dto.AttachDto;
import com.gdu.myapp.dto.UploadDto;
import com.gdu.myapp.dto.UserDto;
import com.gdu.myapp.mapper.UploadMapper;
import com.gdu.myapp.utils.MyFileUtils;
import com.gdu.myapp.utils.MyPageUtils;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@RequiredArgsConstructor
@Service
public class UploadServiceImpl implements UploadService {

  private final UploadMapper uploadMapper;
  private final MyPageUtils myPageUtils;
  private final MyFileUtils myFileUtils;
  
  
  @Override
  public boolean registerUpload(MultipartHttpServletRequest multipartRequest) {
    
    // UPLOAD_T 테이블에 추가하기
    String title = multipartRequest.getParameter("title");
    String contents = multipartRequest.getParameter("contents");
    int userNo = Integer.parseInt(multipartRequest.getParameter("userNo"));
    
    UserDto user = new UserDto();
    user.setUserNo(userNo);
    
    UploadDto upload = UploadDto.builder()
                           .title(title)
                           .contents(contents)
                           .user(user)
                         .build();
    
    // uploadMapper 에 써져있는 파라미터 UploadDto 가 여기서 말하는 upload를 의미함. upload 에 uploadNo 를 저장하는 것.
    System.out.println("INSERT 이전" + upload.getUploadNo());   // uploadNo 없음
    int insertUploadCount = uploadMapper.insertUpload(upload);
    System.out.println("INSERT 이후" + upload.getUploadNo());   // uploadNo 있음(<selectKey> 동작에 의해서)
    
    // 첨부 파일 처리하기
    // write.jsp 안에 있는 <input> -> multiple 이 있기 때문에 getFiles 로 받는 것. 하나만 첨부할 때는 getFile 로 받으면 됨.
    List<MultipartFile> files = multipartRequest.getFiles("files");
     
    // 첨부 파일이 없는 경우 : [MultipartFile[field="files", filename=, contentType=application/octet-stream, size=0]]
    // 첨부 파일이 있는 경우 : [MultipartFile[field="files", filename=KakaoTalk_20240408_144952046_01.jpg, contentType=image/jpeg, size=1405366]]
    // System.out.println(files);
    int insertAttachCount;
    if(files.get(0).getSize() == 0) {
      insertAttachCount = 1;  // -> multipartFile 이 들어가있기 때문에 초기화로 1을 잡은 것. filees.size() 는 1이다
    } else {
      insertAttachCount = 0;
    }
    
    for(MultipartFile multipartFile : files) {
      if(multipartFile != null && !multipartFile.isEmpty()) {
        String uploadPath = myFileUtils.getUploadPath();
        File dir = new File(uploadPath);
        if(!dir.exists()) {
          dir.mkdirs();
        }
        
        String originalFilename = multipartFile.getOriginalFilename();
        // 확장자는 살리고 저장할 file 이름만 변경 
        String filesystemName = myFileUtils.getFilesystemName(originalFilename);
        File file = new File(dir, filesystemName);
        
        try {
          multipartFile.transferTo(file);
          
          // 썸네일 작성
          String contentType = Files.probeContentType(file.toPath());
          // contentType 이 null 이 아니고, image 로 시작하면 1 : 아니면 0
          int hasThumbnail = contentType != null && contentType.startsWith("image") ? 1 : 0;
          if(hasThumbnail == 1) {
            // 이미지의 썸네일 만들기
            File thumbnail = new File(dir, "s_" + filesystemName); // 썸네일 이름은 small 이라는 s_를 붙여서 저장해주기
            Thumbnails.of(file)             // 원본 이미지 파일
                      .size(96, 64)         // 가로 96px, 세로 64px       
                      .toFile(thumbnail);   // 썸네일 이미지 파일
            
            // 96, 64 = 1920/20, 1280/20 : 1/20 사이즈로 줄인 것
          }           
          // ATTACH_T 테이블에 추가하기
          AttachDto attach = AttachDto.builder()
                                  .uploadPath(uploadPath)
                                  .filesystemName(filesystemName)
                                  .originalFilename(originalFilename)
                                  .hasThumbnail(hasThumbnail)
                                  .uploadNo(upload.getUploadNo())
                                .build();
          
          insertAttachCount += uploadMapper.insertAttach(attach);  // for 문 안에 들어가있으면 결과는 계속 덮어쓰기를 하게 되는 것. 그러므로 insertAttachCount 가 예를 들어 5가 나오고싶다면 누적을 해줘야한다.
          
        } catch (Exception e) {
          e.printStackTrace();
        }        
        
      }   // if 끝
    }     // for 끝
    
    return (insertUploadCount == 1) && (insertAttachCount == files.size());
    //      업로드 된 게시물의 개수 &&  게시물 안에 첨부된 파일의 개수 == files 의 size 와 동일해야함.
  }

  @Override
  public void loadUploaddList(Model model) {
    
    /* 
     * method 는 하나지만 클래스들의 이름이 계속 바뀔 수 있다. (파라마티 model 하나만 사용 -> service로 넘겨주는 controller 단에서 클래스를 계속 바꿔가면서 사용하는 것.)
     * interface UploadService {
     *   void execute(Model model);
     * }
     * 
     * class UploadRegisterSErvice implements UploadService {
     *   @Override
     *   void execute(Model model) {     *   
     *   }
     * }
     * 
     * class UploadService implements UploadService {
     *  @override
     *  void execute(Model model) {
     *  }
     * }
     * 
     */
    
    // model.asMap : 예전에 쓰던 방식 -> model 을 map 으로 바꿔서 map 에 있는 데이터를 꺼내씀.
    // model.getAttribute 가 생겼음. version 이 5.2로 아주 최신 버전!
    Map<String, Object> modelMap = model.asMap();
    HttpServletRequest request = (HttpServletRequest) modelMap.get("request");
    
    int total = uploadMapper.getUploadCount();
    
    Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
    int display = Integer.parseInt(optDisplay.orElse("20"));
    
    Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(optPage.orElse("1"));
    
    myPageUtils.setPaging(total, display, page);
    
    Optional<String> optSort = Optional.ofNullable(request.getParameter("sort"));
    String sort = optSort.orElse("DESC");
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin(), "end", myPageUtils.getEnd(), "sort", sort);
    
    /*
     * total = 100, display = 20
     * 
     * page    beginNo
     * 1
     * 2
     * 3
     * 4
     * 5
     */
    
    model.addAttribute("beginNo", total - (page - 1) * display);
    model.addAttribute("uploadList", uploadMapper.getUploadList(map));
    model.addAttribute("paging", myPageUtils.getPaging(request.getContextPath() + "/upload/list.do", sort, display));
    model.addAttribute("display", display);
    model.addAttribute("sort", sort);
    model.addAttribute("page", page);
  }
  
  @Override
  public UploadDto getUploadByNo(int uploadNo) {
    return uploadMapper.getUploadByNo(uploadNo);
  }

}





