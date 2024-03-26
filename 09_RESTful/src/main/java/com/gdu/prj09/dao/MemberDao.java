package com.gdu.prj09.dao;

import java.util.List;
import java.util.Map;

import com.gdu.prj09.dto.AddressDto;
import com.gdu.prj09.dto.MemberDto;

public interface MemberDao {

  int insertMember(MemberDto member);
  int insertAddress(AddressDto address);
  int updateMember(MemberDto member);
  int deleteMember(int memberNo);
  int deleteMembers(List<String> memberNoList);   // String 타입의 List인 memberNoList
  int getTotalMemberCount();    // 페이징 처리
  List<AddressDto> getMemberList(Map<String, Object> map);  // 목록 보기 + resultMap (궁극적으로 AddressDto) 으로 바꿨음
  AddressDto getMemberByNo(int memberNo);    // 상세 반환 + resultMap (궁극적으로 AddressDto) 으로 바꿨음. (주소정보가 MemberDto 에는 없기 때문에 조회할 수 없음)
  
}
