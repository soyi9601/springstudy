package com.gdu.prj08.dao;

import java.util.Map;

import com.gdu.prj08.dto.FileDto;
import com.gdu.prj08.dto.HistoryDto;

public interface FileDao {

  int uploadFile1(FileDto fileDto);
  int uploadFile2(HistoryDto historyDto);
  
  
}
