package com.gdu.prj03.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gdu.prj03.dao.BoardDao;
import com.gdu.prj03.dto.BoardDto;

import lombok.RequiredArgsConstructor;

//       @Controller  @Service  @Repository
// view - controller - service - dao

@RequiredArgsConstructor

@Service      // Service 에서 사용하는 @Component
public class BoardServiceImpl implements BoardService {

  // @Autowired
  // BoardDao 타입을 알아서 가져와라 BoardDao 타입은 BoardDaoImpl 하나
  private final BoardDao boardDao;
  
  @Override
  public List<BoardDto> getBoardList() {
    return boardDao.getBoardList();
  }

  @Override
  public BoardDto getBoardByNo(int boardNo) {
    return boardDao.getBoardByNo(boardNo);
  }

}
