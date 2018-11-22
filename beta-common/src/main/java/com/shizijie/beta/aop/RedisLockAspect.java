package com.shizijie.beta.aop;

import com.shizijie.beta.annotation.Lock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shizijie
 * @version 2018-11-22 下午8:57
 */
@Aspect
@Component
public class RedisLockAspect {
    @Pointcut("@annotation(com.shizijie.beta.annotation.Lock)")
    public void pointcut(){


    }
    @Around("pointcut()&&@annotation(lock)")
    public Object test(ProceedingJoinPoint joinPoint, Lock lock){
        String classType = joinPoint.getTarget().getClass().getName();
        System.out.println(classType);
        String methodName = joinPoint.getSignature().getName();
        // 参数值
        Object[] args = joinPoint.getArgs();
        Class<?>[] classes = new Class[args.length];
        for (int k = 0; k < args.length; k++) {
            if (!args[k].getClass().isPrimitive()) {
                // 获取的是封装类型而不是基础类型
                String result = args[k].getClass().getName();
                System.out.println(result);
                Class s = map.get(result);
                classes[k] = s == null ? args[k].getClass() : s;
                if(s==null){
                    Field[] fields=args[k].getClass().getDeclaredFields();
                    for(int i=0;i<fields.length;i++){
                        fields[i].setAccessible(true);
                        System.out.println("name:"+fields[i].getName());
                        try {
                            System.out.println("value:"+fields[i].get(args[k]));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        // 获取指定的方法，第二个参数可以不传，但是为了防止有重载的现象，还是需要传入参数的类型
        Method method = null;
        try {
            method = Class.forName(classType).getMethod(methodName, classes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 参数名
        String[] parameterNames = pnd.getParameterNames(method);
        // 通过map封装参数和参数值
        HashMap<String, Object> paramMap = new HashMap();
        for (int i = 0; i < parameterNames.length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }
        System.out.println(paramMap);
        Object result=null;
        try{
            result=joinPoint.proceed();
        }catch (Throwable e){

        }
        return result;
    }

    private static HashMap<String, Class> map = new HashMap<String, Class>() {
        {
            put("java.lang.Integer", int.class);
            put("java.lang.Double", double.class);
            put("java.lang.Float", float.class);
            put("java.lang.Long", long.class);
            put("java.lang.Short", short.class);
            put("java.lang.Boolean", boolean.class);
            put("java.lang.Char", char.class);
        }
    };
}
