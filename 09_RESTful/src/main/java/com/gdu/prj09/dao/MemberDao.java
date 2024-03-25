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
  List<MemberDto> getMemberList(Map<String, Object> map);  // 목록 보기
  MemberDto getMemberByNo(int memberNo);    // 상세 반환
  
}
