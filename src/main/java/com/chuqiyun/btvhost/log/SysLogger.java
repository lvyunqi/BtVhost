package com.chuqiyun.btvhost.log;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.chuqiyun.btvhost.annotation.SysLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author mryunqi
 * @date 2023/1/12
 */
@Aspect
@Component
public class SysLogger {
    private static final Logger logger = LoggerFactory.getLogger(SysLogger.class);
    @Pointcut("@annotation(com.chuqiyun.btvhost.annotation.SysLog)")
    public void operLoggerPointCut(){
        logger.info("===============管理员操作日志===============");
    }

    @Pointcut("execution(public * com.chuqiyun.btvhost.controller.*.*(..))")
    public void controllerMethod(){}

    @Before("operLoggerPointCut()")
    public void logBefore(JoinPoint joinPoint){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        StringBuilder requestLog = new StringBuilder();
        Signature signature = joinPoint.getSignature();
        Method method = ((MethodSignature) signature).getMethod();
        SysLog sysAnnotation = method.getAnnotation(SysLog.class);
        // 打印请求内容
        logger.info("===============请求内容开始===============");
        logger.info("操作接口："+sysAnnotation.operMoudle());
        logger.info("操作类型："+sysAnnotation.operMethod());
        logger.info("操作描述："+sysAnnotation.operDes());
        logger.info("请求地址:" + request.getRequestURL().toString());
        logger.info("请求IP:" + request.getRemoteAddr());
        logger.info("请求方式:" + request.getMethod());
        logger.info("请求类方法:" + joinPoint.getSignature());
        logger.info("请求类方法参数值:" + Arrays.toString(joinPoint.getArgs()));

        // 处理请求参数
        String[] paramNames = ((MethodSignature) signature).getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        int paramLength = null == paramNames ? 0 : paramNames.length;
        if (paramLength == 0) {
            requestLog.append("请求参数 = {} ");
        } else {
            requestLog.append("请求参数 = [");
            for (int i = 0; i < paramLength - 1; i++) {
                requestLog.append(paramNames[i]).append("=").append(JSONObject.toJSONString(paramValues[i])).append(",");
            }
            requestLog.append(paramNames[paramLength - 1]).append("=").append(JSONObject.toJSONString(paramValues[paramLength - 1])).append("]");
        }
        logger.info("请求参数明细:"+requestLog.toString());
        logger.info("===============请求内容结束===============");
    }

    @AfterReturning(returning = "o", pointcut = "operLoggerPointCut()")
    public void logResultVOInfo(Object o){
        logger.info("--------------返回内容开始----------------");
        logger.info("Response内容:" + JSON.toJSONString(o));
        logger.info("--------------返回内容结束----------------");
    }

    @AfterThrowing(pointcut = "controllerMethod()", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("连接点方法为：" + methodName + ",参数为：" + args + ",异常为：" + ex);

    }
}
