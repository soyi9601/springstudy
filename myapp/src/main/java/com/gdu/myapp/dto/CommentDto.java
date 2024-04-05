package com.gdu.myapp.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommentDto {
  private int commentNo, depth, groupNo, blogNo;    // dto에 같이 있다고 무조건 Dto 를 부르면 굳이 필요하지 않는 상세정보까지 같이 들어오게됨.
  private String contents;
  private Timestamp createDt;
  private UserDto user;                             // userNo 만 쓰이는데 userDto 를 부르는건 userNo 가 이곳저곳 다 쓰이기 때문에 확실히 어디에 있는 userNo 라고 명시해주는 역할.
}
