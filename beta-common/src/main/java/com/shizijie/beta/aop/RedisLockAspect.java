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
import org.springframework.util.StringUtils;

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
    private static String SPLIT=",";

    private static String CONNECT="_";


    @Pointcut("@annotation(com.shizijie.beta.annotation.Lock)")
    public void pointcut(){


    }
    @Around("pointcut()&&@annotation(lock)")
    public Object test(ProceedingJoinPoint joinPoint, Lock lock){
        String classType = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String key=classType+"."+methodName;
        if(StringUtils.hasText(lock.key())){
            String[] keys=lock.key().split(SPLIT);
            // 参数值
            Object[] args = joinPoint.getArgs();
            String[] parameterNames = getParamsByMethod(classType,methodName,args);
            for(int i=0;i<keys.length;i++){
                boolean isBean=keys[i].indexOf(".")>0;
                if(isBean){
                    String name=keys[i].substring(0,keys[i].indexOf("."));
                    String value=keys[i].substring(keys[i].indexOf(".")+1);
                    for(int j=0;j<parameterNames.length;j++){
                        if(name.equals(parameterNames[j])){
                            if(map.get(args[j].getClass().getName())==null){
                                Field[] fields=args[j].getClass().getDeclaredFields();
                                for(int k=0;k<fields.length;k++){
                                    if(value.equals(fields[k].getName())){
                                        fields[k].setAccessible(true);
                                        try {
                                            key+=CONNECT+fields[k].get(args[j]);
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }else{
                                key+=CONNECT+args[j];
                            }
                            break;
                        }
                    }
                }else{
                    for(int j=0;j<parameterNames.length;j++){
                        if(keys[i].equals(parameterNames[j])){
                            if(map.get(args[j].getClass().getName())==null){
                                Field[] fields=args[j].getClass().getDeclaredFields();
                                for(int k=0;k<fields.length;k++){
                                    if(keys[i].equals(fields[k].getName())){
                                        fields[k].setAccessible(true);
                                        try {
                                            key+=CONNECT+fields[k].get(args[j]);
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }else{
                                key+=CONNECT+args[j];
                            }
                            break;
                        }
                    }

                }
            }
        }
        System.out.println(key);
        Object result=null;
        try{
            result=joinPoint.proceed();
        }catch (Throwable e){

        }
        return result;
    }

    private static String[] getParamsByMethod(String classType,String methodName,Object[] args){
        Class<?>[] classes = new Class[args.length];
        for (int k = 0; k < args.length; k++) {
            if (!args[k].getClass().isPrimitive()) {
                // 获取的是封装类型而不是基础类型
                String result = args[k].getClass().getName();
                Class s = map.get(result);
                classes[k] = s == null ? args[k].getClass() : s;
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
        return pnd.getParameterNames(method);
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
