package com.gdu.prj05.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.gdu.prj05.dto.ContactDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository   // dao 용 component ->  contactDaoImpl 을 spring container로 만들어줄 annotation
public class ContactDaoImpl implements ContactDao {

  private final SqlSessionTemplate sqlSessionTemplate;
  
  // 내가 실행 할 쿼리문의 mapper 이름 -> 공통적으로 다 쓰이기 때문에 저장해놓기
  public final static String NS = "com.gdu.prj.mybatis.mapper.contact_t.";
  
  @Override
  public int registerContact(ContactDto contact) {
                                              // 내가 실행 할 쿼리문의 mapper 이름 + ID
    int insertCount = sqlSessionTemplate.insert(NS + "registerContact", contact);  
    return insertCount;
  }

  @Override
  public int modifyContact(ContactDto contact) {
    int updateCount = sqlSessionTemplate.update(NS + "modifyContact", contact);
    return updateCount;
  }

  @Override
  public int removeContact(int contactNo) {
    int deleteCount = sqlSessionTemplate.delete(NS + "removeContact", contactNo);
    return deleteCount;
  }

  @Override
  public List<ContactDto> getContactList() {
    List<ContactDto> contactList = sqlSessionTemplate.selectList(NS + "getContactList");
    return contactList;
  }

  @Override
  public ContactDto getContactByNo(int contactNo) {
    ContactDto contact = sqlSessionTemplate.selectOne(NS + "getContactByNo", contactNo);
    return contact;
  }

}
