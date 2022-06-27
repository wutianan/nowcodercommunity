package com.fz.newcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 这是一个测试切面的类
 * @author zxf
 * @date 2022/6/26
 */
//@Aspect
//@Component
public class AlphaAspect {

    /**
     * 定义切点
     */
    @Pointcut("execution(* com.fz.newcoder.community.service.*.*(..))")
    public void pointcut(){}


    @Before("pointcut()")
    public void before(){

    }

    @After("pointcut()")
    public void after(){}

    /**
     * 在返回值之后
     */
    @AfterReturning("pointcut()")
    public void afterReturning(){}

    /**
     * 在抛出异常之后
     */
    @AfterThrowing("pointcut()")
    public void afterThrowing(){}

    /**
     * 环绕通知
     * @param joinPoint 切点
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //在方法之前执行
        System.out.println();
        //通过代理对象执行方法
        Object obj = joinPoint.proceed();
        //在方法之后执行
        System.out.println();
        return obj;
    }
}
