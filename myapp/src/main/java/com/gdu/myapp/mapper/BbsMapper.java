package com.gdu.myapp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.myapp.dto.BbsDto;

@Mapper
public interface BbsMapper {
  int insertBbs(BbsDto bbs);
  int getBbsCount();
  List<BbsDto> getBbsList(Map<String, Object> map);  
}
