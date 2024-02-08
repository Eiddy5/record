package aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class AutowriedAspect {
    @Before("@annotation(Autowried)")
    public void before(JoinPoint joinPoint){
        Object target = joinPoint.getTarget();
        Signature signature = joinPoint.getSignature();
        Object aThis = joinPoint.getThis();
        System.out.println("前置通知");
    }
}
