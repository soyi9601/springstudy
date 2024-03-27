package com.gdu.prj09.dao;

import java.util.List;
import java.util.Map;

import com.gdu.prj09.dto.AddressDto;
import com.gdu.prj09.dto.MemberDto;

public interface MemberDao {

  int insertMember(MemberDto member);
  int insertAddress(AddressDto address);
  int updateMember(Map<String, Object> map);
  int updateAddress(Map<String, Object> map);
  
  // int updateMember(MemberDto member);
  // int updateAddress(AddressDto address);
  // update 에서 Address 와 Member 를 구분하지말고 Map 으로 같이 보내주기
  
  int deleteMember(int memberNo);
  int deleteMembers(List<String> memberNoList);   // String 타입의 List인 memberNoList
  int getTotalMemberCount();    // 페이징 처리
  List<AddressDto> getMemberList(Map<String, Object> map);  // 목록 보기 + resultMap (궁극적으로 AddressDto) 으로 바꿨음
  MemberDto getMemberByNo(int memberNo);    
  int getTotalAddressCountByNo(int memberNo);
  List<AddressDto> getAddressListByNo(Map<String, Object> map);
  // Member 는 한개 가져오고, AddressDto 에서는 정보 여러개 가져옴
  
}
