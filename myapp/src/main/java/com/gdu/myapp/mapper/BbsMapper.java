package com.gdu.myapp.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.myapp.dto.BbsDto;

@Mapper
public interface BbsMapper {
  int insertBbs(BbsDto bbs);
}
