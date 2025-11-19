package com.kt.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class DefaultAopTransactionManager implements AopTransactionManager{

    /*
    * 락 인에서 트랜잭션을 새로 열어 비즈니스 로직 실행
    * 1. 락(AOP)과 트랜잭션 분리 - 비즈니스 메서드를 트랜잭션 안에서 실행하는 역할
      LockAspect  (트랜잭션 밖)
                ↓
      DefaultAopTransactionManager.proceed()   👈 트랜잭션 안
                ↓
      OrderService.create()    (비즈니스 로직)
    * 2. REQUIRES_NEW 트랜잭션을 강제로 여는 역할
    * 서비스의 Transactional과는 별도의 독립된 트랜잭션 새로 생성해라 라는 뜻
    * 3. joinPoint.proceed() 를 호출해 실제 비즈니스 로직 실행 ⭐️
    *   ex) OrderService.create() 실행
    */
    @Override
    public Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}