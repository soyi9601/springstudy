package com.gdu.prj08.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FileDto {

  private int fileNo;
  private String uploadPath;
  private String originalName;
  private String filesystemName;
  private int historyNo;
  
}
