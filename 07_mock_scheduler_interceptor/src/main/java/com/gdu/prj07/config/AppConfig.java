package com.gdu.prj07.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.gdu.prj07.aspect.MyAfterAspect;
import com.gdu.prj07.aspect.MyAroundAspect;
import com.gdu.prj07.aspect.MyBeforeAspect;

@EnableAspectJAutoProxy   //  enable 허용하는 annotation
@Configuration
public class AppConfig {    // component 를 대체할 수 있는 스프링 컨테이너

  @Bean
  public MyAroundAspect myAroundAspect() {
    return new MyAroundAspect();
  }
  
  @Bean
  public MyBeforeAspect myBeforeAspect() {
    return new MyBeforeAspect();
  }
  
  @Bean
  public MyAfterAspect myAfterAspect() {
    return new MyAfterAspect();
  }
  
}
