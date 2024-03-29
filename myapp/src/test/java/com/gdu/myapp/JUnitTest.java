package com.gdu.myapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gdu.myapp.dto.UserDto;
import com.gdu.myapp.mapper.UserMapper;

// JUnit4
// @RunWith(SpringJUnit4ClassRunner.class)

// JUnit 5
@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class JUnitTest {

  @Autowired
  // 어디에든 주입한 적이 없어서 @Autowired 필드 주입 방식 썼음.
  private UserMapper userMapper;
    
  @Test
  public void test1() {
    assertEquals(1, userMapper.insertUser(new UserDto()));
  }
}
