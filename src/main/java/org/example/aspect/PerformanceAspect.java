package org.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {
  Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);

  @Pointcut("@annotation(org.example.annotation.ExecutionTime)")
  public void anyExecutionTimeOperation() {}

  @Around("anyExecutionTimeOperation()")
  public Object anyExecutionTimeOperationAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

    Long beforeTime = System.currentTimeMillis();

    Object result = joinPoint.proceed();

    Long afterTime = System.currentTimeMillis();

    logger.info(
        "Time taken to execute: {} ms - Method: {} - Parameters: {}",
        (afterTime - beforeTime),
        joinPoint.getSignature().toShortString(),
        joinPoint.getArgs());

    return result;
  }
}
