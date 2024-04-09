package com.gdu.myapp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.myapp.dto.BlogDto;
import com.gdu.myapp.dto.CommentDto;

@Mapper
public interface BlogMapper {
  
  int insertBlog(BlogDto blog);     // 블로그 글 작성
  int getBlogCount();               // 블로그 목록 조회
  List<BlogDto> getBlogList(Map<String, Object> map);
  
  BlogDto getBlogByNo(int blogNo);  // 상세보기를 위한 blogNo 가져오기
  
  // 댓글 등록
  int insertComment(CommentDto comment);
  // 댓글 조회
  int getCommentCount(int blogNo);
  List<CommentDto> getCommentList(Map<String, Object> map);
  
  int insertReply(CommentDto comment);
}
