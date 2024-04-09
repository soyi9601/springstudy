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
  
  int updateHit(int blogNo);        // HIT UPdate
  
  int insertComment(CommentDto comment);    // 댓글 등록
  int getCommentCount(int blogNo);          // 댓글 조회
  List<CommentDto> getCommentList(Map<String, Object> map); // 댓글 조회
  
  int insertReply(CommentDto comment);    // 댓글의 답글 등록
  
  int deleteBlog(int blogNo);
  int deleteReply(CommentDto comment);
}
