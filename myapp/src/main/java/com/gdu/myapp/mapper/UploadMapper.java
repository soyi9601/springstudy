package com.gdu.myapp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.myapp.dto.AttachDto;
import com.gdu.myapp.dto.UploadDto;

@Mapper
public interface UploadMapper {
  int insertUpload(UploadDto upload);
  int insertAttach(AttachDto attach);
  int getUploadCount();
  List<UploadDto> getUploadList(Map<String, Object> map); 
  UploadDto getUploadByNo(int uploadNo);
  
  List<AttachDto> getAttachList(int uploadNo);  // 첨부파일 리스트 -> 모두 다운로드에 사용
  
  AttachDto getAttachByNo(int attachNo);        // 첨부된 파일의 정보 가져오기
  int updateDownloadCount(int attachNo);        // 첨부파일의 downloadCount +1 시켜주기
  int updateUpload(UploadDto upload);
}
