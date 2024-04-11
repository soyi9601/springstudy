package com.gdu.myapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UploadDto {
  // Dto 는 기본적으로 table  rhk 1:1 매칭을 하지만 꼭 그렇게만 할 필요는 없다.
  private int uploadNo;
  private String title, contents, createDt, modifyDt;
  private UserDto user;
  private int attachCount;
  // select 에서 전체 파일을 보여주면서 첨부된 파일의 개수를 보여줘야하기 때문에 테이블에 없지만 변수를 선언해 준 것.
}
