package com.shizijie.beta.aspect;

import com.shizijie.beta.annotation.Lock;
import com.shizijie.beta.model.ResultBean;
import com.shizijie.beta.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RedisLockAspect {
    @Autowired
    RedisService redisService;

    private static String SPLIT=".";

    private static String CONNECT="_";


    @Pointcut("@annotation(com.shizijie.beta.annotation.Lock)")
    public void pointcut(){}

    @Around("pointcut()&&@annotation(lock)")
    public Object lock(ProceedingJoinPoint joinPoint, Lock lock) throws Throwable {
        String classType = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String key=methodName;
        if(lock.key().length>0){
            String[] keys=lock.key();
            Object[] args=joinPoint.getArgs();
            String[] paramsName=getParamsByMethod(classType,methodName,args);
            if(paramsName!=null&&paramsName.length>0){
                for(String str:keys){
                    if(StringUtils.hasText(str)){
                        String connect=getValueByKey(str,paramsName,args);
                        if(StringUtils.hasText(connect)){
                            key+=connect;
                        }
                    }
                }
            }
        }
        boolean isLock;
        Object result=null;
        if(isLock=redisService.lock(key,lock.expireTime())){
            if(lock.log()){
                log.info("lock-[{}] is lock",key);
            }
            long time=System.currentTimeMillis();
            try{
                result=joinPoint.proceed();
            }catch (Throwable e){
                throw e;
            }finally {
                if(redisService.isLock(key)){
                    redisService.unlock(key);
                }
                if(lock.log()){
                    log.info("lock-[{}] is unlock,locked time is [{}]",key,System.currentTimeMillis()-time);
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
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        try {
            Method[] methods=Class.forName(classType).getDeclaredMethods();
            for(Method method:methods){
                if(method.getName().equals(methodName)&&method.getParameterTypes().length==args.length){
                    if(checkMethodByArgs(args,method.getParameterTypes())){
                        return pnd.getParameterNames(method);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean checkMethodByArgs(Object[] args, Class<?>[] parameterTypes) {
        for(int i=0;i<args.length;i++){
            if(args[i]==null){
                continue;
            }
            if(!args[i].getClass().isPrimitive()){
                String name=args[i].getClass().getName();
                Class s=map.get(name);
                s=s==null?args[i].getClass():s;
                if(s!=parameterTypes[i]){
                    return false;
                }
            }
            if(args[i].getClass()!=parameterTypes[i]){
                return false;
            }
        }
        return true;
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
