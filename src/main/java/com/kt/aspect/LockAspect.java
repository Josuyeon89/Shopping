package com.kt.aspect;

import com.kt.common.CustomException;
import com.kt.common.ErrorCode;
import com.kt.common.Lock;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LockAspect {
    /*
    * 메서드를 가로채 락을 잡고 해제하는 AOP
    * @Lock 이 붙은 메서드 실행을 AOP로 가로채서 아래 내용 수행
    * 1. Redisson을 통해 락 획득
    * 2. 트랜잭션 + 비즈니스 로직을 AopTransactionManager에 위임
    * 3. 트랜잭션이 끝난 뒤 락 해제
    */

    private final AopTransactionManager aopTransactionManager;
    private final RedissonClient redissonClient;

    @Around("@annotation(com.kt.common.Lock) && @annotation(lock)")
    public Object lock(ProceedingJoinPoint joinPoint, Lock lock) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Long productId = (Long) args[1];
        String key = String.format("%s:%d", lock.key().name().toLowerCase(), productId);

        RLock rLock = redissonClient.getLock(key);

        try {
            boolean available = rLock.tryLock(lock.waitTime(), lock.leaseTime(), lock.timeUnit());

            if(!available){
                throw new CustomException(ErrorCode.FAIL_ACQUIRED_LOCK);
            }
            return aopTransactionManager.proceed(joinPoint);
        } finally {
            if(rLock.isHeldByCurrentThread()){
                rLock.unlock();
            }
        }
    }
}
