package com.gdu.prj09.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddressDto {
  private int addressNo;
  private String zonecode;
  private String address;
  private String detailAddress;
  private String extraAddress;
  private MemberDto member;
  
  // private int memberNo;
  // memberDto 안에 있는 memberNo 를 들고올 것.
}
