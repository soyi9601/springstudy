package com.gdu.myapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AttachDto {
  private int attachNo, downloadCount, hasThumbnail, uploadNo;
  private String uploadPath, filesystemName, originalFilename;
  
  // private UploadDto upload; 를 포함시키지 않아도 된다.
  // 포함시키면 resultMap 을 작성하면서
  // resultMap > association UploadDto > association UserDto 의 형태로 작성하게 됨
}
