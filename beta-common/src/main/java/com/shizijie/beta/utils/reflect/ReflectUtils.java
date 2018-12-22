package com.shizijie.beta.utils.reflect;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

/**
 * @author shizijie
 * @version 2018-12-15 下午7:00
 */
public class ReflectUtils {
    /**
     * 获取指定对象里面的指定属性对象
     * @param object
     * @param fieldName
     * @return
     */
    public static Field getField(Object object,String fieldName){
        for(Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()){
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                //Field 不在当前类定义, 继续向上转型
            }
        }
        return null;
    }

    /**
     * 获取指定对象的指定属性值
     * @param obj
     * @param fieldName
     * @return
     */
    @SneakyThrows
    public static Object getFieldValue(Object obj,String fieldName){
        Object result=null;
        Field field=getField(obj,fieldName);
        if(field!=null){
            field.setAccessible(true);
            result=field.get(obj);
        }
        return result;
    }

    /**
     * 设置指定对象的指定属性值
     * @param object
     * @param fieldName
     * @param fieldValue
     */
    @SneakyThrows
    public static void serFieldValue(Object object,String fieldName,Object fieldValue){
        Field field=getField(object,fieldName);
        if(field!=null){
            field.setAccessible(true);
            field.set(object,fieldValue);
        }
    }
}
