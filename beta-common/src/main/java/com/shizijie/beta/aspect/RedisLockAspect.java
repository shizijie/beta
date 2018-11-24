package com.shizijie.beta.aspect;

import com.shizijie.beta.annotation.Lock;
import com.shizijie.beta.model.ResultBean;
import com.shizijie.beta.redis.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author shizijie
 * @version 2018-11-22 下午8:57
 */
@Aspect
@Component
@Order(1)
public class RedisLockAspect {
    @Autowired
    RedisService redisService;

    private static String SPLIT=".";

    private static String CONNECT="_";


    @Pointcut("@annotation(com.shizijie.beta.annotation.Lock)")
    public void pointcut(){


    }
    @Around("pointcut()&&@annotation(lock)")
    public Object test(ProceedingJoinPoint joinPoint, Lock lock) throws Throwable {
        String classType = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String key=methodName;
        if(lock.key().length>0){
            String[] keys=lock.key();
            Object[] args=joinPoint.getArgs();
            String[] paramsName=getParamsByMethod(classType,methodName,args);
            for(String str:keys){
                if(StringUtils.hasText(str)){
                    String connect=getValueByKey(str,paramsName,args);
                    if(StringUtils.hasText(connect)){
                        key+=connect;
                    }
                }
            }
        }
        boolean isLock;
        Object result=null;
        if(isLock=redisService.lock(key)){
            try{
                result=joinPoint.proceed();
            }catch (Throwable e){
                throw e;
            }finally {
                if(redisService.isLock(key)){
                    redisService.unlock(key);
                }
            }
        }
        if(!isLock){
            return ResultBean.fail500();
        }
        return result;
    }

    private String getValueByKey(String str, String[] paramsName, Object[] args) {
        int index=str.indexOf(SPLIT);
        List<String> listParams= Arrays.asList(paramsName);
        if(index==-1){
            int indexNo=listParams.indexOf(str);
            if(indexNo>-1&&args[indexNo].getClass().getClassLoader()==null){
                if(args[indexNo]!=null){
                    return CONNECT+args[indexNo];
                }
            }
        }else{
            String name=str.substring(0,index);
            String value=str.substring(index+1);
            int indexNo=listParams.indexOf(name);
            if(indexNo>-1&&args[indexNo].getClass().getClassLoader()!=null){
                Field[] fields=args[indexNo].getClass().getDeclaredFields();
                for(int i=0;i<fields.length;i++){
                    if(value.equals(fields[i].getName())){
                        fields[i].setAccessible(true);
                        try {
                            if(fields[i].get(args[indexNo])!=null){
                                return CONNECT+fields[i].get(args[indexNo]);
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        return null;
    }

    private String[] getParamsByMethod(String classType,String methodName,Object[] args){
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
