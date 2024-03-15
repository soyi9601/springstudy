package com.gdu.prj04.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gdu.prj04.dto.BoardDto;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class BoardDaoImpl implements BoardDao {
  
  
  private BoardDto board1;
  private BoardDto board2;
  private BoardDto board3;
  
  // 1. @Autowired field  주입 -> 젤 안좋은 방식. 필드 하나씩 다 입력해줘야함
  // 2. @Autowired setter 주입 -> 얘도 별로임
  //  @Autowired
  //  public void setBean(BoardDto board1, BoardDto board2, BoardDto board3) {
  //    this.board1 = board1;
  //    this.board2 = board2;
  //    this.board3 = board3;
  //  }

  @Override
  public List<BoardDto> getBoardList() {
    return Arrays.asList(board1, board2, board3);
  }

  @Override
  public BoardDto getBoardByNo(int boardNo) {
    BoardDto board = null;
    switch(boardNo) {
    case 1 : board = board1; break;
    case 2 : board = board2; break;
    case 3 : board = board3; break;
    }
    return board;
  }

}
