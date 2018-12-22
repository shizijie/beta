package com.shizijie.beta.plugins;


import com.shizijie.beta.annotation.Save;
import com.shizijie.beta.bean.user.AuthContext;
import com.shizijie.beta.utils.reflect.ReflectUtils;
import lombok.Data;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author shizijie
 * @version 2018-12-15 下午6:50
 */
@Intercepts(value = {
        @Signature(type = StatementHandler.class,method = "prepare",args = {Connection.class,Integer.class})
})
public class SaveHelper implements Interceptor {
    private ColumnBean columnBean;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if(invocation.getTarget() instanceof StatementHandler){
            RoutingStatementHandler handler= (RoutingStatementHandler) invocation.getTarget();
            StatementHandler delegate= (StatementHandler) ReflectUtils.getFieldValue(handler,"delegate");
            MappedStatement mappedStatement= (MappedStatement) ReflectUtils.getFieldValue(delegate,"mappedStatement");
            String type=mappedStatement.getSqlCommandType().name();
            if("insert".equalsIgnoreCase(type)||"update".equalsIgnoreCase(type)){
                BoundSql boundSql=delegate.getBoundSql();
                int index=mappedStatement.getId().lastIndexOf(".");
                Class<?> classType=Class.forName(mappedStatement.getId().substring(0,index));
                String methodName=mappedStatement.getId().substring(index+1);
                for(Method method:classType.getDeclaredMethods()){
                    if(method.isAnnotationPresent(Save.class)&&method.getName().equals(methodName)){
                        Save save=method.getAnnotation(Save.class);
                        String username=getUserInfo(save.value());
                        String newSql=SaveHelperUtils.getNewSql(boundSql.getSql(),username,type,columnBean);
                        ReflectUtils.serFieldValue(boundSql,"sql",newSql);
                    }
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {
        columnBean=new ColumnBean();
        columnBean.setCreatedBy(properties.getProperty("createdBy","created_by"));
        columnBean.setCreatedDate(properties.getProperty("createdDate","created_date"));
        columnBean.setUpdatedBy(properties.getProperty("updatedBy","updated_by"));
        columnBean.setUpdatedDate(properties.getProperty("updatedDate","updated_date"));
    }

    public static String getUserInfo(String user){
        switch (user){
            case "system":
                return "system";
            case "user":
                break;
            default:
        }
        return AuthContext.getUserId();
    }
    @Data
    class ColumnBean{
        private String createdBy;
        private String createdDate;
        private String updatedBy;
        private String updatedDate;
    }
}
