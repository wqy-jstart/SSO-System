package cn.tedu.loginsso.system.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 这是一个切面类,主要统计执行流程中Service层的耗时
 *
 * @Author java@Wqy
 * @Version 0.0.1
 */
@Slf4j
@Aspect
@Component
public class TimerAspect {

    public TimerAspect(){
        log.debug("创建切面类对象:TimerAspect");
    }

    @Around("execution(* cn.tedu.loginsso.system.service.*.*(..))")// 切面的位置
    //                 ↑ 此星号表示需要匹配的方法的返回值类型
    //                   ↑ ---------- 根包 ----------- ↑
    //                                                  ↑ 类名
    //                                                    ↑ 方法名
    //                                                      ↑↑ 参数列表
    public Object timer(ProceedingJoinPoint pjp) throws Throwable {
        log.debug("执行了TimeAspect中的方法...");

        String className = pjp.getTarget().getClass().getName();// 获取执行的类对象名称
        String signatureName = pjp.getSignature().getName();// 获取方法签名
        Object[] methodArgs = pjp.getArgs();// 获取执行的参数值
        log.debug("[{}]类型的对象调用了[{}]方法,参数值为[{}]",className,signatureName,methodArgs);

        long start = System.currentTimeMillis();
        Object result = pjp.proceed();// 相当于执行了匹配的service方法,业务方法
        long end = System.currentTimeMillis();

        log.debug("执行耗时:{}ms",end-start);
        return result;// 将获取的业务类结果返回
    }
}
