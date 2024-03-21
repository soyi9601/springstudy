package com.gdu.prj07.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
// @Component
public class MyAroundAspect {

  // PointCut : 언제 동작하는가?
  @Pointcut("execution (* com.gdu.prj07.controller.*Controller.*(..))")       // 모든 클래스, 모든 메소드 / execution (반환타입 패키지.클래스.메소드(매개변수))
  public void setPointCut() {}    // 본문이 공백인 이유 -> advice 에서 할 일을 적어줄 것
  
  
  // Advice : 무슨 동작을 하는가?
  @Around("setPointCut()")
  
  /*
   * Around Advice 메소드 작성 방법
   * 1. 반환타입 : Object
   * 2. 메소드명 : 마음대로
   * 3. 매개변수 : ProceedingJoinPoint 타입 객체
   */
  
  public Object myAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    
    log.info("{}", "-".repeat(80));              // 동작 이전 (@before 이전)
    
    Object obj = proceedingJoinPoint.proceed();  // advice 가 동작하는 시점
    
    log.info("{}\n", "-".repeat(80));            // 동작 이후 (@after 이후)
    
    return obj;
    
  }
  
}
