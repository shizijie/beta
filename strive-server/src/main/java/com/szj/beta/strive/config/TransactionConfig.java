package com.szj.beta.strive.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shizijie
 * @version 2019-07-28 下午10:56
 */
@Aspect
@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    private final int TX_TIMEOUT=60*60*100;

    private final String[] requiredArr=new String[]{"add*","save*","insert*","update*","delete*","remove*","task*"};

    private final String[] readOnlyArr=new String[]{"get*","select*","query*","is*","find*","list*","load*","check*","show*","count*"};

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Bean
    public TransactionInterceptor txAdvice(){
        NameMatchTransactionAttributeSource source=new NameMatchTransactionAttributeSource();
        //只读事务
        final RuleBasedTransactionAttribute readOnlyTx=new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        //当前存在事务就使用当前事务，不存在则创建一个
        final RuleBasedTransactionAttribute requiredTx=new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requiredTx.setTimeout(TX_TIMEOUT);
        Map<String,TransactionAttribute> txMap=new HashMap<>();
        Arrays.asList(readOnlyArr).stream().forEach(a->{
            txMap.put(a,readOnlyTx);
        });
        Arrays.asList(requiredArr).stream().forEach(a->{
            txMap.put(a,requiredTx);
        });
        source.setNameMap(txMap);
        return new TransactionInterceptor(platformTransactionManager,source);
    }
}
